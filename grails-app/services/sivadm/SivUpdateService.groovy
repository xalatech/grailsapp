package sivadm

import exception.SivAdmException
import siv.service.data.KontaktInformasjonServiceData
import siv.type.AdresseType
import siv.type.SkjemaStatus
import util.DateUtil
import util.UtvalgUtil

class SivUpdateService {
	
	boolean transactional = true
	
	def intervjuerService
		
	/**
	 * Setter status til ferdig for intervjuobjekt
	 * 
	 * @param intervjuObjektId
	 * @param endretAv
	 */
	public void settStatusFerdig(Long intervjuObjektId, String endretAv) {
		def intervjuObjektInstance = IntervjuObjekt.get(intervjuObjektId)
		
		if(!intervjuObjektInstance) {
			String errorMsg = "Fant ikke noe intervjuobjekt i SivAdm med id " + intervjuObjektId
			log.error errorMsg
			throw new SivAdmException( errorMsg )
		}
		
		intervjuObjektInstance.intervjuStatus = 0
		intervjuObjektInstance.katSkjemaStatus = SkjemaStatus.Ferdig
		
		// INTERVJUER
		intervjuObjektInstance.intervjuer = endretAv
	
		def historikk = new StatHist(skjemaStatus: intervjuObjektInstance.katSkjemaStatus, intervjuStatus: intervjuObjektInstance.intervjuStatus, redigertAv: endretAv, dato: new Date())
		intervjuObjektInstance.addToStatusHistorikk(historikk)
		
		intervjuObjektInstance.settRiktigKildeFraBlaiseSync()
		intervjuObjektInstance.save(failOnError: true, flush: true)
		
		try {
			Intervjuer intervjuer = intervjuerService.finnIntervjuerForInitialer(endretAv)
			
			// Oppretter intervjuer status historikk kun naar vi finner en registrert intervjuer
			if( intervjuer != null ) {
				def intervjuerHistorikk = new IntHist(intervjuer: intervjuer, intervjuStatus: 0, registrertDato: new Date(), intervjuObjekt: intervjuObjektInstance )
				intervjuerHistorikk.save(failOnError: true, flush: true)
			}
		}
		catch (Exception e) {
			log.error( "Greide ikke aa lagre intervjuerhistorikk: " + e.getMessage())
		}
	}
	
	
	/**
	 * Oppdaterer status for intervjuobjekt
	 * 
	 * @param intervjuObjektId
	 * @param intervjuStatus
	 * @param kommentar
	 * @param endretAv
	 */
	public void updateStatus(Long intervjuObjektId, Integer intervjuStatus, String kommentar, String endretAv) {
		def intervjuObjektInstance = IntervjuObjekt.get(intervjuObjektId)
		
		if( !intervjuObjektInstance ) {
			String errorMsg = "Fant ikke noe intervjuobjekt i SivAdm med id " + intervjuObjektId
			log.error errorMsg
			throw new SivAdmException( errorMsg )
		}
				
		// STATUS
		intervjuObjektInstance.intervjuStatus = intervjuStatus
		
		// FRAFALL
		if( intervjuStatus > 0 && intervjuStatus < 80) {
			intervjuObjektInstance.katSkjemaStatus = SkjemaStatus.Ubehandlet
		}
		// OVERFORING
		else if ( intervjuStatus >= 80 && intervjuStatus < 90 ) {
			intervjuObjektInstance.katSkjemaStatus = SkjemaStatus.Ubehandlet
		}
		// AVGANG
		else if ( intervjuStatus >= 90 && intervjuStatus < 100 ) {
			intervjuObjektInstance.katSkjemaStatus = SkjemaStatus.Ferdig
		}
		
		// KOMMENTAR
		intervjuObjektInstance.statusKommentar = kommentar
		
		// INTERVJUER
		intervjuObjektInstance.intervjuer = endretAv
		
		// Oppretter status historikk som vises i intervjuobjektvinduet
		def historikk = new StatHist(skjemaStatus: intervjuObjektInstance.katSkjemaStatus, intervjuStatus: intervjuStatus, redigertAv: endretAv, dato: new Date())
		intervjuObjektInstance.addToStatusHistorikk(historikk)
		intervjuObjektInstance.settRiktigKildeFraBlaiseSync()
		intervjuObjektInstance.save(failOnError: true, flush: true)
		
		try {
			Intervjuer intervjuer = intervjuerService.finnIntervjuerForInitialer(endretAv)
			
			// Oppretter intervjuer status historikk kun naar vi finner en registrert intervjuer
			if( intervjuer != null ) {
				def intervjuerHistorikk = new IntHist(intervjuer: intervjuer, intervjuStatus: intervjuStatus, registrertDato: new Date(), intervjuObjekt: intervjuObjektInstance )
				intervjuerHistorikk.save(failOnError: true, flush: true)
			}
		}
		catch (Exception e) {
			log.error( "Greide ikke aa lagre intervjuerhistorikk: " + e.getMessage())
		}
	}
	
	
	
	
	/**
	 * Oppdaterer internstatus for intervjuobjekt
	 * 
	 * @param intervjuObjektId
	 * @param internStatus
	 * @param endretAv
	 */
	public void updateInternStatus( Long intervjuObjektId, String internStatus, String endretAv )
	{
		def intervjuObjektInstance = IntervjuObjekt.get(intervjuObjektId)
		
		if( !intervjuObjektInstance ) {
			String errorMsg = "Fant ikke noe intervjuobjekt i SivAdm med id " + intervjuObjektId
			log.error errorMsg
			throw new SivAdmException( errorMsg )
		}
		
		intervjuObjektInstance.internStatus = internStatus
		intervjuObjektInstance.settRiktigKildeFraBlaiseSync()
		intervjuObjektInstance.save(failOnError: true, flush: true)
	}

	
	public void updateKontaktInformasjon(KontaktInformasjonServiceData data ) {
		def intervjuObjektInstance = IntervjuObjekt.get(data.intervjuObjektId)
		
		if( !intervjuObjektInstance ) {
			String errorMsg = "Fant ikke noe intervjuobjekt i SivAdm med id " + data.intervjuObjektId
			log.error errorMsg
			throw new SivAdmException( errorMsg )
		}
		
		intervjuObjektInstance.navn = data.navn
		intervjuObjektInstance.fodselsNummer = data.fodselsNummer
		intervjuObjektInstance.familienummer = data.familieNummer
		intervjuObjektInstance.sivilstand = data.sivilStand
		intervjuObjektInstance.epost = data.epost
		intervjuObjektInstance.alder = data.alder
		intervjuObjektInstance.kjonn = UtvalgUtil.mapKjonn(data.kjonn) 
		intervjuObjektInstance.personKode = data.personKode
		intervjuObjektInstance.kontaktperiode = data.kontaktperiode
		
		updateTelefoner(intervjuObjektInstance, data)
		
		updateAdresse(intervjuObjektInstance, data.gateAdresse, data.tilleggsAdresse, data.postNummer, data.postSted, data.kommuneNummer )
		
		intervjuObjektInstance.settRiktigKildeFraBlaiseSync()
		intervjuObjektInstance.save(failOnError: true, flush: true)
	}
	
	
	
	boolean checkIfAdresseExists(IntervjuObjekt intervjuObjekt, String gateAdresse, String postNummer) {
		boolean adresseExists = false
		
		def adresseList = intervjuObjekt.adresser
		
		adresseList.each { 
			Adresse adresse = it
			
			if( adresse.getAdresseType().equals(AdresseType.BESOK)) {
				if( adresse.getGateAdresse().equalsIgnoreCase(gateAdresse)) {
					if( adresse.getPostNummer().equalsIgnoreCase(postNummer)) {
						adresseExists = true
					}
				}
			}
		}
		
		return adresseExists
	}
	
	
	
	Adresse finnAdresse(IntervjuObjekt intervjuObjekt, String gateAdresse, String postNummer) {
		def adresseList = intervjuObjekt.adresser
		def adr
		adresseList.each {
			Adresse adresse = it
			
			if(adresse.getAdresseType() == AdresseType.BESOK) {
				if(adresse.getGateAdresse()?.equalsIgnoreCase(gateAdresse)) {
					if(adresse.getPostNummer()?.equalsIgnoreCase(postNummer)) {
						adr = adresse
					}
				}
			}
		}
		
		return adr
	}
	
	
	
	void updateAdresse(IntervjuObjekt intervjuObjekt, String gateAdresse, String tilleggsAdresse, String postNummer, String postSted, String kommuneNummer ) {
		Adresse adr = finnAdresse(intervjuObjekt, gateAdresse, postNummer)
	
		if(!adr) {
			def adresseList = intervjuObjekt.adresser
			
			adresseList.each {
				Adresse adresse = it
				
				if(adresse.gjeldende == true ) {
					adresse.gjeldende = false
					adresse.save(failOnError: true)
				}
			}
			
			Adresse adresseNy = new Adresse()
						
			adresseNy.gateAdresse = gateAdresse
			adresseNy.tilleggsAdresse = tilleggsAdresse
			adresseNy.postNummer = postNummer
			adresseNy.postSted = postSted
			adresseNy.kommuneNummer = kommuneNummer
			adresseNy.gjeldende = true
			adresseNy.adresseType = AdresseType.BESOK
			adresseNy.gyldigFom = DateUtil.now()
			
			intervjuObjekt.addToAdresser(adresseNy)
			
			adresseNy.save(failOnError: true, flush: true)
			intervjuObjekt.save(failOnError: true, flush: true)
		}
		else {
			adr.tilleggsAdresse = tilleggsAdresse
			adr.save(failOnError: true, flush: true)
		}
	}
	
	
	
	void updateTelefoner(IntervjuObjekt intervjuObjekt, def data) {	
		def telefonList = intervjuObjekt.telefoner
		
		// Gaa gjennom og sett riktig status paa eksisterende basert paa ekstern oppdatering som kommer inn
		telefonList.each { 
			Telefon t = it
			if (t.telefonNummer.equals(data.telefon1)) {
				t.gjeldende = true
				if (! data.kilde1?.empty){
					t.kilde = data.kilde1
				}
				if (! data.kommentar1?.empty){
					t.kommentar = data.kommentar1
				}
			}else if (t.telefonNummer.equals(data.telefon2)) {
				t.gjeldende = true
				if (! data.kilde2?.empty){
					t.kilde = data.kilde2
				}
				if (! data.kommentar2?.empty){
					t.kommentar = data.kommentar2
				}
			}else if(t.telefonNummer.equals(data.telefon3)) {
				t.gjeldende = true
				t.gjeldende = true
				if (! data.kilde3?.empty){
					t.kilde = data.kilde3
				}
				if (! data.kommentar3?.empty){
					t.kommentar = data.kommentar3
				}
			}else {
				t.gjeldende = false
			}
			t.save(failOnError: true, flush: true)
		}
		
		// Legg til eventuele nye nummer
		if (validTelefon(data.telefon1) && !isInList(telefonList, data.telefon1)) {
			Telefon t = new Telefon(telefonNummer: data.telefon1, kilde:data.kilde1, kommentar: data.kommentar1, gjeldende: true)
			intervjuObjekt.addToTelefoner(t)
			intervjuObjekt.save(failOnError: true)
			t.save(failOnError: true, flush: true)
		}
		
		
		if (validTelefon(data.telefon2) && !isInList(telefonList, data.telefon2)) {
			Telefon t = new Telefon(telefonNummer: data.telefon2, kilde:data.kilde2, kommentar: data.kommentar2, gjeldende: true)
			intervjuObjekt.addToTelefoner(t)
			intervjuObjekt.save(failOnError: true)
			t.save(failOnError: true, flush: true)
		}
		
		if ( validTelefon(data.telefon3) && !isInList(telefonList, data.telefon3)) {
			Telefon t = new Telefon(telefonNummer: data.telefon3, kilde:data.kilde3, kommentar: data.kommentar3, gjeldende: true)
			intervjuObjekt.addToTelefoner(t)
			intervjuObjekt.save(failOnError: true)
			t.save(failOnError: true, flush: true)
		}
	}
	
	
	
	private boolean validTelefon(String telefonNummer) {
		if(telefonNummer && telefonNummer.trim() != "") {
			return true
		}
		else {
			return false
		}
	}
	
	
	
	private boolean isInList(def telefonList, String telefonNummer) {
		
		boolean found = false
		
		telefonList.each { 
			Telefon t = it
			if( t.telefonNummer.trim() == telefonNummer.trim() ) {
				found = true
			}
		}
		
		return found
	}
}
