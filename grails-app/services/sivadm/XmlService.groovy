package sivadm

import groovy.xml.MarkupBuilder
import parser.ExtendedUtvalg
import siv.model.translator.UtvalgTranslator
import siv.type.Kjonn;

class XmlService {

	boolean transactional = true
	def skjemaService
	def intervjuObjektService
	final MALVERSJON_FOR_EKSTRA_FELTER = 3
	def xmlDeclaration = '<?xml version="1.0"?>'
	def grailsApplication

	/**
	 * Returnerer en enkel felles xml som kan brukes for de REST-kall
	 * som ikke trenger flere data enn io id.
	 * 
	 * @param ioId
	 * @return
	 */
	private String getRequestXml( Long ioId ) {

		IntervjuObjekt io = IntervjuObjekt.get(ioId)
		
		String skjemaKortNavn = io.periode.skjema.skjemaKortNavn
		Long versjon = skjemaService.findLatestSkjemaVersjonsNummer (io.periode.skjema)

		String requestXml = "<RequestData>" + getRequestXmlPart(io) + "</RequestData>"
					
		return requestXml
	}
	
	private String getRequestXmlPart( IntervjuObjekt io )
	{
		String skjemaKortNavn = io.periode.skjema.skjemaKortNavn
		Long versjon = skjemaService.findLatestSkjemaVersjonsNummer (io.periode.skjema)

		String requestPartXml = """\
					<intervjuObjektId>${io.id}</intervjuObjektId>
					<intervjuObjektNummer>${io.intervjuObjektNummer}</intervjuObjektNummer>
					<skjemaKortNavn>${skjemaKortNavn? skjemaKortNavn : ""}</skjemaKortNavn>
					<skjemaVersjon>${versjon? versjon : ""}</skjemaVersjon>
					"""

		return requestPartXml
	}

	/**
	 * Returnerer en xml for status request REST-kall.
	 * 
	 * @param ioId
	 * @return
	 */
	public String getStatusXml( Long ioId ) {

		IntervjuObjekt io = IntervjuObjekt.get( ioId )

		String statusXml = 
			"<IntervjuStatusData>" + 
			getRequestXmlPart(io) + 
			"<intervjuStatus>${io.intervjuStatus? io.intervjuStatus : ""}</intervjuStatus>" + 
			"</IntervjuStatusData>"

		return statusXml
	}

	/**
	 * Returnerer en xml for aktiver request REST-kall.
	 * @param ioId
	 * @return
	 */
	public String getAktiverXml ( Long ioId ) {
		return getRequestXml(ioId)
	}

	/**
	 * Returnerer en xml for de-aktiver request REST-kall.
	 * @param ioId
	 * @return
	 */
	public String getDeaktiverXml ( Long ioId ) {
		return getRequestXml(ioId)
	}

	/**
	* Returnerer en xml for settCapi request REST-kall.
	* @param ioId
	* @return
	*/
	public String getCapiXml(Long ioId) {
	   return getRequestXml(ioId)
	}
	
	/**
	 * Returnerer en xml for kommentar til intervjuer request REST-kall.
	 * @param ioId
	 * @return
	 */
	public String getKommentarTilIntervjuerXml ( Long ioId ) {

		IntervjuObjekt io = IntervjuObjekt.get(ioId)
		
		if( ! io.meldingTilIntervjuer ) {
			return null
		}

		String meldingTilIntervjuer = io.meldingTilIntervjuer? io.meldingTilIntervjuer : ""
		
		String filtrertMeldingTilIntervjuer = taVekkUgyldigeTegnIXml( meldingTilIntervjuer )
		
		String kommentarTilIntervjuerXml = 
			"<KommentarTilIntervjuerData>" + 
			getRequestXmlPart(io) +
			"<kommentar>${filtrertMeldingTilIntervjuer}</kommentar>" +
			"</KommentarTilIntervjuerData>"
			
		return kommentarTilIntervjuerXml
	}
	
	/**
	* Returnerer en ny String der ulovlige tegn i XML er erstattet med en SPACE.
	*
	* @param xmlValue
	*/
   private String taVekkUgyldigeTegnIXml( String xmlValue ) {
	   if( xmlValue != null ) {
		   return xmlValue.replace('&', ' ').replace('<', ' ').replace('>', ' ')
	   }
	   else {
		   return null
	   }
   }


	/**
	 * Returnerer en xml for person request REST-kall.
	 * @param ioId
	 * @return
	 */
	public String getPersonXml( Long ioId ) {

		IntervjuObjekt io = IntervjuObjekt.get( ioId )

		def kjonn = getKjonnInBlaiseFormat( io.kjonn )

		String personXmlTopp = "<PersonData>" + getRequestXmlPart(io)
		
		def alder = io.alder? io.alder : ""
		def familieNummer = io.familienummer? io.familienummer : ""
		def fodselsNummer = io.fodselsNummer? io.fodselsNummer : ""
		def kontaktPeriode = io.kontaktperiode? io.kontaktperiode : ""
		def navn = io.navn? io.navn : ""
		def personKode = io.personKode? io.personKode : ""
		def sivilstand = io.sivilstand? io.sivilstand : ""
		def epost = io.epost? io.epost : ""
		
		String personXmlBunn = """\
				<alder>${alder}</alder> 
				<familieNummer>${familieNummer}</familieNummer> 
				<fodselsNummer>${fodselsNummer}</fodselsNummer> 
				<kjonn>${kjonn? kjonn : ""}</kjonn> 
				<kontaktperiode>${kontaktPeriode}</kontaktperiode> 
				<navn>${navn}</navn> 
				<personkode>${personKode}</personkode> 
				<sivilstand>${sivilstand}</sivilstand>
				<epost>${epost}</epost>
			</PersonData>
			"""

		return personXmlTopp + personXmlBunn
	}

	
	/**
	* Returnerer en xml for telefon request REST-kall.
	* @param ioId
	* @return
	*/
	public String getTelefonXml ( Long ioId ) {

		IntervjuObjekt io = IntervjuObjekt.get(ioId)
		def malVersjon = io.periode.skjema.malVersjon

		def telefonList = io.telefoner

		def telefonListIBruk = new ArrayList<Telefon>()

		telefonList.each {
			Telefon telefon = it
			if(telefon.gjeldende == null || telefon.gjeldende ) {
				telefonListIBruk.add telefon
			}
		}

		int antallIBrukTelefoner = telefonListIBruk ? telefonListIBruk.size() : 0

		def tel1
		def tel2
		def tel3

		if( antallIBrukTelefoner > 0 ) {
			tel1 = ['telefonNummer': telefonListIBruk.get(0).telefonNummer,
					'kilde': telefonListIBruk.get(0).kilde,
					'kommentar': telefonListIBruk.get(0).kommentar]
		}
		if (antallIBrukTelefoner > 1 ) {
			tel2 = ['telefonNummer': telefonListIBruk.get(1).telefonNummer,
					'kilde': telefonListIBruk.get(1).kilde,
					'kommentar': telefonListIBruk.get(1).kommentar]
		}
		if (antallIBrukTelefoner > 2 ) {
			tel3 = ['telefonNummer': telefonListIBruk.get(2).telefonNummer,
					'kilde': telefonListIBruk.get(2).kilde,
					'kommentar': telefonListIBruk.get(2).kommentar]

		}

		String telefonXml
		if (malVersjon == MALVERSJON_FOR_EKSTRA_FELTER){
			telefonXml ="<TelefonData>" +
							getRequestXmlPart(io) +
							"<telefon1>${tel1?.telefonNummer? tel1.telefonNummer : ""}</telefon1> " +
							"<kilde1>${tel1?.kilde? tel1.kilde : ""}</kilde1> " +
							"<kommentar1>${tel1?.kommentar? tel1.kommentar : ""}</kommentar1> " +
							
							"<telefon2>${tel2?.telefonNummer? tel2.telefonNummer : ""}</telefon2> " +
							"<kilde2>${tel2?.kilde? tel2.kilde : ""}</kilde2> " +
							"<kommentar2>${tel2?.kommentar? tel2.kommentar : ""}</kommentar2> " +

							"<telefon3>${tel3?.telefonNummer? tel3.telefonNummer : ""}</telefon3> " +
							"<kilde3>${tel3?.kilde? tel3.kilde : ""}</kilde3> " +
							"<kommentar3>${tel3?.kommentar? tel3.kommentar : ""}</kommentar3> " +
						"</TelefonData>"
		}else {
			telefonXml ="<TelefonData>" +
							getRequestXmlPart(io) +
							"<telefon1>${tel1?.telefonNummer? tel1.telefonNummer : ""}</telefon1> " +
							"<telefon2>${tel2?.telefonNummer? tel2.telefonNummer : ""}</telefon2> " +
							"<telefon3>${tel3?.telefonNummer? tel3.telefonNummer : ""}</telefon3> " +
						"</TelefonData>"
		}
		return telefonXml
	}
	
	

	
	/**
	* Returnerer en xml for adresse request REST-kall.
	* @param ioId
	* @return
	*/
	public String getAdresseXml ( Long ioId ) {

		IntervjuObjekt io = IntervjuObjekt.get(ioId)

		Adresse besokAdresse = io.findGjeldendeBesokAdresse()
		Adresse postAdresse = io.findGjeldendePostAdresse()
		
		def adresse = besokAdresse?.gateAdresse? besokAdresse.gateAdresse : ""
		def boligNummer = besokAdresse?.boligNummer? besokAdresse.boligNummer : ""
		def brevAdresse = postAdresse?.gateAdresse? postAdresse.gateAdresse : ""
		def brevPostNummer = postAdresse?.postNummer? postAdresse.postNummer : ""
		def brevPostSted = postAdresse?.postSted? postAdresse.postSted : ""
		def gateGaardNummer = besokAdresse?.gateGaardNummer? besokAdresse.gateGaardNummer : ""
		def husBrukNummer = besokAdresse?.husBruksNummer? besokAdresse.husBruksNummer : ""
		def kommuneNummer = besokAdresse?.kommuneNummer? besokAdresse.kommuneNummer : ""
		def postNummer = besokAdresse?.postNummer? besokAdresse.postNummer : ""
		def postSted = besokAdresse?.postSted? besokAdresse.postSted : ""
		def tilleggsAdresse = besokAdresse?.tilleggsAdresse? besokAdresse.tilleggsAdresse : ""
		
		String adresseXmlTopp = "<AdresseData>" + getRequestXmlPart(io)
		String adresseXmlBunn = """\
			  	<adresse>${adresse}</adresse> 
			  	<boligNummer>${boligNummer}</boligNummer> 
			  	<brevAdresse>${brevAdresse}</brevAdresse> 
				<brevPostNummer>${brevPostNummer}</brevPostNummer> 
				<brevPostSted>${brevPostSted}</brevPostSted> 
				<gateGaardNummer>${gateGaardNummer}</gateGaardNummer> 
				<husBrukNummer>${husBrukNummer}</husBrukNummer> 
				<kommuneNummer>${kommuneNummer}</kommuneNummer> 
				<postNummer>${postNummer}</postNummer> 
				<postSted>${postSted}</postSted> 
				<tilleggsAdresse>${tilleggsAdresse}</tilleggsAdresse> 
			</AdresseData>
			"""
		
		return adresseXmlTopp + adresseXmlBunn
	}

	public String getKjonnInBlaiseFormat( Kjonn kjonn ) {
		if( kjonn == null ) {
			return null
		}
		else if( kjonn.equals(Kjonn.MANN)) {
			return "1"
		}
		else {
			return "2"
		}
	}

	public String getIntervjuObjektSearchResultXml (IntervjuObjektSearch intervjuObjektSearch) {
		List<IntervjuObjekt> intervjuObjekter = intervjuObjektService.searchAll(intervjuObjektSearch)
		UtvalgTranslator translator = new UtvalgTranslator()
		List<ExtendedUtvalg> extUtvalgListe = new ArrayList<ExtendedUtvalg>()
		HashSet periodeNummere = new HashSet()
		HashSet delregisterNummere = new HashSet();
		intervjuObjekter.each {
			ExtendedUtvalg utvalg = translator.translateToUtvalg(null, it, null, true)
			periodeNummere.add(it.periode.periodeNummer)
			delregisterNummere.add(it.periode.delregisterNummer)
			extUtvalgListe.add(utvalg)
		}
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def forsteUtvalg = [:]
		if(!extUtvalgListe.isEmpty()) {
			forsteUtvalg = extUtvalgListe.get(0);
		}
		def meldingsheaderTekst = ''
		if (intervjuObjektSearch.meldingsheaderMalId) {
			def meldingsheaderMal = MeldingsheaderMal.get(intervjuObjektSearch.meldingsheaderMalId)
			meldingsheaderTekst = meldingsheaderMal.getMeldingsheader()
		}
		xml.utsending() {
			if(!extUtvalgListe.isEmpty()) {
				metadata {
					kildesystem('SIV')
					kildesystemreferanse(setTilStreng(delregisterNummere))
					pulje(setTilStreng(periodeNummere))
					beskrivelse(forsteUtvalg.skjemaNavn)
					utsendingstype('')
					mottagertype('')
					enhetstype('')
					periode('')
					meldingsheader(meldingsheaderTekst)
					websakjornalnummer('')
					utsendingsdato('')
					mal {
						navn('')
						format('')
					}
					statiskvedlegg('')
					flettetvedlegg {
						navn('')
						format('')
					}
					kanal('')
					treff(extUtvalgListe.size())
					sokenavn(intervjuObjektSearch.sokeNavn)
				}
				mottakerliste {
					extUtvalgListe.each { utvalg ->
						mottaker {
							ident(utvalg.fNummer)
							identNr(utvalg.intervjuObjektId)
							enhetsType('IO')
							telefonnummer(utvalg.smsNummer)
							epostadresse(utvalg.epostadresse)
							data {
								tilMap(utvalg).each { key, value ->
									"$key"(value)
								}
							}
						}
					}
				}
			}
		}
		return xmlDeclaration + '\n' +writer.toString()
	}

	public String getPingXml() {
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		xml.ping() {
			pong(grailsApplication.metadata['app.version'])
		}
		return xmlDeclaration + '\n' + writer.toString()
	}

	private Map tilMap(pogo) {
		def map = pogo.properties
		map.remove('heading')
		return map.sort()
	}

	private String setTilStreng(Set set) {
		def streng = ""
		set.each { verdi ->
			if(streng.length() > 0) {
				streng += '-'
			}
			streng += verdi
		}
		return streng
	}

}
