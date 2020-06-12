package sivadm


import siv.type.IntervjuerArbeidsType;
import siv.type.IntervjuerStatus;
import siv.type.Kjonn;
import siv.type.PeriodeType
import siv.type.ProsjektFinansiering
import siv.type.ProsjektModus
import siv.type.ProsjektStatus
import siv.type.TimeforingStatus;
import siv.type.TransportMiddel;
import siv.type.UndersokelsesType
import util.DateUtil;

class TimeforingServiceTest extends GroovyTestCase {
	
	def timeforingService
	
	protected void setup() {
		super.setUp()
	}
	
	protected void tearDown() {
		super.tearDown()
	}
	
	void testHentProduktKodeUtenDato() {
		Skjema sOne = genererSkjema()
		Skjema sTwo = genererSkjemaTo()
		Produkt produktOne = new Produkt(navn: "Etter avtale med kontoret", produktNummer: "0980-0", finansiering: ProsjektFinansiering.STAT, prosentStat: 100, prosentMarked: 0)
		produktOne.save(failOnError: true, flush: true)
		Produkt produktTwo = new Produkt(navn: "Opplæring", produktNummer: "0980-1", finansiering: ProsjektFinansiering.STAT, prosentStat: 100, prosentMarked: 0)
		produktTwo.save(failOnError: true, flush: true)
		Produkt produktThree = new Produkt(navn: "SIV - System for intervjuvirksomhet, FOSS", produktNummer: "7413-0", finansiering: ProsjektFinansiering.STAT, prosentStat: 100, prosentMarked: 0)
		produktThree.save(failOnError: true, flush: true)
		
		List liste = timeforingService.hentProduktKode(null, true)
		
		assertEquals 5, liste.size()
	}
	
	void testHentProduktKodeMedDato() {
		Skjema sOne = genererSkjema()
		Skjema sTwo = genererSkjemaTo()
		Produkt produktOne = new Produkt(navn: "Etter avtale med kontoret", produktNummer: "0980-0", finansiering: ProsjektFinansiering.STAT, prosentStat: 100, prosentMarked: 0)
		produktOne.save(failOnError: true, flush: true)
		Produkt produktTwo = new Produkt(navn: "Opplæring", produktNummer: "0980-1", finansiering: ProsjektFinansiering.STAT, prosentStat: 100, prosentMarked: 0)
		produktTwo.save(failOnError: true, flush: true)
		Produkt produktThree = new Produkt(navn: "SIV - System for intervjuvirksomhet, FOSS", produktNummer: "7413-0", finansiering: ProsjektFinansiering.STAT, prosentStat: 100, prosentMarked: 0)
		produktThree.save(failOnError: true, flush: true)
		
		List liste = timeforingService.hentProduktKode(new Date(), true)
		
		assertEquals 4, liste.size()
	}
	
	void testHentProduktKodeUtenAdmin() {
		Skjema sOne = genererSkjema()
		Skjema sTwo = genererSkjemaTo()
		Produkt produktOne = new Produkt(navn: "Etter avtale med kontoret", produktNummer: "0980-0", finansiering: ProsjektFinansiering.STAT, prosentStat: 100, prosentMarked: 0)
		produktOne.save(failOnError: true, flush: true)
		Produkt produktTwo = new Produkt(navn: "Opplæring", produktNummer: "0980-1", finansiering: ProsjektFinansiering.STAT, prosentStat: 100, prosentMarked: 0)
		produktTwo.save(failOnError: true, flush: true)
		Produkt produktThree = new Produkt(navn: "SIV - System for intervjuvirksomhet, FOSS", produktNummer: "7413-0", finansiering: ProsjektFinansiering.STAT, prosentStat: 100, prosentMarked: 0)
		produktThree.save(failOnError: true, flush: true)
		
		List liste = timeforingService.hentProduktKode(new Date(), false)
		
		assertEquals 1, liste.size()
	}
	
	
	
	private Skjema genererSkjema() {
		ProsjektLeder prosjektLeder = new ProsjektLeder()
		prosjektLeder.navn = "Paal Soreng"
		prosjektLeder.initialer = "spo"
		prosjektLeder.epost = "spo"
		prosjektLeder.save(failOnError: true, flush: true)
			
		Prosjekt pro = new Prosjekt()
		pro.panel = true
		pro.produktNummer = "6789"
		pro.modus = ProsjektModus.MIXMODUS
		pro.registerNummer = "123"
		pro.prosjektNavn = "Levekaar2"
		pro.aargang = 2010
		pro.prosjektLeder = prosjektLeder
		pro.oppstartDato = new Date()
		pro.avslutningsDato = new Date()
		pro.prosjektStatus = ProsjektStatus.PLANLEGGING
		pro.finansiering = ProsjektFinansiering.STAT
		pro.prosentStat = 100
		pro.prosentMarked = 0
		pro.kommentar = "test"
		pro.save(failOnError: true, flush: true)
		
		Periode per = new Periode()
		per.aar = "2010"
		per.periodeNummer = 2
		per.periodeType = PeriodeType.AAR
		per.oppstartDataInnsamling = new Date()
		per.hentesTidligst = new Date()
		per.planlagtSluttDato = new Date()
		per.sluttDato = new Date()
		per.incentiver = "in"
		per.kommentar = "test"
		per.delregisterNummer = 1234
		//per.save(failOnError: true, flush: true)
		
		
		SkjemaVersjon skjemaVersjon = new SkjemaVersjon()
		skjemaVersjon.versjonsNummer = 1
		skjemaVersjon.gyldigFom = new Date()
		skjemaVersjon.gyldigTom = new Date()
		skjemaVersjon.skjemaGodkjentDato = new Date()
		skjemaVersjon.skjemaGodkjentInitialer = "spo"
		skjemaVersjon.webskjemaGodkjentDato  = new Date()
		skjemaVersjon.webskjemaGodkjentInitialer = "spo"
		skjemaVersjon.kommentar = "test"
		//skjemaVersjon.save(failOnError: true, flush: true)
		
		
		Skjema skjema = new Skjema()
		skjema.prosjekt = pro
		skjema.undersokelseType = UndersokelsesType.BEDRIFT
		skjema.delProduktNummer = "6789-1"
		skjema.skjemaNavn = "levekaar_skjema2"
		skjema.skjemaKortNavn = "lev"
		skjema.intervjuTypeBesok = true
		skjema.intervjuTypeTelefon = true
		skjema.intervjuTypePapir = false
		skjema.intervjuTypeWeb = false
		skjema.slettesEtterRetur = false
		skjema.oppstartDataInnsamling = new Date()
		skjema.klarTilGenerering = true
		skjema.klarTilUtsending = true
		skjema.status = "S"
		skjema.planlagtSluttDato = new Date()
		Calendar c = Calendar.getInstance()
		c.add Calendar.DAY_OF_MONTH, -2
		skjema.sluttDato = c.getTime()
		skjema.overtid = true
		skjema.onsketSvarProsent = 80
		skjema.dataUttaksDato = new Date()
		skjema.hentAlleOppdrag = true
		skjema.kanSlettesLokalt = true
		skjema.langtidsLagretAv = "spo"
		skjema.langtidsLagretDato = new Date()
		skjema.antallIntervjuObjekterLagret = 100
		skjema.antallOppdragLagret = 100
		skjema.intervjuVarighet = 20
		skjema.adminTid = 20
		skjema.regoverforingDato = new Date()
		skjema.regoverforingInitialer = "spo"
		skjema.regoverforingSeksjon = "750"
		skjema.ioBrevGodkjentDato = new Date()
		skjema.ioBrevGodkjentInitialer = "spo"
		skjema.krypteringDato = new Date()
		skjema.krypteringMailSendt = new Date()
		skjema.anonymDato = new Date()
		skjema.anonymMailSendt = new Date()
		skjema.ryddDato = new Date()
		skjema.ryddMailSendt = new Date()
		skjema.papirMakuleringDato = new Date()
		skjema.papirMakuleringMailSendt = new Date()
		skjema.maxAntIntervjuObjekterKontakt = 100
		skjema.kommentar = "test"
		skjema.aktivertForIntervjuing = false
		
		skjema.addToPerioder(per)
		skjema.addToSkjemaVersjoner(skjemaVersjon)
		
		skjema.save(failOnError: true, flush: true)
		skjemaVersjon.save(failOnError: true, flush: true)
		per.save(failOnError: true, flush: true)
		
		return skjema
	}
	
	private Skjema genererSkjemaTo() {
		ProsjektLeder prosjektLeder = new ProsjektLeder()
		prosjektLeder.navn = "Stian Karlsen"
		prosjektLeder.initialer = "krn"
		prosjektLeder.epost = "_krn_@ssb.no"
		prosjektLeder.save(failOnError: true, flush: true)
			
		Prosjekt pro = new Prosjekt()
		pro.panel = true
		pro.produktNummer = "5566"
		pro.modus = ProsjektModus.MIXMODUS
		pro.registerNummer = "123"
		pro.prosjektNavn = "Levekaar2"
		pro.aargang = 2010
		pro.prosjektLeder = prosjektLeder
		pro.oppstartDato = new Date()
		pro.avslutningsDato = new Date()
		pro.prosjektStatus = ProsjektStatus.PLANLEGGING
		pro.finansiering = ProsjektFinansiering.STAT_MARKED
		pro.prosentStat = 67
		pro.prosentMarked = 33
		pro.kommentar = "test"
		pro.save(failOnError: true, flush: true)
		
		Periode per = new Periode()
		per.aar = "2010"
		per.periodeNummer = 2
		per.periodeType = PeriodeType.AAR
		per.oppstartDataInnsamling = new Date()
		per.hentesTidligst = new Date()
		per.planlagtSluttDato = new Date()
		per.sluttDato = new Date()
		per.incentiver = "in"
		per.kommentar = "test"
		per.delregisterNummer = 1234
		//per.save(failOnError: true, flush: true)
		
		
		SkjemaVersjon skjemaVersjon = new SkjemaVersjon()
		skjemaVersjon.versjonsNummer = 1
		skjemaVersjon.gyldigFom = new Date()
		skjemaVersjon.gyldigTom = new Date()
		skjemaVersjon.skjemaGodkjentDato = new Date()
		skjemaVersjon.skjemaGodkjentInitialer = "spo"
		skjemaVersjon.webskjemaGodkjentDato  = new Date()
		skjemaVersjon.webskjemaGodkjentInitialer = "spo"
		skjemaVersjon.kommentar = "test"
		//skjemaVersjon.save(failOnError: true, flush: true)
		
		
		Skjema skjema = new Skjema()
		skjema.prosjekt = pro
		skjema.undersokelseType = UndersokelsesType.BEDRIFT
		skjema.delProduktNummer = "5566-1"
		skjema.skjemaNavn = "levekaar_skjema3"
		skjema.skjemaKortNavn = "lev3"
		skjema.intervjuTypeBesok = true
		skjema.intervjuTypeTelefon = true
		skjema.intervjuTypePapir = false
		skjema.intervjuTypeWeb = false
		skjema.slettesEtterRetur = false
		skjema.oppstartDataInnsamling = new Date()
		skjema.klarTilGenerering = true
		skjema.klarTilUtsending = true
		skjema.status = "S"
		skjema.planlagtSluttDato = new Date()
		
		Calendar c = Calendar.getInstance()
		c.add Calendar.DAY_OF_MONTH, 10
		skjema.sluttDato = c.getTime()
		skjema.overtid = true
		skjema.onsketSvarProsent = 80
		skjema.dataUttaksDato = new Date()
		skjema.hentAlleOppdrag = true
		skjema.kanSlettesLokalt = true
		skjema.langtidsLagretAv = "spo"
		skjema.langtidsLagretDato = new Date()
		skjema.antallIntervjuObjekterLagret = 100
		skjema.antallOppdragLagret = 100
		skjema.intervjuVarighet = 20
		skjema.adminTid = 20
		skjema.regoverforingDato = new Date()
		skjema.regoverforingInitialer = "spo"
		skjema.regoverforingSeksjon = "750"
		skjema.ioBrevGodkjentDato = new Date()
		skjema.ioBrevGodkjentInitialer = "spo"
		skjema.krypteringDato = new Date()
		skjema.krypteringMailSendt = new Date()
		skjema.anonymDato = new Date()
		skjema.anonymMailSendt = new Date()
		skjema.ryddDato = new Date()
		skjema.ryddMailSendt = new Date()
		skjema.papirMakuleringDato = new Date()
		skjema.papirMakuleringMailSendt = new Date()
		skjema.maxAntIntervjuObjekterKontakt = 100
		skjema.kommentar = "test"
		skjema.aktivertForIntervjuing = false
		
		skjema.addToPerioder(per)
		skjema.addToSkjemaVersjoner(skjemaVersjon)
		
		skjema.save(failOnError: true, flush: true)
		skjemaVersjon.save(failOnError: true, flush: true)
		per.save(failOnError: true, flush: true)
		
		return skjema
	}
}
