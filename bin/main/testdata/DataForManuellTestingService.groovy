package testdata

import grails.gorm.transactions.Transactional
import siv.type.*
import sivadm.*

@Transactional
class DataForManuellTestingService {
	
	def sessionFactory
	Date newDate = new Date()

	def Map opprettTestDataForManuellTesting() {
		Map testObjekterMap = [:]
		ProsjektLeder prosjektLeder = new ProsjektLeder()
		prosjektLeder.navn = "Braakjekk Seeberg"
		prosjektLeder.initialer = "raz"
		prosjektLeder.epost = "raz"
		
		prosjektLeder.save(failOnError: true, flush: true)

		Prosjekt prosjekt = new Prosjekt()
		prosjekt.panel = true
		prosjekt.produktNummer = "05490"
		prosjekt.modus = ProsjektModus.ENMODUS
		prosjekt.registerNummer = "0123"
		prosjekt.prosjektNavn = "Levekaar"
		prosjekt.aargang = 2010
		prosjekt.prosjektLeder = prosjektLeder
		prosjekt.oppstartDato = new Date()
		Calendar calen = Calendar.getInstance()
		calen.add Calendar.DAY_OF_YEAR, 60
		prosjekt.avslutningsDato = calen.getTime()
		prosjekt.prosjektStatus = ProsjektStatus.AKTIV
		prosjekt.finansiering = ProsjektFinansiering.STAT
		prosjekt.prosentStat = 100
		prosjekt.prosentMarked = 0
		prosjekt.kommentar = "test"

		Prosjekt prosjekt2 = new Prosjekt()
		prosjekt2.panel = true
		prosjekt2.produktNummer = "00345"
		prosjekt2.modus = ProsjektModus.ENMODUS
		prosjekt2.registerNummer = "0234"
		prosjekt2.prosjektNavn = "Medie"
		prosjekt2.aargang = 2010
		prosjekt2.prosjektLeder = prosjektLeder
		prosjekt2.oppstartDato = new Date()
		prosjekt2.avslutningsDato = calen.getTime()
		prosjekt2.prosjektStatus = ProsjektStatus.AKTIV
		prosjekt2.finansiering = ProsjektFinansiering.STAT
		prosjekt2.prosentStat = 100
		prosjekt2.prosentMarked = 0
		prosjekt2.kommentar = "test"
		prosjekt2.save()
		
		Prosjekt prosjekt3 = new Prosjekt()
		prosjekt3.panel = true
		prosjekt3.produktNummer = "05776"
		prosjekt3.modus = ProsjektModus.ENMODUS
		prosjekt3.registerNummer = "0234"
		prosjekt3.prosjektNavn = "PIAAC"
		prosjekt3.aargang = 2011
		prosjekt3.prosjektLeder = prosjektLeder
		prosjekt3.oppstartDato = new Date()
		prosjekt3.avslutningsDato = calen.getTime()
		prosjekt3.prosjektStatus = ProsjektStatus.AKTIV
		prosjekt3.finansiering = ProsjektFinansiering.STAT
		prosjekt3.prosentStat = 100
		prosjekt3.prosentMarked = 0
		prosjekt3.kommentar = "piaac test"
		prosjekt3.save()
		
		Prosjekt prosjekt4 = new Prosjekt()
		prosjekt4.panel = true
		prosjekt4.produktNummer = "02090"
		prosjekt4.modus = ProsjektModus.ENMODUS
		prosjekt4.registerNummer = "0234"
		prosjekt4.prosjektNavn = "AKU"
		prosjekt4.aargang = 2011
		prosjekt4.prosjektLeder = prosjektLeder
		prosjekt4.oppstartDato = new Date()
		prosjekt4.avslutningsDato = calen.getTime()
		prosjekt4.prosjektStatus = ProsjektStatus.AKTIV
		prosjekt4.finansiering = ProsjektFinansiering.STAT
		prosjekt4.prosentStat = 100
		prosjekt4.prosentMarked = 0
		prosjekt4.kommentar = "aku test"
		prosjekt4.save()
		
		
		
		Periode periode = new Periode()
		periode.aar = "2010"
		periode.periodeNummer = 1
		periode.periodeType = PeriodeType.KVRT
		periode.oppstartDataInnsamling = new Date()
		periode.hentesTidligst = new Date()
		periode.planlagtSluttDato = new Date()
		periode.sluttDato = new Date()
		periode.incentiver = "in"
		periode.kommentar = "periode 1 skjema 1"
		periode.delregisterNummer = 1234
		
		testObjekterMap['periode'] = periode

		Periode periode2skjema1 = new Periode()
		periode2skjema1.aar = "2011"
		periode2skjema1.periodeNummer = 2
		periode2skjema1.periodeType = PeriodeType.KVRT
		periode2skjema1.oppstartDataInnsamling = new Date()
		periode2skjema1.hentesTidligst = new Date()
		periode2skjema1.planlagtSluttDato = new Date()
		periode2skjema1.sluttDato = new Date()
		periode2skjema1.incentiver = "in"
		periode2skjema1.kommentar = "periode 2 skjema 1"
		periode2skjema1.delregisterNummer = 1234

		testObjekterMap['periode2skjema1'] = periode2skjema1

		Periode periode3skjema1 = new Periode()
		periode3skjema1.aar = "2012"
		periode3skjema1.periodeNummer = 3
		periode3skjema1.periodeType = PeriodeType.KVRT
		periode3skjema1.oppstartDataInnsamling = new Date()
		periode3skjema1.hentesTidligst = new Date()
		periode3skjema1.planlagtSluttDato = new Date()
		periode3skjema1.sluttDato = new Date()
		periode3skjema1.incentiver = "in"
		periode3skjema1.kommentar = "periode 3 skjema 1"
		periode3skjema1.delregisterNummer = 1234

		testObjekterMap['periode3skjema1'] = periode3skjema1

		Periode periode3 = new Periode()
		periode3.aar = "2011"
		periode3.periodeNummer = 3
		periode3.periodeType = PeriodeType.AAR
		periode3.oppstartDataInnsamling = new Date()
		periode3.hentesTidligst = new Date()
		periode3.planlagtSluttDato = newDate
		periode3.sluttDato = newDate
		periode3.incentiver = "gave"
		periode3.kommentar = "test2"
		periode3.delregisterNummer = 5678
		
		testObjekterMap['periode3'] = periode3
		
		Periode periode41 = new Periode()
		periode41.aar = "2011"
		periode41.periodeNummer = 1
		periode41.periodeType = PeriodeType.AAR
		periode41.oppstartDataInnsamling = new Date()
		periode41.hentesTidligst = new Date()
		periode41.planlagtSluttDato = newDate
		periode41.sluttDato = newDate
		periode41.incentiver = "gave"
		periode41.kommentar = "test periode 1"
		periode41.delregisterNummer = 1234
		
		Periode periode42 = new Periode()
		periode42.aar = "2012"
		periode42.periodeNummer = 2
		periode42.periodeType = PeriodeType.AAR
		periode42.oppstartDataInnsamling = new Date()
		periode42.hentesTidligst = new Date()
		periode42.planlagtSluttDato = newDate
		periode42.sluttDato = newDate
		periode42.incentiver = "gave"
		periode42.kommentar = "test periode 2"
		periode42.delregisterNummer = 1234

		testObjekterMap['periode42'] = periode42


		Skjema skjema = new Skjema()
		skjema.prosjekt = prosjekt
		skjema.undersokelseType = UndersokelsesType.BEDRIFT
		skjema.delProduktNummer = "05490-0"
		skjema.skjemaNavn = "levekaar_skjema"
		skjema.skjemaKortNavn = "lev"
		skjema.instrumentId = "skjema1"
		skjema.intervjuTypeBesok = true
		skjema.intervjuTypeTelefon = true
		skjema.intervjuTypePapir = false
		skjema.intervjuTypeWeb = false
		skjema.slettesEtterRetur = false
		skjema.altIBlaise5 = true
		
		def odiDato1 = new GregorianCalendar(2012, Calendar.JANUARY, 25)
		skjema.oppstartDataInnsamling = odiDato1.time

		skjema.klarTilGenerering = true
		skjema.klarTilUtsending = true
		skjema.status = "S"
		skjema.planlagtSluttDato = new Date()
		skjema.sluttDato = new Date()
		skjema.overtid = true
		skjema.onsketSvarProsent = 80
		skjema.dataUttaksDato = new Date()
		skjema.hentAlleOppdrag = true
		skjema.kanSlettesLokalt = true
		skjema.langtidsLagretAv = "raz"
		skjema.langtidsLagretDato = new Date()
		skjema.antallIntervjuObjekterLagret = 100
		skjema.antallOppdragLagret = 100
		skjema.intervjuVarighet = 20
		skjema.adminTid = 20
		skjema.regoverforingDato = new Date()
		skjema.regoverforingInitialer = "raz"
		skjema.regoverforingSeksjon = "750"
		skjema.ioBrevGodkjentDato = new Date()
		skjema.ioBrevGodkjentInitialer = "raz"
		skjema.krypteringDato = new Date()
		skjema.krypteringMailSendt = new Date()
		skjema.anonymDato = new Date()
		skjema.anonymMailSendt = new Date()
		skjema.ryddDato = new Date()
		skjema.ryddMailSendt = new Date()
		skjema.papirMakuleringDato = new Date()
		skjema.papirMakuleringMailSendt = new Date()
		skjema.maxAntIntervjuObjekterKontakt = 100
		skjema.malVersjon = 2
		skjema.kommentar = "test"
		skjema.aktivertForIntervjuing = false

		SkjemaVersjon skjemaVersjon = new SkjemaVersjon()
		skjemaVersjon.versjonsNummer = 1
		skjemaVersjon.gyldigFom = new Date()
		skjemaVersjon.gyldigTom = new Date()
		skjemaVersjon.skjemaGodkjentDato = new Date()
		skjemaVersjon.skjemaGodkjentInitialer = "raz"
		skjemaVersjon.webskjemaGodkjentDato  = new Date()
		skjemaVersjon.webskjemaGodkjentInitialer = "raz"
		skjemaVersjon.kommentar = "test"
		
		skjema.addToPerioder(periode)
		skjema.addToPerioder(periode2skjema1)
		skjema.addToPerioder(periode3skjema1)

		skjema.addToSkjemaVersjoner(skjemaVersjon)

		Kommune kommune1
		kommune1 = Kommune.findByKommuneNummer("0301")
		if(!kommune1) {
			kommune1= new Kommune()
			kommune1.kommuneNavn = "OSLO"
			kommune1.kommuneNummer = "0301"
		}
		
		Kommune kommune2
		kommune2 = Kommune.findByKommuneNummer("1201")
		if(!kommune2) {
			kommune2 = new Kommune()
			kommune2.kommuneNavn = "BERGEN"
			kommune2.kommuneNummer = "1201"
		}
		
		Kommune kommune3
		kommune3 = Kommune.findByKommuneNummer("0106")
		if(!kommune3) {
			kommune3 = new Kommune()
			kommune3.kommuneNavn = "FREDRIKSTAD"
			kommune3.kommuneNummer = "0106"
		}
				
		Klynge klynge
		klynge = Klynge.findByKlyngeNavn("Klynge Oslo")
		
		if(!klynge) {
			klynge = new Klynge()
			klynge.klyngeNavn = "Klynge Oslo"
			klynge.klyngeSjef = "Ola"
			klynge.beskrivelse = "Klynge for Oslo"
			klynge.epost = "raz@ssb.no"
			klynge.addToKommuner(kommune1)
		}
		

		Intervjuer intervjuer = new Intervjuer()
		intervjuer.klynge = klynge
		intervjuer.initialer = "raz"
		intervjuer.intervjuerNummer = 1
		intervjuer.navn = "brakjekk seeberg"
		intervjuer.kjonn = Kjonn.MANN
		intervjuer.fodselsDato = new Date()
		intervjuer.gateAdresse = "bollerveien 30"
		intervjuer.gateAdresse2 = "bollerveien 40"
		intervjuer.postNummer = "6789"
		intervjuer.postSted = "Loen"
		intervjuer.kommuneNummer = "1449"
		intervjuer.epostPrivat = "pal.soreng@gmail.com"
		intervjuer.epostJobb ="raz@ssb.no"
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
		intervjuer2.postNummer = "0602"
		intervjuer2.postSted = "Drammen"
		intervjuer2.kommuneNummer = "1449"
		intervjuer2.epostPrivat = "pal.soreng@gmail.com"
		intervjuer2.epostJobb ="raz@ssb.no"
		intervjuer2.status = IntervjuerStatus.AKTIV
		intervjuer2.ansattDato = new Date()
		intervjuer2.avtaltAntallTimer = 700
		intervjuer2.arbeidsType = IntervjuerArbeidsType.BESOK
		intervjuer2.sluttDato = new Date()

		Intervjuer intervjuer3 = new Intervjuer()
		intervjuer3.klynge = klynge
		intervjuer3.initialer = "krx"
		intervjuer3.intervjuerNummer = 3
		intervjuer3.navn = "Ola Nordman"
		intervjuer3.kjonn = Kjonn.MANN
		intervjuer3.fodselsDato = new Date()
		intervjuer3.gateAdresse = "Bogstadveien 17"
		intervjuer3.gateAdresse2 = "Bogstadveien 17"
		intervjuer3.postNummer = "0275"
		intervjuer3.postSted = "Oslo"
		intervjuer3.kommuneNummer = "0301"
		intervjuer3.epostPrivat = "_test_@testtesttest.com"
		intervjuer3.epostJobb ="krn@ssb.no"
		intervjuer3.status = IntervjuerStatus.AKTIV
		intervjuer3.ansattDato = new Date()
		intervjuer3.avtaltAntallTimer = 500
		intervjuer3.arbeidsType = IntervjuerArbeidsType.BESOK
		intervjuer3.sluttDato = new Date()

		Intervjuer intervjuer4 = new Intervjuer()
		intervjuer4.klynge = klynge
		intervjuer4.initialer = "kno"
		intervjuer4.intervjuerNummer = 4
		intervjuer4.navn = "Kari Nordmann"
		intervjuer4.kjonn = Kjonn.KVINNE
		intervjuer4.fodselsDato = new Date()
		intervjuer4.gateAdresse = "Storgata 100"
		intervjuer4.gateAdresse2 = "Storgata 100"
		intervjuer4.postNummer = "0250"
		intervjuer4.postSted = "Oslo"
		intervjuer4.kommuneNummer = "0301"
		intervjuer4.epostPrivat = "kari.nordmann@entestxx.com"
		intervjuer4.epostJobb ="kari.nordmann@xssbx.no"
		intervjuer4.status = IntervjuerStatus.AKTIV
		intervjuer4.ansattDato = new Date()
		intervjuer4.avtaltAntallTimer = 500
		intervjuer4.arbeidsType = IntervjuerArbeidsType.BESOK
		intervjuer4.sluttDato = new Date()

		Intervjuer intervjuer5 = new Intervjuer()
		intervjuer5.klynge = klynge
		intervjuer5.initialer = "si6"
		intervjuer5.intervjuerNummer = 5
		intervjuer5.navn = "Test Test"
		intervjuer5.kjonn = Kjonn.KVINNE
		intervjuer5.fodselsDato = new Date()
		intervjuer5.gateAdresse = "Storgata 100"
		intervjuer5.gateAdresse2 = "Storgata 100"
		intervjuer5.postNummer = "0250"
		intervjuer5.postSted = "Oslo"
		intervjuer5.kommuneNummer = "0301"
		intervjuer5.epostPrivat = "test.test@entestxx.com"
		intervjuer5.epostJobb ="test.test@xssbx.no"
		intervjuer5.status = IntervjuerStatus.AKTIV
		intervjuer5.ansattDato = new Date()
		intervjuer5.avtaltAntallTimer = 500
		intervjuer5.arbeidsType = IntervjuerArbeidsType.BESOK
		intervjuer5.sluttDato = new Date()

		Skjema skjema2 = new Skjema()
		skjema2.altIBlaise5 = false
		skjema2.prosjekt = prosjekt2
		skjema2.undersokelseType = UndersokelsesType.BEDRIFT
		skjema2.delProduktNummer = "00345-2"
		skjema2.skjemaNavn = "medie_skjema"
		skjema2.skjemaKortNavn = "med"
		skjema.instrumentId = "skjema2"
		skjema2.intervjuTypeBesok = false
		skjema2.intervjuTypeTelefon = true
		skjema2.intervjuTypePapir = false
		skjema2.intervjuTypeWeb = false
		skjema2.slettesEtterRetur = false
		
		def odiDato2 = new GregorianCalendar(2013, Calendar.JANUARY, 25)
		skjema2.oppstartDataInnsamling = odiDato2.time
		
		skjema2.klarTilGenerering = true
		skjema2.klarTilUtsending = true
		skjema2.status = "S"
		skjema2.planlagtSluttDato = new Date()
		skjema2.sluttDato = new Date()
		skjema2.overtid = false
		skjema2.onsketSvarProsent = 80
		skjema2.dataUttaksDato = new Date()
		skjema2.hentAlleOppdrag = true
		skjema2.kanSlettesLokalt = true
		skjema2.langtidsLagretAv = "raz"
		skjema2.langtidsLagretDato = new Date()
		skjema2.antallIntervjuObjekterLagret = 100
		skjema2.antallOppdragLagret = 100
		skjema2.intervjuVarighet = 20
		skjema2.adminTid = 20
		skjema2.regoverforingDato = new Date()
		skjema2.regoverforingInitialer = "raz"
		skjema2.regoverforingSeksjon = "750"
		skjema2.ioBrevGodkjentDato = new Date()
		skjema2.ioBrevGodkjentInitialer = "raz"
		skjema2.krypteringDato = new Date()
		skjema2.krypteringMailSendt = new Date()
		skjema2.anonymDato = new Date()
		skjema2.anonymMailSendt = new Date()
		skjema2.ryddDato = new Date()
		skjema2.ryddMailSendt = new Date()
		skjema2.papirMakuleringDato = new Date()
		skjema2.papirMakuleringMailSendt = new Date()
		skjema2.maxAntIntervjuObjekterKontakt = 100
		skjema2.malVersjon = 2
		skjema2.kommentar = "test"
		skjema2.aktivertForIntervjuing = false
		
		Skjema skjema3 = new Skjema()
		skjema3.altIBlaise5 = false
		skjema3.prosjekt = prosjekt3
		skjema3.undersokelseType = UndersokelsesType.PERSON
		skjema3.delProduktNummer = "05776-0"
		skjema3.skjemaNavn = "PIAAC 2011 xx"
		skjema3.skjemaKortNavn = "piaac2011xx"
		skjema.instrumentId = "skjema3"
		skjema3.intervjuTypeBesok = true
		skjema3.intervjuTypeTelefon = true
		skjema3.intervjuTypePapir = false
		skjema3.intervjuTypeWeb = false
		skjema3.slettesEtterRetur = false
		
		def odiDato3 = new GregorianCalendar(2009, Calendar.JANUARY, 25)
		skjema3.oppstartDataInnsamling = odiDato3.time
		skjema3.klarTilGenerering = true
		skjema3.klarTilUtsending = true
		skjema3.status = "S"
		skjema3.planlagtSluttDato = newDate
		skjema3.sluttDato = newDate
		skjema3.overtid = true
		skjema3.onsketSvarProsent = 80
		skjema3.dataUttaksDato = new Date()
		skjema3.hentAlleOppdrag = true
		skjema3.kanSlettesLokalt = true
		skjema3.langtidsLagretAv = "raz"
		skjema3.langtidsLagretDato = new Date()
		skjema3.antallIntervjuObjekterLagret = 100
		skjema3.antallOppdragLagret = 100
		skjema3.intervjuVarighet = 20
		skjema3.adminTid = 20
		skjema3.regoverforingDato = new Date()
		skjema3.regoverforingInitialer = "raz"
		skjema3.regoverforingSeksjon = "750"
		skjema3.ioBrevGodkjentDato = new Date()
		skjema3.ioBrevGodkjentInitialer = "raz"
		skjema3.krypteringDato = new Date()
		skjema3.krypteringMailSendt = new Date()
		skjema3.anonymDato = new Date()
		skjema3.anonymMailSendt = new Date()
		skjema3.ryddDato = new Date()
		skjema3.ryddMailSendt = new Date()
		skjema3.papirMakuleringDato = new Date()
		skjema3.papirMakuleringMailSendt = new Date()
		skjema3.maxAntIntervjuObjekterKontakt = 100
		skjema3.malVersjon = 2
		skjema3.kommentar = "test"
		skjema3.aktivertForIntervjuing = true
		
		skjema3.addToPerioder(periode3)
		
		Skjema skjema4 = new Skjema()
		skjema4.altIBlaise5 = false
		skjema4.prosjekt = prosjekt4
		skjema4.undersokelseType = UndersokelsesType.PERSON
		skjema4.delProduktNummer = "02090-2"
		skjema4.skjemaNavn = "AKU 2011 xx"
		skjema4.skjemaKortNavn = "aku2011xx"
		skjema.instrumentId = "skjema4"
		skjema4.intervjuTypeBesok = true
		skjema4.intervjuTypeTelefon = true
		skjema4.intervjuTypePapir = false
		skjema4.intervjuTypeWeb = false
		skjema4.slettesEtterRetur = false
		
		def odiDato4 = new GregorianCalendar(2014, Calendar.JANUARY, 25)
		skjema4.oppstartDataInnsamling = odiDato4.time
		skjema4.klarTilGenerering = true
		skjema4.klarTilUtsending = true
		skjema4.status = "S"
		skjema4.planlagtSluttDato = newDate
		skjema4.sluttDato = newDate
		skjema4.overtid = true
		skjema4.onsketSvarProsent = 80
		skjema4.dataUttaksDato = new Date()
		skjema4.hentAlleOppdrag = true
		skjema4.kanSlettesLokalt = true
		skjema4.langtidsLagretAv = "raz"
		skjema4.langtidsLagretDato = new Date()
		skjema4.antallIntervjuObjekterLagret = 100
		skjema4.antallOppdragLagret = 100
		skjema4.intervjuVarighet = 20
		skjema4.adminTid = 20
		skjema4.regoverforingDato = new Date()
		skjema4.regoverforingInitialer = "raz"
		skjema4.regoverforingSeksjon = "750"
		skjema4.ioBrevGodkjentDato = new Date()
		skjema4.ioBrevGodkjentInitialer = "raz"
		skjema4.krypteringDato = new Date()
		skjema4.krypteringMailSendt = new Date()
		skjema4.anonymDato = new Date()
		skjema4.anonymMailSendt = new Date()
		skjema4.ryddDato = new Date()
		skjema4.ryddMailSendt = new Date()
		skjema4.papirMakuleringDato = new Date()
		skjema4.papirMakuleringMailSendt = new Date()
		skjema4.maxAntIntervjuObjekterKontakt = 100
		skjema4.malVersjon = 2
		skjema4.kommentar = "test"
		skjema4.aktivertForIntervjuing = true
		
		skjema4.addToPerioder(periode41)
		skjema4.addToPerioder(periode42)

		
		prosjekt.save(failOnError: true, flush: true)

		skjema.save(failOnError: true, flush: true)
		skjemaVersjon.save(failOnError: true, flush: true)

		periode.save(failOnError: true, flush: true)
		periode2skjema1.save(failOnError: true, flush: true)
		periode3skjema1.save(failOnError: true, flush: true)

		kommune1.save(failOnError: true, flush: true)
		kommune2.save(failOnError: true, flush: true)
		klynge.save(failOnError: true, flush: true)
		intervjuer.save(failOnError: true, flush: true)
		intervjuer2.save(failOnError: true, flush: true)
		intervjuer3.save(failOnError: true, flush: true)
		intervjuer4.save(failOnError: true, flush: true)
		intervjuer5.save(failOnError: true, flush: true)
		skjema2.save(failOnError: true, flush: true)
		skjema3.save(failOnError: true, flush: true)
		skjema4.save(failOnError: true, flush: true)
				
		/* SIL TEST DATA */
		Calendar cal = Calendar.getInstance()
		cal.add Calendar.DAY_OF_YEAR, -1
		
		return testObjekterMap
	}


}
