package sil.rapport.util

import java.util.Calendar;
import java.util.Date;

import sil.rapport.data.UkeRapportDatoerTilbakeData;

class RapportTimeUtil {
	
	
	/**
	 * Returnerer en map med ukestart- og sluttdatoer for denne uka, og fire uker
	 * tilbake i tid. Brukes i forbindelse med en sil-rapport.
	 * @return 
	 */
	public static UkeRapportDatoerTilbakeData finnDatoerForUkerTilbake(Date dato) {
		Calendar c = Calendar.getInstance()
		c.setTime(dato)
		
		Calendar start = Calendar.getInstance()
		start.clear()
		
		start.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
		
		int ukeDag = c.get(Calendar.DAY_OF_WEEK)
		
		if( ukeDag <= 2 ) {
			ukeDag += 7
		}
		
		// finner start dato (første dag denne uka)
		int ukeDagerNedTilStart = ukeDag - 2
		start.add(Calendar.DATE, (ukeDagerNedTilStart * -1))
		
		// denne uka
		start.add(Calendar.DATE, 7)
		Date sluttDenneUka = start.getTime()
		
		start.add(Calendar.DATE, -7)
		Date startDenneUka = start.getTime()
		
		// 1 uke tilbake
		Date sluttEnUkeTilbake = startDenneUka
		
		start.add(Calendar.DATE, -7)
		Date startEnUkeTilbake = start.getTime()
		
		// 2 uker tilbake
		Date sluttToUkerTilbake = startEnUkeTilbake
		
		start.add(Calendar.DATE, -7)
		Date startToUkerTilbake = start.getTime()
		
		// 3 uker tilbake
		Date sluttTreUkerTilbake = startToUkerTilbake
		
		start.add(Calendar.DATE, -7)
		Date startTreUkerTilbake = start.getTime()
		
		// 4 uker tilbake
		Date sluttFireUkerTilbake = startTreUkerTilbake
		
		start.add(Calendar.DATE, -7)
		Date startFireUkerTilbake = start.getTime()
		
		UkeRapportDatoerTilbakeData ukeRapportDatoerTilbakeData = new UkeRapportDatoerTilbakeData()
		
		ukeRapportDatoerTilbakeData.startDenneUka = startDenneUka
		ukeRapportDatoerTilbakeData.sluttDenneUka = sluttDenneUka
		ukeRapportDatoerTilbakeData.startEnUkeTilbake = startEnUkeTilbake
		ukeRapportDatoerTilbakeData.sluttEnUkeTilbake = sluttEnUkeTilbake
		ukeRapportDatoerTilbakeData.startToUkerTilbake = startToUkerTilbake
		ukeRapportDatoerTilbakeData.sluttToUkerTilbake = sluttToUkerTilbake
		ukeRapportDatoerTilbakeData.startTreUkerTilbake = startTreUkerTilbake
		ukeRapportDatoerTilbakeData.sluttTreUkerTilbake = sluttTreUkerTilbake
		ukeRapportDatoerTilbakeData.startFireUkerTilbake = startFireUkerTilbake
		ukeRapportDatoerTilbakeData.sluttFireUkerTilbake = sluttFireUkerTilbake
		
		return ukeRapportDatoerTilbakeData
	} 
}
