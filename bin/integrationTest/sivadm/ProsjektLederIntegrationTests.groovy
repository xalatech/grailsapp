package sivadm

;

class ProsjektLederIntegrationTests extends GroovyTestCase {
    
	protected void setup() {
        super.setUp()
		
		ProsjektLeder prosjektLeder = new ProsjektLeder()
		prosjektLeder.setNavn "Paal Soreng"
		prosjektLeder.setInitialer "spo"
		prosjektLeder.setEpost "spo"
		prosjektLeder.save(flush:true)
		
		ProsjektLeder prosjektLeder2 = new ProsjektLeder()
		prosjektLeder2.setNavn "Olo Soreng"
		prosjektLeder2.setInitialer "olo"
		prosjektLeder2.setEpost "olo"
		prosjektLeder2.save(flush:true)
	}

    protected void tearDown() {
        super.tearDown()
    }

    void testList() {
		
		def list = ProsjektLeder.list()
		assertEquals 2, list.size()
    }
	
	void testFindByInitialer()
	{
		ProsjektLeder prosjektLeder = ProsjektLeder.findByInitialer("spo")
		assertNotNull(prosjektLeder)
		assertEquals "Paal Soreng", prosjektLeder.navn
	}
}
