package siv.util

import groovy.time.Duration
import groovy.time.TimeDuration
import java.util.Date;

import util.TimeUtil;

class DatoUtil {
	
	public static Date hentForsteDagIUkaForDato( Date dato ) {
		Calendar c = Calendar.getInstance()
		c.setTime(TimeUtil.getStartOfDay(dato))
		
		def forsteDagIUka = c.getFirstDayOfWeek()
		
		while( c.get(Calendar.DAY_OF_WEEK) != forsteDagIUka ) {
			c.add(Calendar.DAY_OF_WEEK, -1)
		}
		
		return c.getTime()
	}
	
	public static Date hentSisteDagIUkaForDato( Date dato ) {
		return hentForsteDagIUkaForDato(dato) + 7
	}
	
	public static boolean erDetNatt() {
		Calendar c = Calendar.getInstance()
		
		int hour = c.get(Calendar.HOUR_OF_DAY)
		
		if( hour < 6 ) {
			return true
		} 
		else {
			return false
		}
	}
	
	public static Duration finnTidsDifferanse(String fraTid, String tilTid) {
		def fra = fraTid.tokenize(':')
		def til = tilTid.tokenize(':')

		def fraTidspunkt = new TimeDuration(fra.get(0).toInteger(), fra.get(1).toInteger(), 0, 0)
		def tilTidspunkt = new TimeDuration(til.get(0).toInteger(), til.get(1).toInteger(), 0, 0)
		
		return tilTidspunkt.minus(fraTidspunkt)
	}
	
}
