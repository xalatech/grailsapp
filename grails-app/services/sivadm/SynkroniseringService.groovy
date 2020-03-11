package sivadm


import util.HttpRequestUtil
import util.StringUtil
import util.TimeUtil
import siv.search.MeldingSok
import siv.type.MeldingType
import siv.type.SkjemaStatus
import siv.util.xml.RestResponseData

// import grails.core.ConfigurationHolder

class SynkroniseringService {
	
	boolean transactional = true
	def xmlService
	def brukerService
	def blaiseOppdateringService

	def grailsApplication
	
	def sjekkMeldingUt() {

		def syncActivated = grailsApplication.config.getProperty("sync.active")
		def blaiseUrl = grailsApplication.config.getProperty("sync.blaise.url")
		
		if(syncActivated == false) {
			return
		}
		
		def meldinger = findAllMeldingUt()
		
		meldinger.each {
			
			MeldingUt meldingUt = it
			
			registerMeldingHandlingStarted(meldingUt)
			
			def restPathUrl
			
			if( MeldingType.STATUS == meldingUt.meldingType ) {
				restPathUrl = "/Service.svc/oppdaterStatus"
			}
			else if( MeldingType.ADRESSE == meldingUt.meldingType ) {
				restPathUrl = "/Service.svc/oppdaterAdresse"
			}
			else if( MeldingType.TELEFON == meldingUt.meldingType ) {
				restPathUrl = "/Service.svc/oppdaterTelefon"
			}
			else if( MeldingType.KOMMENTAR_TIL_INTERVJUER == meldingUt.meldingType) {
				restPathUrl = "/Service.svc/oppdaterKommentarTilIntervjuer"
			}
			else if( MeldingType.PERSON == meldingUt.meldingType) {
				restPathUrl = "/Service.svc/oppdaterPerson"
			}
			else if( MeldingType.AKTIVER == meldingUt.meldingType) {
				restPathUrl = "/Service.svc/aktiverIntervjuobjekt"
			}
			else if( MeldingType.DEAKTIVER == meldingUt.meldingType) {
				restPathUrl = "/Service.svc/deaktiverIntervjuobjekt"
			}
			else if( MeldingType.CAPI == meldingUt.meldingType) {
				restPathUrl = "/Service.svc/settCapi"
			}
			
			def melding = meldingUt.melding
			
			RestResponseData restResponse = sendRestMessage(melding, blaiseUrl + restPathUrl )
			
			boolean sendtOk = restResponse.suksess
			
			registerMeldingSent(meldingUt, sendtOk, restResponse.feilType, restResponse.melding)
		}
	}
	
	/**
	 * Sorger for at nodvendige meldinger blir lagt inn i MeldingUt tabellen for
	 * en synkronisering mot Blaise og eventuelle andre systemer.
	 * 
	 * Inneholder noe logikk knyttet til status og aktivering som er bestemt av
	 * intervjuseksjonen. Se egne dokumenter om dette.
	 *  
	 * @param ioId id'en til IntervjuObjektet endringen gjelder
	 * @param erMassevalg forteller om endringen er gjort via endring av status for mange IO samtidig,
	 * og i så fall skal ikke person og kommentar synkroniseres til blaise.
	 */
	public void synkroniserIntervjuObjektEndring(Long ioId, boolean erMassevalg) {
		def statusXml = xmlService.getStatusXml(ioId) 
		def aktiverXml = xmlService.getAktiverXml(ioId)
		def deAktiverXml = xmlService.getDeaktiverXml(ioId)
				
		IntervjuObjekt io = IntervjuObjekt.get( ioId )
		
		SkjemaStatus skjemaStatus = io.katSkjemaStatus
		String skjemaKortNavn = io.periode.skjema.skjemaKortNavn
		String bruker = brukerService.getCurrentUserName()
		
		if( skjemaStatus == SkjemaStatus.Utsendt_CATI || skjemaStatus == SkjemaStatus.Utsendt_CATI_WEB) {
			
			if( io.intervjuStatus ) {
				// Send status melding
				insertIntoMeldingUt (MeldingType.STATUS, ioId, io.intervjuObjektNummer, skjemaKortNavn, statusXml, bruker)
			}
			
			// Send aktiver melding
			insertIntoMeldingUt (MeldingType.AKTIVER, ioId, io.intervjuObjektNummer, skjemaKortNavn, aktiverXml, bruker)
		}
		else if ( skjemaStatus == SkjemaStatus.Ferdig )
		{
			// Send status
			if( io.intervjuStatus ) {
				insertIntoMeldingUt (MeldingType.STATUS, ioId, io.intervjuObjektNummer, skjemaKortNavn, statusXml, bruker)
			}
			
			// Send deaktiver
			insertIntoMeldingUt (MeldingType.DEAKTIVER, ioId, io.intervjuObjektNummer, skjemaKortNavn, deAktiverXml, bruker)
		}
		else if ( skjemaStatus == SkjemaStatus.Paa_vent )
		{
			// Send de-aktiver
			insertIntoMeldingUt (MeldingType.DEAKTIVER, ioId, io.intervjuObjektNummer, skjemaKortNavn, deAktiverXml, bruker)
		}
		
		def kommentarTilIntervjuerXml = xmlService.getKommentarTilIntervjuerXml(ioId)
		
		// Send kommentarTilIntervjuer
		insertIntoMeldingUt (MeldingType.KOMMENTAR_TIL_INTERVJUER, ioId, io.intervjuObjektNummer, skjemaKortNavn, kommentarTilIntervjuerXml, bruker)
		
		if(!erMassevalg) {
			// Hvis dette ikke er massevalg skal person og kommentar info også synkes til blaise
			def personXml = xmlService.getPersonXml(ioId)
			
			// Send person
			insertIntoMeldingUt (MeldingType.PERSON, ioId, io.intervjuObjektNummer, skjemaKortNavn, personXml, bruker)
		}	
	}

	/*
	Synkroniserer endringer i skjemastatus til Blaise.
	 */
	def synkroniserIntervjuObjektEndringSkjemastatus (IntervjuObjekt intervjuObjekt) {
		def nySkjemastatus = intervjuObjekt.katSkjemaStatus

		switch (nySkjemastatus) {
			case SkjemaStatus.Utsendt_CATI:
				blaiseOppdateringService.activate(intervjuObjekt)
				break
			case SkjemaStatus.Utsendt_CATI_WEB:
				blaiseOppdateringService.activate(intervjuObjekt)
				break
			case SkjemaStatus.Ferdig:
				blaiseOppdateringService.complete(intervjuObjekt)
				break
			case SkjemaStatus.Utsendt_WEB:
				blaiseOppdateringService.onlyWeb(intervjuObjekt)
				break
			default:
				break
		}
	}

	void sjekkUtEndringer() {
		blaiseOppdateringService.sjekkEndringer()
	}

	void hentFullForingsStatus() {
		blaiseOppdateringService.hentFullForingsStatus()
	}



	void synkroniserTelefonEndringV5(IntervjuObjekt intervjuObjekt){
		blaiseOppdateringService.oppdaterTelefoner(intervjuObjekt)
	}

	void synkroniserAdresseEndringV5(IntervjuObjekt intervjuObjekt) {
		blaiseOppdateringService.oppdaterAdresse(intervjuObjekt)
	}

	void synkroniserIntervjuObjektEndringV5(IntervjuObjekt intervjuObjekt) {
		blaiseOppdateringService.oppdaterIntervjuObjekt(intervjuObjekt)
	}

		/**
	 * Sorger for at Tildelt intervjueobjekt er synkronisert med Blaise
	 * 'IO skal sendes til intervjuer for CAPI. "X" i styring.daybatch. Intervjuerinitialer skrives til offlinecapi.towhom.
	 *
	 * @param intervjuObjekt
	 */
	void synkroniserIntervjuObjektTildelCAPI(IntervjuObjekt intervjuObjekt, String initialer) {
		blaiseOppdateringService.oppdaterIntervjuObjektTildelCAPI(intervjuObjekt, initialer)
	}

	/**
	 * Sorger for at nodvendig adressemelding blir lagt inn i MeldingUt tabellen for
	 * en synkronisering mot Blaise og eventuelle andre systemer.
	 * 
	 * @param ioId
	 */
	public void synkroniserAdresseEndring( Long ioId ) {
		
		def adresseXml = xmlService.getAdresseXml(ioId)
		
		IntervjuObjekt io = IntervjuObjekt.get( ioId )
		
		SkjemaStatus skjemaStatus = io.katSkjemaStatus
		String skjemaKortNavn = io.periode.skjema.skjemaKortNavn
		String bruker = brukerService.getCurrentUserName()
		
		insertIntoMeldingUt (MeldingType.ADRESSE, ioId, io.intervjuObjektNummer, skjemaKortNavn, adresseXml, bruker)
	}

	
	/**
	* Sorger for at nodvendig telefonmelding blir lagt inn i MeldingUt tabellen for
	* en synkronisering mot Blaise og eventuelle andre systemer.
	*
	* @param ioId
	*/
	public void synkroniserTelefonEndring( Long ioId )
	{
		String telefonXml = xmlService.getTelefonXml(ioId)
		
		IntervjuObjekt io = IntervjuObjekt.get( ioId )
		
		SkjemaStatus skjemaStatus = io.katSkjemaStatus
		String skjemaKortNavn = io.periode.skjema.skjemaKortNavn
		String bruker = brukerService.getCurrentUserName()
		
		insertIntoMeldingUt (MeldingType.TELEFON, ioId, io.intervjuObjektNummer, skjemaKortNavn, telefonXml, bruker)
	}
	
	/**
	* Sorger for at nodvendig telefonmelding blir lagt inn i MeldingUt tabellen for
	* en synkronisering mot Blaise og eventuelle andre systemer.
	*
	* @param ioId
	*/
	public void synkroniserCapi(Long ioId)
	{
		String capiXml = xmlService.getCapiXml(ioId)
		
		IntervjuObjekt io = IntervjuObjekt.get(ioId)
		
		String skjemaKortNavn = io.periode.skjema.skjemaKortNavn
		String bruker = brukerService.getCurrentUserName()
		
		insertIntoMeldingUt (MeldingType.CAPI, ioId, io.intervjuObjektNummer, skjemaKortNavn, capiXml, bruker)
	}
	
	/**
	 * Returnerer en liste med meldinger fra MeldingUt tabell basert paa visse
	 * kriterier.
	 * 
	 * @return en liste med meldinger fra MeldingUt tabell.
	 */
	public List<MeldingUt> findAllMeldingUt() {
		def c = MeldingUt.createCriteria() 
		
		def list = c {
			eq('antallForsok', 0)
			eq('sendtOk', false)
			eq('deaktivert', false)
		}
		
		return list
	}
	
	
	/**
	 * Setter inn en rad i MeldingUt tabellen.
	 * 
	 * @param type
	 * @param intervjuObjektId
	 * @param intervjuObjektNummer
	 * @param skjemaKortNavn
	 * @param melding
	 * @param sendtAv
	 */
	private void insertIntoMeldingUt( MeldingType type, Long intervjuObjektId, String intervjuObjektNummer, String skjemaKortNavn, String melding, String sendtAv ) {
		
		if(melding == null) {
			// ikke send noe hvis det ikke er noe aa sende
			return
		}
		
		// se om det finnes meldinger som ikke er sendt av samme type
		// samme type = samme io & samme meldingstype
		// hvis det finnes maa vi deaktivere den
				
		def meldingerSomSkalDeaktiveres = MeldingUt.createCriteria().list {
			eq("intervjuObjektId", intervjuObjektId)
			eq("meldingType", type)
			eq("deaktivert", false)
			ne("sendtOk", Boolean.TRUE)
		}
		
		meldingerSomSkalDeaktiveres.each {  
			MeldingUt meldingDeaktiv = it
			meldingDeaktiv.deaktivert = true
			meldingDeaktiv.save(flush: true)
		}
		
		def meldingUt = new MeldingUt()
		
		meldingUt.sendtAv = sendtAv
		meldingUt.antallForsok = 0
		meldingUt.deaktivert = false
		meldingUt.intervjuObjektId = intervjuObjektId
		meldingUt.intervjuObjektNummer = intervjuObjektNummer
		meldingUt.melding = melding
		meldingUt.meldingType = type
		meldingUt.tidRegistrert = new Date()
		meldingUt.skjemaKortNavn = skjemaKortNavn
		meldingUt.sendtOk = false
		
		meldingUt.save()
	}
	
	/**
	* Sends a REST request with specified xml data, and URL.
	*/
	RestResponseData sendRestMessage( String xmlData, String url ) {
	   
	   try
	   {
		   URL endPoint = new URL(url)
					  
		   String responseText = HttpRequestUtil.postStringData(xmlData, endPoint)
		   def xmlResponse = new XmlSlurper().parseText(responseText)
		   
		   def suksess = xmlResponse.suksess.toBoolean()
		   def feilType = xmlResponse.feilType.toString()
		   def melding = xmlResponse.melding.toString()
		   
		   RestResponseData restResponse = new RestResponseData(suksess, feilType, melding)
		   
		   return restResponse
	   }
	   catch (Exception e) {
		   return new RestResponseData(false, e.getClass().toString(), e.getMessage())
	   }
	}
	
	public void registerMeldingHandlingStarted( MeldingUt meldingUt ) {
		meldingUt.antallForsok ++
		meldingUt.save()
	}
	
	public void registerMeldingSent( MeldingUt meldingUt, boolean sendtOk, String feilType, String responseText) {
		meldingUt.sendtOk = sendtOk
		meldingUt.tidSendt = new Date()
		meldingUt.feilType = StringUtil.get255FirstChars(feilType)
		meldingUt.responseText = StringUtil.get255FirstChars(responseText)
		meldingUt.save()
	}
	
	public void deleteAllSuccessfulMeldingUt() {
		def list = MeldingUt.findAllBySendtOk(new Boolean(true))
		
		list.each {
			it.delete()
		}
	}	
	
	def ryddMeldingUt() {
		
		def antallDager = grailsApplication.config.getProperty("behold.meldinger.ut.antall.dager")
		log.info("Kjører ryddMeldingUt, sletter meldinger eldre enn " + antallDager + " dager")
		
		Calendar cal = Calendar.getInstance()
		cal.add(Calendar.DAY_OF_MONTH, (-1 * antallDager))
		
		slettMeldingerUtEldreEnnDato(cal.getTime())
	}
	
	public int slettMeldingerUtEldreEnnDato(Date dato) {
		def meldingerUtTilSlettingListe = MeldingUt.findAllByTidRegistrertLessThan(dato)
		int cnt = 0
		meldingerUtTilSlettingListe.each {
			try {
				it.delete(flush: true)
				cnt++
			}
			catch(Exception ex) {
				log.error("Kunne ikke slette MeldingUt med id " + it.id + ", " + ex.getMesssage())
			}
		}
		
		log.info("Har slettet " + cnt + " MeldingUt, som var eldre enn " + dato)
		
		return cnt
	}
	
	public List sokMeldingUtAlle(MeldingSok sok) {
		return this.sokMeldingUt(sok, new HashMap() )
	}
	
	public List sokMeldingUt(MeldingSok sok, Map params ) {
		
		int max = params.max
		int first = params.offset? Integer.parseInt(params.offset) : 0
		
		def meldingUtList = sokKriteria(sok, first, max, false, params.sort, params.order)
		
		return meldingUtList
		
	}
	
	
	public int tellSokMeldingUt(MeldingSok sok) {
		List mldCount = sokKriteria(sok, null, null, true, null, null)
		return mldCount.get(0)
	}

	def sokKriteria = {sok, first, max, countAll, sortParam, orderParam ->
		def c = MeldingUt.createCriteria()
		
		def meldingUtIds = c {
			
			// sorter etter dato lagt inn, eller det som brukeren klikker på i tabellheader
			if( countAll == false) {
				if( sortParam) {
					order( sortParam, orderParam)
				}
				else {
					order( "tidRegistrert", "desc")
				}
			}
			
			if(first )	{
				firstResult(first)
			}
			
			if(max) {
				maxResults(max)
			}
			
			if(countAll ) {
				projections { countDistinct("id") }
			}
						
			if(sok.ioNr) {
				eq('intervjuObjektNummer', sok.ioNr)
			}
			
			if(sok.ioId) {
				eq('intervjuObjektId', sok.ioId)
			}
			
			if(sok.fra){
				gt("tidRegistrert", TimeUtil.getStartOfDay(sok.fra))
			}
			if(sok.til){
				lt("tidRegistrert", TimeUtil.getStartOfNextDay(sok.til))
			}
			
			if(sok.status ) { 
				if(sok.status.equals("Alle")) {
					// no criterias
				}
				else if(sok.status.equals("Sendt ok")) {
					eq("sendtOk", true)
				}
				else if(sok.status.equals("Feilede")) {
					eq("sendtOk", false)
					ne("deaktivert", Boolean.TRUE)
					gt("antallForsok", 0)
				}
				else if(sok.status.equals("De-aktivert")) {
					eq("deaktivert", Boolean.TRUE)
				}
				else if(sok.status.equals("Skal sendes")) {
					eq("deaktivert", Boolean.FALSE)
					eq("antallForsok", 0)
					eq("sendtOk", false)
				}
			}
			
			
		}
		
		return meldingUtIds
	}


}