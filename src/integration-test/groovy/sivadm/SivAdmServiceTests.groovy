package sivadm

import java.util.Date;

import grails.test.*
import sil.Krav
import sil.SilMelding
import siv.type.ArbeidsType
import siv.type.IntervjuerArbeidsType
import siv.type.IntervjuerStatus
import siv.type.Kjonn
import siv.type.ProsjektFinansiering
import siv.type.ProsjektModus
import siv.type.ProsjektStatus
import siv.type.TimeforingStatus
import siv.type.TransportMiddel
import siv.type.UtleggType

class SivAdmServiceTests  extends GroovyTestCase {
    def sivAdmService
	def kravService
	
	Prosjekt prosjekt
	Intervjuer intervjuer
	
	protected void setup() {
        super.setUp()
		
		ProsjektLeder prosjektLeder = new ProsjektLeder()
		prosjektLeder.navn = "Paal Soreng"
		prosjektLeder.initialer = "spo"
		prosjektLeder.epost = "spo"
		prosjektLeder.save(failOnError: true, flush: true)
			
		prosjekt = new Prosjekt()
		prosjekt.panel = true
		prosjekt.produktNummer = "1234"
		prosjekt.modus = ProsjektModus.MIXMODUS
		prosjekt.registerNummer = "123"
		prosjekt.prosjektNavn = "Levekaar"
		prosjekt.aargang = 2010
		prosjekt.prosjektLeder = prosjektLeder
		prosjekt.oppstartDato = new Date()
		prosjekt.avslutningsDato = new Date()
		prosjekt.prosjektStatus = ProsjektStatus.PLANLEGGING
		prosjekt.finansiering = ProsjektFinansiering.STAT
		prosjekt.prosentStat = 100
		prosjekt.prosentMarked = 0
		prosjekt.kommentar = "test"
		prosjekt.save(failOnError: true, flush: true)
		
		Klynge klynge = new Klynge()
		klynge.klyngeNavn = "Test"
		klynge.klyngeSjef = "Test Ola"
		klynge.beskrivelse = "Dette er en test klynge"
		klynge.epost = "test.klynge@ssb.no"
		klynge.save(failOnError: true, flush: true)
		
		intervjuer = new Intervjuer()
		intervjuer.klynge = klynge
		intervjuer.initialer = "krn"
		intervjuer.intervjuerNummer = new Long(101001)
		intervjuer.navn = "Stian Karlsen"
		intervjuer.kjonn = Kjonn.MANN
		intervjuer.lokal = false
		
		Calendar cal = Calendar.getInstance()
		
		cal.set Calendar.DAY_OF_MONTH, 15
		cal.set Calendar.MONTH, 2
		cal.set Calendar.YEAR, 1974
		
		intervjuer.fodselsDato = cal.getTime()
		intervjuer.gateAdresse = "Lille Grensen 5"
		intervjuer.gateAdresse2 = ""
		intervjuer.postNummer = "0159"
		intervjuer.kommuneNummer = "0301"
		intervjuer.epostPrivat = "stian.karlsen@ttteeesssttt.com"
		intervjuer.epostJobb ="stian.karlsen@ssb.no"
		intervjuer.status = IntervjuerStatus.AKTIV
		
		cal.set Calendar.DAY_OF_MONTH, 1
		cal.set Calendar.MONTH, 0
		cal.set Calendar.YEAR, 2009
		
		intervjuer.ansattDato = cal.getTime()
		intervjuer.avtaltAntallTimer = 700
		intervjuer.arbeidsType = IntervjuerArbeidsType.BESOK
		
		cal.set Calendar.DAY_OF_MONTH, 3
		cal.set Calendar.MONTH, 2
		cal.set Calendar.YEAR, 2011
		
		intervjuer.sluttDato = cal.getTime()
		intervjuer.save(failOnError: true, flush: true)
    }

    protected void tearDown() {
        super.tearDown()
		prosjekt = null
		intervjuer = null
    }

	void testAvvisKravNull() {
		Krav krav
		
		boolean b = sivAdmService.avvisKrav(krav)
		
		// Forventer false når krav gitt til avvisKrav metode er null
		assertEquals false, b		
	}
	
    void testAvvisKravTime() {
		Timeforing time = new Timeforing()
		time.arbeidsType = ArbeidsType.INTERVJUE
		time.intervjuer = intervjuer
		time.timeforingStatus = TimeforingStatus.SENDT_INN
		time.produktNummer = "1234"
		Calendar cal = Calendar.getInstance()
		cal.set Calendar.HOUR_OF_DAY, 9
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		time.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 50)
		time.til = cal.getTime()
		time.redigertAv = "krn"
		time.redigertDato = new Date()
		time.save(failOnError: true, flush: true)
		
		Krav krav = kravService.konverterTimeforingTilKrav(time)
		
		SilMelding mld = new SilMelding(tittel: "Tittel", melding: "Melding", krav: krav, intervjuer: intervjuer, skrevetAv: "krn")
		mld.save(failOnError: true, flush: true)
		
		krav.silMelding = mld
		
		krav.save(failOnError: true, flush: true)
		
		assertNotNull time
		assertNotNull krav
		
		boolean b = sivAdmService.avvisKrav(krav)
		
		// Forventer true -> kravet er avvist og timeføringen satt til AVVIST
		assertEquals true, b
		
		Timeforing t = Timeforing.get(time.id)
		
		assertEquals TimeforingStatus.AVVIST, t.timeforingStatus
		
		// Sjekk at SilMelding er satt
		assertNotNull t.silMelding
	}
	
	void testAvvisKravKjorebok() {
		Kjorebok kjorebok = new Kjorebok()
		kjorebok.intervjuer = intervjuer
		kjorebok.transportmiddel = TransportMiddel.EGEN_BIL
		kjorebok.timeforingStatus = TimeforingStatus.SENDT_INN
		Calendar cal = Calendar.getInstance()
		cal.set Calendar.HOUR_OF_DAY, 9
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		kjorebok.fraTidspunkt = cal.getTime()
		cal.add(Calendar.MINUTE, 20)
		kjorebok.tilTidspunkt = cal.getTime()
		kjorebok.fraAdresse = "Storgata 100"
		kjorebok.tilAdresse = "Parkveien 200"
		kjorebok.fraPoststed = "Oslo"
		kjorebok.tilPoststed = "Oslo"
		kjorebok.produktNummer = "1234"
		kjorebok.kjorteKilometer = 10
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = new Date()
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		kjorebok.timeforing = kjorebok.opprettTimeforing()
		kjorebok.save(failOnError:true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		
		SilMelding mld = new SilMelding(tittel: "Tittel", melding: "Melding", krav: krav, intervjuer: intervjuer, skrevetAv: "krn")
		mld.save(failOnError: true, flush: true)
		
		krav.silMelding = mld
		
		krav.save(failOnError: true, flush: true)
		
		assertNotNull kjorebok
		assertNotNull krav
		
		boolean b = sivAdmService.avvisKrav(krav)
		
		// Forventer true -> kravet er avvist og kjøreboken satt til AVVIST
		assertEquals true, b
		
		Kjorebok k = Kjorebok.get(kjorebok.id)
		
		assertEquals TimeforingStatus.AVVIST, k.timeforingStatus
		
		// Sjekk at SilMelding er satt
		assertNotNull k.silMelding
	}
	
	void testAvvisKravUtlegg() {
		Utlegg utlegg = new Utlegg()
		utlegg.intervjuer = intervjuer
		utlegg.dato = new Date()
		utlegg.produktNummer = "1234"
		utlegg.utleggType = UtleggType.BILLETT
		utlegg.spesifisering = "Test"
		utlegg.merknad = "En test"
		utlegg.belop = 250
		utlegg.redigertAv = "krn"
		utlegg.redigertDato = new Date()
		utlegg.timeforingStatus = TimeforingStatus.SENDT_INN
		utlegg.save(failOnError:true, flush: true)
		
		Krav krav = kravService.konverterUtleggTilKrav(utlegg)
				
		SilMelding mld = new SilMelding(tittel: "Tittel", melding: "Melding", krav: krav, intervjuer: intervjuer, skrevetAv: "krn")
		mld.save(failOnError: true, flush: true)
		
		krav.silMelding = mld
		
		krav.save(failOnError: true, flush: true)
		
		assertNotNull utlegg
		assertNotNull krav
		
		boolean b = sivAdmService.avvisKrav(krav)
		
		// Forventer true -> kravet er avvist og utlegget satt til AVVIST
		assertEquals true, b
		
		Utlegg u = Utlegg.get(utlegg.id)
		
		assertEquals TimeforingStatus.AVVIST, u.timeforingStatus
		
		// Sjekk at SilMelding er satt
		assertNotNull u.silMelding
	}
}
