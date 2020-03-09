package sivadm


import spock.lang.Specification

class RegexTests extends Specification {
	
	def reg = "\\d\\d:\\d\\d"
	
    protected void setup() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testRegex() {
		assert ("12:45" =~ reg)
		assert !("12*45" =~ reg)
		assert !("a2:45" =~ reg)
		assert !("aa:bb" =~ reg)
		assert !("12453" =~ reg)
		assert !("  ert" =~ reg)
		assert !("  :  " =~ reg)
	}
	
	
}
