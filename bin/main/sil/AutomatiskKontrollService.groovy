package sil

import java.util.Collections
import java.util.Date;

import sil.Krav
import sil.AutomatiskKontroll
import sil.type.*

class AutomatiskKontrollService {
	boolean transactional = true
	
	/**
	* Kjører automatiske kontroller på en liste med Krav.
	* @param kravList listen med Krav som skal kontrolleres
	* @return En liste med de krav som ikke bestod de automatiske kontrollene
	*/
	List<Krav> kjorAutomatiskeKontroller(List<Krav> kravList) {
		return kjorAutomatiskeKontroller(kravList, true)
	}
	
	/**
	 * Kjører automatiske kontroller på en liste med Krav.
	 * @param kravList listen med Krav som skal kontrolleres'
	 * @param returnerFeilet er det listen med de kravene som feilet som skal returneres
	 * @return En liste med de krav som ikke bestod de automatiske kontrollene (hvis returnerFeilet
	 * er satt til true) eller en liste med de krav som bestod de automatiske kontrollene.
	 */
	List<Krav> kjorAutomatiskeKontroller(List<Krav> kravList, boolean returnerFeilet) {
		
		List<Krav> feiletListe = []
		List<Krav> bestodListe = []
		List<AutomatiskKontroll> kontrollTimeListe = AutomatiskKontroll.findAllByKravType(KravType.T)
		List<AutomatiskKontroll> kontrollKjorebokListe = AutomatiskKontroll.findAllByKravType(KravType.K)
		List<AutomatiskKontroll> kontrollUtleggListe = AutomatiskKontroll.findAllByKravType(KravType.U)
		
		kravList.each {k -> 
			boolean feilet = false
			
			if(k.kravType == KravType.T) {
				kontrollTimeListe.each {ak ->
					if(feiletAutomatiskKontroll(k, ak)) {
						feilet = true
						k.kravStatus = KravStatus.FEILET_AUTOMATISK_KONTROLL
						k.automatiskeKontroller << ak
						if(!feiletListe.contains(k)) {
							feiletListe << k
						}
					}
					else if(!bestodListe.contains(k)) {
						bestodListe << k
					}
				}
			}
			else if(k.kravType == KravType.K) {
				kontrollKjorebokListe.each {ak ->
					if(feiletAutomatiskKontroll(k, ak)) {
						feilet = true
						k.kravStatus = KravStatus.FEILET_AUTOMATISK_KONTROLL
						k.automatiskeKontroller << ak
						if(!feiletListe.contains(k)) {
							feiletListe << k
						}
					}
					else if(!bestodListe.contains(k)) {
						bestodListe << k
					}
				}
			}
			else if(k.kravType == KravType.U) {
				kontrollUtleggListe.each {ak ->
					if(feiletAutomatiskKontroll(k, ak)) {
						feilet = true
						k.kravStatus = KravStatus.FEILET_AUTOMATISK_KONTROLL
						k.automatiskeKontroller << ak
						if(!feiletListe.contains(k)) {
							feiletListe << k
						}
					}
					else if(!bestodListe.contains(k)) {
						bestodListe << k
					}
				}
			}

			if(feilet == false) {
				k.kravStatus = KravStatus.BESTOD_AUTOMATISK_KONTROLL
			}
			
			k.save(failOnError:true)
		}
		
		if(returnerFeilet) {
			return feiletListe
		}
		else {
			return bestodListe
		}
	}

	/**
	 * Sjekk om et krav består en automatisk kontroll.
	 * @param krav Kravet som skal sjekkes
	 * @param automatiskKontroll Den automatiske kontrollen det skal sjekkes mot
	 * @return true hvis kravet består kontrollen og false hvis ikke
	 */
	boolean bestodAutomatiskKontroll(Krav krav, AutomatiskKontroll automatiskKontroll) {
		if(automatiskKontroll.produktNummer) {
			if(krav.produktNummer != automatiskKontroll.produktNummer) {
				//log.info("Ikke rett produktNummer '" + automatiskKontroll.produktNummer + "' vs '" + krav?.produktNummer + "' ser bort fra automatisk kontroll " + automatiskKontroll.kontrollNavn)
				return true;
			}
		}
		
		if(automatiskKontroll.transportmiddel && krav.kravType == KravType.K) {
			if(automatiskKontroll.transportmiddel != krav?.kjorebok?.transportmiddel) {
				//log.info("Ikke rett transportmiddel '" + automatiskKontroll.transportmiddel + "' vs '" + krav?.kjorebok?.transportmiddel + "' ser bort fra automatisk kontroll " + automatiskKontroll.kontrollNavn)
				return true
			}
		}
		
		if(automatiskKontroll.gyldigFra && automatiskKontroll.gyldigFra.after(krav.dato)) {
			//log.info("Krav dato er før gyldigFra dato for kontrollen " + krav.dato + " vs " + automatiskKontroll.gyldigFra + " ser bort fra automatisk kontroll " + automatiskKontroll.kontrollNavn)
			return true;
		}
		
		if(automatiskKontroll.gyldigTil && automatiskKontroll.gyldigTil.before(krav.dato)) {
			//log.info("Krav dato er etter gyldigTil dato for kontrollen " + krav.dato + " vs " + automatiskKontroll.gyldigTil + " ser bort fra automatisk kontroll " + automatiskKontroll.kontrollNavn)
			return true;
		}
		
		if(krav.antall > automatiskKontroll.grenseVerdi) {
			return false;
		}
			
		return true;
	}
	
	/**
	* Sjekk om et krav feiler en automatisk kontroll.
	* @param krav Kravet som skal sjekkes
	* @param automatiskKontroll Den automatiske kontrollen det skal sjekkes mot
	* @return true hvis kravet feiler kontrollen og false hvis ikke
	*/
	boolean feiletAutomatiskKontroll(Krav krav, AutomatiskKontroll automatiskKontroll) {
		return !bestodAutomatiskKontroll(krav, automatiskKontroll)
	}
	
	
	/**
	 * Sletter en automatisk kontroll. Sletter også eventuelle koblinger mellom
	 * Krav og denne kontrollen.
	 * 
	 * @param automatiskKontroll
	 */
	public void slettAutomatiskKontroll(AutomatiskKontroll automatiskKontroll) {
		
		def c = Krav.createCriteria()
		
		def kravList = c.list {
			automatiskeKontroller {
				eq("id", automatiskKontroll.id)
			}
		}
		
		kravList.each { Krav krav ->
			krav.automatiskeKontroller.clear()
			krav.save(failOnError: true)
		}
		
		automatiskKontroll.delete(flush: true)
	}
}
