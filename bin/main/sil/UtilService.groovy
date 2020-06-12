package sil

import java.util.Date

class UtilService {

    boolean transactional = true

    public Date getFromDate(Date d) {
		Calendar cal = Calendar.getInstance()
		cal.setTime(d)
		
		cal.set(Calendar.HOUR_OF_DAY, 0)
		cal.set(Calendar.MINUTE, 0)
		cal.set(Calendar.SECOND, 0)
		
		return cal.getTime()
	}
	
	public Date getToDate(Date d) {
		Calendar cal = Calendar.getInstance()
		cal.setTime(d)
		
		cal.set(Calendar.HOUR_OF_DAY, 23)
		cal.set(Calendar.MINUTE, 59)
		cal.set(Calendar.SECOND, 59)
		
		return cal.getTime()
	}
	
	public long beregnTidMinutter(Date d1, Date d2) {
		if(!d1 && !d2) {
			log.error "Kan ikke beregne tid når dato1 og/eller dato2 er NULL"
			return 0;
		}
		
		Long minutter = 0
		
		Long tidMillisekunder = d2.time - d1.time
				
		minutter = (tidMillisekunder/(1000*60))
		
		return minutter
	}
	
	public boolean dateEquals(Date dateOne, Date dateTwo) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateOne);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		Date d1 = cal.getTime();
		
		cal.setTime(dateTwo);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		Date d2 = cal.getTime();
		
		if(d1.compareTo(d2) == 0) {
			return true;
		}
		
		return false;
	}
}
