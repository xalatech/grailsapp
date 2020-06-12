package sivadm

import siv.type.MeldingInnType
import util.HttpRequestUtil

class BlaiseEventInnService {

	def skjemaService
	def grailsApplication
	
    boolean transactional = false
	
	private static String STATUS_CASE_FUL = "00" ;
	private static String STATUS_CASE_AVG = "01" ;
	private static String STATUS_CASE_FRA = "02" ;
	private static String STATUS_CASE_OVR = "04" ;
	
	/**
	 * Metode som:
	 * - sjekker om det er kommet noen nye rader i BlaiseEventInn tabell
	 * - gjor eventuelle nodvendige web service kall mot .NET service
	 * - setter inn i MeldingInn tabell
	 * 
	 * @return
	 */
    def behandleMeldingerBlaiseEventInn() {
		
		def blaiseUrl = grailsApplication.config.getProperty("sync.blaise.url")

		def blaiseEventInnList = BlaiseEventInn.findAllByBehandlet( false )

		// GAA GJENNOM ALLE SOM BLE FUNNET I BLAISE INN
		blaiseEventInnList.each { blaiseEventInn ->
			
			try {
				// HENT UT INFO FRA SIVADM SOM SKAL BRUKES FOR AA KALLE .NET SERVICE
				def intervjuObjekt
				def intervjuObjektId
				def skjemaKortNavn
				def skjemaVersjonsNummer
								
				intervjuObjektId = blaiseEventInn.intervjuObjektId
				
				intervjuObjekt = IntervjuObjekt.get( new Long(intervjuObjektId) )
				
				def error = false
				
				try {
					if(intervjuObjekt == null) {
						throw new Exception("Intervjuobjekt ikke funnet i sivadm datamodell kan ikke behandle BlaiseEventInn. IntervjuObjektId i BlaiseEventInn " + blaiseEventInn.intervjuObjektId)
					}
								
					skjemaKortNavn = intervjuObjekt.periode.skjema.skjemaKortNavn
					
					if(skjemaKortNavn == null ) {
						throw new Exception("Fant ikke skjemakortnavn for intervjuobjekt i sivadm datamodell.")
					}
					
					skjemaVersjonsNummer = skjemaService.findLatestSkjemaVersjonsNummer (intervjuObjekt.periode.skjema)
					
					if( skjemaVersjonsNummer == null ) {
						throw new Exception("Fant ingen skjema for intervjuobjekt i sivadm datamodell.")
					}
			
				}
				catch (Exception ex) {
					log.error(ex.message)
					error = true
				}
				
				def meldingInnType
				def url
				
				if(!error && blaiseEventInn.endringKontaktInfo) {
					MeldingInn kontaktMelding = new MeldingInn()
					try {
						kontaktMelding.meldingInnType = MeldingInnType.KONTAKT_INFORMASJON
						kontaktMelding.tidRegistrert = new Date()
						kontaktMelding.intervjuObjektId = intervjuObjektId
						kontaktMelding.intervjuObjektNummer = intervjuObjekt.intervjuObjektNummer
						kontaktMelding.skjemaKortNavn = skjemaKortNavn
						kontaktMelding.sendtAv = blaiseEventInn.initialer
						
						url = blaiseUrl + "/Service.svc/lesKontaktinformasjon?ioId=${intervjuObjektId}&skjema=${skjemaKortNavn}&versjon=${skjemaVersjonsNummer}"
					
						// kaller web service i .net applikasjon
						String responsXml = HttpRequestUtil.getStringData(new URL(url))
						
						def xmlData = new XmlSlurper().parseText(responsXml)
						
						def suksess = xmlData.suksess.toBoolean()
						
						kontaktMelding.melding = responsXml
						kontaktMelding.antallForsok = 1
						
						if(suksess == false) {
							kontaktMelding.responseText = xmlData.melding
							throw new Exception("Kall mot .NET service var ingen suksess.")
						}
											
						kontaktMelding.mottattOk = true
					}
					catch (Exception e) {
						log.error(e.message)
						kontaktMelding.feilType = e.message
						kontaktMelding.mottattOk = false
					}
					finally {
						deaktiverDuplikateMeldinger(kontaktMelding.meldingInnType, kontaktMelding.intervjuObjektId)
						kontaktMelding.save()
					}
				}
				
				if(!error && blaiseEventInn.statusCase) {
					MeldingInn meldingInn = new MeldingInn()
					
					meldingInn.tidRegistrert = new Date()
					meldingInn.intervjuObjektId = intervjuObjektId
					meldingInn.intervjuObjektNummer = intervjuObjekt.intervjuObjektNummer
					meldingInn.skjemaKortNavn = skjemaKortNavn
					meldingInn.sendtAv = blaiseEventInn.initialer
					// meldingInn.dayBatchKode = blaiseEventInn.dayBatchKode.equals("") ? null : blaiseEventInn.dayBatchKode.toInteger()
					
					if(blaiseEventInn.statusCase.equals(STATUS_CASE_FUL)) {
						meldingInn.meldingInnType = MeldingInnType.FULLFORT
						meldingInn.kommentar = blaiseEventInn.kommentar
					}
					else if(blaiseEventInn.statusCase.equals(STATUS_CASE_AVG) 
						|| blaiseEventInn.statusCase.equals(STATUS_CASE_FRA)
						|| blaiseEventInn.statusCase.equals(STATUS_CASE_OVR)) {
											
						meldingInn.meldingInnType = MeldingInnType.STATUS
						meldingInn.kommentar = blaiseEventInn.kommentar
						meldingInn.intervjuStatus = blaiseEventInn.intervjuStatus?.equals("") ? null : blaiseEventInn.intervjuStatus.toInteger()
					}
					else {
						log.info("Fant StatusCase som ikke er dekket av Blaise-SivAdm integrasjon, statusCase " + blaiseEventInn.statusCase)	
					}
						
					if(meldingInn.meldingInnType) {
						meldingInn.mottattOk = true
						deaktiverDuplikateMeldinger(meldingInn.meldingInnType, meldingInn.intervjuObjektId)
						meldingInn.save()
					}
					else {
						//	MeldingInnType ikke satt lagrer ikke, dette indikerer linje i BlaiseEventInn tabell
						// som ikke det ikke er avtalt integrasjon for.
					}
				}
				
				if( !error && blaiseEventInn.internStatus )
				{
					MeldingInn meldingInn = new MeldingInn()
					
					meldingInn.tidRegistrert = new Date()
					meldingInn.intervjuObjektId = intervjuObjektId
					meldingInn.intervjuObjektNummer = intervjuObjekt.intervjuObjektNummer
					meldingInn.skjemaKortNavn = skjemaKortNavn
					meldingInn.sendtAv = blaiseEventInn.initialer
					meldingInn.meldingInnType = MeldingInnType.INTERN_STATUS
					meldingInn.mottattOk = true
					meldingInn.internStatus = blaiseEventInn.internStatus
					
					deaktiverDuplikateMeldinger(meldingInn.meldingInnType, meldingInn.intervjuObjektId)
					
					meldingInn.save( failOnError: true)
				}
			}
			catch(Exception ex) {
				try {
					log.error(ex.getMessage())
				}
				catch(Exception ex2) {
					log.error(ex2.getMessage())
				}
			}
			finally {
				blaiseEventInn.behandlet = true
				blaiseEventInn.save(flush: true)
			}
		}	
    }

	def deaktiverDuplikateMeldinger(MeldingInnType type, Long intervjuObjektId) {
		// se om det finnes meldinger som ikke er lest av sivadm av samme type
		// samme type = samme io & samme meldingstype
		// hvis det finnes maa vi deaktivere den
		def meldinger = MeldingInn.createCriteria().list {
			eq("intervjuObjektId", intervjuObjektId)
			eq("meldingInnType", type)
			eq("deaktivert", false)
		}
		
		meldinger.each {
			it.deaktivert = true
			it.save(flush: true)
		}
	}
}