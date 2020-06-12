
package sivadm

import siv.type.AdresseType;
import grails.test.*
import groovy.util.GroovyTestCase;

class AdresseIntegrationTests extends GroovyTestCase { // GrailsUnitTestCase {
    protected void setup() {
        super.setUp()
		
		Adresse adresse = new Adresse()
		
		adresse.setAdresseType AdresseType.BESOK
		adresse.setGyldigFom(new Date())
		adresse.setKommuneNummer "1234"
		adresse.setPostNummer "1850"
		adresse.setGjeldende (new Boolean(true))
		
		adresse.save(flush: true)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testFindAllAdresses() {
    	def adresseList = Adresse.list()
		assertNotNull adresseList
    }
	
	void testFindByGjeldende() {
		def adresse = Adresse.findByGjeldende( new Boolean(true))
		assertNotNull adresse
	}
	
	void testFindByGjeldendeFalse() {
		def adresse = Adresse.findByGjeldende( new Boolean(false))
		assertNull adresse
	}
	
	void testFindByGjeldendeAndAdresseType() {
		def adresse = Adresse.findByGjeldendeAndAdresseType( new Boolean(true), AdresseType.BESOK )
		assertNotNull adresse
	}
	
	void testFindByGjeldendeAndAdresseTypeNotBesok() {
		def adresse = Adresse.findByGjeldendeAndAdresseType( new Boolean(true), AdresseType.POST )
		assertNull adresse
	}
	
	
}
