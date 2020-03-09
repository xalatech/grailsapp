package sivadm

import grails.test.*
import siv.type.MeldingInnType

class BlaiseMeldingInnServiceTests extends GroovyTestCase {
    def blaiseMeldingInnService
	
	protected void setup() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSlettMeldingerInnEldreEnnDato() {
		
		// Oppretter 6 MeldingInn, hvor 2 er eldre
		// enn 60 dager og bør bli slettet av
		// slettMeldingerInnEldreEnnDato
		
		oppdrettMeldingInn()
		
		def meldingInnListeOne = MeldingInn.getAll()
		
		int exp = 6
		assertEquals exp, meldingInnListeOne?.size()
		
		Calendar c = Calendar.getInstance()
		c.add(Calendar.DATE, -60) // 60 dager bakover i tid
		
		int slettet = blaiseMeldingInnService.slettMeldingerInnEldreEnnDato(c.getTime())
		
		// Forventer at 2 MeldingInn er slettet
		assertEquals 2, slettet
		
		def meldingInnListeTwo = MeldingInn.getAll()
		
		exp = 4
		
		assertEquals exp, meldingInnListeTwo?.size()
    }
	
	def oppdrettMeldingInn = {
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
		
		MeldingInn m1 = new MeldingInn(intervjuObjektId: 1L, meldingInnType: MeldingInnType.FULLFORT, tidRegistrert: d1)
		MeldingInn m2 = new MeldingInn(intervjuObjektId: 2L, meldingInnType: MeldingInnType.FULLFORT, tidRegistrert: d2)
		MeldingInn m3 = new MeldingInn(intervjuObjektId: 3L, meldingInnType: MeldingInnType.FULLFORT, tidRegistrert: d3)
		MeldingInn m4 = new MeldingInn(intervjuObjektId: 4L, meldingInnType: MeldingInnType.FULLFORT, tidRegistrert: d4)
		MeldingInn m5 = new MeldingInn(intervjuObjektId: 5L, meldingInnType: MeldingInnType.FULLFORT, tidRegistrert: d5)
		MeldingInn m6 = new MeldingInn(intervjuObjektId: 6L, meldingInnType: MeldingInnType.FULLFORT, tidRegistrert: d6)
		
		m1.save(failOnError: true, flush: true)
		m2.save(failOnError: true, flush: true)
		m3.save(failOnError: true, flush: true)
		m4.save(failOnError: true, flush: true)
		m5.save(failOnError: true, flush: true)
		m6.save(failOnError: true, flush: true)
	}
}
