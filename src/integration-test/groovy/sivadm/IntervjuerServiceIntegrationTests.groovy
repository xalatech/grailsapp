package sivadm

import siv.type.*;
import grails.test.*

class IntervjuerServiceIntegrationTests extends GroovyTestCase {
	
	def intervjuerService
	
    protected void setup() {
        super.setUp()
		
		Kommune kommune
		kommune = Kommune.findByKommuneNummer("0301")
		if(!kommune) {
			kommune= new Kommune()
			kommune.kommuneNavn = "OSLO"
			kommune.kommuneNummer = "0301"
			kommune.save(failOnError: true, flush: true)
		}
				
		Klynge klynge
		klynge = Klynge.findByKlyngeNavn("Klynge Oslo")
		
		if(!klynge) {
			klynge = new Klynge()
			klynge.klyngeNavn = "Klynge Oslo"
			klynge.klyngeSjef = "Test"
			klynge.beskrivelse = "Klynge for Oslo"
			klynge.epost = "test_test@ssb.no"
			klynge.addToKommuner(kommune)
			klynge.save(failOnError: true, flush: true)
		}
				
		Intervjuer intervjuer = new Intervjuer()
		intervjuer.klynge = klynge
		intervjuer.initialer = "si4"
		intervjuer.intervjuerNummer = 112233
		intervjuer.navn = "Test Intervjuer"
		intervjuer.kjonn = Kjonn.MANN
		
		Calendar cal = Calendar.getInstance()
		cal.set Calendar.YEAR, 1970
		cal.set Calendar.MONTH, 1
		cal.set Calendar.DAY_OF_MONTH, 15
		intervjuer.fodselsDato = cal.getTime()
		
		intervjuer.gateAdresse = "Kongens Gate 11"
		intervjuer.postNummer = "0033"
		intervjuer.postSted = "Oslo"
		intervjuer.kommuneNummer = "0301"
		intervjuer.epostPrivat = "spo@ssb.no"
		intervjuer.epostJobb ="spo@ssb.no"
		intervjuer.status = IntervjuerStatus.AKTIV
		cal.set Calendar.YEAR, 2010
		cal.set Calendar.MONTH, 0
		cal.set Calendar.DAY_OF_MONTH, 0
		intervjuer.ansattDato = cal.getTime()
		intervjuer.avtaltAntallTimer = 1500
		intervjuer.arbeidsType = IntervjuerArbeidsType.BEGGE
		
		cal.set Calendar.YEAR, 2050
		cal.set Calendar.MONTH, 1
		cal.set Calendar.DAY_OF_MONTH, 1
		intervjuer.sluttDato = cal.getTime()
		
		intervjuer.save(failOnError: true, flush: true)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testDataModell() {
		assertEquals(1, Intervjuer.count())
    }
	
	void testFinnIntervjuerForInitialer() {
		def intervjuer = intervjuerService.finnIntervjuerForInitialer("si4")
		assertNotNull(intervjuer)
	}
	
	void testFinnIntervjuerForInitialerMedIkkeEksisterendeIntervjuer() {
		def intervjuer = intervjuerService.finnIntervjuerForInitialer("xxx")
		assertNull(intervjuer)
	}
	
	void testFinnIntervjuerForInitialerMedFeilCase() {
		def intervjuer = intervjuerService.finnIntervjuerForInitialer("SI4")
		assertNotNull(intervjuer)
	}
}
