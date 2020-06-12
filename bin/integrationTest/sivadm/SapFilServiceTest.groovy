package sivadm



import java.text.SimpleDateFormat;
import java.util.Calendar

import org.codehaus.groovy.grails.commons.*

import sil.Krav
import sil.Lonnart
import sil.SapFil
import sil.type.FilType
import sil.type.KontoTekstType
import sil.type.MarkedType
import sil.type.SapFilStatusType;
import siv.type.ArbeidsType
import siv.type.IntervjuerArbeidsType
import siv.type.IntervjuerStatus
import siv.type.Kjonn
import siv.type.PeriodeType
import siv.type.ProsjektFinansiering
import siv.type.ProsjektModus
import siv.type.ProsjektStatus
import siv.type.TimeforingStatus
import siv.type.TransportMiddel
import siv.type.UndersokelsesType
import siv.type.UtleggType

class SapFilServiceTest extends GroovyTestCase {


	def sapFilService
	def kravService
	def config = ConfigurationHolder.config
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd")

	Date dato
	Intervjuer intervjuer
	Intervjuer intervjuerLokal
	Prosjekt proStatMarked
	Prosjekt prosjekt
	Prosjekt prosjektMarked
	Calendar cal

	Produkt produkt

	protected void setup() {
		super.setUp()

		dato = new Date()
		cal = Calendar.getInstance()

		populateLonnArter()

		ProsjektLeder prosjektLeder = new ProsjektLeder()
		prosjektLeder.navn = "Paal Soreng"
		prosjektLeder.initialer = "spo"
		prosjektLeder.epost = "spo"
		prosjektLeder.save(failOnError: true, flush: true)

		prosjekt = new Prosjekt()
		prosjekt.panel = true
		prosjekt.produktNummer = "12345"
		prosjekt.modus = ProsjektModus.MIXMODUS
		prosjekt.registerNummer = "123"
		prosjekt.prosjektNavn = "Levekaar"
		prosjekt.aargang = 2010
		prosjekt.prosjektLeder = prosjektLeder
		prosjekt.oppstartDato = dato
		prosjekt.avslutningsDato = dato
		prosjekt.prosjektStatus = ProsjektStatus.PLANLEGGING
		prosjekt.finansiering = ProsjektFinansiering.STAT
		prosjekt.prosentStat = 100
		prosjekt.prosentMarked = 0
		prosjekt.kommentar = "test"
		prosjekt.save(failOnError: true, flush: true)

		prosjektMarked = new Prosjekt()
		prosjektMarked.panel = true
		prosjektMarked.produktNummer = "45678"
		prosjektMarked.modus = ProsjektModus.MIXMODUS
		prosjektMarked.registerNummer = "124"
		prosjektMarked.prosjektNavn = "Levekaar - marked"
		prosjektMarked.aargang = 2010
		prosjektMarked.prosjektLeder = prosjektLeder
		prosjektMarked.oppstartDato = dato
		prosjektMarked.avslutningsDato = dato
		prosjektMarked.prosjektStatus = ProsjektStatus.PLANLEGGING
		prosjektMarked.finansiering = ProsjektFinansiering.MARKED
		prosjektMarked.prosentStat = 0
		prosjektMarked.prosentMarked = 100
		prosjektMarked.kommentar = "test"
		prosjektMarked.save(failOnError: true, flush: true)

		proStatMarked = new Prosjekt()
		proStatMarked.panel = true
		proStatMarked.produktNummer = "98765"
		proStatMarked.modus = ProsjektModus.MIXMODUS
		proStatMarked.registerNummer = "123"
		proStatMarked.prosjektNavn = "LevekaarXX"
		proStatMarked.aargang = 2010
		proStatMarked.prosjektLeder = prosjektLeder
		proStatMarked.oppstartDato = dato
		proStatMarked.avslutningsDato = dato
		proStatMarked.prosjektStatus = ProsjektStatus.PLANLEGGING
		proStatMarked.finansiering = ProsjektFinansiering.STAT_MARKED
		proStatMarked.prosentStat = 40
		proStatMarked.prosentMarked = 60
		proStatMarked.kommentar = "test"
		proStatMarked.save(failOnError: true, flush: true)

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

		intervjuerLokal = new Intervjuer()
		intervjuerLokal.klynge = klynge
		intervjuerLokal.initialer = "xxx"
		intervjuerLokal.intervjuerNummer = new Long(101002)
		intervjuerLokal.navn = "Stian Karlsen To"
		intervjuerLokal.kjonn = Kjonn.MANN
		intervjuerLokal.lokal = true

		cal.set Calendar.DAY_OF_MONTH, 15
		cal.set Calendar.MONTH, 2
		cal.set Calendar.YEAR, 1974

		intervjuerLokal.fodselsDato = cal.getTime()
		intervjuerLokal.gateAdresse = "Lille Grensen 5"
		intervjuerLokal.gateAdresse2 = ""
		intervjuerLokal.postNummer = "0159"
		intervjuerLokal.kommuneNummer = "0301"
		intervjuerLokal.epostPrivat = "stian.karlsen@ttteeesssttt.com"
		intervjuerLokal.epostJobb ="stian.karlsen@ssb.no"
		intervjuerLokal.status = IntervjuerStatus.AKTIV

		cal.set Calendar.DAY_OF_MONTH, 1
		cal.set Calendar.MONTH, 0
		cal.set Calendar.YEAR, 2009

		intervjuerLokal.ansattDato = cal.getTime()
		intervjuerLokal.avtaltAntallTimer = 700
		intervjuerLokal.arbeidsType = IntervjuerArbeidsType.BESOK

		cal.set Calendar.DAY_OF_MONTH, 3
		cal.set Calendar.MONTH, 2
		cal.set Calendar.YEAR, 2011

		intervjuerLokal.sluttDato = cal.getTime()
		intervjuerLokal.save(failOnError: true, flush: true)

		produkt = new Produkt()
		produkt.navn = "Statsfinansiert"
		produkt.produktNummer = "1234567"
		produkt.prosentMarked = 0
		produkt.prosentStat = 100
		produkt.finansiering = ProsjektFinansiering.STAT
		produkt.save(failOnError: true, flush: true)

		produkt = new Produkt()
		produkt.navn = "Delfinansiert"
		produkt.produktNummer = "9876543"
		produkt.prosentMarked = 60
		produkt.prosentStat = 40
		produkt.finansiering = ProsjektFinansiering.STAT_MARKED
		produkt.save(failOnError: true, flush: true)

		produkt = new Produkt()
		produkt.navn = "Markedsfinansiert"
		produkt.produktNummer = "4567890"
		produkt.finansiering = ProsjektFinansiering.MARKED
		produkt.prosentStat = 0
		produkt.prosentMarked = 100
		produkt.save(failOnError: true, flush: true)

// ========================================================================
// Følgende objekter blir brukt i  testGenererKravLinjeTime_1700_1750_rapportering_paa_produktnummer
// og må fjernes etter full overgang til arbeidsordrenummer.

		prosjekt = new Prosjekt()
		prosjekt.panel = true
		prosjekt.produktNummer = "3891"
		prosjekt.modus = ProsjektModus.MIXMODUS
		prosjekt.registerNummer = "123"
		prosjekt.prosjektNavn = "Levekaar"
		prosjekt.aargang = 2010
		prosjekt.prosjektLeder = prosjektLeder
		prosjekt.oppstartDato = dato
		prosjekt.avslutningsDato = dato
		prosjekt.prosjektStatus = ProsjektStatus.PLANLEGGING
		prosjekt.finansiering = ProsjektFinansiering.STAT
		prosjekt.prosentStat = 100
		prosjekt.prosentMarked = 0
		prosjekt.kommentar = "test"
		prosjekt.save(failOnError: true, flush: true)

		produkt = new Produkt()
		produkt.navn = "Statsfinansiert"
		produkt.produktNummer = "3891-8"
		produkt.prosentMarked = 0
		produkt.prosentStat = 100
		produkt.finansiering = ProsjektFinansiering.STAT
		produkt.save(failOnError: true, flush: true)

	}

	protected void tearDown() {
		super.tearDown()
		dato = null
		intervjuer = null
		cal = null
	}

	void testGenererForsteLinje2010TimeFil() {
		String testStr = sapFilService.genererForsteLinjeSapFil(20, FilType.TIME)

		String forventet = config.sil.sap.firmakode + "      " + config.sil.sap.kode.time + " " + sdf.format(dato) + config.sil.sap.forste.lopenummer + "           000000020000000000"

		assertEquals forventet, testStr
	}

	void testGenererForsteLinje0015ReiseFil() {
		String testStr = sapFilService.genererForsteLinjeSapFil(6, FilType.REISE)

		String forventet = config.sil.sap.firmakode + "      " + config.sil.sap.kode.reise + " " + sdf.format(dato) + config.sil.sap.forste.lopenummer + "           000000006000000000"

		assertEquals forventet, testStr
	}

	void testGenererForsteLinje0015ReiseFilTo() {
		// Test generering av fil for 0015Reise naar det allerede er skrevet en fil sammme dag

		SapFil fil = new SapFil()
		fil.setFil "xxx"
		fil.setFilType FilType.REISE
		fil.setStatus SapFilStatusType.OK
		fil.save(failOnError: true, flush: true)

		String testStr = sapFilService.genererForsteLinjeSapFil(6, FilType.REISE)

		int lNr = Integer.parseInt(config.sil.sap.forste.lopenummer) + 1

		String forventet = config.sil.sap.firmakode + "      " + config.sil.sap.kode.reise + " " + sdf.format(dato) + lNr + "           000000006000000000"

		assertEquals forventet, testStr
	}

	void testGenererForsteLinje2010TimeFilTo() {
		// Test generering av fil for 2010Time naar det allerede er skrevet en fil sammme dag

		SapFil fil = new SapFil()
		fil.setFil "zzz"
		fil.setFilType FilType.TIME
		fil.setStatus SapFilStatusType.OK
		fil.save(failOnError: true, flush: true)

		String testStr = sapFilService.genererForsteLinjeSapFil(20, FilType.TIME)

		int lNr = Integer.parseInt(config.sil.sap.forste.lopenummer) + 1

		String forventet = config.sil.sap.firmakode + "      " + config.sil.sap.kode.time + " " + sdf.format(dato) + lNr + "           000000020000000000"

		assertEquals forventet, testStr
	}

	void testGenererFilNavn2010Time() {
		String testStr = sapFilService.genererFilnavnSapFil(FilType.TIME)

		String forventet = config.sil.sap.firmakode + config.sil.sap.kode.time + sdf.format(dato) + config.sil.sap.forste.lopenummer + config.sil.sap.fil.extension

		assertEquals forventet, testStr
	}

	void testGenererFilNavn0015Reise() {
		String testStr = sapFilService.genererFilnavnSapFil(FilType.REISE)

		String forventet = config.sil.sap.firmakode + config.sil.sap.kode.reise + sdf.format(dato) + config.sil.sap.forste.lopenummer + config.sil.sap.fil.extension

		assertEquals forventet, testStr
	}

	void testGenererFilNavn2010TimeTo() {
		// Test generering av filnavn for 2010Time naar det allerede er skrevet en fil sammme dag
		SapFil fil = new SapFil()
		fil.setFil "zzz"
		fil.setFilType FilType.TIME
		fil.setStatus SapFilStatusType.OK
		fil.save(failOnError: true, flush: true)

		String testStr = sapFilService.genererFilnavnSapFil(FilType.TIME)

		int lNr = Integer.parseInt(config.sil.sap.forste.lopenummer) + 1

		String forventet = config.sil.sap.firmakode + config.sil.sap.kode.time + sdf.format(dato) + lNr + config.sil.sap.fil.extension

		assertEquals forventet, testStr
	}

	void testGenererFilNavn0015ReiseTo() {
		// Test generering av filnavn for 0015Reise naar det allerede er skrevet en fil sammme dag

		SapFil fil = new SapFil()
		fil.setFil "xxx"
		fil.setFilType FilType.REISE
		fil.setStatus SapFilStatusType.OK
		fil.save(failOnError: true, flush: true)

		String testStr = sapFilService.genererFilnavnSapFil(FilType.REISE)

		int lNr = Integer.parseInt(config.sil.sap.forste.lopenummer) + 1

		String forventet = config.sil.sap.firmakode + config.sil.sap.kode.reise + sdf.format(dato) + lNr + config.sil.sap.fil.extension

		assertEquals forventet, testStr
	}

	void testBeregnAntallBelopTime() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuer
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "1234567"
		cal.set Calendar.HOUR_OF_DAY, 9
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 35)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		def intArray = sapFilService.beregnAntallBelop(krav.antall, krav.kravType, 100, false)

		assertNotNull intArray

		assertEquals 1, intArray.size()

		int forventet = 58 // 35 min er avrundet 0.58 t og ganget med 100 blir dette 58

		assertEquals forventet, intArray[0].intValue()
	}

	void testBeregnAntallBelopTimeTo() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuer
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "1234567"
		cal.set Calendar.HOUR_OF_DAY, 9
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 79)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		def intArray = sapFilService.beregnAntallBelop(krav.antall, krav.kravType, 100, false)

		assertNotNull intArray

		assertEquals 1, intArray.size()

		int forventet = 132 // 79 min er avrundet 1.32 t og ganget med 100 blir dette 132

		assertEquals forventet, intArray[0].intValue()
	}

	void testBeregnAntallBelopTimeStatMarked() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuer
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "1234567"
		cal.set Calendar.HOUR_OF_DAY, 9
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 80)
		//cal.add(Calendar.MINUTE, 60)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		def intArray = sapFilService.beregnAntallBelop(krav.antall, krav.kravType, 50, true)

		assertNotNull intArray

		assertEquals 2, intArray.size()

		int forventet = 67 // 80 min / 2 er avrundet 0.67 t og ganget med 100 blir dette 67
		int forventetTo = 66 // 80 min / 60 min = 1.33 t og 1.33 - 0.67 = 0,66 og ganget med 100 blir dette 66
		//int forventet = 50 // 60 min / 2 er avrundet 0.5 t og ganget med 100 blir dette 50
		//int forventetTo = 50 // 60 min / 2 er avrundet 0.5 t og ganget med 100 blir dette 50

		assertEquals forventet, intArray[0].intValue()
		assertEquals forventetTo, intArray[1].intValue()
	}

	void testBeregnAntallBelopKjorebok() {
		Kjorebok kjorebok = new Kjorebok()
		kjorebok.intervjuer = intervjuer
		kjorebok.transportmiddel = TransportMiddel.EGEN_BIL
		kjorebok.timeforingStatus = TimeforingStatus.SENDT_INN
		kjorebok.fraTidspunkt = cal.getTime()
		cal.add(Calendar.MINUTE, 20)
		kjorebok.tilTidspunkt = cal.getTime()
		kjorebok.fraAdresse = "Storgata 100"
		kjorebok.tilAdresse = "Parkveien 200"
		kjorebok.fraPoststed = "Oslo"
		kjorebok.tilPoststed = "Oslo"
		kjorebok.produktNummer = "1234567"
		kjorebok.kjorteKilometer = 11
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = dato
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		kjorebok.timeforing = kjorebok.opprettTimeforing()
		kjorebok.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)

		krav.save(failOnError: true, flush: true)

		def intArray = sapFilService.beregnAntallBelop(krav.antall, krav.kravType, 100, false)

		assertNotNull intArray

		assertEquals 1, intArray.size()

		int forventet = 1100 // 11 km x 100

		assertEquals forventet, intArray[0].intValue()
	}

	void testBeregnAntallBelopKjorebokStatMarked() {
		Kjorebok kjorebok = new Kjorebok()
		kjorebok.intervjuer = intervjuer
		kjorebok.transportmiddel = TransportMiddel.EGEN_BIL
		kjorebok.timeforingStatus = TimeforingStatus.SENDT_INN
		kjorebok.fraTidspunkt = cal.getTime()
		cal.add(Calendar.MINUTE, 20)
		kjorebok.tilTidspunkt = cal.getTime()
		kjorebok.fraAdresse = "Storgata 100"
		kjorebok.tilAdresse = "Parkveien 200"
		kjorebok.fraPoststed = "Oslo"
		kjorebok.tilPoststed = "Oslo"
		kjorebok.produktNummer = "1234567"
		kjorebok.kjorteKilometer = 10
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = dato
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		kjorebok.timeforing = kjorebok.opprettTimeforing()
		kjorebok.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)

		krav.save(failOnError: true, flush: true)

		def intArray = sapFilService.beregnAntallBelop(krav.antall, krav.kravType, 60, true)

		assertNotNull intArray

		assertEquals 2, intArray.size()

		int forventet = 600 // 10 km * 60% x 100
		int forventetTo = 400 // 10 km * 40% x 100

		assertEquals forventet, intArray[0].intValue()
		assertEquals forventetTo, intArray[1].intValue()
	}

	void testBeregnAntallBelopUtlegg() {
		Date d = dato
		Utlegg utlegg = new Utlegg()
		utlegg.intervjuer = intervjuer
		utlegg.dato = d
		utlegg.produktNummer = "1234567"
		utlegg.utleggType = UtleggType.BILLETT
		utlegg.spesifisering = "Test"
		utlegg.merknad = "En test"
		utlegg.belop = 250
		utlegg.redigertAv = "krn"
		utlegg.redigertDato = dato
		utlegg.timeforingStatus = TimeforingStatus.SENDT_INN
		utlegg.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterUtleggTilKrav(utlegg)
		krav.save(failOnError: true, flush: true)

		def intArray = sapFilService.beregnAntallBelop(krav.antall, krav.kravType, 100, false)

		assertNotNull intArray

		assertEquals 1, intArray.size()

		int forventet = 25000 // 250 kr x 100

		assertEquals forventet, intArray[0].intValue()
	}

	void testBeregnAntallBelopUtleggStatMarked() {
		Date d = dato
		Utlegg utlegg = new Utlegg()
		utlegg.intervjuer = intervjuer
		utlegg.dato = d
		utlegg.produktNummer = "1234567"
		utlegg.utleggType = UtleggType.BILLETT
		utlegg.spesifisering = "Test"
		utlegg.merknad = "En test"
		utlegg.belop = 250
		utlegg.redigertAv = "krn"
		utlegg.redigertDato = dato
		utlegg.timeforingStatus = TimeforingStatus.SENDT_INN
		utlegg.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterUtleggTilKrav(utlegg)
		krav.save(failOnError: true, flush: true)

		def intArray = sapFilService.beregnAntallBelop(krav.antall, krav.kravType, 75, true)

		assertNotNull intArray

		assertEquals 2, intArray.size()

		int forventet = 18750 // 250 kr x 75% x 100
		int forventetTo = 6250 // 250 kr x 25% x 100

		assertEquals forventet, intArray[0].intValue()
		assertEquals forventetTo, intArray[1].intValue()
	}

	void testGenererKravLinjeTime() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuer
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "1234567"
		cal.set Calendar.HOUR_OF_DAY, 9
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 50)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 171

		String forventet = "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3112"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000083"							// 7 Antall 50/60 avrundet
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		println "forventet"
		println forventet
		println "kravLinje"
		println kravLinje

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeTime_1700_1750() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuer
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "1234567"
		cal.set Calendar.HOUR_OF_DAY, 17
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 50)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 171

		String forventet = "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3196"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000083"							// 7 Antall 50/60 avrundet
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		println "forventet"
		println forventet
		println "kravLinje"
		println kravLinje

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeTime_1600_1900() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuer
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "1234567"
		cal.set Calendar.HOUR_OF_DAY, 16
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 180)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 171 + 1 + 171

		String forventet = "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3112"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000050"							// 7 Antall 30/60
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "							// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		forventet += "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3196"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000250"							// 7 Antall (180-30)/60
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "							// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		println "forventet"
		println forventet
		println "kravLinje"
		println kravLinje

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeTimeLokal_1600_1900() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuerLokal
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "1234567"
		cal.set Calendar.HOUR_OF_DAY, 16
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 180)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 171 + 1 + 171

		String forventet = "00000101002"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3110"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000050"							// 7 Antall 30/60
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		forventet += "00000101002"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3192"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000250"							// 7 Antall (180-30)/60
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		println "forventet"
		println forventet
		println "kravLinje"
		println kravLinje

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeTime_1600_1630() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuer
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "1234567"
		cal.set Calendar.HOUR_OF_DAY, 16
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 30)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 171

		String forventet = "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3112"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000050"							// 7 Antall 30/60
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeTime_1600_1631() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuer
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "1234567"
		cal.set Calendar.HOUR_OF_DAY, 16
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 31)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 171 + 1 + 171

		String forventet = "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3112"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000050"							// 7 Antall 30/60
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		forventet += "00000101001"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3196"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000002"							// 7 Antall 1/60, avrundet
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeTime_1629_1645() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuer
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "1234567"
		cal.set Calendar.HOUR_OF_DAY, 16
		cal.set Calendar.MINUTE, 29
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 16)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 171 + 1 + 171

		String forventet = "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3112"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000002"							// 7 Antall 1/60, avrundet
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		forventet += "00000101001"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3196"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000025"							// 7 Antall 15/60
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeTime_1630_2130() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuer
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "1234567"
		cal.set Calendar.HOUR_OF_DAY, 16
		cal.set Calendar.MINUTE, 30
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.HOUR, 5)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 171

		String forventet = "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3196"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000500"							// 7 Antall 5t
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeTimeLokalIntervjuer() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuerLokal
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "1234567"
		cal.set Calendar.HOUR_OF_DAY, 9
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 50)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 171

		String forventet = "00000101002"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3110"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000083"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeTimeLokalIntervjuer_1700_1750() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuerLokal
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "1234567"
		cal.set Calendar.HOUR_OF_DAY, 17
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 50)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 171

		String forventet = "00000101002"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3192"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000083"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjerTimeStatMarked() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuer
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "9876543"
		cal.set Calendar.HOUR_OF_DAY, 9
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 50)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)


		//Fordeling på produktNummer 9876543 er 40% stat og 60% marked

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		// Linje 1 + linjeskift + linje 2
		int forventetSize = 171 + 1 + 171

		String forventet = "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3112"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000033"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    9876543 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		forventet += "00000101001"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3112"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000050"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    9876543 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked	// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjerTimeStatMarked_1700_1750() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuer
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "9876543"
		cal.set Calendar.HOUR_OF_DAY, 17
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 50)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)


		//Fordeling på produktNummer 9876 er 40% stat og 60% marked

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		// Linje 1 + linjeskift + linje 2
		int forventetSize = 171 + 1 + 171

		String forventet = "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3196"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000033"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    9876543 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		forventet += "00000101001"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3196"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000050"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    9876543 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked	// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeTimePensjonist() {
		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = genererPensjonistIntervjuer()
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = "1234567"
		cal.set Calendar.HOUR_OF_DAY, 9
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 50)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 171

		String forventet = "00000101999"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "2003"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000083"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "							// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeTimeOvertid() {
		Skjema sk = genererOvertidSkjema()

		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuerLokal
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = sk.delProduktNummer
		cal.set Calendar.YEAR, 2011
		cal.set Calendar.MONTH, 1
		cal.set Calendar.DAY_OF_MONTH, 19
		cal.set Calendar.HOUR_OF_DAY, 9
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 50)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 171 + 171 + 1

		String forventet = "00000101002"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3110"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000083"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    6789010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		// Pluss ny linje for overtid
		forventet += "00000101002"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3116"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000083"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    6789010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjerTimeOvertidStatMarked() {
		Skjema sk = genererOvertidSkjemaStatMarked()

		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuerLokal
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = sk.delProduktNummer
		cal.set Calendar.YEAR, 2011
		cal.set Calendar.MONTH, 1
		cal.set Calendar.DAY_OF_MONTH, 19
		cal.set Calendar.HOUR_OF_DAY, 9
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 50)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		// Fordelingen på skjema er 67% stat og 33% marked

		//Linje 1 + linjeskift + linje 2 + linjeskift + linje 3 + linjeskift + linje 4
		int forventetSize = 171 + 1 + 171 + 1 + 171 + 1 + 171

		String forventet = "00000101002"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3110"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000056"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    5566010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		// Pluss ny linje for overtid
		forventet += "00000101002"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3116"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000056"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    5566010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		forventet += "00000101002"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3110"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000027"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    5566010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked	// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		// Pluss ny linje for overtid
		forventet += "00000101002"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3116"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000027"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    5566010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked	// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjerTimeLokalOvertidStatMarked_1600_1800() {
		Skjema sk = genererOvertidSkjemaStatMarked()

		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuerLokal
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = sk.delProduktNummer
		cal.set Calendar.YEAR, 2011
		cal.set Calendar.MONTH, 1
		cal.set Calendar.DAY_OF_MONTH, 19
		cal.set Calendar.HOUR_OF_DAY, 16
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 120)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		// Fordelingen på skjema er 67% stat og 33% marked
		// 1600-1630: stat: 0,5*0,67=0,34 marked: 0,5*0,33=0,17
		// 1630-1800: stat: 1,5*0,67=1,01 marked: 1,5*0,33=0,50

		//Linje 1 + linjeskift + linje 2 + linjeskift + linje 3 + linjeskift + linje 4 osv.
		int forventetSize = 171 + 1 + 171 + 1 + 171 + 1 + 171 + 1 + 171 + 1 + 171

		String forventet = "00000101002"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3110"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000034"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    5566010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		forventet += "00000101002"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3192"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000101"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    5566010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		// Pluss ny linje for overtid
		forventet += "00000101002"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3116"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000134"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    5566010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		forventet += "00000101002"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3110"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000017"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    5566010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked	// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		forventet += "00000101002"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3192"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000050"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    5566010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked	// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		// Pluss ny linje for overtid
		forventet += "00000101002"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3116"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000066"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    5566010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked	// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjerLokalTimeOvertidStatMarked_1700_1750() {
		Skjema sk = genererOvertidSkjemaStatMarked()

		Timeforing timeOne = new Timeforing()
		timeOne.arbeidsType = ArbeidsType.INTERVJUE
		timeOne.intervjuer = intervjuerLokal
		timeOne.timeforingStatus = TimeforingStatus.SENDT_INN
		timeOne.produktNummer = sk.delProduktNummer
		cal.set Calendar.YEAR, 2011
		cal.set Calendar.MONTH, 1
		cal.set Calendar.DAY_OF_MONTH, 19
		cal.set Calendar.HOUR_OF_DAY, 17
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		timeOne.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 50)
		timeOne.til = cal.getTime()
		timeOne.redigertAv = "krn"
		timeOne.redigertDato = dato
		timeOne.save(failOnError: true, flush: true)

		Krav krav = kravService.konverterTimeforingTilKrav(timeOne)
		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		// Fordelingen på skjema er 67% stat og 33% marked

		//Linje 1 + linjeskift + linje 2 + linjeskift + linje 3 + linjeskift + linje 4
		int forventetSize = 171 + 1 + 171 + 1 + 171 + 1 + 171

		String forventet = "00000101002"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3192"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000056"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    5566010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		// Pluss ny linje for overtid
		forventet += "00000101002"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3116"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000056"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    5566010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		forventet += "00000101002"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3192"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000027"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    5566010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked	// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330\n"		// 24 Kontoart

		// Pluss ny linje for overtid
		forventet += "00000101002"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += "        "							// 8 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += " "								// 1 blank
		forventet += "3116"								// 4 Lønnart
		forventet += "       "							// 7 blanke
		forventet += "0000027"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    5566010 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked	// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    1330"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeReiseKjorebokEgenBil() {
		Kjorebok kjorebok = new Kjorebok()
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
		kjorebok.produktNummer = "1234567"
		kjorebok.kjorteKilometer = 10
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = dato
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		kjorebok.timeforing = kjorebok.opprettTimeforing()
		kjorebok.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 170

		String forventet = "00000101001"				// Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5711"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0001000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeReiseKjorebokEgenBilMedPassasjerer() {
		Kjorebok kjorebok = new Kjorebok()
		kjorebok.intervjuer = intervjuer
		kjorebok.transportmiddel = TransportMiddel.EGEN_BIL
		kjorebok.timeforingStatus = TimeforingStatus.SENDT_INN
		kjorebok.antallPassasjerer = 2
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
		kjorebok.produktNummer = "1234567"
		kjorebok.kjorteKilometer = 10
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = dato
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		kjorebok.timeforing = kjorebok.opprettTimeforing()
		kjorebok.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		// Linje 1 + linjeskift + passasjerlinje 1 + linjeskift + passasjerlinje 2
		int forventetSize = 170 + 1 + 170 + 1 +170

		String forventet = "00000101001"				// Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5711"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0001000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311\n"		// 24 Kontoart

		// Passasjer 1
		forventet += "00000101001"						// Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5712"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0001000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311\n"		// 24 Kontoart

		// Passasjer 2
		forventet += "00000101001"						// Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5712"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0001000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311"			// 24 Kontoart

		//assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjerReiseKjorebokEgenBilStatMarked() {
		Kjorebok kjorebok = new Kjorebok()
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
		kjorebok.produktNummer = "9876543"
		kjorebok.kjorteKilometer = 10
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = dato
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		kjorebok.timeforing = kjorebok.opprettTimeforing()
		kjorebok.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		// Linje 1 + linjeskift + 170
		int forventetSize = 170 + 1 + 170

		String forventet = "00000101001"				// Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5711"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0000400"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    9876543 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311\n"		// 24 Kontoart

		forventet += "00000101001"						// Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5711"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0000600"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    9876543 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked	// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	/*
	 *	Tromsø har egen lønnar for km godtgjørelse og intervjuere
	 * 	som er registrert med kommunenummer 1902 Tromsø skal da
	 * 	ha egen lønnart ved km godtgjørelse
	 */
	void testGenererKravLinjeReiseKjorebokEgenBilTromsoStat() {
		Intervjuer inter = genererTromsoIntervjuer()

		Kjorebok kjorebok = new Kjorebok()
		kjorebok.intervjuer = inter
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
		kjorebok.fraPoststed = "Tromsø"
		kjorebok.tilPoststed = "Tromsø"
		kjorebok.produktNummer = "1234567"
		kjorebok.kjorteKilometer = 10
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = dato
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		kjorebok.timeforing = kjorebok.opprettTimeforing()
		kjorebok.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 170

		String forventet = "00000101901"				// Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5718"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0001000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	/*
	*	Tromsø har egen lønnar for km godtgjørelse og intervjuere
	* 	som er registrert med kommunenummer 1902 Tromsø skal da
	* 	ha egen lønnart ved km godtgjørelse
	*/
	void testGenererKravLinjeReiseKjorebokEgenBilTromsoMarked() {
		Intervjuer inter = genererTromsoIntervjuer()

		Kjorebok kjorebok = new Kjorebok()
		kjorebok.intervjuer = inter
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
		kjorebok.fraPoststed = "Tromsø"
		kjorebok.tilPoststed = "Tromsø"
		kjorebok.produktNummer = "4567890"
		kjorebok.kjorteKilometer = 10
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = dato
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		kjorebok.timeforing = kjorebok.opprettTimeforing()
		kjorebok.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 170

		String forventet = "00000101901"					// Intervjuernr
		forventet += "                             "		// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5718"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0001000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted			// 3 Kostnadssted
		forventet += "    4567890 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	/*
    *	Tromsø har egen lønnar for km godtgjørelse og intervjuere
    * 	som er registrert med kommunenummer 1902 Tromsø skal da
    * 	ha egen lønnart ved km godtgjørelse
    */
	void testGenererKravLinjeReiseKjorebokEgenBilTromsoStatMarked() {
		Intervjuer inter = genererTromsoIntervjuer()

		Kjorebok kjorebok = new Kjorebok()
		kjorebok.intervjuer = inter
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
		kjorebok.fraPoststed = "Tromsø"
		kjorebok.tilPoststed = "Tromsø"
		kjorebok.produktNummer = "9876543"
		kjorebok.kjorteKilometer = 10
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = dato
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		kjorebok.timeforing = kjorebok.opprettTimeforing()
		kjorebok.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		// Linje 1 + linjeskift + 170
		int forventetSize = 170 + 1 + 170

		String forventet = "00000101901"					// Intervjuernr
		forventet += "                             "		// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5718"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0000400"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted			// 3 Kostnadssted
		forventet += "    9876543 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311\n"		// 24 Kontoart

		forventet += "00000101901"						// Intervjuernr
		forventet += "                             "		// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5718"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0000600"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted			// 3 Kostnadssted
		forventet += "    9876543 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjerReiseKjorebokEgenBilStatMarkedMedPassasjer() {
		Kjorebok kjorebok = new Kjorebok()
		kjorebok.intervjuer = intervjuer
		kjorebok.transportmiddel = TransportMiddel.EGEN_BIL
		kjorebok.antallPassasjerer = 1
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
		kjorebok.produktNummer = "9876543"
		kjorebok.kjorteKilometer = 10
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = dato
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		kjorebok.timeforing = kjorebok.opprettTimeforing()
		kjorebok.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		// Linje 1 + linjeskift + 170 + linjeskift + passasjer stat + linjeskift + passasjer marked
		int forventetSize = 170 + 1 + 170 + 1 + 170 + 1 + 170

		String forventet = "00000101001"				// Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5711"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0000400"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    9876543 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311\n"		// 24 Kontoart

		forventet += "00000101001"						// Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5711"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0000600"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    9876543 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked	// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311\n"		// 24 Kontoart

		forventet += "00000101001"						// Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5712"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0000400"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    9876543 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311\n"		// 24 Kontoart

		forventet += "00000101001"						// Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5712"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0000600"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    9876543 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked	// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeReiseKjorebokMotorsykkel() {
		Kjorebok kjorebok = new Kjorebok()
		kjorebok.intervjuer = intervjuer
		kjorebok.transportmiddel = TransportMiddel.MOTORSYKKEL
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
		kjorebok.produktNummer = "1234567"
		kjorebok.kjorteKilometer = 10
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = dato
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		kjorebok.timeforing = kjorebok.opprettTimeforing()
		kjorebok.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 170

		String forventet = "00000101001"				// Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5716"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0001000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeReiseKjorebokBaat() {
		Kjorebok kjorebok = new Kjorebok()
		kjorebok.intervjuer = intervjuer
		kjorebok.transportmiddel = TransportMiddel.MOTOR_BAAT
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
		kjorebok.produktNummer = "1234567"
		kjorebok.kjorteKilometer = 10
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = dato
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		kjorebok.timeforing = kjorebok.opprettTimeforing()
		kjorebok.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 170

		String forventet = "00000101001"				// Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5715"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0001000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeReiseKjorebokMoped() {
		Kjorebok kjorebok = new Kjorebok()
		kjorebok.intervjuer = intervjuer
		kjorebok.transportmiddel = TransportMiddel.MOPED_SYKKEL
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
		kjorebok.produktNummer = "1234567"
		kjorebok.kjorteKilometer = 10
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = dato
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		kjorebok.timeforing = kjorebok.opprettTimeforing()
		kjorebok.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterKjorebokTilKrav(kjorebok)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 170

		String forventet = "00000101001"				// Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5717"								// 4 Lønnart
		forventet += "0000000"							// 7 Beløp
		forventet += "0001000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeReiseUtlegg() {
		Date d = dato
		Utlegg utlegg = new Utlegg()
		utlegg.intervjuer = intervjuer
		utlegg.dato = d
		utlegg.produktNummer = "1234567"
		utlegg.utleggType = UtleggType.BILLETT
		utlegg.spesifisering = "Test"
		utlegg.merknad = "En test"
		utlegg.belop = 250
		utlegg.redigertAv = "krn"
		utlegg.redigertDato = dato
		utlegg.timeforingStatus = TimeforingStatus.SENDT_INN
		utlegg.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterUtleggTilKrav(utlegg)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 170

		String forventet = "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "5705"								// 4 Lønnart
		forventet += "0025000"							// 7 Beløp
		forventet += "0000000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2311"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeUtlegg() {
		Date d = dato
		Utlegg utlegg = new Utlegg()
		utlegg.intervjuer = intervjuer
		utlegg.dato = d
		utlegg.produktNummer = "1234567"
		utlegg.utleggType = UtleggType.FRIMERKER
		utlegg.spesifisering = "Test"
		utlegg.merknad = "En test"
		utlegg.belop = 250
		utlegg.redigertAv = "krn"
		utlegg.redigertDato = dato
		utlegg.timeforingStatus = TimeforingStatus.SENDT_INN
		utlegg.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterUtleggTilKrav(utlegg)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 170

		String forventet = "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "1462"								// 4 Lønnart
		forventet += "0025000"							// 7 Beløp
		forventet += "0000000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1234567 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2280"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjerUtleggStatMarkedProsjekt() {
		Date d = dato
		Utlegg utlegg = new Utlegg()
		utlegg.intervjuer = intervjuer
		utlegg.dato = d
		utlegg.produktNummer = "9876543"
		utlegg.utleggType = UtleggType.FRIMERKER
		utlegg.spesifisering = "Test"
		utlegg.merknad = "En test"
		utlegg.belop = 250
		utlegg.redigertAv = "krn"
		utlegg.redigertDato = dato
		utlegg.timeforingStatus = TimeforingStatus.SENDT_INN
		utlegg.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterUtleggTilKrav(utlegg)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		// Linje 1 + linjeskift + 170
		int forventetSize = 170 + 1 + 170

		String forventet = "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "1462"								// 4 Lønnart
		forventet += "0010000"							// 7 Beløp
		forventet += "0000000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    9876543 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2280\n"		// 24 Kontoart

		// Linje 2 - marked
		forventet += "00000101001"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "1462"								// 4 Lønnart
		forventet += "0015000"							// 7 Beløp
		forventet += "0000000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    9876543 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked	// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2280"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}


	void testGenererKravLinjeProduktStat() {
		Produkt produktFagforening = new Produkt()
		produktFagforening.navn = "Fagforeningsarbeid"
		produktFagforening.produktNummer = "3220001"
		produktFagforening.finansiering = ProsjektFinansiering.STAT
		produktFagforening.prosentStat = 100
		produktFagforening.prosentMarked = 0
		produktFagforening.save(failOnError: true, flush: true)

		Date d = dato
		Utlegg utlegg = new Utlegg()
		utlegg.intervjuer = intervjuer
		utlegg.dato = d
		utlegg.produktNummer = "3220001"
		utlegg.utleggType = UtleggType.FRIMERKER
		utlegg.spesifisering = "Test"
		utlegg.merknad = "En test"
		utlegg.belop = 250
		utlegg.redigertAv = "krn"
		utlegg.redigertDato = dato
		utlegg.timeforingStatus = TimeforingStatus.SENDT_INN
		utlegg.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterUtleggTilKrav(utlegg)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		int forventetSize = 170

		String forventet = "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "1462"								// 4 Lønnart
		forventet += "0025000"							// 7 Beløp
		forventet += "0000000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    3220001 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2280"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testGenererKravLinjeProduktStatMarked() {
		Produkt produktAdmin = new Produkt()
		produktAdmin.navn = "Administrasjon"
		produktAdmin.produktNummer = "1004001"
		produktAdmin.finansiering = ProsjektFinansiering.STAT_MARKED
		produktAdmin.prosentStat = 50
		produktAdmin.prosentMarked = 50
		produktAdmin.save(failOnError: true, flush: true)

		Date d = dato
		Utlegg utlegg = new Utlegg()
		utlegg.intervjuer = intervjuer
		utlegg.dato = d
		utlegg.produktNummer = "1004001"
		utlegg.utleggType = UtleggType.FRIMERKER
		utlegg.spesifisering = "Test"
		utlegg.merknad = "En test"
		utlegg.belop = 250
		utlegg.redigertAv = "krn"
		utlegg.redigertDato = dato
		utlegg.timeforingStatus = TimeforingStatus.SENDT_INN
		utlegg.save(failOnError:true, flush: true)

		Krav krav = kravService.konverterUtleggTilKrav(utlegg)

		krav.save(failOnError: true, flush: true)

		String kravLinje = sapFilService.genererKravLinje(krav)

		// Linje 1 + linjeskift + 170
		int forventetSize = 170 + 1 + 170

		String forventet = "00000101001"				// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "1462"								// 4 Lønnart
		forventet += "0012500"							// 7 Beløp
		forventet += "0000000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1004001 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.stat		// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2280\n"		// 24 Kontoart

		// Linje 2 - marked
		forventet += "00000101001"						// 11 Intervjuernr
		forventet += "                             "	// 29 blanke
		forventet += sdf.format(krav.dato)				// 8 Arbeidsdato yyyyMMdd
		forventet += "        "							// 8 blanke
		forventet += "1462"								// 4 Lønnart
		forventet += "0012500"							// 7 Beløp
		forventet += "0000000"							// 7 Antall
		forventet += "       "							// 7 blanke før kostnadssted
		forventet += config.sil.sap.kostnadssted		// 3 Kostnadssted
		forventet += "    1004001 "						// 12 Arbeidsordrenummer
		forventet += "                        "			// 24 blanke
		forventet += "    "								// 4 blanke før kontopost
		forventet += config.sil.sap.kontopost.marked	// 6 Kontopost
		forventet += "               -"					// 16 stiplet
		forventet += "                    2280"			// 24 Kontoart

		assertEquals forventetSize, kravLinje.size()
		assertEquals forventet, kravLinje
	}

	void testErHelg() {
		cal.set Calendar.DAY_OF_MONTH, 20
		cal.set Calendar.MONTH, 1
		cal.set Calendar.YEAR, 2011

		boolean erHelg = sapFilService.erHelg(cal.getTime())

		assertEquals erHelg, true

		cal.set Calendar.DAY_OF_MONTH, 21

		erHelg = sapFilService.erHelg(cal.getTime())

		assertEquals erHelg, false
	}

	void testErPensjonist() {

		cal.set Calendar.DAY_OF_MONTH, 20
		cal.set Calendar.MONTH, 1
		cal.set Calendar.YEAR, 2011

		boolean erPensjonist = sapFilService.erPensjonist(genererPensjonistIntervjuer())

		assertEquals true, erPensjonist

		erPensjonist = sapFilService.erPensjonist(intervjuer)

		assertEquals false, erPensjonist

		erPensjonist = sapFilService.erPensjonist(genererPensjonistIntervjuerTo())

		assertEquals true, erPensjonist

		erPensjonist = sapFilService.erPensjonist(genererPensjonistIntervjuerTre())

		assertEquals false, erPensjonist
	}

	private void populateLonnArter() {
		/* LØNNARTER */
		Lonnart lonn_1 = new Lonnart(lonnartNummer: "2003", navn: "Pensjonistavlønning", konto: "1620211330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_1.save(failOnError: true, flush: true)
		Lonnart lonn_2 = new Lonnart(lonnartNummer: "2003", navn: "Pensjonistavlønning", konto: "1620011330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_2.save(failOnError: true, flush: true)
		Lonnart lonn_3 = new Lonnart(lonnartNummer: "3110", navn: "Timelønn lokal", konto: "1620011330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_3.save(failOnError: true, flush: true)
		Lonnart lonn_4 = new Lonnart(lonnartNummer: "3110", navn: "Timelønn lokal", konto: "1620211330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_4.save(failOnError: true, flush: true)
		Lonnart lonn_5 = new Lonnart(lonnartNummer: "3112", navn: "Timelønn", konto: "1620011330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_5.save(failOnError: true, flush: true)
		Lonnart lonn_6 = new Lonnart(lonnartNummer: "3112", navn: "Timelønn", konto: "1620211330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_6.save(failOnError: true, flush: true)
		/*
		Lonnart lonn_7 = new Lonnart(lonnartNummer: "3114", navn: "Øvelsestid A-tab.", konto: "1620011330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_7.save(failOnError: true, flush: true)
		Lonnart lonn_8 = new Lonnart(lonnartNummer: "3114", navn: "Øvelsestid A-tab.", konto: "1620211330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_8.save(failOnError: true, flush: true)
		Lonnart lonn_9 = new Lonnart(lonnartNummer: "3116", navn: "Overtid", konto: "1620011330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_9.save(failOnError: true, flush: true)
		Lonnart lonn_10 = new Lonnart(lonnartNummer: "3116", navn: "Overtid", konto: "1620211330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_10.save(failOnError: true, flush: true)
		Lonnart lonn_11 = new Lonnart(lonnartNummer: "3121", navn: "Int.gjf. <20 min.", konto: "1620211330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_11.save(failOnError: true, flush: true)
		Lonnart lonn_12 = new Lonnart(lonnartNummer: "3121", navn: "Int.gjf. <20 min.", konto: "1620011330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_12.save(failOnError: true, flush: true)
		Lonnart lonn_13 = new Lonnart(lonnartNummer: "3122", navn: "Int.tild.<20 min.", konto: "1620011330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_13.save(failOnError: true, flush: true)
		Lonnart lonn_14 = new Lonnart(lonnartNummer: "3122", navn: "Int.tild.<20 min.", konto: "1620211330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_14.save(failOnError: true, flush: true)
		Lonnart lonn_15 = new Lonnart(lonnartNummer: "3123", navn: "Int.gjf. >20 min.", konto: "1620011330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_15.save(failOnError: true, flush: true)
		Lonnart lonn_16 = new Lonnart(lonnartNummer: "3123", navn: "Int.gjf. >20 min.", konto: "1620211330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_16.save(failOnError: true, flush: true)
		Lonnart lonn_17 = new Lonnart(lonnartNummer: "3124", navn: "Int.tild.>20 min.", konto: "1620011330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_17.save(failOnError: true, flush: true)
		Lonnart lonn_18 = new Lonnart(lonnartNummer: "3124", navn: "Int.tild.>20 min.", konto: "1620211330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_18.save(failOnError: true, flush: true)
		*/
		Lonnart lonn_19 = new Lonnart(lonnartNummer: "1462", navn: "Refusjon av div.", konto: "1620212280", kontoTekst: KontoTekstType.DIVERSE_UTGIFTER ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_19.save(failOnError: true, flush: true)
		Lonnart lonn_20 = new Lonnart(lonnartNummer: "1462", navn: "Refusjon av div.", konto: "1620012280", kontoTekst: KontoTekstType.DIVERSE_UTGIFTER ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_20.save(failOnError: true, flush: true)
		Lonnart lonn_21 = new Lonnart(lonnartNummer: "5705", navn: "Reiseutlegg", konto: "1620212311", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_21.save(failOnError: true, flush: true)
		Lonnart lonn_22 = new Lonnart(lonnartNummer: "5705", navn: "Reiseutlegg", konto: "1620212311", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_22.save(failOnError: true, flush: true)
		Lonnart lonn_23 = new Lonnart(lonnartNummer: "5711", navn: "Bil", konto: "1620212311", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_23.save(failOnError: true, flush: true)
		Lonnart lonn_24 = new Lonnart(lonnartNummer: "5711", navn: "Bil", kmKode: "1", konto: "1620012311", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_24.save(failOnError: true, flush: true)
		Lonnart lonn_25 = new Lonnart(lonnartNummer: "5712", navn: "Pasasjertillegg", kmKode: "10", konto: "1620012311", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_25.save(failOnError: true, flush: true)
		Lonnart lonn_26 = new Lonnart(lonnartNummer: "5712", navn: "Pasasjertillegg", konto: "1620212311", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_26.save(failOnError: true, flush: true)
		Lonnart lonn_27 = new Lonnart(lonnartNummer: "5715", navn: "Liten båt", konto: "1620212311", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_27.save(failOnError: true, flush: true)
		Lonnart lonn_28 = new Lonnart(lonnartNummer: "5715", navn: "Liten båt", kmKode: "4", konto: "1620012311", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_28.save(failOnError: true, flush: true)
		Lonnart lonn_29 = new Lonnart(lonnartNummer: "5716", navn: "Motorsykkel", konto: "1620212311", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_29.save(failOnError: true, flush: true)
		Lonnart lonn_30 = new Lonnart(lonnartNummer: "5716", navn: "Motorsykkel", kmKode: "2", konto: "1620012311", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_30.save(failOnError: true, flush: true)
		Lonnart lonn_31 = new Lonnart(lonnartNummer: "5717", navn: "Sykkel/moped", konto: "1620212311", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_31.save(failOnError: true, flush: true)
		Lonnart lonn_32 = new Lonnart(lonnartNummer: "5717", navn: "Sykkel/moped", kmKode: "3", konto: "1620012311", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_32.save(failOnError: true, flush: true)
		Lonnart lonn_33 = new Lonnart(lonnartNummer: "5718", navn: "Km. godtgjørelse - sats for Tromsø", kmKode: "1", konto: "1620212311", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_33.save(failOnError: true, flush: true)
		Lonnart lonn_34 = new Lonnart(lonnartNummer: "5718", navn: "Km. godtgjørelse - sats for Tromsø", kmKode: "1", konto: "1620012311", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_34.save(failOnError: true, flush: true)

		Lonnart lonn_35 = new Lonnart(lonnartNummer: "3192", navn: "Timelønn lokal, etter 16:30", konto: "1620011330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_35.save(failOnError: true, flush: true)
		Lonnart lonn_36 = new Lonnart(lonnartNummer: "3192", navn: "Timelønn lokal, etter 16:30", konto: "1620211330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_36.save(failOnError: true, flush: true)
		Lonnart lonn_37 = new Lonnart(lonnartNummer: "3196", navn: "Timelønn, etter 16:30", konto: "1620011330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
		lonn_37.save(failOnError: true, flush: true)
		Lonnart lonn_38 = new Lonnart(lonnartNummer: "3196", navn: "Timelønn, etter 16:30", konto: "1620211330", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
		lonn_38.save(failOnError: true, flush: true)
	}

	// Generer pensjonist intervjuer som helt klart er over 70 aar
	private Intervjuer genererPensjonistIntervjuer() {
		Klynge kl = new Klynge()
		kl.klyngeNavn = "Test"
		kl.klyngeSjef = "Test Ola"
		kl.beskrivelse = "Dette er en test klynge"
		kl.epost = "test.klynge@ssb.no"
		kl.save(failOnError: true, flush: true)

		Intervjuer inter = new Intervjuer()
		inter.klynge = kl
		inter.initialer = "yxz"
		inter.intervjuerNummer = new Long(101999)
		inter.navn = "Test Intervjuer"
		inter.kjonn = Kjonn.MANN

		Calendar c = Calendar.getInstance()

		c.add Calendar.YEAR, -75


		inter.fodselsDato = c.getTime()
		inter.gateAdresse = "Lille Grensen 5"
		inter.gateAdresse2 = ""
		inter.postNummer = "0159"
		inter.kommuneNummer = "0301"
		inter.epostPrivat = "stian.karlsen@ttteeesssttt.com"
		inter.epostJobb ="stian.karlsen@ssb.no"
		inter.status = IntervjuerStatus.AKTIV
		inter.lokal = false

		cal.set Calendar.DAY_OF_MONTH, 1
		cal.set Calendar.MONTH, 0
		cal.set Calendar.YEAR, 2009

		inter.ansattDato = cal.getTime()
		inter.avtaltAntallTimer = 700
		inter.arbeidsType = IntervjuerArbeidsType.BESOK

		cal.set Calendar.DAY_OF_MONTH, 3
		cal.set Calendar.MONTH, 2
		cal.set Calendar.YEAR, 2011

		inter.sluttDato = cal.getTime()
		inter.save(failOnError: true, flush: true)

		return inter
	}

	// Generer pensjonist intervjuer som saavidt er over 70 aar
	private Intervjuer genererPensjonistIntervjuerTo() {
		Klynge kl = new Klynge()
		kl.klyngeNavn = "Test"
		kl.klyngeSjef = "Test Ola"
		kl.beskrivelse = "Dette er en test klynge"
		kl.epost = "test.klynge@ssb.no"
		kl.save(failOnError: true, flush: true)

		Intervjuer inter = new Intervjuer()
		inter.klynge = kl
		inter.initialer = "xyz"
		inter.intervjuerNummer = new Long(101998)
		inter.navn = "Test Intervjuer"
		inter.kjonn = Kjonn.MANN
		inter.lokal = false

		Calendar c = Calendar.getInstance()

		c.add Calendar.MONTH, -1
		c.add Calendar.YEAR, -70

		inter.fodselsDato = c.getTime()
		inter.gateAdresse = "Lille Grensen 5"
		inter.gateAdresse2 = ""
		inter.postNummer = "0159"
		inter.kommuneNummer = "0301"
		inter.epostPrivat = "stian.karlsen@ttteeesssttt.com"
		inter.epostJobb ="stian.karlsen@ssb.no"
		inter.status = IntervjuerStatus.AKTIV

		cal.set Calendar.DAY_OF_MONTH, 1
		cal.set Calendar.MONTH, 0
		cal.set Calendar.YEAR, 2009

		inter.ansattDato = cal.getTime()
		inter.avtaltAntallTimer = 700
		inter.arbeidsType = IntervjuerArbeidsType.BESOK

		cal.set Calendar.DAY_OF_MONTH, 3
		cal.set Calendar.MONTH, 2
		cal.set Calendar.YEAR, 2011

		inter.sluttDato = cal.getTime()
		inter.save(failOnError: true, flush: true)

		return inter
	}

	// Generer en intervjuer som bor i Tromsø
	// slik at lønnart km godtgjørelse Tromsø
	// kan testes
	private Intervjuer genererTromsoIntervjuer() {
		Klynge kl = new Klynge()
		kl.klyngeNavn = "Tromsø"
		kl.klyngeSjef = "Test Tromsø"
		kl.beskrivelse = "Dette er en test klynge"
		kl.epost = "test.klynge@ssb.no"
		kl.save(failOnError: true, flush: true)

		Intervjuer inter = new Intervjuer()
		inter.klynge = kl
		inter.initialer = "ttt"
		inter.intervjuerNummer = new Long(101901)
		inter.navn = "Tromsø Intervjuer"
		inter.kjonn = Kjonn.MANN
		inter.lokal = false

		Calendar c = Calendar.getInstance()

		c.add Calendar.MONTH, 1
		c.add Calendar.YEAR, -40

		inter.fodselsDato = c.getTime()
		inter.gateAdresse = "Storgata 3"
		inter.gateAdresse2 = ""
		inter.postNummer = "9002"
		inter.postSted = "Tromsø"
		inter.kommuneNummer = "1902"
		inter.epostPrivat = "ttteeesssttt@ttteeesssttt.com"
		inter.epostJobb ="ttteeesssttt@ssb.no"
		inter.status = IntervjuerStatus.AKTIV

		cal.set Calendar.DAY_OF_MONTH, 1
		cal.set Calendar.MONTH, 0
		cal.set Calendar.YEAR, 2009

		inter.ansattDato = cal.getTime()
		inter.avtaltAntallTimer = 700
		inter.arbeidsType = IntervjuerArbeidsType.BESOK

		cal.set Calendar.DAY_OF_MONTH, 3
		cal.set Calendar.MONTH, 2
		cal.set Calendar.YEAR, 2011

		inter.sluttDato = cal.getTime()
		inter.save(failOnError: true, flush: true)

		return inter
	}

	// Generer pensjonist intervjuer som ikke er fylt
	// 70 aar, men gjoer det neste mnd
	private Intervjuer genererPensjonistIntervjuerTre() {
		Klynge kl = new Klynge()
		kl.klyngeNavn = "Test"
		kl.klyngeSjef = "Test Ola"
		kl.beskrivelse = "Dette er en test klynge"
		kl.epost = "test.klynge@ssb.no"
		kl.save(failOnError: true, flush: true)

		Intervjuer inter = new Intervjuer()
		inter.klynge = kl
		inter.initialer = "zyx"
		inter.intervjuerNummer = new Long(101997)
		inter.navn = "Test Intervjuer"
		inter.kjonn = Kjonn.MANN
		inter.lokal = false

		Calendar c = Calendar.getInstance()

		c.add Calendar.MONTH, 1
		c.add Calendar.YEAR, -70

		inter.fodselsDato = c.getTime()
		inter.gateAdresse = "Lille Grensen 5"
		inter.gateAdresse2 = ""
		inter.postNummer = "0159"
		inter.kommuneNummer = "0301"
		inter.epostPrivat = "stian.karlsen@ttteeesssttt.com"
		inter.epostJobb ="stian.karlsen@ssb.no"
		inter.status = IntervjuerStatus.AKTIV

		cal.set Calendar.DAY_OF_MONTH, 1
		cal.set Calendar.MONTH, 0
		cal.set Calendar.YEAR, 2009

		inter.ansattDato = cal.getTime()
		inter.avtaltAntallTimer = 700
		inter.arbeidsType = IntervjuerArbeidsType.BESOK

		cal.set Calendar.DAY_OF_MONTH, 3
		cal.set Calendar.MONTH, 2
		cal.set Calendar.YEAR, 2011

		inter.sluttDato = cal.getTime()
		inter.save(failOnError: true, flush: true)

		return inter
	}

	private Skjema genererOvertidSkjema() {
		ProsjektLeder prosjektLeder = new ProsjektLeder()
		prosjektLeder.navn = "Paal Soreng"
		prosjektLeder.initialer = "spo"
		prosjektLeder.epost = "spo"
		prosjektLeder.save(failOnError: true, flush: true)

		Prosjekt pro = new Prosjekt()
		pro.panel = true
		pro.produktNummer = "67890"
		pro.modus = ProsjektModus.MIXMODUS
		pro.registerNummer = "123"
		pro.prosjektNavn = "Levekaar2"
		pro.aargang = 2010
		pro.prosjektLeder = prosjektLeder
		pro.oppstartDato = dato
		pro.avslutningsDato = dato
		pro.prosjektStatus = ProsjektStatus.PLANLEGGING
		pro.finansiering = ProsjektFinansiering.STAT
		pro.prosentStat = 100
		pro.prosentMarked = 0
		pro.kommentar = "test"
		pro.save(failOnError: true, flush: true)

		Periode per = new Periode()
		per.aar = "2010"
		per.periodeNummer = 2
		per.periodeType = PeriodeType.KVRT
		per.oppstartDataInnsamling = dato
		per.hentesTidligst = dato
		per.planlagtSluttDato = dato
		per.sluttDato = dato
		per.incentiver = "in"
		per.kommentar = "test"
		per.delregisterNummer = 1234
		//per.save(failOnError: true, flush: true)


		SkjemaVersjon skjemaVersjon = new SkjemaVersjon()
		skjemaVersjon.versjonsNummer = 1
		skjemaVersjon.gyldigFom = dato
		skjemaVersjon.gyldigTom = dato
		skjemaVersjon.skjemaGodkjentDato = dato
		skjemaVersjon.skjemaGodkjentInitialer = "spo"
		skjemaVersjon.webskjemaGodkjentDato  = dato
		skjemaVersjon.webskjemaGodkjentInitialer = "spo"
		skjemaVersjon.kommentar = "test"
		//skjemaVersjon.save(failOnError: true, flush: true)


		Skjema skjema = new Skjema()
		skjema.prosjekt = pro
		skjema.undersokelseType = UndersokelsesType.BEDRIFT
		skjema.delProduktNummer = "6789010"
		skjema.skjemaNavn = "levekaar_skjema2"
		skjema.skjemaKortNavn = "lev"
		skjema.intervjuTypeBesok = true
		skjema.intervjuTypeTelefon = true
		skjema.intervjuTypePapir = false
		skjema.intervjuTypeWeb = false
		skjema.slettesEtterRetur = false
		skjema.oppstartDataInnsamling = dato
		skjema.klarTilGenerering = true
		skjema.klarTilUtsending = true
		skjema.status = "S"
		skjema.planlagtSluttDato = dato
		skjema.sluttDato = dato
		skjema.overtid = true
		skjema.onsketSvarProsent = 80
		skjema.dataUttaksDato = dato
		skjema.hentAlleOppdrag = true
		skjema.kanSlettesLokalt = true
		skjema.langtidsLagretAv = "spo"
		skjema.langtidsLagretDato = dato
		skjema.antallIntervjuObjekterLagret = 100
		skjema.antallOppdragLagret = 100
		skjema.intervjuVarighet = 20
		skjema.adminTid = 20
		skjema.regoverforingDato = dato
		skjema.regoverforingInitialer = "spo"
		skjema.regoverforingSeksjon = "750"
		skjema.ioBrevGodkjentDato = dato
		skjema.ioBrevGodkjentInitialer = "spo"
		skjema.krypteringDato = dato
		skjema.krypteringMailSendt = dato
		skjema.anonymDato = dato
		skjema.anonymMailSendt = dato
		skjema.ryddDato = dato
		skjema.ryddMailSendt = dato
		skjema.papirMakuleringDato = dato
		skjema.papirMakuleringMailSendt = dato
		skjema.maxAntIntervjuObjekterKontakt = 100
		skjema.kommentar = "test"
		skjema.aktivertForIntervjuing = false
		skjema.malVersjon = 0

		skjema.addToPerioder(per)
		skjema.addToSkjemaVersjoner(skjemaVersjon)

		skjema.save(failOnError: true, flush: true)
		skjemaVersjon.save(failOnError: true, flush: true)
		per.save(failOnError: true, flush: true)

		return skjema
	}

	private Skjema genererOvertidSkjemaStatMarked() {
		ProsjektLeder prosjektLeder = new ProsjektLeder()
		prosjektLeder.navn = "Paal Soreng"
		prosjektLeder.initialer = "spo"
		prosjektLeder.epost = "spo"
		prosjektLeder.save(failOnError: true, flush: true)

		Prosjekt pro = new Prosjekt()
		pro.panel = true
		pro.produktNummer = "55660"
		pro.modus = ProsjektModus.MIXMODUS
		pro.registerNummer = "123"
		pro.prosjektNavn = "Levekaar2"
		pro.aargang = 2010
		pro.prosjektLeder = prosjektLeder
		pro.oppstartDato = dato
		pro.avslutningsDato = dato
		pro.prosjektStatus = ProsjektStatus.PLANLEGGING
		pro.finansiering = ProsjektFinansiering.STAT_MARKED
		pro.prosentStat = 67
		pro.prosentMarked = 33
		pro.kommentar = "test"
		pro.save(failOnError: true, flush: true)

		Periode per = new Periode()
		per.aar = "2010"
		per.periodeNummer = 2
		per.periodeType = PeriodeType.KVRT
		per.oppstartDataInnsamling = dato
		per.hentesTidligst = dato
		per.planlagtSluttDato = dato
		per.sluttDato = dato
		per.incentiver = "in"
		per.kommentar = "test"
		per.delregisterNummer = 1234
		//per.save(failOnError: true, flush: true)


		SkjemaVersjon skjemaVersjon = new SkjemaVersjon()
		skjemaVersjon.versjonsNummer = 1
		skjemaVersjon.gyldigFom = dato
		skjemaVersjon.gyldigTom = dato
		skjemaVersjon.skjemaGodkjentDato = dato
		skjemaVersjon.skjemaGodkjentInitialer = "spo"
		skjemaVersjon.webskjemaGodkjentDato  = dato
		skjemaVersjon.webskjemaGodkjentInitialer = "spo"
		skjemaVersjon.kommentar = "test"
		//skjemaVersjon.save(failOnError: true, flush: true)


		Skjema skjema = new Skjema()
		skjema.prosjekt = pro
		skjema.undersokelseType = UndersokelsesType.BEDRIFT
		skjema.delProduktNummer = "5566010"
		skjema.skjemaNavn = "levekaar_skjema2"
		skjema.skjemaKortNavn = "lev"
		skjema.intervjuTypeBesok = true
		skjema.intervjuTypeTelefon = true
		skjema.intervjuTypePapir = false
		skjema.intervjuTypeWeb = false
		skjema.slettesEtterRetur = false
		skjema.oppstartDataInnsamling = dato
		skjema.klarTilGenerering = true
		skjema.klarTilUtsending = true
		skjema.status = "S"
		skjema.planlagtSluttDato = dato
		skjema.sluttDato = dato
		skjema.overtid = true
		skjema.onsketSvarProsent = 80
		skjema.dataUttaksDato = dato
		skjema.hentAlleOppdrag = true
		skjema.kanSlettesLokalt = true
		skjema.langtidsLagretAv = "spo"
		skjema.langtidsLagretDato = dato
		skjema.antallIntervjuObjekterLagret = 100
		skjema.antallOppdragLagret = 100
		skjema.intervjuVarighet = 20
		skjema.adminTid = 20
		skjema.regoverforingDato = dato
		skjema.regoverforingInitialer = "spo"
		skjema.regoverforingSeksjon = "750"
		skjema.ioBrevGodkjentDato = dato
		skjema.ioBrevGodkjentInitialer = "spo"
		skjema.krypteringDato = dato
		skjema.krypteringMailSendt = dato
		skjema.anonymDato = dato
		skjema.anonymMailSendt = dato
		skjema.ryddDato = dato
		skjema.ryddMailSendt = dato
		skjema.papirMakuleringDato = dato
		skjema.papirMakuleringMailSendt = dato
		skjema.maxAntIntervjuObjekterKontakt = 100
		skjema.kommentar = "test"
		skjema.aktivertForIntervjuing = false
		skjema.malVersjon = 0

		skjema.addToPerioder(per)
		skjema.addToSkjemaVersjoner(skjemaVersjon)

		skjema.save(failOnError: true, flush: true)
		skjemaVersjon.save(failOnError: true, flush: true)
		per.save(failOnError: true, flush: true)

		return skjema
	}

}
