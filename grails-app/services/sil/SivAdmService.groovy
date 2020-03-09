package sil

import java.util.Date;

import siv.type.TimeforingStatus
import sivadm.Timeforing
import sivadm.Kjorebok
import sivadm.Utlegg
import sivadm.Intervjuer
import sil.type.*


class SivAdmService {

    boolean transactional = true
	
	def timeforingService
	def utilService

    List serviceMethod() {
		def timeforingListe = Timeforing.list()
		
		return timeforingListe
    }
	
	List<Timeforing> hentInnsendteTimeforinger() {
		List<Timeforing> timer = Timeforing.findAllByTimeforingStatus(TimeforingStatus.SENDT_INN)
		return timer
	}
	
	List<Kjorebok> hentInnsendteKjoreboker() {
		List<Kjorebok> kjoreboker = Kjorebok.findAllByTimeforingStatus(TimeforingStatus.SENDT_INN)
		return kjoreboker
	}
	
	List<Utlegg> hentInnsendteUtlegg() {
		List<Utlegg> utlegg = Utlegg.findAllByTimeforingStatus(TimeforingStatus.SENDT_INN)
		return utlegg
	}
	
	List<Timeforing> hentInnsendteTimeforinger(Intervjuer intervjuer, Date dato) {
		Date fraDato = utilService.getFromDate(dato)
		Date tilDato = utilService.getToDate(dato)
		
		List<Timeforing> timer = Timeforing.createCriteria().list() {
			and {
				eq("intervjuer", intervjuer)
				eq("timeforingStatus", TimeforingStatus.SENDT_INN)
				between("fra", fraDato, tilDato)
			}
		}
		
		return timer
	}
	
	List<Kjorebok> hentInnsendteKjoreboker(Intervjuer intervjuer, Date dato) {
		Date fraDato = utilService.getFromDate(dato)
		Date tilDato = utilService.getToDate(dato)
		
		List<Kjorebok> kjoreboker = Kjorebok.createCriteria().list() {
			and {
				eq("intervjuer", intervjuer)
				eq("timeforingStatus", TimeforingStatus.SENDT_INN)
				between("fraTidspunkt", fraDato, tilDato)
			}
		}
		
		return kjoreboker
	}
	
	List<Utlegg> hentInnsendteUtlegg(Intervjuer intervjuer, Date dato) {
		Date fraDato = utilService.getFromDate(dato)
		Date tilDato = utilService.getToDate(dato)
		
		List<Utlegg> utlegg = Utlegg.createCriteria().list() {
			and {
				eq("intervjuer", intervjuer)
				eq("timeforingStatus", TimeforingStatus.SENDT_INN)
				between("dato", fraDato, tilDato)
			}
		}
		
		return utlegg
	}
	
	/**
	 * Proever aa aapne en dag for timeforing igjen hvis intervjueren har glemt noe, eller gjort noe 
	 * feil. Tar hensyn til bestemte statuser, baade paa timeforing, kjorebok og utlegg i tillegg
	 * til Krav. Det er f.eks ikke lov aa aapne dagen hvis Krav er sendt til SAP.
	 * 
	 * @param dato
	 * @param intervjuer
	 * @return
	 */
	public int apneDagForTimeforing(Date dato, Intervjuer intervjuer) {
		Date fraDato = utilService.getFromDate(dato)
		Date tilDato = utilService.getToDate(dato)
		
		List<Timeforing> tList = Timeforing.createCriteria().list() {
			and {
				eq("intervjuer", intervjuer)
				between("fra", fraDato, tilDato)
				ne("timeforingStatus", TimeforingStatus.IKKE_GODKJENT)
			}
		}
		
		tList.each { t ->
			def krav = Krav.findByTimeforing( t )
			
			if( krav == null || krav.kravStatus != KravStatus.OVERSENDT_SAP ) {
				t.setTimeforingStatus TimeforingStatus.GODKJENT
				t.save(failOnError: true, flush: true)
			}
		}
		
		List<Kjorebok> kList = Kjorebok.createCriteria().list() {
			and {
				eq("intervjuer", intervjuer)
				between("fraTidspunkt", fraDato, tilDato)
				ne("timeforingStatus", TimeforingStatus.IKKE_GODKJENT)
			}
		}
		
		kList.each { k ->
			def krav = Krav.findByKjorebok( k )
			
			if( krav == null || krav.kravStatus != KravStatus.OVERSENDT_SAP ) {
				k.setTimeforingStatus TimeforingStatus.GODKJENT
				k.save(failOnError: true, flush: true)
			}
		}
		
		List<Utlegg> uList = Utlegg.createCriteria().list() {
			and {
				eq("intervjuer", intervjuer)
				between("dato", fraDato, tilDato)
				ne("timeforingStatus", TimeforingStatus.IKKE_GODKJENT)
			}
		}
		uList.each { u ->
			def krav = Krav.findByKjorebok( u )
			
			if( krav == null || krav.kravStatus != KravStatus.OVERSENDT_SAP ) {
				u.setTimeforingStatus TimeforingStatus.GODKJENT
				u.save(failOnError: true, flush: true)
			}
		}
		
		int s = setKravInaktivForInterjuer(dato, intervjuer)
		
		return s
	}
	
	/**
	 * Metode for å sette status på timeføring, kjørebok eller utlegg
	 * tilhørende et krav til TimeforingStatus.AVVIST
	 * @param krav
	 * @return
	 */
	boolean avvisKrav(Krav krav) {
		if(!krav) {
			return false
		}
		
		SilMelding sm = krav.getSilMelding()?.kopierSilMelding()
		if(sm) {
			sm.save(failOnError: true, flush: true)
		}
		if(krav.timeforing) {
			if(sm) {
				krav.timeforing.setSilMelding sm
			}
			krav.timeforing.setTimeforingStatus TimeforingStatus.AVVIST
			krav.timeforing.save(failOnError: true, flush: true)
		}
		else if(krav.kjorebok) {
			if(sm) {
				krav.kjorebok.setSilMelding sm
			}
			krav.kjorebok.setTimeforingStatus TimeforingStatus.AVVIST
			krav.kjorebok.save(failOnError: true, flush: true)
		}
		else if(krav.utlegg) {
			if(sm) {
				krav.utlegg.setSilMelding sm
			}
			krav.utlegg.setTimeforingStatus TimeforingStatus.AVVIST
			krav.utlegg.save(failOnError: true, flush: true)
		}
		
		return true
	}
	
	boolean apneDagForTimeforingOgSettMelding(List<Krav> kravTilRetting) {
		if(!kravTilRetting) {
			return false
		}
		
		List<Date> datoListe = []
		Intervjuer intervjuer = kravTilRetting[0].intervjuer 
		kravTilRetting.each { kr ->
			SilMelding sm = kr.getSilMelding()?.kopierSilMelding()
			sm.save(failOnError: true, flush: true)
			datoListe << kr.dato
			if(kr.timeforing) {
				Timeforing t = kr.timeforing
				t.setSilMelding sm
				t.setTimeforingStatus TimeforingStatus.IKKE_GODKJENT
				t.save(failOnError: true, flush: true)
			}
			else if(kr.kjorebok) {
				Kjorebok k = kr.kjorebok
				k.setSilMelding sm
				k.setTimeforingStatus TimeforingStatus.IKKE_GODKJENT
				k.save(failOnError: true, flush: true)
			}
			else if(kr.utlegg) {
				Utlegg u = kr.utlegg
				u.setSilMelding sm
				u.setTimeforingStatus TimeforingStatus.IKKE_GODKJENT
				u.save(failOnError: true, flush: true)
			}
		}
		
		def ferdigDatoListe = []
		
		datoListe.each { d ->
			Date fraDato = utilService.getFromDate(d)
			Date tilDato = utilService.getToDate(d)
			
			if(!ferdigDatoListe.contains(fraDato)) {
				ferdigDatoListe << fraDato
				//log.info("Åpne timeføring for dato, fra " + fraDato + " til " + tilDato)
			
				List<Timeforing> tList = Timeforing.createCriteria().list() {
					and {
						eq("intervjuer", intervjuer)
						between("fra", fraDato, tilDato)
						ne("timeforingStatus", TimeforingStatus.IKKE_GODKJENT)
					}
				}
				//log.info("Fant " + tList.size() + " timeforinger som settes tilbake til godkjent")
				tList.each { t ->
					if(t.timeforingStatus != TimeforingStatus.AVVIST) {
						//log.info("Setter godkjent på timeforing med id " + t.id)
						t.setTimeforingStatus TimeforingStatus.GODKJENT
						t.save(failOnError: true, flush: true)	
					}
				}
				
				List<Kjorebok> kList = Kjorebok.createCriteria().list() {
					and {
						eq("intervjuer", intervjuer)
						between("fraTidspunkt", fraDato, tilDato)
						ne("timeforingStatus", TimeforingStatus.IKKE_GODKJENT)
					}
				}
				//log.info("Fant " + kList.size() + " kjoreboker som settes tilbake til godkjent")
				kList.each { k ->
					if(k.timeforingStatus != TimeforingStatus.AVVIST) {
						//log.info("Setter godkjent på kjorebok med id " + k.id)
						k.setTimeforingStatus TimeforingStatus.GODKJENT
						k.save(failOnError: true, flush: true)
					}
				}
				
				List<Utlegg> uList = Utlegg.createCriteria().list() {
					and {
						eq("intervjuer", intervjuer)
						between("dato", fraDato, tilDato)
						ne("timeforingStatus", TimeforingStatus.IKKE_GODKJENT)
					}
				}
				//log.info("Fant " + uList.size() + " utlegg som settes tilbake til godkjent")
				uList.each { u ->
					if(u.timeforingStatus != TimeforingStatus.AVVIST) {
						//log.info("Setter godkjent på utlegg med id " + u.id)
						u.setTimeforingStatus TimeforingStatus.GODKJENT
						u.save(failOnError: true, flush: true)
					}
				}
			}
			
			// TODO: Send mail til intervjuer om at datoer i ferdigDatoListe er åpnet for timeføring
		}
		
		return true
	}
	
	/*
	* Metode for å sette timeføringer, kjørerbøker og utlegg for en intervjuer til
	* SENDT_INN slik at dagen blir låst for føring.
	*/
	public void sendInnForingerForIntervjuerForDato(Intervjuer intervjuer, Date dato) {
		timeforingService.sendInnAlle(intervjuer, dato)
   	}
	
	
	public int setKravInaktivForInterjuer(Date dato, Intervjuer intervjuer) {
		Date fraDato = utilService.getFromDate(dato)
		Date tilDato = utilService.getToDate(dato)
		
		def statusList = [KravStatus.RETTET_AV_INTERVJUER, KravStatus.INAKTIV, KravStatus.AVVIST, KravStatus.OVERSENDT_SAP]
		
		List<Krav> kravListe = Krav.createCriteria().list() {
			and {
				eq("intervjuer", intervjuer)
				between("dato", fraDato, tilDato)
				not {
					'in'("kravStatus", statusList)
				}
			}
		}
		
		kravListe.each { krav ->
			//log.info("Sett inaktiv for krav med status " + krav.kravStatus)
			krav.setForrigeKravStatus  krav.kravStatus
			krav.setKravStatus KravStatus.INAKTIV
			krav.save(failOnError: true, flush: true)
		}
		
		return kravListe.size()
	}
}
