package sivadm

import java.util.ArrayList
import java.util.Date
import java.util.List
import java.util.Map
import util.DateUtil
import siv.search.OppdragSok
import siv.type.IntervjuType
import siv.type.IntervjuerArbeidsType
import siv.type.IntervjuerStatus
import siv.type.SkjemaStatus
import siv.type.TildelingsType

import sivadm.IntervjuObjektSearch

class OppdragService {

	def sessionFactory
	
	def intervjuObjektService
	def synkroniseringService
	
	public List sendOppdragTilSynkForSkjema(Skjema skjema) {
		def oppdragListe = []
		
		if(!skjema) {
			log.error("Kan ikke sende oppdrag til synkronisering når skjema er null")
			return oppdragListe	
		}
		
		def ikkeSynketListe = Oppdrag.createCriteria().list() {
			ilike("skjemaKortNavn", skjema.skjemaKortNavn)
			eq("klarTilSynk", false)
		}
		
		ikkeSynketListe.each {
			it.klarTilSynk = true
			if(it.save(flush: true)) {
				oppdragListe << it
			}
			else {
				log.error("Kunne ikke sette klarTilSynk = true for oppdrag med id " + it.id + ", noe gikk galt ved lagring")	
			}	
		}
		
		return oppdragListe
	}
	
	public List sokOppdragAlle(OppdragSok sok) {
		return this.sokOppdrag(sok, new HashMap() )
	}
	
	public List sokOppdrag(OppdragSok sok, Map pagination ) {
		
		int max = pagination.max
		int first = pagination.offset? Integer.parseInt(pagination.offset) : 0
		
		def oppdragIds = sokKriteria(sok, first, max, false)
		
		if(oppdragIds.size() > 0) {
			return Oppdrag.getAll(oppdragIds)
		}
		else {
			return new ArrayList()
		}
	}
	
	
	public int tellSokOppdrag(OppdragSok sok) {
		List ioCount = sokKriteria(sok, null, null, true)
		return ioCount.get(0)
	}

	def sokKriteria = {sok, first, max, countAll ->
		def c = Oppdrag.createCriteria()
		
		def oppdragIds = c {
			
			if(first )	{
				firstResult(first)
			}
			
			if(max) {
				maxResults(max)
			}
			
			if(countAll ) {
				projections { countDistinct("id") }
			}
			else {
				projections { distinct("id") }
			}
			
			if(sok?.skjemaKortNavn) {
				ilike("skjemaKortNavn", sok.skjemaKortNavn)	
			}
			
			if(sok?.ioNr) {
				eq("intervjuObjektNummer", sok.ioNr)
			}
			
			if(sok?.ioId) {
				intervjuObjekt {
					eq("id", sok.ioId)
				}
			}
			
			if(sok?.intervjuerId) {
				intervjuer {
					eq("id", sok.intervjuerId)
				}
				
			}
			
			if(sok?.klarTilSynk && sok.klarTilSynk != 'alle') {
				if(sok.klarTilSynk == 'klar') {
					eq("klarTilSynk", true)
				}
				else {
					eq("klarTilSynk", false)
				}
			}
		}
		
		return oppdragIds
	}
	
	public Oppdrag findLatestOppdragByIntervjuObjekt(IntervjuObjekt intervjuObjekt) {
		def c = Oppdrag.createCriteria()

		def oppdragList = c {

			eq("intervjuObjekt", intervjuObjekt)

			order( "generertDato", "desc")
		}

		return oppdragList?.size() > 0 ? oppdragList.get(0) : null
	}

	
	public void tildelIntervjuer(Intervjuer intervjuer, IntervjuObjekt intervjuObjekt, Date sisteFrist, String initialer) {
		if(!intervjuer || !intervjuObjekt) {
			return null
		}
		
		// Lag nytt oppdrag
		Oppdrag nyttOppdrag = new Oppdrag()

		nyttOppdrag.operatorId = initialer
		nyttOppdrag.generertAv = initialer
		nyttOppdrag.selvplukk = false
		nyttOppdrag.generertDato = DateUtil.now()
		nyttOppdrag.intervjuer = intervjuer
		nyttOppdrag.intervjuerInitialer = intervjuer.initialer
		nyttOppdrag.intervjuObjekt = intervjuObjekt
		nyttOppdrag.intervjuType = IntervjuType.BESOK
		nyttOppdrag.intervjuStatus = intervjuObjekt.intervjuStatus
		nyttOppdrag.klarTilSynk = true

		// Finn eventuelle oppdrag tilhørende IO-et som enna ikke er synket
		// til intervjuer pc (overfortLokaltTidspunkt er null), disse skal slettes
		def oppdragSkalSlettesListe = Oppdrag.findAllByIntervjuObjektAndOverfortLokaltTidspunktIsNull(intervjuObjekt)
		
		oppdragSkalSlettesListe.each { Oppdrag o ->
			try {
				o.delete(flush: true)
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				log.error("Kunne ikke slette oppdrag " + e)
			}
		}
		
		// se om det finnes gamle tildelinger / oppdrag
		Oppdrag latestOppdrag = this.findLatestOppdragByIntervjuObjekt(intervjuObjekt)

		// sett gyldighetsdato
		Date calculatedGyldighetsDato = extractDefaultGyldighetsdato(intervjuObjekt);

		if(sisteFrist && isSisteFristAfterOriginalGyldighetsDato(sisteFrist, calculatedGyldighetsDato) && !DateUtil.isExpired(sisteFrist) ) {
			nyttOppdrag.gyldighetsDato = sisteFrist
		}
		else if(calculatedGyldighetsDato) {
			nyttOppdrag.gyldighetsDato = calculatedGyldighetsDato
		}
		else if(sisteFrist) {
			nyttOppdrag.gyldighetsDato = sisteFrist
		}
				
		// Sett klokkeslett til 23:59:59
		Calendar cal = Calendar.getInstance()
		if(!nyttOppdrag.gyldighetsDato) {
			cal.add(Calendar.DAY_OF_YEAR, 14)	
		}
		else {
			cal.setTime nyttOppdrag.gyldighetsDato
		}
		cal.set Calendar.HOUR_OF_DAY, 23
		cal.set Calendar.MINUTE, 59
		cal.set Calendar.SECOND, 59
		nyttOppdrag.gyldighetsDato = cal.getTime()
		
		def tildeltHistorikk = new TildelHist(dato: new Date(), intervjuer: intervjuer)

		// Hvis allerede tildelt maa det ryddes opp i gammel tildeling
		if(latestOppdrag != null ) {
			nyttOppdrag.tildelingsType = TildelingsType.OVERFORING

			// forrige oppdrag ikke gyldig lenger
			latestOppdrag.gyldighetsDato = DateUtil.now()

			latestOppdrag.intervjuStatus = intervjuObjekt.intervjuStatus

			// har intervjuer allerede lastet ned oppdraget til lokal PC?
			if (null != latestOppdrag.overfortLokaltTidspunkt ) {
				latestOppdrag.merketSlettHosIntervjuer = true
			} else {
				//intervjuer har ikke lastet ned oppdraget
				latestOppdrag.slettetHosIntervjuer = true
			}
		}
		else {
			nyttOppdrag.tildelingsType = TildelingsType.ORDINAR
		}
		
		if(intervjuObjekt.tildeltHistorikk?.size() > 3) {
			// Fjern den eldste av TildelHist
			TildelHist th = intervjuObjekt.tildeltHistorikk.sort{it.dato}[0]
			intervjuObjekt.removeFromTildeltHistorikk(th)
		}
		
		intervjuObjekt.addToTildeltHistorikk(tildeltHistorikk)

		nyttOppdrag.skjemaKortNavn = intervjuObjekt.periode?.skjema?.skjemaKortNavn
		nyttOppdrag.periodeNummer = intervjuObjekt.periode?.periodeNummer
		nyttOppdrag.intervjuObjektNummer = intervjuObjekt.intervjuObjektNummer

		nyttOppdrag.save(failOnError: true)

		synkroniseringService.synkroniserCapi(intervjuObjekt.id)

		def statusHistorikk = new StatHist(skjemaStatus: SkjemaStatus.Utsendt_CAPI, intervjuStatus: intervjuObjekt.intervjuStatus, redigertAv: initialer, dato: new Date())
		intervjuObjekt.addToStatusHistorikk(statusHistorikk)
		
		intervjuObjekt.katSkjemaStatus = SkjemaStatus.Utsendt_CAPI
		intervjuObjekt.intervjuer = intervjuer.initialer

		intervjuObjekt.save(failOnError: true, flush: true)
	}

	private Date extractDefaultGyldighetsdato(IntervjuObjekt intervjuObjekt) {
		Date sluttDato

		sluttDato = intervjuObjekt?.periode?.sluttDato

		if(sluttDato == null) {
			sluttDato = intervjuObjekt?.periode?.planlagtSluttDato
		}

		return sluttDato
	}

	private boolean isSisteFristAfterOriginalGyldighetsDato(Date sisteFrist, Date gyldighetsDato) {
		if(sisteFrist == null || gyldighetsDato == null) {
			return false;
		} else {
			return DateUtil.differ(gyldighetsDato, sisteFrist) > 0;
		}
	}

	public Intervjuer findNaboIntervjuer(IntervjuObjekt intervjuObjekt) {
		return this.findNaboIntervjuer (intervjuObjekt, null)
	}

	public Intervjuer findNaboIntervjuer(IntervjuObjekt intervjuObjekt, List intervjuerList) {
		Adresse adresse = intervjuObjektService.getGyldigBesokAdresse(intervjuObjekt)

		int intervjuObjektPostNummer =  Integer.parseInt(adresse.postNummer)
		def intervjuere
		
		if(intervjuerList != null && intervjuerList.size() == 0) {
			// Når listen er tom og ikke NULL er det automatisk tildeling som
			// kaller metoden og da er listen med intervjuere for tildeling "oppbrukt"
			return null	
		}
		
		intervjuere = Intervjuer.createCriteria().list() {
			order("postNummer", "asc")

			if(intervjuerList != null) {
				ArrayList intervjuerIdList = new ArrayList()

				intervjuerList.each {
					intervjuerIdList.add(it.id)
				}

				'in'("id", intervjuerIdList)
			}
			else {
				'in'("arbeidsType", [IntervjuerArbeidsType.BEGGE, IntervjuerArbeidsType.BESOK])
				eq("status", IntervjuerStatus.AKTIV)
			}
		}

		int diff = 100000

		def naboIntervjuer
		
		intervjuere.each() {
			Intervjuer intervjuer = it

			int intervjuerPostNummer = Integer.parseInt(intervjuer.postNummer)

			int currentDiff = Math.abs(intervjuObjektPostNummer - intervjuerPostNummer)

			if(currentDiff < diff) {
				diff = currentDiff
				naboIntervjuer = intervjuer
			}

			//log.info(intervjuObjektPostNummer + " intervjuer: " + intervjuerPostNummer)
		}

		return naboIntervjuer
	}


	public List<Oppdrag> findOppdragForIntervjuer(Intervjuer intervjuer) {
		// gyldig oppdragsdato
		// - sjekk siste frist gitt intervjuer
		// - sjekk skjema oppstart- og sluttdato

		// - ikke returner oppdrag som er ferdig utfort
	}

	public void notifyOppdragAboutIoChange(IntervjuObjekt intervjuObjekt) {
		def oppdragList = Oppdrag.findAllByIntervjuObjekt( intervjuObjekt )

		oppdragList.each {
			Oppdrag oppdrag = it
			oppdrag.setEndringIntervjuObjekt "Y"
			oppdrag.save()
		}
	}

	/**
	 * Metode for aa tildele intervjuere automatisk til intervjuobjekter
	 * basert paa postnummer. Vi prover aa finne intervjuere som bor
	 * i naerheten av intervjubjektene.
	 * 
	 * @param intervjuObjekter
	 * @param intervjuere
	 */
	public void tildelIntervjuereAutomatisk(List intervjuObjekter, List intervjuere, Date sisteFrist, Integer maxIoPrIntervjuer, boolean taFamilieHensyn, String initialer) {
		def maxAntall = true
		
		if(!maxIoPrIntervjuer || maxIoPrIntervjuer == 0) {
			maxAntall = false
		}
		
		Intervjuer forrigeIntervjuer = null
		String forrigeFamilieNummer = null
		
		Map intervjuerIoCnt = [:]
		
		if( taFamilieHensyn ) {
			intervjuObjekter.sort { it.familienummer}
		}
		
		intervjuObjekter.each{
			IntervjuObjekt intervjuObjekt = it

			Adresse adresse = intervjuObjektService.getGyldigBesokAdresse(intervjuObjekt)
			int intervjuObjektPostNummer =  Integer.parseInt(adresse.postNummer)
			
			Intervjuer naboIntervjuer = null
			
			if( taFamilieHensyn ) {
				if( forrigeFamilieNummer != null && intervjuObjekt.familienummer != null && (intervjuObjekt.familienummer == forrigeFamilieNummer) ) {
					naboIntervjuer = forrigeIntervjuer
				}
			}
			
			// ikke familiehensyn eller forrige fam nummer er ulikt
			if( naboIntervjuer == null ) {
				// finn naermeste intervjuer
				naboIntervjuer = findNaboIntervjuer(intervjuObjekt, intervjuere)
			}			
			
			if(naboIntervjuer) {
				// Hvis naboIntervjuer er null er det ingen tilgjengelige intervjuere
				// eller at alle tilgjengelige har fått tildelt max antall IO
				if(maxAntall) {
					if(!intervjuerIoCnt[naboIntervjuer.initialer]) {
						intervjuerIoCnt[naboIntervjuer.initialer] = 1;
						
						if(1 == maxIoPrIntervjuer) {
							intervjuere.remove naboIntervjuer
						}
					}
					else {
						int i = intervjuerIoCnt[naboIntervjuer.initialer]
						i++
						if(i == maxIoPrIntervjuer) {
							intervjuere.remove naboIntervjuer
						}
						else {
							intervjuerIoCnt[naboIntervjuer.initialer] = i;
						}
					}
				}
				
				// tildel 
				tildelIntervjuer(naboIntervjuer, intervjuObjekt, sisteFrist, initialer)
				
				// setter disse i forbindelse med familileNummer hensyn
				forrigeIntervjuer = naboIntervjuer
				forrigeFamilieNummer = intervjuObjekt.familienummer
			}			
		}
	}

	/**
	 * Sletter oppdrag for alle intervjuobjekter for det definert utvalg.
	 * 
	 * @param utvalgImport
	 */
	public void slettOppdragForIntervjuUtvalg( UtvalgImport utvalgImport ) {

		def ioIdList = new ArrayList<Long>()

		def c = Oppdrag.createCriteria()

		def oppdragListe = c {
			intervjuObjekt{
				eq("utvalgImport", utvalgImport)
			}
		}

		int n = 0
		
		oppdragListe.each {
			Oppdrag oppdrag = it
			oppdrag.delete()
			
			// for aa unngaa problemer med Hibernate og for stor cache.
			if( n == 200 ) {
				def session = sessionFactory.getCurrentSession()
				session.flush()
				session.clear()
				n = 0
			}
			
			n ++
		}
	}

}
