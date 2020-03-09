package sivadm

import siv.type.*;
import grails.test.*

class SkjemaServiceIntegrationTests extends GroovyTestCase {
	
	def skjema
	
	def periode
	
	def skjemaService
	
	
	
	protected void setup() {
		super.setUp()
		
		ProsjektLeder prosjektLeder = new ProsjektLeder()
		prosjektLeder.navn = "Paal Soreng"
		prosjektLeder.initialer = "spo"
		prosjektLeder.epost = "spo"
		
		Prosjekt prosjekt = new Prosjekt()
		prosjekt.panel = true
		prosjekt.produktNummer = "0123"
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
		
		periode = new Periode()
		periode.aar = "2010"
		periode.periodeNummer = 1
		periode.periodeType = PeriodeType.KVRT
		periode.oppstartDataInnsamling = new Date()
		periode.hentesTidligst = new Date()
		periode.planlagtSluttDato = new Date()
		periode.sluttDato = new Date()
		periode.incentiver = "in"
		periode.kommentar = "test"
		periode.delregisterNummer = 1234
		
		SkjemaVersjon skjemaVersjon = new SkjemaVersjon()
		skjemaVersjon.versjonsNummer = 1
		skjemaVersjon.gyldigFom = new Date()
		skjemaVersjon.gyldigTom = new Date()
		skjemaVersjon.skjemaGodkjentDato = new Date()
		skjemaVersjon.skjemaGodkjentInitialer = "spo"
		skjemaVersjon.webskjemaGodkjentDato  = new Date()
		skjemaVersjon.webskjemaGodkjentInitialer = "spo"
		skjemaVersjon.kommentar = "test"
		
		skjema = new Skjema()
		skjema.prosjekt = prosjekt
		skjema.undersokelseType = UndersokelsesType.BEDRIFT
		skjema.delProduktNummer = "0123-0"
		skjema.skjemaNavn = "levekaar_skjema"
		skjema.skjemaKortNavn = "lev"
		skjema.intervjuTypeBesok = false
		skjema.intervjuTypeTelefon = true
		skjema.intervjuTypePapir = false
		skjema.intervjuTypeWeb = false
		skjema.slettesEtterRetur = false
		skjema.oppstartDataInnsamling = new Date()
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
		
		skjema.addToPerioder(periode)
		periode.skjema = skjema
		skjema.addToSkjemaVersjoner(skjemaVersjon)
		
		prosjektLeder.save(flush:true)
		prosjekt.save(flush:true)
		
		skjema.save(flush:true)
		skjemaVersjon.save(flush:true)
		periode.save(flush:true)
	}
	
	
	
	void testFindByPeriode() {
			
		Skjema s = skjemaService.findByPeriode(periode)
		
		assertNotNull s
		
		assertEquals "lev", s.skjemaKortNavn
	}
	
	void testFindLatestSkjemaVersjonsNummer() {
		Long latestSkjemaVersjonsNummer = skjemaService.findLatestSkjemaVersjonsNummer(skjema)
		
		assertEquals 1L, latestSkjemaVersjonsNummer
	}
	
	void testFindLatestSkjemaVersjonsNummerMedFlereVersjoner() {
		SkjemaVersjon skjemaVersjon = new SkjemaVersjon()
		skjemaVersjon.versjonsNummer = 3
		skjemaVersjon.gyldigFom = new Date()
		skjemaVersjon.gyldigTom = new Date()
		skjemaVersjon.skjemaGodkjentDato = new Date()
		skjemaVersjon.skjemaGodkjentInitialer = "spo"
		skjemaVersjon.webskjemaGodkjentDato  = new Date()
		skjemaVersjon.webskjemaGodkjentInitialer = "spo"
		skjemaVersjon.kommentar = "test"
		
		skjema.addToSkjemaVersjoner(skjemaVersjon)
		
		skjema.save(flush:true)
		
		Long latestSkjemaVersjonsNummer = skjemaService.findLatestSkjemaVersjonsNummer(skjema)
		
		assertEquals 3L, latestSkjemaVersjonsNummer
	}
	
	void testFindLatestSkjemaVersjonsNummerMedFlereNull() {
		Long latestSkjemaVersjonsNummer = skjemaService.findLatestSkjemaVersjonsNummer(null)
		
		assertNull latestSkjemaVersjonsNummer
	}
	
	void testFindByPeriodeId() {
		def periode = Periode.findByDelregisterNummerAndPeriodeNummer(1234, 1)
		
		assertNotNull periode
		
		def s = skjemaService.findByPeriode(periode.id)
		
		assertNotNull s
		assertEquals "lev", s.skjemaKortNavn
	}
	
	void testFindBySkjemaVersjon() {
		def skjemaVersjon = SkjemaVersjon.findBySkjemaGodkjentInitialerAndVersjonsNummer("spo", 1)
		
		assertNotNull skjemaVersjon
		
		def s = skjemaService.findBySkjemaVersjon(skjemaVersjon)
		
		assertNotNull s
		assertEquals "lev", s.skjemaKortNavn
	}
	
	void testFindBySkjemaVersjonNull() {
		def skjemaVersjon
			
		def s = skjemaService.findBySkjemaVersjon(skjemaVersjon)
		
		assertNull s
	}
	
	void testFindBySkjemaVersjonIkkeTreff() {
		def skjemaVersjon = new SkjemaVersjon()
		skjemaVersjon.versjonsNummer = 2
		skjemaVersjon.gyldigFom = new Date()
		skjemaVersjon.gyldigTom = new Date()
		skjemaVersjon.skjemaGodkjentDato = new Date()
		skjemaVersjon.skjemaGodkjentInitialer = "krn"
		skjemaVersjon.webskjemaGodkjentDato  = new Date()
		skjemaVersjon.webskjemaGodkjentInitialer = "krn"
		skjemaVersjon.kommentar = "Dette er en test"
			
		def s = skjemaService.findBySkjemaVersjon(skjemaVersjon)
		
		assertNull s
	}
	
	void testFindBySkjemaVersjonIdNull() {
		def vId
		def s = skjemaService.findBySkjemaVersjonId(vId)
		
		assertNull s
	}
	
	void testFindBySkjemaVersjonIdIkkeTreff() {
		def s = skjemaService.findBySkjemaVersjonId(9999L)
		
		assertNull s
	}
		
	void testFindBySkjemaVersjonId() {
		def skjemaVersjon = SkjemaVersjon.findBySkjemaGodkjentInitialerAndVersjonsNummer("spo", 1)
		
		assertNotNull skjemaVersjon
		
		def s = skjemaService.findBySkjemaVersjonId(skjemaVersjon.id)
		
		assertNotNull s
		assertEquals "lev", s.skjemaKortNavn
	}
}
