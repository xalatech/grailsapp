package siv.util

import groovy.time.Duration
import groovy.time.TimeDuration
import java.util.Date;

import util.TimeUtil;

class SorteringsUtil {
	
	// Tar inn en liste med Skjema objekter og returnerer en liste med skjema
	// som ikke er avsluttet sortert på oppstartsDato, det nyeste først
	
	public static List sorterPaaOppstartsDatoSkjemaSomIkkeErAvsluttet( def skjemaer ) {

		def sortertSkjemaList = []
		def today = new Date()

		skjemaer?.each {
			// if (it.sluttDato==null || it.sluttDato?.after(today))  eller:
			if (! it.sluttDato?.before(today)) {
				sortertSkjemaList.add(it)
			}
		}
		sortertSkjemaList.sort { a,b-> b.oppstartDataInnsamling<=>a.oppstartDataInnsamling }
		return sortertSkjemaList
	}
	
	
}