package no.ssb.sivadm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat(
			"dd.MM.yyyy HH:mm");
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd.MM.yyyy");


	public static Date now() {
		return new Date();
	}


	/**
	 * Difference between two dates in ms.
	 * 
	 * @param from
	 *            From date
	 * @param to
	 *            To date
	 * @return The difference in ms.
	 */
	public static long differ(Date from, Date to) {
		long diff = to.getTime() - from.getTime();
		return diff;
	}


	public static boolean isExpired(Date date) {
		if (null == date) {
			return true;
		} else {
			return date.before(DateUtil.now());
		}
	}


	public static Date getWithOneHourAdded(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, 59);

		return c.getTime();
	}
	
	public static Date getWithMinutesAdded(Date date, int minutes) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minutes);

		return c.getTime();
	}


	/**
	 * En metode som setter timer og minutter på en dato.
	 * 
	 * @param date
	 * @param time
	 *            format: HH:mm
	 * @return
	 */
	public static Date getDateWithTime(Date date, String tid) {

		if (date == null || (tid == null || tid.trim().equalsIgnoreCase(""))) {
			return null;
		}
		
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);

			String timer = tid.substring(0, 2);
			String minutter = tid.substring(3, 5);

			c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timer));
			c.set(Calendar.MINUTE, Integer.parseInt(minutter));
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.SECOND, 0);

			return c.getTime();	
		}
		catch (Exception e) {
			return null;
		}
	}


	public static String getTimeOnDate(Date date) {
		if( date == null ) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		return sdf.format(date);
	}


	public static boolean isTimeValid(String fra, String til) {
		Date fraDate = getDateWithTime(new Date(), fra);
		Date tilDate = getDateWithTime(new Date(), til);

		return tilDate.after(fraDate);
	}


	public static Integer getMinutesBetweenDates(Date fra, Date til) {

		if (fra == null || til == null) {
			return 0;
		}

		long tilMilli = til.getTime();
		long fraMilli = fra.getTime();

		long milliDiff = tilMilli - fraMilli;

		int iMilliDiff = (int) milliDiff;

		int minuteDiff = iMilliDiff / (1000 * 60);

		return minuteDiff;
	}

	public static String formatMinutes(int minutes) {
		String hours = formatIntToTid(minutes / 60);
		String minutesRest = formatIntToTid(minutes % 60);
		return hours + ":" + minutesRest;
	}

	private static String formatIntToTid(int i) {
		String string;
		if (i < 10){
			string = String.format("%02d", i);
		}else{
			string = Integer.toString(i);
		}
		return string;
	}	
}
