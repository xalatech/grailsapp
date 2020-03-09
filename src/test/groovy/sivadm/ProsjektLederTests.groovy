package sivadm


import spock.lang.Specification

class ProsjektLederTests extends Specification {
    
	protected void setup() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testFindAll() {
    	
		ProsjektLeder prosjektLeder = new ProsjektLeder()
		prosjektLeder.setNavn "Paal Soreng"
		prosjektLeder.setInitialer "spo"
		
		mockDomain ProsjektLeder, [prosjektLeder]
		
		def list = ProsjektLeder.list()
		
		assertEquals 1, list.size()
		assertEquals "spo", list[0].initialer	
    }
	
	void testFindByInitialer() {
		
		ProsjektLeder prosjektLeder = new ProsjektLeder()
		prosjektLeder.setNavn "Paal Soreng"
		prosjektLeder.setInitialer "spo"
		
		mockDomain ProsjektLeder, [prosjektLeder]
		
		def leder = ProsjektLeder.findByInitialer("spo")
		
		assertEquals "spo", leder.initialer	
	}
	
	void testFindByNameAndInitialer()
	{
		ProsjektLeder prosjektLeder = new ProsjektLeder()
		prosjektLeder.setNavn "Paal Soreng"
		prosjektLeder.setInitialer "spo"
		
		mockDomain ProsjektLeder, [prosjektLeder]
		
		def leder = ProsjektLeder.findByInitialerAndNavn("spo", "Paal Soreng")
		
		assertEquals "spo", leder.initialer
	}
}
