package sivadm


import spock.lang.Specification

class ScriptServiceTests extends Specification {
    protected void setup() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testAntallDbSok() {
		
		int totaltAntallIo = 600
		
		int step = 300
		
		double antallDbSokDouble = totaltAntallIo / step
		
		int antallDbSok = Math.ceil(antallDbSokDouble)
		
		assertEquals(2, antallDbSok)
    }
	
	void testAntallDbSok2() {
		
		int totaltAntallIo = 500
		
		int step = 300
		
		double antallDbSokDouble = totaltAntallIo / step
		
		int antallDbSok = Math.ceil(antallDbSokDouble)
		
		assertEquals(2, antallDbSok)
	}
	
	void testAntallDbSok3() {
		
		int totaltAntallIo = 601
		
		int step = 300
		
		double antallDbSokDouble = totaltAntallIo / step
		
		int antallDbSok = Math.ceil(antallDbSokDouble)
		
		assertEquals(3, antallDbSok)
	}
	
	void testAntallDbSok4() {
		
		int totaltAntallIo = 200
		
		int step = 300
		
		double antallDbSokDouble = totaltAntallIo / step
		
		int antallDbSok = Math.ceil(antallDbSokDouble)
		
		assertEquals(1, antallDbSok)
	}
}
