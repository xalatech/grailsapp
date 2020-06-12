package sivadm

import grails.test.*
import siv.type.MeldingType

class SynkroniseringServiceTests extends GroovyTestCase {
	def synkroniseringService
	
    protected void setup() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSlettMeldingerUtEldreEnnDato() {
		// Oppretter 6 MeldingUt, hvor 2 er eldre
		// enn 60 dager og bør bli slettet av
		// slettMeldingerUtEldreEnnDato
		
		oppdrettMeldingUt()
		
		def meldingUtListeOne = MeldingUt.getAll()
		
		int exp = 6
		assertEquals exp, meldingUtListeOne?.size()
		
		Calendar c = Calendar.getInstance()
		c.add(Calendar.DATE, -60) // 60 dager bakover i tid
		
		int slettet = synkroniseringService.slettMeldingerUtEldreEnnDato(c.getTime())
		
		// Forventer at 2 MeldingUt er slettet
		assertEquals 2, slettet
		
		def meldingUtListeTwo = MeldingUt.getAll()
		
		exp = 4
		
		assertEquals exp, meldingUtListeTwo?.size()
    }
	
	def oppdrettMeldingUt = {
		Calendar cal = Calendar.getInstance()
		
		Date d1 = cal.getTime()
	
		cal.add(Calendar.DATE, -10) // 10 dager bakover i tid
				
		Date d2 = cal.getTime()
		
		cal.add(Calendar.DATE, -20) // 30 dager bakover i tid
		
		Date d3 = cal.getTime()
		
		cal.add(Calendar.DATE, -10) // 40 dager bakover i tid
		
		Date d4 = cal.getTime()
		
		cal.add(Calendar.DATE, -30) // 70 dager bakover i tid
		
		Date d5 = cal.getTime()
		
		cal.add(Calendar.DATE, -10) // 80 dager bakover i tid
		
		Date d6 = cal.getTime()
		
		MeldingUt m1 = new MeldingUt(intervjuObjektId: 1L, tidRegistrert: d1, antallForsok: 1, intervjuObjektNummer: "111112", skjemaKortNavn: "DUMMY", melding: "mld", meldingType: MeldingType.STATUS, sendtAv: "xxx", sendtOk: Boolean.TRUE)
		MeldingUt m2 = new MeldingUt(intervjuObjektId: 2L, tidRegistrert: d2, antallForsok: 1, intervjuObjektNummer: "111113", skjemaKortNavn: "DUMMY", melding: "mld", meldingType: MeldingType.STATUS, sendtAv: "xxx", sendtOk: Boolean.TRUE)
		MeldingUt m3 = new MeldingUt(intervjuObjektId: 3L, tidRegistrert: d3, antallForsok: 1, intervjuObjektNummer: "111114", skjemaKortNavn: "DUMMY", melding: "mld", meldingType: MeldingType.STATUS, sendtAv: "xxx", sendtOk: Boolean.TRUE)
		MeldingUt m4 = new MeldingUt(intervjuObjektId: 4L, tidRegistrert: d4, antallForsok: 1, intervjuObjektNummer: "111115", skjemaKortNavn: "DUMMY", melding: "mld", meldingType: MeldingType.STATUS, sendtAv: "xxx", sendtOk: Boolean.TRUE)
		MeldingUt m5 = new MeldingUt(intervjuObjektId: 5L, tidRegistrert: d5, antallForsok: 1, intervjuObjektNummer: "111116", skjemaKortNavn: "DUMMY", melding: "mld", meldingType: MeldingType.STATUS, sendtAv: "xxx", sendtOk: Boolean.TRUE)
		MeldingUt m6 = new MeldingUt(intervjuObjektId: 6L, tidRegistrert: d6, antallForsok: 1, intervjuObjektNummer: "111117", skjemaKortNavn: "DUMMY", melding: "mld", meldingType: MeldingType.STATUS, sendtAv: "xxx", sendtOk: Boolean.TRUE)
		
		m1.save(failOnError: true, flush: true)
		m2.save(failOnError: true, flush: true)
		m3.save(failOnError: true, flush: true)
		m4.save(failOnError: true, flush: true)
		m5.save(failOnError: true, flush: true)
		m6.save(failOnError: true, flush: true)
	}
}
