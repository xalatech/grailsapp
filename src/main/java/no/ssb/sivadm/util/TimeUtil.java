package no.ssb.sivadm.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	public static Date getNow() {
		Calendar c = Calendar.getInstance();
		Calendar currentDate = Calendar.getInstance();
		currentDate.clear();

		// bare dato - ikke time informasjon
		currentDate.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));

		return currentDate.getTime();
	}


	public static Date getTomorrow() {
		Calendar c = Calendar.getInstance();
		Calendar tomorrowDate = Calendar.getInstance();
		tomorrowDate.clear();
		tomorrowDate.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
		tomorrowDate.add(Calendar.DATE, 1);

		return tomorrowDate.getTime();
	}


	public static Date getOneWeekAgo() {
		Calendar c = Calendar.getInstance();
		Calendar oneWeekAgo = Calendar.getInstance();
		oneWeekAgo.clear();
		oneWeekAgo.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
		oneWeekAgo.add(Calendar.DATE, -7);

		return oneWeekAgo.getTime();
	}


	public static Date getStartOfDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		Calendar startOfDayDate = Calendar.getInstance();
		startOfDayDate.clear();
		startOfDayDate.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));

		return startOfDayDate.getTime();
	}


	public static Date getEndOfDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);

		return c.getTime();
	}


	public static Date getStartOfNextDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		Calendar nextStartOfDayDate = Calendar.getInstance();
		nextStartOfDayDate.clear();
		nextStartOfDayDate.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
		nextStartOfDayDate.add(Calendar.DATE, 1);

		return nextStartOfDayDate.getTime();
	}
	
	
	public static Date getStartOfYear() {
		
		Calendar now = Calendar.getInstance();
		
		Calendar startOfYearDate = Calendar.getInstance();
		startOfYearDate.clear();
		startOfYearDate.set(now.get(Calendar.YEAR), 0, 1);

		return startOfYearDate.getTime();
	}
	
	
	public static Date getStartOfYear(Date today) {
		
		Calendar now = Calendar.getInstance();
		now.setTime(today);
		
		Calendar startOfYearDate = Calendar.getInstance();
		startOfYearDate.clear();
		startOfYearDate.set(now.get(Calendar.YEAR), 0, 1);

		return startOfYearDate.getTime();
	}
	
	
	public static Date getStartOfNextYear() {
		
		Calendar now = Calendar.getInstance();
		
		Calendar startOfYearDate = Calendar.getInstance();
		startOfYearDate.clear();
		startOfYearDate.set(now.get(Calendar.YEAR) + 1, 0, 1);
		
		return startOfYearDate.getTime();
	}
	
	
	public static Date getStartOfNextYear(Date today) {
		
		Calendar now = Calendar.getInstance();
		now.setTime(today);
		
		Calendar startOfYearDate = Calendar.getInstance();
		startOfYearDate.clear();
		startOfYearDate.set(now.get(Calendar.YEAR) + 1, 0, 1);
		
		return startOfYearDate.getTime();
	}
	
	
	public static int getWeekNumberForNow() {
		Calendar now = Calendar.getInstance() ;
		return now.get(Calendar.WEEK_OF_YEAR) ;		
	}
	
}