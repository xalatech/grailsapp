package sivadm


import spock.lang.Specification
import util.DateUtil

class DateUtilTests extends Specification {
	
    protected void setup() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

	void testFormatMinutes() {

		assert ( "00:00" == DateUtil.formatMinutes(0))
		
		assert ( "00:01" == DateUtil.formatMinutes(1))
		
		assert ( "00:59" == DateUtil.formatMinutes(59))
		
		assert ( "01:00" == DateUtil.formatMinutes(60))
		
		assert ( "01:01" == DateUtil.formatMinutes(61))
		
		assert ( "02:00" == DateUtil.formatMinutes(120))
		
		assert ( "02:10" == DateUtil.formatMinutes(130))

		assert ( "00:45" == DateUtil.formatMinutes(45))
		
		assert ( "129:15" == DateUtil.formatMinutes(7755))
		
		assert ( "189:16" == DateUtil.formatMinutes(11356))
		
		assert ( "416:40" == DateUtil.formatMinutes(25000))

	}
	
	
}
