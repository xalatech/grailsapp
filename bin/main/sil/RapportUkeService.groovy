package sil

import util.TimeUtil;
import sil.rapport.data.UkeRapportData;
import sil.rapport.data.UkeRapportDatoerTilbakeData;
import sil.rapport.data.UkeRapportUkeNummerData
import sil.rapport.util.RapportTimeUtil;
import sil.type.KravType;
import sivadm.Intervjuer

class RapportUkeService {

    static transactional = true
	
	def intervjuerService

	/**
	 * Oppretter en UkeRapportData for hver intervjuer, og returner alle 
	 * disse som en liste. Denne metoden brukes for å generere en sil rapport.
	 * 
	 * @return en liste med UkeRapportData
	 */
    public List<UkeRapportData> hentUkeRapport(Date dato) {
		
		// begynnelsen og slutten paa dette aaret
		Date fra = TimeUtil.getStartOfYear(dato)
		Date til = TimeUtil.getStartOfNextYear(dato)
		
		// hent datoer for uker tilbake
		UkeRapportDatoerTilbakeData datoer = RapportTimeUtil.finnDatoerForUkerTilbake(dato)
		
		List ukeRapportDataList = []
		
		List intervjuere = intervjuerService.finnAktiveIntervjuere()
		
		intervjuere.each { Intervjuer intervjuer ->
			
			UkeRapportData data = new UkeRapportData()
			data.intervjuerNummer = intervjuer.intervjuerNummer
			data.navn = intervjuer.navn
			data.initialer = intervjuer.initialer
			data.avtaltAntallTimer = intervjuer.avtaltAntallTimer
			data.timerTotalt = finnTimerTotalt(intervjuer, fra, til)
			data.uke1Tilbake = finnTimerTotalt(intervjuer, datoer.startEnUkeTilbake, datoer.sluttEnUkeTilbake )
			data.uke2Tilbake = finnTimerTotalt(intervjuer, datoer.startToUkerTilbake, datoer.sluttToUkerTilbake )
			data.uke3Tilbake = finnTimerTotalt(intervjuer, datoer.startTreUkerTilbake, datoer.sluttTreUkerTilbake )
			data.uke4Tilbake = finnTimerTotalt(intervjuer, datoer.startFireUkerTilbake, datoer.sluttFireUkerTilbake )
			
			ukeRapportDataList.add(data)	
		}
		
		return ukeRapportDataList
    }
	
	
	/**
	 * Henter de siste ukenumrene for bruk i uke-rapporten
	 * @return
	 */
	public UkeRapportUkeNummerData hentSisteUkeNummere(Date dato) {
		
		Calendar calDato = Calendar.getInstance()
		calDato.setTime(dato)
		
		int ukeNummer = calDato.get(Calendar.WEEK_OF_YEAR)
		
		UkeRapportUkeNummerData ukeNumre = new UkeRapportUkeNummerData ()
		
		ukeNumre.ukeNummerDenneUka = ukeNummer
		ukeNumre.ukeNummerEnUkeTilbake = ukeNummer - 1
		ukeNumre.ukeNummerToUkerTilbake = ukeNummer - 2
		ukeNumre.ukeNummerTreUkerTilbake = ukeNummer - 3
		ukeNumre.ukeNummerFireUkerTilbake = ukeNummer -4
		
		return ukeNumre  	
	}

	
	/**
	 * Finner timer totalt for en intervjuer
	 * 
	 * @param intervjuer
	 * @return
	 */
	private Long finnTimerTotalt( Intervjuer intervjuer, Date fra, Date til ) {
		
		def c = Krav.createCriteria()
		
		def kravListe = c {
			
			eq("kravType", KravType.T)
			eq("intervjuer", intervjuer)
			
			if( fra != null && til != null) {
				
				timeforing {
					ge("fra", fra)
					lt("til", til)
				}
			}
		}
		
		double antallMinutter = 0
		
		kravListe.each {
			antallMinutter = antallMinutter + it.antall
		}
		
		double antallTimer = antallMinutter / 60
		
		return antallTimer
	}
	
	
	
}
