package sivadm

import siv.type.LoggType;
import grails.test.*

class LoggServiceIntegrationTests extends GroovyTestCase {

	LoggService loggService
	
	protected void setup() {
		super.setUp()
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testStartProsess() {
		loggService.startProsess(LoggType.SIL_BAKGRUNN_PROSESS, "Importerer krav", "Starter import...", "spo")	
		assertEquals(1, Logg.count())	
		def logg = Logg.findByLoggType(LoggType.SIL_BAKGRUNN_PROSESS)
		assertEquals(1, logg.loggLinjer.size())
	}
	
	void testSjekkOmProsessErAktiv_Aktiv() {
		loggService.startProsess(LoggType.SIL_BAKGRUNN_PROSESS, "Importerer krav", "Starter import...", "spo")
		assertEquals(true, loggService.sjekkOmProsessErAktiv(LoggType.SIL_BAKGRUNN_PROSESS))
	}
	
	void testSjekkOmProsessErAktiv_IkkeStartet() {
		assertEquals(false, loggService.sjekkOmProsessErAktiv(LoggType.SIL_BAKGRUNN_PROSESS))
	}
	
	void testStoppProsess() {
		Logg logg = loggService.startProsess(LoggType.SIL_BAKGRUNN_PROSESS, "Importerer krav", "Starter import...", "spo")
		loggService.stoppProsess(logg, "Import ferdig.", false)
		assertEquals(false, loggService.sjekkOmProsessErAktiv(LoggType.SIL_BAKGRUNN_PROSESS))
		assertEquals(2, logg.loggLinjer.size())
	}
}
