package sivadm

import java.util.Calendar;


import groovy.lang.MetaClass

class UtilServiceTest extends GroovyTestCase {

	def utilService
	
	protected void setup() {
		super.setUp()
	}
	
	protected void tearDown() {
		super.tearDown()
	}
	
	void testBeregnTidMinutter() {
		Calendar c = Calendar.getInstance()
		
		Date dTwo = c.getTime()
		c.add(Calendar.MINUTE, -70)
		Date dOne = c.getTime()
				
		Long l = utilService.beregnTidMinutter(dOne, dTwo)
		assertEquals 70, l
	}
	
	void testDateEquals() {
		Date d1 = new Date()
		Calendar cal = Calendar.getInstance()
		cal.add Calendar.HOUR_OF_DAY, 1
		Date d2 = cal.getTime()
		
		boolean b = utilService.dateEquals(d1,d2)
		
		assertEquals b, true
	}
}
