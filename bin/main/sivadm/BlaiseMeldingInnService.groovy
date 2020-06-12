package sivadm

import siv.search.MeldingSok
import siv.service.data.KontaktInformasjonServiceData
import siv.type.MeldingInnType
import util.TimeUtil
// import grails.core.ConfigurationHolder

class BlaiseMeldingInnService {

	static transactional = false
	
	def grailsApplication
    def sivUpdateService

    def sjekkBlaiseMeldingInn() {

		//Se om det finnes nye meldinger i MeldingInn
		def meldinger = MeldingInn.findAllByDeaktivertIsNull()
	
		// behandle disse og kall opp oppdateringService
		meldinger.each {
			log.info( "sjekkBlaiseMeldingInn() IO ID: " + it.intervjuObjektId )
			
			try {
				if(it.meldingInnType == MeldingInnType.KONTAKT_INFORMASJON) {
					if(it.melding) {
						KontaktInformasjonServiceData data = xmlToKontaktInformasjon(it.melding, it.intervjuObjektId)
						sivUpdateService.updateKontaktInformasjon(data)
					}
					else {
						// TODO: ??	
					}
				}
				else if(it.meldingInnType == MeldingInnType.STATUS) {
					sivUpdateService.updateStatus(it.intervjuObjektId, it.intervjuStatus, it.kommentar, (it.sendtAv ?: "blaise"))
				}
				else if(it.meldingInnType == MeldingInnType.FULLFORT) {
					sivUpdateService.settStatusFerdig(it.intervjuObjektId,	(it.sendtAv ?: "blaise"))
				}
				else if(it.meldingInnType == MeldingInnType.INTERN_STATUS) {
					sivUpdateService.updateInternStatus(it.intervjuObjektId, it.internStatus, (it.sendtAv ?: "blaise"))
				}
				else {
					log.error("Melding mottat fra Blaise uten MeldingInnType satt, ioId: " + it.intervjuObjektId + " mld: " + it.melding)	
				}
				
				it.deaktivert = true	
			}
			catch(Exception ex) {
				try {
					it.deaktivert = true
					it.mottattOk = false
					it.save(flush: true)
					
					def feilMsg = ex.getMessage()
					if( feilMsg && feilMsg.length() > 250 ) {
						feilMsg = feilMsg.substring (0, 240)
					}
					
					it.feilType = feilMsg
					log.error(ex.getMessage())
				}
				catch(Exception ex2) { 
					log.error(ex2.getMessage())	
				}
			}
			finally {
				it.save(flush: true)
			}
		}
    }
	
	def ryddMeldingInn() {
		log.info("Kjører ryddMeldingInn, sletter meldinger eldre enn " + grailsApplication.config.getProperty("behold.meldinger.inn.antall.dager") + " dager")
		
		Calendar cal = Calendar.getInstance()
		cal.add(Calendar.DAY_OF_MONTH, (-1 * grailsApplication.config.getProperty("behold.meldinger.inn.antall.dager")))
		
		slettMeldingerInnEldreEnnDato(cal.getTime())
	}
	
	
	public int slettMeldingerInnEldreEnnDato(Date dato) {
		def meldingerInnTilSlettingListe = MeldingInn.findAllByTidRegistrertLessThan(dato)
		int cnt = 0
		meldingerInnTilSlettingListe.each {
			try {
				it.delete(flush: true)
				cnt++
			}
			catch(Exception ex) {
				log.error("Kunne ikke slette MeldingInn med id " + it.id + ", " + ex.getMesssage())	
			}
		}
		
		log.info("Har slettet " + cnt + " MeldingInn, som var eldre enn " + dato)
		
		return cnt
	}
		
	
	private KontaktInformasjonServiceData xmlToKontaktInformasjon(String xmlText, Long intervjuObjektId) {
		def inputData = new XmlSlurper().parseText(xmlText)
		
		KontaktInformasjonServiceData data = new KontaktInformasjonServiceData()
		
		// XML -> Dataobjekt
		data.intervjuObjektId = intervjuObjektId
		data.intervjuObjektNummer = inputData.intervjuObjektNummer.toString()
		data.skjemaKortNavn = inputData.skjemaKortNavn?.toString()
		
		data.alder = inputData.alder.equals("") ? null : inputData.alder.toInteger()
		data.familieNummer = inputData.familieNummer
		data.fodselsNummer = inputData.fodselsNummer
		data.gateAdresse = inputData.adresse
		data.kommuneNummer = inputData.kommuneNummer
		data.kontaktperiode = inputData.kontaktperiode
		data.kjonn = inputData.kjonn
		data.navn = inputData.navn
		data.tilleggsAdresse = inputData.tilleggsAdresse
		data.personKode = inputData.personKode
		data.postNummer = inputData.postNummer
		data.postSted = inputData.postSted
		data.sivilStand = inputData.sivilStand
		
		data.epost = inputData.epost

		data.telefon1 = inputData.telefon1
		data.kilde1 = inputData.kilde1
		data.kommentar1 = inputData.kommentar1
		
		data.telefon2 = inputData.telefon2
		data.kilde2 = inputData.kilde2
		data.kommentar2 = inputData.kommentar2

		data.telefon3 = inputData.telefon3
		data.kilde3 = inputData.kilde3
		data.kommentar3 = inputData.kommentar3

		return data
	}
	
	public List sokMeldingInnAlle(MeldingSok sok) {
		return this.sokMeldingInn(sok, new HashMap() )
	}
	
	public List sokMeldingInn(MeldingSok sok, Map pagination ) {
		
		int max = pagination.max
		int first = pagination.offset? Integer.parseInt(pagination.offset) : 0
		
		def meldingInnIds = sokKriteria(sok, first, max, false)
		
		if(meldingInnIds.size() > 0) {
			return MeldingInn.getAll(meldingInnIds)
		}
		else {
			return new ArrayList()
		}
	}
	
	
	public int tellSokMeldingInn(MeldingSok sok) {
		List mldCount = sokKriteria(sok, null, null, true)
		return mldCount.get(0)
	}

	def sokKriteria = {sok, first, max, countAll ->
		def c = MeldingInn.createCriteria()
		
		def meldingInnIds = c {
			
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
			
			if(sok.status && !sok.status.equals("Alle")) {
				if(sok.status.equals("Mottatt ok")) {
					eq("mottattOk", true)
				}
				else {
					eq("mottattOk", false)
				}
			}
						
			if(sok?.meldingInnType){
				eq("meldingInnType", sok.meldingInnType)
			}
		}
		
		return meldingInnIds
	}
}
