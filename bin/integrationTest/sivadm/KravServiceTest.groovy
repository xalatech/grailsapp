package sivadm

import grails.test.*


import java.util.Calendar
import java.util.Date
import java.util.List

import sil.Krav
import sil.search.IntervjuerKontrollSearch
import sil.search.KravSearch
import sil.type.*
import siv.type.ArbeidsType
import siv.type.IntervjuerArbeidsType
import siv.type.IntervjuerStatus
import siv.type.Kjonn
import siv.type.TimeforingStatus
import siv.type.TransportMiddel
import siv.type.UtleggType

class KravServiceTest extends GroovyTestCase { // GrailsUnitTestCase
	
	def kravService
	
	Utlegg utlegg
	Timeforing time3
	Kjorebok kjorebok
	
	protected void setup() {
		super.setUp()
		Calendar cal = Calendar.getInstance()
		Klynge klynge = new Klynge()
		klynge.klyngeNavn = "Oslo"
		klynge.klyngeSjef = "Ola"
		klynge.beskrivelse = "Klynge for Oslo"
		klynge.epost = "spo@ssb.no"
		
		klynge.save([failOnError: true, flush: true])
		
		Intervjuer intervjuer = new Intervjuer()
		intervjuer.klynge = klynge
		intervjuer.initialer = "spo"
		intervjuer.intervjuerNummer = 1
		intervjuer.navn = "paal soreng"
		intervjuer.kjonn = Kjonn.MANN
		intervjuer.fodselsDato = new Date()
		intervjuer.gateAdresse = "bollerveien 30"
		intervjuer.gateAdresse2 = "bollerveien 40"
		intervjuer.postNummer = "6789"
		intervjuer.kommuneNummer = "0301"
		intervjuer.epostPrivat = "pal.soreng@gmail.com"
		intervjuer.epostJobb ="spo@ssb.no"
		intervjuer.status = IntervjuerStatus.AKTIV
		intervjuer.ansattDato = new Date()
		intervjuer.avtaltAntallTimer = 700
		intervjuer.arbeidsType = IntervjuerArbeidsType.BESOK
		intervjuer.sluttDato = new Date()
		
		Intervjuer intervjuer2 = new Intervjuer()
		intervjuer2.klynge = klynge
		intervjuer2.initialer = "uhu"
		intervjuer2.intervjuerNummer = 2
		intervjuer2.navn = "uhu hansen"
		intervjuer2.kjonn = Kjonn.MANN
		intervjuer2.fodselsDato = new Date()
		intervjuer2.gateAdresse = "bollerveien 30"
		intervjuer2.gateAdresse2 = "bollerveien 40"
		intervjuer2.postNummer = "3000"
		intervjuer2.kommuneNummer = "0301"
		intervjuer2.epostPrivat = "pal.soreng@gmail.com"
		intervjuer2.epostJobb ="spo@ssb.no"
		intervjuer2.status = IntervjuerStatus.AKTIV
		intervjuer2.ansattDato = new Date()
		intervjuer2.avtaltAntallTimer = 700
		intervjuer2.arbeidsType = IntervjuerArbeidsType.BESOK
		intervjuer2.sluttDato = new Date()
		
		
		
		intervjuer.save(failOnError: true, flush: true)
		intervjuer2.save(failOnError: true, flush: true)
		
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuer
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "xxx"
		cal.set Calendar.HOUR_OF_DAY, 9
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 50)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "spo"
		timeOne.redigertDato = new Date()
		
		Timeforing timeTwo = new Timeforing()
		timeTwo.arbeidsType = ArbeidsType.INTERVJUE
		timeTwo.intervjuer = intervjuer2
		timeTwo.timeforingStatus = TimeforingStatus.SENDT_INN
		timeTwo.produktNummer = "yyy"
		cal.set Calendar.HOUR_OF_DAY, 9
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeTwo.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 70)
		timeTwo.til = cal.getTime()
		timeTwo.redigertAv = "spo"
		timeTwo.redigertDato = new Date()
		
		time3 = new Timeforing()
		time3.arbeidsType = ArbeidsType.INTERVJUE
		time3.intervjuer = intervjuer
		time3.timeforingStatus = TimeforingStatus.SENDT_INN
		time3.produktNummer = "yyy"
		cal.set Calendar.HOUR_OF_DAY, 18
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		time3.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 70)
		time3.til = cal.getTime()
		time3.redigertAv = "spo"
		time3.redigertDato = new Date()
		
		timeOne.save(failOnError:true, flush:true)
		timeTwo.save(failOnError:true, flush:true)
		time3.save(failOnError:true, flush:true)
		
		Krav kravOne = kravService.konverterTimeforingTilKrav(timeOne)
		kravOne.kravStatus = KravStatus.BESTOD_AUTOMATISK_KONTROLL
		
		Krav kravTwo = kravService.konverterTimeforingTilKrav(timeTwo)
		kravTwo.kravStatus = KravStatus.BESTOD_AUTOMATISK_KONTROLL
		
		kravOne.save(failOnError:true, flush:true)
		kravTwo.save(failOnError:true, flush:true)
	
		kjorebok = new Kjorebok()
		kjorebok.intervjuer = intervjuer
		kjorebok.transportmiddel = TransportMiddel.EGEN_BIL
		kjorebok.timeforingStatus = TimeforingStatus.SENDT_INN
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
		kjorebok.produktNummer = "1234-5"
		kjorebok.kjorteKilometer = 10
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = new Date()
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		
		Timeforing timeforing = kjorebok.opprettTimeforing()
		timeforing.save(failOnError:true, flush: true)

		kjorebok.timeforing = timeforing
		kjorebok.save(failOnError:true, flush: true)
		
		utlegg = new Utlegg()
		utlegg.intervjuer = intervjuer2
		utlegg.dato = new Date()
		utlegg.produktNummer = "3456-7"
		utlegg.utleggType = UtleggType.BILLETT
		utlegg.spesifisering = "Test"
		utlegg.merknad = "En test"
		utlegg.belop = 250
		utlegg.redigertAv = "krn"
		utlegg.redigertDato = new Date()
		utlegg.timeforingStatus = TimeforingStatus.SENDT_INN
		utlegg.save(failOnError:true, flush: true)
	}

	protected void tearDown() {
		super.tearDown()
		
		utlegg = null
		time3 = null
		kjorebok = null
	}

	void testKonverterTimeforingTilKrav() {
		Krav k = kravService.konverterTimeforingTilKrav(time3)
		
		assertEquals time3.fra, k.dato
		assertEquals time3.intervjuer, k.intervjuer
		assertEquals time3, k.timeforing
		assertEquals 70, k.antall
		assertEquals KravType.T, k.kravType
	}
	
	void testKonverterKjorebokTilKrav() {
		Krav k = kravService.konverterKjorebokTilKrav(kjorebok)
		
		assertEquals kjorebok.fraTidspunkt, k.dato
		assertEquals kjorebok.intervjuer, k.intervjuer
		assertEquals kjorebok, k.kjorebok
		assertEquals kjorebok.kjorteKilometer, k.antall
		assertEquals KravType.K, k.kravType
	}
	
	void testKonverterUtleggTilKrav() {
		Krav k = kravService.konverterUtleggTilKrav(utlegg)
		
		assertEquals utlegg.dato, k.dato
		assertEquals utlegg.intervjuer, k.intervjuer
		assertEquals utlegg, k.utlegg
		assertEquals utlegg.belop, k.antall
		assertEquals KravType.U, k.kravType
	}
	
	void testKonverterTimeforingTilKravTimeforingErNull() {
		Timeforing time
		Krav krav = kravService.konverterTimeforingTilKrav(time)
		
		assertNull krav
	}
	
	void testKonverterKjorebokTilKravKjorebokErNull() {
		Kjorebok kjore
		Krav krav = kravService.konverterKjorebokTilKrav(kjore)
		
		assertNull krav
	}
	
	void testKonverterUtleggTilKravUtleggErNull() {
		Utlegg utlegg
		Krav krav = kravService.konverterUtleggTilKrav(utlegg)
		
		assertNull krav
	}
		
	void testFinnKravTilManuellKontroll() {
		List<Krav> tilManKontrollListe = kravService.finnKravTilManuellKontroll(50)
	
		// Det er 2 intervjuere med ett krav hver, å plukke ut 50% av disse skal
		// gi en liste med ett krav
		
		assertEquals 1, tilManKontrollListe.size()
	}
	
	void testFinnKravTilManuellKontrollTomIntervjuerListe() {
		List<Intervjuer> intervjuerListe
		def liste = kravService.finnKravTilManuellKontroll(intervjuerListe)
		
		// Forventer tom liste tilbake
		assertNotNull liste
		assertEquals 0, liste.size()
	}
		
	void testGodkjennAlleForIntervjuer() {
		Klynge klynge = new Klynge()
		klynge.klyngeNavn = "Test"
		klynge.klyngeSjef = "Ola Nordmann"
		klynge.beskrivelse = "Klynge for Oslo"
		klynge.epost = "test_test@ssb.no"
		klynge.save(failOnError: true, flush: true)
			
		Intervjuer krn = new Intervjuer()
		krn.klynge = klynge
		krn.initialer = "krn"
		krn.intervjuerNummer = 3
		krn.navn = "Stian Karlsen"
		krn.kjonn = Kjonn.MANN
		krn.fodselsDato = new Date()
		krn.gateAdresse = "Lille Grensen 5"
		krn.gateAdresse2 = "Lille Grensen 5"
		krn.postNummer = "0159"
		krn.kommuneNummer = "0301"
		krn.epostPrivat = "stian.karlsen.stian.karlsen@gmail.com"
		krn.epostJobb ="krn@ssb.no"
		krn.status = IntervjuerStatus.AKTIV
		krn.ansattDato = new Date()
		krn.avtaltAntallTimer = 700
		krn.arbeidsType = IntervjuerArbeidsType.BESOK
		krn.sluttDato = new Date()
		krn.save(failOnError: true, flush: true)
		
		Krav k1 = new Krav(intervjuer: krn, antall: 120, kravType: KravType.T, kravStatus: KravStatus.FEILET_AUTOMATISK_KONTROLL, produktNummer: "1234-5")
		k1.save(failOnError: true, flush: true)
		Krav k2 = new Krav(intervjuer: krn, antall: 120, kravType: KravType.T, kravStatus: KravStatus.TIL_MANUELL_KONTROLL, produktNummer: "1234-5")
		k2.save(failOnError: true, flush: true)
		Long testId = k2.id
		Krav k3 = new Krav(intervjuer: krn, antall: 30, kravType: KravType.K, kravStatus: KravStatus.TIL_MANUELL_KONTROLL, produktNummer: "1234-5")
		k3.save(failOnError: true, flush: true)
		Krav k4 = new Krav(intervjuer: krn, antall: 250, kravType: KravType.U, kravStatus: KravStatus.FEILET_AUTOMATISK_KONTROLL, produktNummer: "1234-5")
		k4.save(failOnError: true, flush: true)
		
		int antall = kravService.godkjennAlleForIntervjuer(krn)
		
		assertEquals 4, antall
		
		Krav testKrav = Krav.get(testId)
		assertEquals testKrav.kravStatus, KravStatus.GODKJENT
	}
	
	void testOppdaterKravEtterRettingKravErNull() {
		Krav krav
		
		def liste = kravService.oppdaterKravEtterRetting(krav)
		
		assertEquals 0, liste.size()
	}
	
	void testOppdaterKravEtterRettingTimeforingErNull() {
		Krav krav = new Krav(kravType: KravType.T)
		
		def liste = kravService.oppdaterKravEtterRetting(krav)
		
		assertEquals 0, liste.size()
	}
	
	void testOppdaterKravEtterRettingUtleggErNull() {
		Krav krav = new Krav(kravType: KravType.U)
		
		def liste = kravService.oppdaterKravEtterRetting(krav)
		
		assertEquals 0, liste.size()
	}
	
	void testOppdaterKravEtterRettingKjorebokErNull() {
		Krav krav = new Krav(kravType: KravType.K)
		
		def liste = kravService.oppdaterKravEtterRetting(krav)
		
		assertEquals 0, liste.size()
	}
	
	void testOppdaterKravEtterRettingTime() {
		Krav krav = kravService.konverterTimeforingTilKrav(time3)
		krav.save(failOnError: true, flush: true)
		
		Calendar c = Calendar.getInstance()
		c.setTime(time3.fra)
		c.add(Calendar.MINUTE, 30)
		time3.til = c.getTime()
		time3.produktNummer = "0333-1"
		
		def liste = kravService.oppdaterKravEtterRetting(krav)
		
		assertEquals 1, liste.size()
		
		def oppdatertKrav = liste[0]
		
		assertNotNull oppdatertKrav
		
		assertEquals "0333-1", oppdatertKrav.produktNummer
		assertEquals 30, oppdatertKrav.antall
	}
	
	void testOppdaterKravEtterRettingUtlegg() {
		Krav krav = kravService.konverterUtleggTilKrav(utlegg)
		krav.save(failOnError: true, flush: true)
		
		utlegg.produktNummer = "0333-2"
		utlegg.belop = 49.50D
		
		def liste = kravService.oppdaterKravEtterRetting(krav)
		
		assertEquals 1, liste.size()
		
		def oppdatertKrav = liste[0]
		
		assertNotNull oppdatertKrav
		
		assertEquals "0333-2", oppdatertKrav.produktNummer
		assertEquals 49.5D, oppdatertKrav.antall		
	}
	
	void testOppdaterKravEtterRettingKjorebokUtenUtleggKm() {
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		kjorebok.produktNummer = "0333-3"
		kjorebok.kjorteKilometer = 18
		
		def liste = kravService.oppdaterKravEtterRetting(krav)
		
		// Forventer 2 krav, et for kjorebok og et for tilhørende timeforing
		assertEquals 2, liste.size()
		
		def oppdatertKrav = liste[0]
		
		assertNotNull oppdatertKrav
		
		assertEquals "0333-3", oppdatertKrav.produktNummer
		assertEquals 18, oppdatertKrav.antall
	}
	
	void testOppdaterKravEtterRettingKjorebokUtenUtleggLeiebil() {
		kjorebok.transportmiddel = TransportMiddel.LEIEBIL
		kjorebok.kjorteKilometer = 0L
		kjorebok.save(failOnError:true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		
		Calendar c = Calendar.getInstance()
		c.setTime(kjorebok.fraTidspunkt)
		c.add(Calendar.MINUTE, 38)
		kjorebok.tilTidspunkt = c.getTime()
		kjorebok.produktNummer = "0333-3"
				
		def liste = kravService.oppdaterKravEtterRetting(krav)
		
		// Forventer 2 krav, et for kjorebok og et for tilhørende timeforing
		assertEquals 2, liste.size()
		
		def oppdatertKjorebokKrav = liste[0]
		def oppdatertTimeKrav = liste[1]
		
		assertNotNull oppdatertKjorebokKrav
		
		assertEquals "0333-3", oppdatertKjorebokKrav.produktNummer
		assertEquals "0333-3", oppdatertTimeKrav.produktNummer
		assertEquals 38, oppdatertKjorebokKrav.antall
		assertEquals 38, oppdatertTimeKrav.antall
	}
	
	void testOppdaterKravEtterRettingKjorebokUtenUtleggGikk() {
		kjorebok.transportmiddel = TransportMiddel.GIKK
		kjorebok.kjorteKilometer = 0L
		kjorebok.save(failOnError:true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		
		Calendar c = Calendar.getInstance()
		c.setTime(kjorebok.fraTidspunkt)
		c.add(Calendar.MINUTE, 38)
		kjorebok.tilTidspunkt = c.getTime()
		kjorebok.produktNummer = "0333-3"
				
		def liste = kravService.oppdaterKravEtterRetting(krav)
		
		// Forventer 2 krav, et for kjorebok og et for tilhørende timeforing
		assertEquals 2, liste.size()
		
		def oppdatertKjorebokKrav = liste[0]
		def oppdatertTimeKrav = liste[1]
		
		assertNotNull oppdatertKjorebokKrav
		
		assertEquals "0333-3", oppdatertKjorebokKrav.produktNummer
		assertEquals "0333-3", oppdatertTimeKrav.produktNummer
		assertEquals 38, oppdatertKjorebokKrav.antall
		assertEquals 38, oppdatertTimeKrav.antall
	}
	
	void testOppdaterKravEtterRettingKjorebokUtenUtleggTid() {
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		String forventetProduktNummer = kjorebok.produktNummer
		
		Calendar c = Calendar.getInstance()
		c.setTime(kjorebok.fraTidspunkt)
		c.add(Calendar.MINUTE, 30)
		kjorebok.tilTidspunkt = c.getTime()
		
		kjorebok.transportmiddel = TransportMiddel.LEIEBIL
				
		def liste = kravService.oppdaterKravEtterRetting(krav)
		
		// Forventer 2 krav, et for kjorebok og et for tilhørende timeforing
		assertEquals 2, liste.size()
		
		def oppdatertKrav = liste[0]
		def oppdatertKravTime = liste[1]
		
		assertNotNull oppdatertKrav
		assertNotNull oppdatertKravTime
		
		assertEquals forventetProduktNummer, oppdatertKrav.produktNummer
		assertEquals 30, oppdatertKrav.antall
		assertEquals forventetProduktNummer, oppdatertKravTime.produktNummer
		assertEquals 30, oppdatertKravTime.antall
		
	}
		
	void testOppdaterKravEtterRettingKjorebokMedUtleggFerge() {
		//Oppdater kjørebok til å passe testen
		kjorebok.transportmiddel = TransportMiddel.FERJE
		kjorebok.utleggFerge = utlegg
		kjorebok.kjorteKilometer = 0
		kjorebok.save(failOnError: true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(kjorebok.utleggFerge)
		kravUtlegg.save(failOnError: true, flush: true)
		
		utlegg.belop = 180D
		
		def liste = kravService.oppdaterKravEtterRetting(krav)
				
		// Forventer 3 krav, et for kjorebok, et for tilhørende timeforing og et for utlegg ferge
		assertEquals 3, liste.size()
				
		Krav oppdaterteUtleggKrav = liste[2]
	
		assertEquals 180D, oppdaterteUtleggKrav.antall
	}
	
	void testOppdaterKravEtterRettingKjorebokMedUtleggBom() {
		//Oppdater utlegg til å passe utlegg bom
		utlegg.utleggType = UtleggType.BOMPENGER
		
		//Oppdater kjørebok til å passe testen
		kjorebok.utleggBom = utlegg
		kjorebok.save(failOnError: true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(kjorebok.utleggBom)
		kravUtlegg.save(failOnError: true, flush: true)
		
		utlegg.belop = 36D
		
		def liste = kravService.oppdaterKravEtterRetting(krav)
				
		// Forventer 3 krav, et for kjorebok, et for tilhørende timeforing og et for utlegg bom
		assertEquals 3, liste.size()
				
		Krav oppdaterteUtleggKrav = liste[2]
	
		assertEquals 36D, oppdaterteUtleggKrav.antall
	}
	
	void testOppdaterKravEtterRettingKjorebokMedUtleggParkering() {
		//Oppdater utlegg til å passe utlegg parkering
		utlegg.utleggType = UtleggType.PARKERING
		
		//Oppdater kjørebok til å passe testen
		kjorebok.utleggParkering = utlegg
		kjorebok.save(failOnError: true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(kjorebok.utleggParkering)
		kravUtlegg.save(failOnError: true, flush: true)
		
		utlegg.belop = 60D
		
		def liste = kravService.oppdaterKravEtterRetting(krav)
				
		// Forventer 3 krav, et for kjorebok, et for tilhørende timeforing og et for utlegg parkering
		assertEquals 3, liste.size()
				
		Krav oppdaterteUtleggKrav = liste[2]
	
		assertEquals 60D, oppdaterteUtleggKrav.antall
	}
	
	void testOppdaterKravEtterRettingKjorebokMedUtleggBelop() {
		//Oppdater utlegg til å passe utlegg taxi
		utlegg.utleggType = UtleggType.TAXI
		
		//Oppdater kjørebok til å passe testen
		kjorebok.utleggBelop = utlegg
		kjorebok.kjorteKilometer = 0
		kjorebok.transportmiddel = TransportMiddel.TAXI
		kjorebok.save(failOnError: true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(kjorebok.utleggBelop)
		kravUtlegg.save(failOnError: true, flush: true)
		
		utlegg.belop = 301D
		
		def liste = kravService.oppdaterKravEtterRetting(krav)
				
		// Forventer 3 krav, et for kjorebok, et for tilhørende timeforing og et for utlegg taxi
		assertEquals 3, liste.size()
				
		Krav oppdaterteUtleggKrav = liste[2]
	
		assertEquals 301D, oppdaterteUtleggKrav.antall
	}
	
	void testOppdaterKravEtterRettingKjorebokMedFlereUtlegg() {
		//Oppdater utlegg til å passe utlegg parkering
		utlegg.utleggType = UtleggType.PARKERING
		
		Utlegg utleggBom = new Utlegg()
		utleggBom.intervjuer = utlegg.intervjuer
		utleggBom.dato = utlegg.dato
		utleggBom.produktNummer = utlegg.produktNummer
		utleggBom.utleggType = UtleggType.BOMPENGER
		utleggBom.spesifisering = "Test"
		utleggBom.merknad = "En test"
		utleggBom.belop = 25D
		utleggBom.redigertAv = "krn"
		utleggBom.redigertDato = new Date()
		utleggBom.timeforingStatus = TimeforingStatus.SENDT_INN
				
		//Oppdater kjørebok til å passe testen
		kjorebok.utleggParkering = utlegg
		kjorebok.utleggBom = utleggBom
		kjorebok.save(failOnError: true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(kjorebok.utleggParkering)
		kravUtlegg.save(failOnError: true, flush: true)
		
		Krav kravUtleggBom = kravService.konverterUtleggTilKrav(kjorebok.utleggBom)
		kravUtleggBom.save(failOnError: true, flush: true)
				
		def liste = kravService.oppdaterKravEtterRetting(krav)
				
		// Forventer 4 krav, et for kjorebok, et for tilhørende timeforing, et for utlegg parkering
		// og et for utlegg bom
		assertEquals 4, liste.size()
	}
	
	void testOppdaterTimeforingKravEtterRetting() {
		Krav kr = kravService.konverterTimeforingTilKrav(time3)
		kr.save(failOnError: true, flush:true)
		assertNotNull kr
		
		Calendar c = Calendar.getInstance()
		c.setTime(time3.fra)
		c.add(Calendar.MINUTE, 30)
		time3.til = c.getTime()
		time3.produktNummer = "0333-1"
		
		Krav krav = kravService.oppdaterTimeforingKravEtterRetting(time3)
			
		assertNotNull krav
		
		assertEquals "0333-1", krav.produktNummer
		assertEquals 30, krav.antall
	}
	
	void testOppdaterTimeforingKravEtterRettingTimeforingNull() {
		Timeforing timeforing
		Krav krav = kravService.oppdaterTimeforingKravEtterRetting(timeforing)
		
		// Timeforing er null så forventer null i retur
		assertNull krav
	}
	
	void testOppdaterTimeforingKravEtterRettingKravNull() {
		Krav kr = kravService.konverterTimeforingTilKrav(time3)
		kr.save(failOnError: true, flush:true)
		assertNotNull kr
		
		kr.delete(flush: true)
		
		Krav krav = kravService.oppdaterTimeforingKravEtterRetting(time3)
				
		// Kravet er fjernet så forventer null i retur
		assertNull krav
	}
	
	void testOppdaterUtleggKravEtterRetting() {
		Krav kr = kravService.konverterUtleggTilKrav(utlegg)
		kr.save(failOnError: true, flush:true)
		assertNotNull kr
		
		utlegg.belop = 99.50D
		utlegg.produktNummer = "0333-3"
		
		Krav krav = kravService.oppdaterUtleggKravEtterRetting(utlegg)
			
		assertNotNull krav
		
		assertEquals "0333-3", krav.produktNummer
		assertEquals 99.5D, krav.antall
	}
	
	void testOppdaterUtleggKravEtterRettingTimeforingNull() {
		Utlegg utlegg
		Krav krav = kravService.oppdaterUtleggKravEtterRetting(utlegg)
		
		// Utlegg er null så forventer null i retur
		assertNull krav
	}
	
	void testOppdaterUtleggKravEtterRettingKravNull() {
		Krav kr = kravService.konverterUtleggTilKrav(utlegg)
		kr.save(failOnError: true, flush:true)
		assertNotNull kr
		
		kr.delete(flush: true)
		
		Krav krav = kravService.oppdaterUtleggKravEtterRetting(utlegg)
				
		// Kravet er fjernet så forventer null i retur
		assertNull krav
	}
	
	void testFinnTilhorendeKjorebokKravTime() {
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		Krav kr = kravService.finnTilhorendeKjorebokKrav(kravTime)
		
		assertNotNull kr
		
		assertEquals krav.id, kr.id
	}
	
	void testFinnTilhorendeKjorebokKravBom() {
		//Oppdater utlegg til å passe utlegg bom
		utlegg.utleggType = UtleggType.BOMPENGER
		
		//Oppdater kjørebok til å passe testen
		kjorebok.utleggBom = utlegg
		kjorebok.save(failOnError: true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.save(failOnError: true, flush: true)
		
		Krav kr = kravService.finnTilhorendeKjorebokKrav(kravUtlegg)
		
		assertNotNull kr
		
		assertEquals krav.id, kr.id
	}
	
	void testFinnTilhorendeKjorebokKravParkering() {
		//Oppdater utlegg til å passe utlegg parkering
		utlegg.utleggType = UtleggType.PARKERING
		
		//Oppdater kjørebok til å passe testen
		kjorebok.utleggParkering = utlegg
		kjorebok.save(failOnError: true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.save(failOnError: true, flush: true)
		
		Krav kr = kravService.finnTilhorendeKjorebokKrav(kravUtlegg)
		
		assertNotNull kr
		
		assertEquals krav.id, kr.id
	}

	void testFinnTilhorendeKjorebokKravFerje() {
		//Oppdater utlegg til å passe utlegg ferje
		utlegg.utleggType = UtleggType.BILLETT
		
		//Oppdater kjørebok til å passe testen
		kjorebok.utleggFerge = utlegg
		kjorebok.save(failOnError: true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.save(failOnError: true, flush: true)
		
		Krav kr = kravService.finnTilhorendeKjorebokKrav(kravUtlegg)
		
		assertNotNull kr
		
		assertEquals krav.id, kr.id
	}

	void testFinnTilhorendeKjorebokKravBelop() {
		//Oppdater utlegg til å passe utlegg belop
		utlegg.utleggType = UtleggType.BILLETT
		
		//Oppdater kjørebok til å passe testen
		kjorebok.utleggBelop = utlegg
		kjorebok.save(failOnError: true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.save(failOnError: true, flush: true)
		
		Krav kr = kravService.finnTilhorendeKjorebokKrav(kravUtlegg)
		
		assertNotNull kr
		
		assertEquals krav.id, kr.id
	}
	
	void testFinnTilhorendeKjorebokKravTaxi() {
		//Oppdater utlegg til å passe utlegg belop
		utlegg.utleggType = UtleggType.TAXI
		
		//Oppdater kjørebok til å passe testen
		kjorebok.utleggBelop = utlegg
		kjorebok.save(failOnError: true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.save(failOnError: true, flush: true)
		
		Krav kr = kravService.finnTilhorendeKjorebokKrav(kravUtlegg)
		
		assertNotNull kr
		
		assertEquals krav.id, kr.id
	}
		
	void testFinnTilhorendeKjorebokKravIngenTilhorende() {
		utlegg.utleggType = UtleggType.TELEFON
		
		Krav krav = kravService.konverterUtleggTilKrav(utlegg)
		krav.save(failOnError: true, flush: true)
				
		Krav kr = kravService.finnTilhorendeKjorebokKrav(krav)
		
		// Kravet tilhørende utlegget er ikke knyttet til noen
		// kjørebok så forventer at kr skal være null
		
		assertNull kr
	}
	
	void testFinnTilhorendeKjorebokKravNull() {
		Krav k
		
		Krav kr = kravService.finnTilhorendeKjorebokKrav(k)
		
		// Kravet gitt er null så forventer null
		
		assertNull kr
	}
	
	void testFinnMinutter() {
		Integer intInn = new Integer(150)
		
		Integer intUt = kravService.finnMinutter(intInn)

		// Integer som er input er 150 så forventer 30 i retur
		// 150 - (2x60) = 30
		assertEquals 30, intUt.intValue()
	}
	
	void testFinnMinutterNull() {
		Integer intInn
				
		Integer intUt = kravService.finnMinutter(intInn)
		
		// Integer som er input er NULL så forventer null i retur
		assertNull intUt
	}
	
	void testFinnTimer() {
		Integer intInn = new Integer(150)
		
		Integer intUt = kravService.finnTimer(intInn)

		// Integer som er input er 150 så forventer 2 hele timer i retur (2x60)
		
		assertEquals 2, intUt.intValue()
	}
	
	void testFinnTimerNull() {
		Integer intInn
		
		Integer intUt = kravService.finnTimer(intInn)

		// Integer som er input er NULL så forventer null i retur
		assertNull intUt
	}
	
	void testFinnUtleggKravKjorebokNull() {
		Kjorebok kjore
		
		def liste = kravService.finnUtleggKrav(kjore)
		
		// Forventer at liste er null siden kjørebok som er input er null
		assertNull liste
	}
	
	void testFinnUtleggKravKjorebokIngenUtlegg() {
		def liste = kravService.finnUtleggKrav(kjorebok)
		
		// Forventer at liste er null siden kjørebok ikke har noen
		// utlegg knyttet til seg
		assertNull liste
	}
	
	void testFinnUtleggKravBelop() {
		//Oppdater utlegg til å passe utlegg belop
		utlegg.utleggType = UtleggType.TAXI
				
		//Oppdater kjørebok til å passe testen
		kjorebok.utleggBelop = utlegg
		kjorebok.kjorteKilometer = 0
		kjorebok.save(failOnError: true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.save(failOnError: true, flush: true)
		
		def liste = kravService.finnUtleggKrav(kjorebok)
		
		assertNotNull liste
		
		def kravU = liste[0]
		
		// Forventer et utlegg i listen
		assertEquals 1, liste.size()
		assertEquals kravUtlegg.id, kravU.id	
	}
	
	void testFinnUtleggKravFerge() {
		//Oppdater utlegg til å passe utlegg belop
		utlegg.utleggType = UtleggType.BILLETT
				
		//Oppdater kjørebok til å passe testen
		kjorebok.utleggFerge = utlegg
		kjorebok.transportmiddel = TransportMiddel.FERJE
		kjorebok.kjorteKilometer = 0
		kjorebok.save(failOnError: true, flush: true)
				
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.save(failOnError: true, flush: true)
		
		def liste = kravService.finnUtleggKrav(kjorebok)
		
		assertNotNull liste
		
		def kravU = liste[0]
		
		// Forventer et utlegg i listen
		assertEquals 1, liste.size()
		assertEquals kravUtlegg.id, kravU.id
	}
	
	void testFinnUtleggKravParkering() {
		//Oppdater utlegg til å passe utlegg belop
		utlegg.utleggType = UtleggType.PARKERING
		
		//Oppdater kjørebok til å passe testen
		kjorebok.utleggParkering = utlegg
		kjorebok.save(failOnError: true, flush: true)
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.save(failOnError: true, flush: true)
		
		def liste = kravService.finnUtleggKrav(kjorebok)
		
		assertNotNull liste
		
		def kravU = liste[0]
		
		// Forventer et utlegg i listen
		assertEquals 1, liste.size()
		assertEquals kravUtlegg.id, kravU.id
	}
	
	void testFinnUtleggKravBom() {
		//Oppdater utlegg til å passe utlegg belop
		utlegg.utleggType = UtleggType.BOMPENGER
		
		//Oppdater kjørebok til å passe testen
		kjorebok.utleggBom = utlegg
		kjorebok.save(failOnError: true, flush: true)
		
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.save(failOnError: true, flush: true)
		
		def liste = kravService.finnUtleggKrav(kjorebok)
		
		assertNotNull liste
		
		def kravU = liste[0]
		
		// Forventer et utlegg i listen
		assertEquals 1, liste.size()
		assertEquals kravUtlegg.id, kravU.id
	}
	
	void testFinnTilhorendeKravTimeReise() {
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		def liste = kravService.finnTilhorendeKrav(kravTime)
		
		assertNotNull liste
		
		// Forventer at listen inneholder et tilhørende krav og at
		// det er kjørebok kravet
		assertEquals 1, liste.size()
		
		assertEquals liste[0].id, krav.id
	}
	
	void testFinnTilhorendeKravTimeReiseUtlegg() {
		utlegg.utleggType = UtleggType.BOMPENGER
		kjorebok.utleggBom = utlegg
		kjorebok.save(failOnError: true, flush: true)
				
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(kjorebok.utleggBom)
		kravUtlegg.save(failOnError: true, flush: true)
				
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		def liste = kravService.finnTilhorendeKrav(kravTime)
		
		assertNotNull liste
		
		// Forventer at listen inneholder 2 tilhørende krav, kjørebok-kravet og utlegg-kravet
		assertEquals 2, liste.size()
		
		assertEquals liste[0].id, krav.id
		assertEquals liste[1].id, kravUtlegg.id
	}
	
	void testFinnTilhorendeKravKjorebok() {
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		def liste = kravService.finnTilhorendeKrav(krav)
		
		assertNotNull liste
		
		// Forventer at listen inneholder et tilhørende krav og at
		// det er timeføring kravet
		assertEquals 1, liste.size()
		
		assertEquals liste[0].id, kravTime.id
	}
	
	void testFinnTilhorendeKravKjorebokUtlegg() {
		utlegg.utleggType = UtleggType.BOMPENGER
		kjorebok.utleggBom = utlegg
		kjorebok.save(failOnError: true, flush: true)
				
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(kjorebok.utleggBom)
		kravUtlegg.save(failOnError: true, flush: true)
				
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		def liste = kravService.finnTilhorendeKrav(krav)
		
		assertNotNull liste
		
		// Forventer at listen inneholder 2 tilhørende krav, timeføring-kravet og utlegg-kravet
		assertEquals 2, liste.size()
		
		assertEquals liste[0].id, kravTime.id
		assertEquals liste[1].id, kravUtlegg.id
	}
	
	void testFinnTilhorendeKravUtleggTaxi() {
		utlegg.utleggType = UtleggType.TAXI
		kjorebok.utleggBelop = utlegg
		kjorebok.transportmiddel = TransportMiddel.TAXI
		kjorebok.kjorteKilometer = 0
		kjorebok.save(failOnError: true, flush: true)
				
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(kjorebok.utleggBelop)
		kravUtlegg.save(failOnError: true, flush: true)
				
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		def liste = kravService.finnTilhorendeKrav(kravUtlegg)
		
		assertNotNull liste
		
		// Forventer at listen inneholder 2 tilhørende krav, kjørebok-kravet og timeføring-kravet
		assertEquals 2, liste.size()
		
		assertEquals liste[0].id, krav.id
		assertEquals liste[1].id, kravTime.id		
	}
	
	void testFinnTilhorendeKravUtleggBillettTog() {
		utlegg.utleggType = UtleggType.BILLETT
		kjorebok.utleggBelop = utlegg
		kjorebok.transportmiddel = TransportMiddel.TOG
		kjorebok.kjorteKilometer = 0
		kjorebok.save(failOnError: true, flush: true)
				
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(kjorebok.utleggBelop)
		kravUtlegg.save(failOnError: true, flush: true)
				
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		def liste = kravService.finnTilhorendeKrav(kravUtlegg)
		
		assertNotNull liste
		
		// Forventer at listen inneholder 2 tilhørende krav, kjørebok-kravet og timeføring-kravet
		assertEquals 2, liste.size()
		
		assertEquals liste[0].id, krav.id
		assertEquals liste[1].id, kravTime.id
	}
	
	void testFinnTilhorendeKravUtleggBillettBuss() {
		utlegg.utleggType = UtleggType.BILLETT
		kjorebok.utleggBelop = utlegg
		kjorebok.transportmiddel = TransportMiddel.BUSS_TRIKK
		kjorebok.kjorteKilometer = 0
		kjorebok.save(failOnError: true, flush: true)
				
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(kjorebok.utleggBelop)
		kravUtlegg.save(failOnError: true, flush: true)
				
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		def liste = kravService.finnTilhorendeKrav(kravUtlegg)
		
		assertNotNull liste
		
		// Forventer at listen inneholder 2 tilhørende krav, kjørebok-kravet og timeføring-kravet
		assertEquals 2, liste.size()
		
		assertEquals liste[0].id, krav.id
		assertEquals liste[1].id, kravTime.id
	}
	
	void testFinnTilhorendeKravUtleggBillettFerge() {
		utlegg.utleggType = UtleggType.BILLETT
		kjorebok.utleggFerge = utlegg
		kjorebok.transportmiddel = TransportMiddel.FERJE
		kjorebok.kjorteKilometer = 0
		kjorebok.save(failOnError: true, flush: true)
				
		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)
		krav.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(kjorebok.utleggFerge)
		kravUtlegg.save(failOnError: true, flush: true)
				
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		def liste = kravService.finnTilhorendeKrav(kravUtlegg)
		
		assertNotNull liste
		
		// Forventer at listen inneholder 2 tilhørende krav, kjørebok-kravet og timeføring-kravet
		assertEquals 2, liste.size()
		
		assertEquals liste[0].id, krav.id
		assertEquals liste[1].id, kravTime.id
	}
	
	void testTimeforingForandretEndretTil() {
		Timeforing time = new Timeforing()
		time.properties = time3.properties
		
		Calendar c = Calendar.getInstance()
		c.setTime(time3.til)
		c.add(Calendar.HOUR_OF_DAY, 1)
		time.til = c.getTime()
				
		boolean endret = kravService.timeforingForandret(time3, time)
		
		assertEquals true, endret
	}
	
	void testTimeforingForandretEndretFra() {
		Timeforing time = new Timeforing()
		time.properties = time3.properties
		
		Calendar c = Calendar.getInstance()
		c.setTime(time3.fra)
		c.add(Calendar.HOUR_OF_DAY, -1)
		time.fra = c.getTime()
				
		boolean endret = kravService.timeforingForandret(time3, time)
		
		assertEquals true, endret
	}
	
	void testTimeforingForandretEndretProduktnummer() {
		Timeforing time = new Timeforing()
		time.properties = time3.properties
		
		time.produktNummer = "0311-9"
						
		boolean endret = kravService.timeforingForandret(time3, time)
		
		assertEquals true, endret
	}
	
	void testTimeforingForandretIngenEndring() {
		Timeforing time = new Timeforing()
		time.properties = time3.properties
						
		boolean endret = kravService.timeforingForandret(time3, time)
		
		assertEquals false, endret
	}
	
	void testTimeforingForandretTimeforingEnNull() {
		Timeforing time
		boolean endret = kravService.timeforingForandret(time, time3)
		
		assertEquals true, endret
	}
	
	void testTimeforingForandretTimeforingToNull() {
		Timeforing time
		boolean endret = kravService.timeforingForandret(time3, time)
		
		assertEquals true, endret
	}
	
	void testKjorebokForandretFraEndret() {
		Kjorebok kjore = new Kjorebok()
		kjore.properties = kjorebok.properties
		
		Calendar c = Calendar.getInstance()
		c.setTime(kjore.fraTidspunkt)
		c.add(Calendar.HOUR_OF_DAY, -1)
		kjore.fraTidspunkt = c.getTime()
		
		boolean endret = kravService.kjorebokForandret(kjore, kjorebok)
		
		assertEquals true, endret
	}
	
	void testKjorebokForandretTilEndret() {
		Kjorebok kjore = new Kjorebok()
		kjore.properties = kjorebok.properties
		
		Calendar c = Calendar.getInstance()
		c.setTime(kjore.tilTidspunkt)
		c.add(Calendar.HOUR_OF_DAY, 1)
		kjore.tilTidspunkt = c.getTime()
		
		boolean endret = kravService.kjorebokForandret(kjore, kjorebok)
		
		assertEquals true, endret
	}
	
	void testKjorebokForandretKjorteKmEndret() {
		Kjorebok kjore = new Kjorebok()
		kjore.properties = kjorebok.properties
		
		kjore.kjorteKilometer = 99L
		
		boolean endret = kravService.kjorebokForandret(kjore, kjorebok)
		
		assertEquals true, endret
	}
	
	void testKjorebokForandretTransportmiddelEndret() {
		Kjorebok kjore = new Kjorebok()
		kjore.properties = kjorebok.properties
		
		kjore.transportmiddel = TransportMiddel.TAXI
		
		boolean endret = kravService.kjorebokForandret(kjore, kjorebok)
		
		assertEquals true, endret
	}
	
	void testKjorebokForandretProduktnummerEndret() {
		Kjorebok kjore = new Kjorebok()
		kjore.properties = kjorebok.properties
		
		kjore.produktNummer = "0399-8"
		
		boolean endret = kravService.kjorebokForandret(kjore, kjorebok)
		
		assertEquals true, endret
	}
	
	void testKjorebokForandretIngenEndring() {
		Kjorebok kjore = new Kjorebok()
		kjore.properties = kjorebok.properties
		
		boolean endret = kravService.kjorebokForandret(kjore, kjorebok)
		
		assertEquals false, endret
	}
	
	void testKjorebokForandretKjorebokEnNull() {
		Kjorebok kjore
		boolean endret = kravService.kjorebokForandret(kjore, kjorebok)
		
		assertEquals true, endret
	}
	
	void testKjorebokForandretKjorebokToNull() {
		Kjorebok kjore
		boolean endret = kravService.kjorebokForandret(kjorebok, kjore)
				
		assertEquals true, endret
	}
	
	void testUtleggForandretDatoEndret() {
		Utlegg u = new Utlegg()
		u.properties = utlegg.properties
		
		Calendar c = Calendar.getInstance()
		c.setTime(u.dato)
		c.add(Calendar.HOUR_OF_DAY, 1)
		u.dato = c.getTime()
		
		boolean endret = kravService.utleggForandret(utlegg, u)
		
		assertEquals true, endret
	}
	
	void testUtleggForandretBelopEndret() {
		Utlegg u = new Utlegg()
		u.properties = utlegg.properties
		
		u.belop = 94L
		
		boolean endret = kravService.utleggForandret(utlegg, u)
		
		assertEquals true, endret
	}
	
	void testUtleggForandretProduktnummerEndret() {
		Utlegg u = new Utlegg()
		u.properties = utlegg.properties
		
		u.produktNummer = "0988-1"
		
		boolean endret = kravService.utleggForandret(utlegg, u)
		
		assertEquals true, endret
	}
	
	void testUtleggForandretIngenEndring() {
		Utlegg u = new Utlegg()
		u.properties = utlegg.properties
		
		boolean endret = kravService.utleggForandret(utlegg, u)
		
		assertEquals false, endret
	}
	
	void testUtleggForandretUtleggEnNull() {
		Utlegg u
		boolean endret = kravService.utleggForandret(u, utlegg)
		
		assertEquals true, endret
	}
	
	void testUtleggForandretUtleggToNull() {
		Utlegg u
		boolean endret = kravService.utleggForandret(utlegg, u)
		
		assertEquals true, endret
	}
	
	void testKonverterTilKrav() {	
		def timeforingListe = [time3]
		def kjorebokListe = [kjorebok]
		def utleggListe = [utlegg]
		
		def liste = kravService.konverterTilKrav(timeforingListe, kjorebokListe, utleggListe)
		
		assertNotNull liste
		
		// Forventer at listen som returneres fra konverTilKrav metoden inneholder
		// 2 lister med Krav, en liste med nye krav og en liste med krav som er
		// "aktivert" (det fantes allerede krav tilknyttet denne timeføringen, 
		// kjøreboken eller utlegget) 
		assertEquals 2, liste.size()
		
		// Forventer at listen med nye krav inneholder 3 krav, et for time2, et
		// for kjorebok og et for utlegg
		assertEquals 3, liste[0].size()
	}
	
	void testKonverterTilKravIngenTimeforing() {
		def timeforingListe
		def kjorebokListe = [kjorebok]
		def utleggListe = [utlegg]
		
		def liste = kravService.konverterTilKrav(timeforingListe, kjorebokListe, utleggListe)
		
		assertNotNull liste
		
		// Forventer at listen som returneres fra konverTilKrav metoden inneholder
		// 2 lister med Krav, en liste med nye krav og en liste med krav som er
		// "aktivert" (det fantes allerede krav tilknyttet denne timeføringen,
		// kjøreboken eller utlegget)
		assertEquals 2, liste.size()
		
		// Forventer at listen med nye krav inneholder 2 krav, et for kjorebok og et for utlegg
		assertEquals 2, liste[0].size()
	}
	
	void testKonverterTilKravIngenKjorebok() {
		def timeforingListe = [time3]
		def kjorebokListe
		def utleggListe = [utlegg]
		
		def liste = kravService.konverterTilKrav(timeforingListe, kjorebokListe, utleggListe)
		
		assertNotNull liste
		
		// Forventer at listen som returneres fra konverTilKrav metoden inneholder
		// 2 lister med Krav, en liste med nye krav og en liste med krav som er
		// "aktivert" (det fantes allerede krav tilknyttet denne timeføringen,
		// kjøreboken eller utlegget)
		assertEquals 2, liste.size()
		
		// Forventer at listen med nye krav inneholder 2 krav, et for time3 og et for utlegg
		assertEquals 2, liste[0].size()
	}
	
	void testKonverterTilKravIngenUtlegg() {
		def timeforingListe = [time3]
		def kjorebokListe = [kjorebok]
		def utleggListe
		
		def liste = kravService.konverterTilKrav(timeforingListe, kjorebokListe, utleggListe)
		
		assertNotNull liste
		
		// Forventer at listen som returneres fra konverTilKrav metoden inneholder
		// 2 lister med Krav, en liste med nye krav og en liste med krav som er
		// "aktivert" (det fantes allerede krav tilknyttet denne timeføringen,
		// kjøreboken eller utlegget)
		assertEquals 2, liste.size()
		
		// Forventer at listen med nye krav inneholder 2 krav, et for time3 og et for kjorebok
		assertEquals 2, liste[0].size()
	}
	
	void testFinnKravForIntervjuer() {
		// Test intervjuerInstance er null
		Intervjuer intervjuerNull
		def liste = kravService.finnKravForIntervjuer(intervjuerNull)
		
		// Forventer at listen returnert skal være null når intervjuer inn er null
		assertNull liste
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(time3)
		kravTime.save(failOnError: true, flush: true)
		
		Krav kravKjore = kravService.konverterKjorebokTilKrav(kjorebok)
		kravKjore.save(failOnError: true, flush: true)
	
		Intervjuer intervjuer = Intervjuer.findByInitialer("spo")
		
		assertNotNull intervjuer
			
		def idListe = kravService.finnKravForIntervjuer(intervjuer)
		
		assertNotNull idListe
		
		// Forventer at listen med id'er inneholder 3 id'er, et for time3, et for kjorebok 
		// og en for timeOne generert i setUp() metode
		assertEquals 3, idListe.size()
	}
	
	void testSearchCriteria() {
		Krav kravTime = kravService.konverterTimeforingTilKrav(time3)
		kravTime.save(failOnError: true, flush: true)
		
		Krav kravKjore = kravService.konverterKjorebokTilKrav(kjorebok)
		kravKjore.save(failOnError: true, flush: true)
				
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.save(failOnError: true, flush: true)
		
		Intervjuer intervjuer = Intervjuer.findByInitialer("spo")
		assertNotNull intervjuer
		
		Intervjuer intervjuer2 = Intervjuer.findByInitialer("uhu")
		assertNotNull intervjuer2
		
		def search = new KravSearch()
		
		def pagination = ['dummy':false]
		
		def kravListe = kravService.search(search, pagination)
		
		assertNotNull kravListe
		// Forventer 5 krav i liste, 3 lag i denne testen og 2 fra setUp() metode
		assertEquals 5, kravListe.size()
		
		search.intervjuer = intervjuer.id
				
		def kravListeIntervjuer = kravService.search(search, pagination)
		
		assertNotNull kravListeIntervjuer
		// Forventer 3 krav tilhørende intervjuer
		assertEquals 3, kravListeIntervjuer.size()
		
		search.intervjuer = intervjuer2.id
		
		def kravListeIntervjuerTo = kravService.search(search, pagination)
		
		assertNotNull kravListeIntervjuerTo
		// Forventer 2 krav tilhørende intervjuer2
		assertEquals 2, kravListeIntervjuerTo.size()
				
		search.produktNummer = "3456-7"
		
		def kravListeTo = kravService.search(search, pagination)
		
		assertNotNull kravListeTo
		// Forventer et krav tilhørende intervjuer2 som har produktnummer "3456-7"
		assertEquals 1, kravListeTo.size()
		
		def searchTo = new KravSearch()
		
		searchTo.kravType = KravType.T
		
		def kravListeType = kravService.search(searchTo, pagination)
		
		assertNotNull kravListeType
		// Forventer 3 krav av kravtype T (time)
		assertEquals 3, kravListeType.size()
		
		def searchTre = new KravSearch()
		
		Long uId = utlegg.id
				
		searchTre.bilagsnummer = uId 
		
		def kravListeBilag = kravService.search(searchTre, pagination)
		
		assertNotNull kravListeBilag
		// Forventer et krav med bilagsnummer
		assertEquals 1, kravListeBilag.size()
		
		def searchFire = new KravSearch()
		
		Klynge klynge = Klynge.findByKlyngeNavn("Oslo")
		
		assertNotNull klynge
		
		searchFire.klynge = klynge.id 
		
		def kravListeKlynge = kravService.search(searchFire, pagination)
		
		assertNotNull kravListeKlynge
		// Forventer 5 krav som tilhører intervjuere som tilhører klynge "Oslo"
		assertEquals 5, kravListeKlynge.size()
		
		kravTime.kravStatus = KravStatus.INAKTIV
		kravTime.save(failOnError: true, flush: false)
		
		def searchFem = new KravSearch()
		
		searchFem.kravStatus = [KravStatus.INAKTIV]
		
		def kravListeStatus = kravService.search(searchFem, pagination)
		
		assertNotNull kravListeStatus
		// Forventer et krav som har kravstatus INAKTIV
		assertEquals 1, kravListeStatus.size()
		
		searchFem.kravStatus = [KravStatus.INAKTIV, KravStatus.OPPRETTET]
		
		def kravListeStatusTo = kravService.search(searchFem, pagination)
		
		assertNotNull kravListeStatusTo
		// Forventer 3 krav som har kravstatus INAKTIV (kravTime)
		// eller OPPRETTET (kravUtlegg og kravKjore)
		assertEquals 3, kravListeStatusTo.size()
		
		def searchSeks = new KravSearch()
		
		Calendar c = Calendar.getInstance()
		c.add(Calendar.DAY_OF_MONTH, 1)
		
		searchSeks.fraDato = c.getTime()
		
		def kravListeDato = kravService.search(searchSeks, pagination)
		
		
		assertNotNull kravListeDato
		
		// Forventer ingen krav som har dato etter i morgen -> tom liste
		assertEquals 0, kravListeDato.size()
		
		def searchSju = new KravSearch()
		
		Calendar ca = Calendar.getInstance()
		ca.add(Calendar.DAY_OF_MONTH, -1)
		
		searchSju.tilDato = ca.getTime()
		
		//Endre krav utlegg til å være eldre enn i går
		ca.add(Calendar.DAY_OF_MONTH, -4)
		kravUtlegg.dato = ca.getTime()
		kravUtlegg.save(failOnError: true, flush: true)
		
		def kravListeDatoTo = kravService.search(searchSju, pagination)
		
		assertNotNull kravListeDatoTo
		
		// Forventer et krav som har dato før i går
		assertEquals 1, kravListeDatoTo.size()
		
		
		def searchAtte = new KravSearch()
		
		Calendar calen = Calendar.getInstance()
		calen.add(Calendar.DAY_OF_MONTH, -1)
		
		searchAtte.tilDato = calen.getTime()

		calen.add(Calendar.DAY_OF_MONTH, -9)
		searchAtte.fraDato = calen.getTime()
		
		// fra er 10 dg tilbake i tid
		// til er 1 dg tilbake i tid		
						
		def kravListeDatoTre = kravService.search(searchAtte, pagination)
		
		assertNotNull kravListeDatoTre
		
		// Forventer et krav som har dato som faller inn under
		// fraDato og tilDato kriteriene og at dette kravet
		// er utleggKrav
		assertEquals 1, kravListeDatoTre.size()
		assertEquals kravListeDatoTre[0].id, kravUtlegg.id
	}
	
	void testCountSearch() {
		Krav kravTime = kravService.konverterTimeforingTilKrav(time3)
		kravTime.save(failOnError: true, flush: true)
		
		Krav kravKjore = kravService.konverterKjorebokTilKrav(kjorebok)
		kravKjore.save(failOnError: true, flush: true)
				
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.save(failOnError: true, flush: true)
		
		def search = new KravSearch()
		
		int cnt = kravService.countSearch(search)
		
		// Forventer 5 krav (alle) i telling
		assertEquals 5, cnt
	}
	
	void testGetAllIds() {
		Krav kravTime = kravService.konverterTimeforingTilKrav(time3)
		kravTime.save(failOnError: true, flush: true)
		
		Krav kravKjore = kravService.konverterKjorebokTilKrav(kjorebok)
		kravKjore.save(failOnError: true, flush: true)
				
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.save(failOnError: true, flush: true)
		
		def search = new KravSearch()
		
		def liste = kravService.hentKravIdListe(search)
		
		assertNotNull liste
		
		// Forventer 5 id'er i liste
		assertEquals 5, liste.size()
	}
	
	void testSetKravForIntervjuerForDatoInaktiv() {
		Krav kravKjore = kravService.konverterKjorebokTilKrav(kjorebok)
		kravKjore.save(failOnError: true, flush: true)
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(time3)
		kravTime.save(failOnError: true, flush: true)
		
		int cnt = kravService.setKravForIntervjuerForDatoInaktiv(kravTime)
		
		// Forventer at 2 andre krav for intervjuer blir satt til inaktiv
		assertEquals 2, cnt
	}
	
	void testAvvisKrav() {
		Krav kravTime = kravService.konverterTimeforingTilKrav(time3)
		kravTime.save(failOnError: true, flush: true)
		
		boolean avvist = kravService.avvisKrav(kravTime)
		
		assertEquals true, avvist	
	}
	
	void testGodkjennAlleBestodAutomatiskKontroll() {
		int cnt = kravService.godkjennAlleBestodAutomatiskKontroll()
		
		// Forventer at 2 krav hadde status BESTOD_AUTOMATISK_KONTROLL
		// og nå er blitt godkjent
		assertEquals 2, cnt
		
		def liste = Krav.findAllByKravStatus(KravStatus.GODKJENT)
		
		assertNotNull liste
		
		assertEquals 2, liste.size()
	}
	
	void testGodkjennKravForventerNull() {
		Krav kravTime = kravService.konverterTimeforingTilKrav(time3)
		kravTime.kravStatus = KravStatus.OVERSENDT_SAP
		kravTime.save(failOnError: true, flush: true)
		
		def godkjentKrav = kravService.godkjennKrav(kravTime)
		
		// Ikke lov og godkjenne krav med status OVERSENDT_SAP,
		// forventer null tilbake fra godkjennKrav metode
		assertNull godkjentKrav
		
		kravTime.kravStatus = KravStatus.TIL_RETTING_INTERVJUER
		kravTime.save(failOnError: true, flush: true)
		
		def godkjentKravTo = kravService.godkjennKrav(kravTime)
		
		// Ikke lov og godkjenne krav med status TIL_RETTING_INTERVJUER,
		// forventer null tilbake fra godkjennKrav metode
		assertNull godkjentKravTo
		
		kravTime.kravStatus = KravStatus.SENDES_TIL_INTERVJUER
		kravTime.save(failOnError: true, flush: true)
		
		def godkjentKravTre = kravService.godkjennKrav(kravTime)
		
		// Ikke lov og godkjenne krav med status SENDES_TIL_INTERVJUER,
		// forventer null tilbake fra godkjennKrav metode
		assertNull godkjentKravTre
	}
	
	void testGodkjennKrav() {
		utlegg.utleggType = UtleggType.BOMPENGER
		kjorebok.utleggBom = utlegg
		kjorebok.save(failOnError: true, flush: true)
				
		Krav kravKjore = kravService.konverterKjorebokTilKrav(kjorebok)
		kravKjore.save(failOnError: true, flush: true)
		
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(kjorebok.utleggBom)
		kravUtlegg.save(failOnError: true, flush: true)
				
		Krav kravTime = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTime.save(failOnError: true, flush: true)
		
		def godkjentKrav = kravService.godkjennKrav(kravKjore)
		
		assertNotNull godkjentKrav
		
		// Finn fram utlegg og time kravene
		def kUtlegg = Krav.get(kravUtlegg.id)
		def kTime = Krav.get(kravTime.id)
		
		assertNotNull kUtlegg
		assertNotNull kTime
		
		// Forventer at alle 3 krav har status godkjent
		assertEquals KravStatus.GODKJENT, godkjentKrav.kravStatus
		assertEquals KravStatus.GODKJENT, kUtlegg.kravStatus
		assertEquals KravStatus.GODKJENT, kTime.kravStatus
	}
	
	void testSearchIntervjuer() {
		// Sett krav som tilhører intervjuer1 og intervjuer2 til en
		// kravstatus somg gjør at disse intervjuerene skal dukke opp
		// i intervjuere til kontroll søket 
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.kravStatus = KravStatus.TIL_MANUELL_KONTROLL
		kravUtlegg.save(failOnError: true, flush: true)
				
		Krav kravTime = kravService.konverterTimeforingTilKrav(time3)
		kravTime.kravStatus = KravStatus.TIL_MANUELL_KONTROLL
		kravTime.save(failOnError: true, flush: true)
		
		def search = new IntervjuerKontrollSearch()
		def pagination = ['dummy':false]
		
		search.navn = "soreng"
		
		def listeNavn = kravService.searchIntervjuer(search, pagination)
		
		assertNotNull listeNavn
		
		// Forventer å finne en intervjuer
		assertEquals 1, listeNavn.size()
		
		Intervjuer intervjuerInstance = Intervjuer.findByInitialer("uhu")
		
		assertNotNull intervjuerInstance
		
		def searchTo = new IntervjuerKontrollSearch()
		searchTo.intervjuer = intervjuerInstance.id
		
		def listeInt = kravService.searchIntervjuer(searchTo, pagination)
		
		assertNotNull listeInt
		// Forventer å finne en intervjuer og at det er intervjuer intervjuerInstance
		assertEquals 1, listeInt.size()
		assertEquals listeInt[0].intervjuer.id, intervjuerInstance.id
	}
		
	void testCountSearchIntervjuer() {
		// Sett krav som tilhører intervjuer1 og intervjuer2 til en
		// kravstatus somg gjør at disse intervjuerene skal dukke opp
		// i intervjuere til kontroll søket
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.kravStatus = KravStatus.TIL_MANUELL_KONTROLL
		kravUtlegg.save(failOnError: true, flush: true)
				
		Krav kravTime = kravService.konverterTimeforingTilKrav(time3)
		kravTime.kravStatus = KravStatus.TIL_MANUELL_KONTROLL
		kravTime.save(failOnError: true, flush: true)
		
		def search = new IntervjuerKontrollSearch()
		
		int cnt = kravService.countSearchIntervjuer(search)
		
		// Forventer 2 intervjuere opprettet i setUp() metode
		assertEquals 2, cnt
	}
	
	void testSearchAllIntervjuer() {
		// Sett krav som tilhører intervjuer1 og intervjuer2 til en
		// kravstatus somg gjør at disse intervjuerene skal dukke opp
		// i intervjuere til kontroll søket
		Krav kravUtlegg = kravService.konverterUtleggTilKrav(utlegg)
		kravUtlegg.kravStatus = KravStatus.TIL_MANUELL_KONTROLL
		kravUtlegg.save(failOnError: true, flush: true)
				
		Krav kravTime = kravService.konverterTimeforingTilKrav(time3)
		kravTime.kravStatus = KravStatus.TIL_MANUELL_KONTROLL
		kravTime.save(failOnError: true, flush: true)
		
		def search = new IntervjuerKontrollSearch()
		
		def liste = kravService.searchAllIntervjuer(search)
		
		assertNotNull liste
		
		// Forventer 2 intervjuere opprettet i setUp() metode
		assertEquals 2, liste.size()
	}
	
	void testFinnKontrollSammendragForIntervjuer() {
		Krav kravKjore = kravService.konverterKjorebokTilKrav(kjorebok)
		kravKjore.kravStatus = KravStatus.TIL_MANUELL_KONTROLL
		kravKjore.save(failOnError: true, flush: true)
		
		Krav kravTimeKjore = kravService.konverterTimeforingTilKrav(kjorebok.timeforing)
		kravTimeKjore.kravStatus = KravStatus.TIL_MANUELL_KONTROLL
		kravTimeKjore.save(failOnError: true, flush: true)
		
		Krav kravTime = kravService.konverterTimeforingTilKrav(time3)
		kravTime.kravStatus = KravStatus.TIL_MANUELL_KONTROLL
		kravTime.save(failOnError: true, flush: true)
		
		Intervjuer intervjuer = Intervjuer.findByInitialer("spo")
		
		assertNotNull intervjuer
		
		def kontrollSammendrag = kravService.finnKontrollSammendragForIntervjuer(intervjuer)
		
		assertNotNull kontrollSammendrag
		
		assertEquals intervjuer, kontrollSammendrag.intervjuer
		
		// Forventer 3 krav, 2 x time og et kjørebok
		assertEquals 3, kontrollSammendrag.antallKrav			
		assertEquals 2, kontrollSammendrag.timeKrav
		assertEquals 1, kontrollSammendrag.kjorebokKrav
		assertEquals 0, kontrollSammendrag.utleggKrav
		assertEquals 0, kontrollSammendrag.totalBelop
		assertEquals 10, kontrollSammendrag.totalKm
		
		// Tilsammen 90 min -> 1t og 30 min
		assertEquals 1, kontrollSammendrag.totalTimer
		assertEquals 30, kontrollSammendrag.totalMinutter	
	}
}
