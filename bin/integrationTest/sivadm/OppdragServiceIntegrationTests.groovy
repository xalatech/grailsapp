package sivadm

import grails.test.*
import siv.type.*

class OppdragServiceIntegrationTests extends GroovyTestCase {
    
	def oppdragService
	def intervjuerService
	
	Intervjuer intervjuer
	Periode periode
	Skjema skjema
		
	protected void setup() {
        super.setUp()
		
		ProsjektLeder prosjektLeder = new ProsjektLeder()
		prosjektLeder.navn = "Paal Soreng"
		prosjektLeder.initialer = "spo"
		prosjektLeder.epost = "spo"
		
		
		Prosjekt prosjekt = new Prosjekt()
		prosjekt.panel = true
		prosjekt.produktNummer = "0213"
		prosjekt.modus = ProsjektModus.MIXMODUS
		prosjekt.registerNummer = "123"
		prosjekt.prosjektNavn = "Levekaar"
		prosjekt.aargang = 2010
		prosjekt.prosjektLeder = prosjektLeder
		prosjekt.avslutningsDato = new Date()
		prosjekt.oppstartDato = new Date()
		prosjekt.prosjektStatus = ProsjektStatus.PLANLEGGING
		prosjekt.finansiering = ProsjektFinansiering.STAT
		prosjekt.prosentStat = 100
		prosjekt.prosentMarked = 0
		prosjekt.kommentar = "test"
		
		
		periode = new Periode()
		periode.aar = "2010"
		periode.periodeNummer = 1
		periode.periodeType = PeriodeType.AAR
		periode.oppstartDataInnsamling = new Date()
		periode.hentesTidligst = new Date()
		Calendar cal = Calendar.getInstance()
		cal.add Calendar.DAY_OF_MONTH, 2
		periode.planlagtSluttDato = cal.getTime()
		periode.sluttDato = cal.getTime()
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
		skjema.delProduktNummer = "0213-1"
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
		
		Adresse adresse = new Adresse()
		adresse.adresseType = AdresseType.BESOK
		adresse.gateAdresse = "bolerlia 8"
		adresse.postNummer = "2000"
		adresse.gyldigFom = new Date()
		adresse.kommuneNummer = "0301"
		adresse.gjeldende = true
		
		
		IntervjuObjekt intervjuObjekt = new IntervjuObjekt()
		intervjuObjekt.intervjuObjektNummer = "12345"
		intervjuObjekt.navn = "hulken"
		intervjuObjekt.addToAdresser( adresse )
		
		
		
		
		
		Klynge klynge = new Klynge()
		klynge.klyngeNavn = "Oslo"
		klynge.klyngeSjef = "Ola"
		klynge.beskrivelse = "Klynge for Oslo"
		klynge.epost = "spo@ssb.no"
		
		intervjuer = new Intervjuer()
		intervjuer.klynge = klynge
		intervjuer.initialer = "spo"
		intervjuer.intervjuerNummer = 1
		intervjuer.navn = "paal soreng"
		intervjuer.kjonn = Kjonn.MANN
		intervjuer.fodselsDato = new Date()
		intervjuer.gateAdresse = "bollerveien 30"
		intervjuer.gateAdresse2 = "bollerveien 40"
		intervjuer.postNummer = "4000"
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
		intervjuer2.initialer = "olo"
		intervjuer2.intervjuerNummer = 2
		intervjuer2.navn = "olo soreng"
		intervjuer2.kjonn = Kjonn.MANN
		intervjuer2.fodselsDato = new Date()
		intervjuer2.gateAdresse = "bollerveien 30"
		intervjuer2.gateAdresse2 = "bollerveien 40"
		intervjuer2.postNummer = "1000"
		intervjuer2.kommuneNummer = "0301"
		intervjuer2.epostPrivat = "pal.soreng@gmail.com"
		intervjuer2.epostJobb ="spo@ssb.no"
		intervjuer2.status = IntervjuerStatus.AKTIV
		intervjuer2.ansattDato = new Date()
		intervjuer2.avtaltAntallTimer = 700
		intervjuer2.arbeidsType = IntervjuerArbeidsType.BESOK
		intervjuer2.sluttDato = new Date()
		
		
		skjema.addToPerioder(periode)
		periode.skjema = skjema
		periode.addToIntervjuObjekter( intervjuObjekt )
		skjema.addToSkjemaVersjoner(skjemaVersjon)
		
		
		adresse.save(failOnError:true, flush:true)
		
		klynge.save(flush:true)
		intervjuer.save(flush:true)
		intervjuer2.save(flush:true)
		prosjektLeder.save(flush:true)
		prosjekt.save(flush:true)
		skjema.save(flush:true)
		skjemaVersjon.save(flush:true)
		periode.save(flush:true)
		intervjuObjekt.save(failOnError:true, flush:true)
		
		
    }

    protected void tearDown() {
        super.tearDown()
    }

    
	
	void testDataModelForTests() {
		assertEquals 1, IntervjuObjekt.list().size()	
		assertEquals 1, Adresse.list().size()
		
		def intervjuObjekt = IntervjuObjekt.findByIntervjuObjektNummer("12345")
		
		Adresse[] adresseList = intervjuObjekt.adresser
		
		assertEquals 1, adresseList.length
		
		assertEquals 2, Intervjuer.list().size()
	}
	
	
	
	
	void testFjernOppdragVedTildeling() {
		Adresse adr = new Adresse()
		adr.adresseType = AdresseType.BESOK
		adr.gateAdresse = "Storgata 8"
		adr.postNummer = "0159"
		adr.gyldigFom = new Date()
		adr.kommuneNummer = "0301"
		adr.gjeldende = true
		
		IntervjuObjekt io = new IntervjuObjekt()
		io.intervjuObjektNummer = "999888"
		io.navn = "Test Testsen"
		io.addToAdresser(adr)
		io.periode = periode
		
		io.save(failOnError: true, flush: true)
		
		// Hvis et IO har et oppdrag som ikke er synket til
		// intervjuer pc (overfortLokaltTidspunkt er null) skal
		// dette oppdraget slettes, men eventuelt andre oppdrag
		// for dette IO'et skal ikke slettes
		
		Oppdrag oppdrag1 = new Oppdrag(intervjuObjekt: io, skjemaKortNavn: "dummyKortNavn")
		oppdrag1.save(failOnError: true, flush: true)
	
		oppdragService.tildelIntervjuer(intervjuer, io, new Date(), "ttt")
			
		def oppdragListe = Oppdrag.findAllByIntervjuObjekt(io)
		
		int exp = 1
		
		assertEquals exp, oppdragListe?.size()
	}
	
	void testFindNaboIntervjuer() {
		def intervjuObjekt = IntervjuObjekt.findByIntervjuObjektNummer("12345")
		
		Intervjuer intervjuer = oppdragService.findNaboIntervjuer( intervjuObjekt, null )
		
		assertEquals "olo", intervjuer?.initialer
	}
	
	void testFindNaboIntervjuerMedList() {
		def intervjuObjekt = IntervjuObjekt.findByIntervjuObjektNummer("12345")
		
		def intervjuerList = new ArrayList()
		
		intervjuerList.add( Intervjuer.findByInitialer("spo") )
		
		Intervjuer intervjuer = oppdragService.findNaboIntervjuer( intervjuObjekt, intervjuerList )
		
		assertEquals "spo", intervjuer?.initialer
	}
	
	void testTildelIntervjuereAutomatisk() {
		def intervjuObjekter = IntervjuObjekt.list()
		def intervjuere = Intervjuer.list()
		oppdragService.tildelIntervjuereAutomatisk(intervjuObjekter, intervjuere, new Date(), null, false, "spo" )
		
		def intervjuer1 = Intervjuer.findByInitialer("olo")
		def utvalgteIntervjuObjekter1 = intervjuerService.getUtvalgteCapiIntervjuObjekter( intervjuer1.initialer, Skjema.findBySkjemaKortNavn("lev") )
		
		assertEquals 1, utvalgteIntervjuObjekter1.size()
		
		def intervjuer2 = Intervjuer.findByInitialer("spo")
		def utvalgteIntervjuObjekter2 = intervjuerService.getUtvalgteCapiIntervjuObjekter( intervjuer2.initialer, Skjema.findBySkjemaKortNavn("lev") )
		
		assertEquals 0, utvalgteIntervjuObjekter2.size()
	}
	
	
	void testTildelIntervjuereAutomatisk2() {
		
		Adresse adresse = new Adresse()
		adresse.adresseType = AdresseType.BESOK
		adresse.gateAdresse = "trondveien 18"
		adresse.postNummer = "1000"
		adresse.gyldigFom = new Date()
		adresse.kommuneNummer = "0301"
		adresse.gjeldende = true
		adresse.save(flush: true, failOnError: true)
		
		IntervjuObjekt intervjuObjekt = new IntervjuObjekt()
		intervjuObjekt.intervjuObjektNummer = "23456"
		intervjuObjekt.navn = "elvis"
		intervjuObjekt.addToAdresser( adresse )
		
		periode.addToIntervjuObjekter( intervjuObjekt )
		intervjuObjekt.save(failOnError:true, flush:true)
		
		def intervjuObjekter = IntervjuObjekt.list()
		def intervjuere = Intervjuer.list()
		oppdragService.tildelIntervjuereAutomatisk(intervjuObjekter, intervjuere, new Date(), null, false, "spo" )
		
		def intervjuer1 = Intervjuer.findByInitialer("olo")
		def utvalgteIntervjuObjekter1 = intervjuerService.getUtvalgteCapiIntervjuObjekter( intervjuer1.initialer, Skjema.findBySkjemaKortNavn("lev") )
		
		assertEquals 2, utvalgteIntervjuObjekter1.size()
		
		def intervjuer2 = Intervjuer.findByInitialer("spo")
		def utvalgteIntervjuObjekter2 = intervjuerService.getUtvalgteCapiIntervjuObjekter( intervjuer2.initialer, Skjema.findBySkjemaKortNavn("lev") )
		
		assertEquals 0, utvalgteIntervjuObjekter2.size()
	}
	
	
	void testTildelIntervjuereAutomatisk3() {
		
		Adresse adresse = new Adresse()
		adresse.adresseType = AdresseType.BESOK
		adresse.gateAdresse = "trondveien 18"
		adresse.postNummer = "3500"
		adresse.gyldigFom = new Date()
		adresse.kommuneNummer = "0301"
		adresse.gjeldende = true
		adresse.save(flush: true, failOnError: true)
		
		IntervjuObjekt intervjuObjekt = new IntervjuObjekt()
		intervjuObjekt.intervjuObjektNummer = "23456"
		intervjuObjekt.navn = "elvis"
		intervjuObjekt.addToAdresser( adresse )
		
		periode.addToIntervjuObjekter( intervjuObjekt )
		intervjuObjekt.save(failOnError:true, flush:true)
		
		def intervjuObjekter = IntervjuObjekt.list()
		def intervjuere = Intervjuer.list()
		oppdragService.tildelIntervjuereAutomatisk(intervjuObjekter, intervjuere, new Date(), null, false, "spo" )
		
		def intervjuer1 = Intervjuer.findByInitialer("olo")
		def utvalgteIntervjuObjekter1 = intervjuerService.getUtvalgteCapiIntervjuObjekter( intervjuer1.initialer, Skjema.findBySkjemaKortNavn("lev") )
		
		assertEquals 1, utvalgteIntervjuObjekter1.size()
		
		def intervjuer2 = Intervjuer.findByInitialer("spo")
		def utvalgteIntervjuObjekter2 = intervjuerService.getUtvalgteCapiIntervjuObjekter( intervjuer2.initialer, Skjema.findBySkjemaKortNavn("lev") )
		
		assertEquals 1, utvalgteIntervjuObjekter2.size()
	}
	
	
	void testTildelIntervjuereAutomatiskMedMaksIoPrIntervjuer() {
		
		Adresse adresse = new Adresse()
		adresse.adresseType = AdresseType.BESOK
		adresse.gateAdresse = "trondveien 18"
		adresse.postNummer = "1000"
		adresse.gyldigFom = new Date()
		adresse.kommuneNummer = "0301"
		adresse.gjeldende = true
		adresse.save(flush: true, failOnError: true)
		
		IntervjuObjekt intervjuObjekt = new IntervjuObjekt()
		intervjuObjekt.intervjuObjektNummer = "23456"
		intervjuObjekt.navn = "elvis"
		intervjuObjekt.addToAdresser( adresse )
		
		periode.addToIntervjuObjekter( intervjuObjekt )
		intervjuObjekt.save(failOnError:true, flush:true)
		
		def intervjuObjekter = IntervjuObjekt.list()
		def intervjuere = Intervjuer.list()
		oppdragService.tildelIntervjuereAutomatisk(intervjuObjekter, intervjuere, new Date(), new Integer(1), false, "spo" )
		
		def intervjuer1 = Intervjuer.findByInitialer("olo")
		def utvalgteIntervjuObjekter1 = intervjuerService.getUtvalgteCapiIntervjuObjekter( intervjuer1.initialer, Skjema.findBySkjemaKortNavn("lev") )
		
		assertEquals 1, utvalgteIntervjuObjekter1.size()
		
		def intervjuer2 = Intervjuer.findByInitialer("spo")
		def utvalgteIntervjuObjekter2 = intervjuerService.getUtvalgteCapiIntervjuObjekter( intervjuer2.initialer, Skjema.findBySkjemaKortNavn("lev") )
		
		assertEquals 1, utvalgteIntervjuObjekter2.size()
	}
	
	void testTildelIntervjuereAutomatiskMedMaksIoPrIntervjuerMedFamilieHensyn_ManglendeFamilieNr() {
		
		Adresse adresse = new Adresse()
		adresse.adresseType = AdresseType.BESOK
		adresse.gateAdresse = "trondveien 18"
		adresse.postNummer = "1000"
		adresse.gyldigFom = new Date()
		adresse.kommuneNummer = "0301"
		adresse.gjeldende = true
		adresse.save(flush: true, failOnError: true)
		
		IntervjuObjekt intervjuObjekt = new IntervjuObjekt()
		intervjuObjekt.intervjuObjektNummer = "23456"
		intervjuObjekt.navn = "elvis"
		intervjuObjekt.addToAdresser( adresse )
		
		periode.addToIntervjuObjekter( intervjuObjekt )
		intervjuObjekt.save(failOnError:true, flush:true)
		
		def intervjuObjekter = IntervjuObjekt.list()
		def intervjuere = Intervjuer.list()
		oppdragService.tildelIntervjuereAutomatisk(intervjuObjekter, intervjuere, new Date(), new Integer(1), true, "spo" )
		
		def intervjuer1 = Intervjuer.findByInitialer("olo")
		def utvalgteIntervjuObjekter1 = intervjuerService.getUtvalgteCapiIntervjuObjekter( intervjuer1.initialer, Skjema.findBySkjemaKortNavn("lev") )
		
		assertEquals 1, utvalgteIntervjuObjekter1.size()
		
		def intervjuer2 = Intervjuer.findByInitialer("spo")
		def utvalgteIntervjuObjekter2 = intervjuerService.getUtvalgteCapiIntervjuObjekter( intervjuer2.initialer, Skjema.findBySkjemaKortNavn("lev") )
		
		assertEquals 1, utvalgteIntervjuObjekter2.size()
	}
	
	void testTildelIntervjuereAutomatiskMedMaksIoPrIntervjuerMedFamilieHensyn_UlikeFamNr() {
		
		// setter fam nr paa eksisterende test io
		IntervjuObjekt eksIo = IntervjuObjekt.findByIntervjuObjektNummer("12345")
		eksIo.familienummer = "11111111111"
		eksIo.save(flush: true, failOnError: true)
		
		Adresse adresse = new Adresse()
		adresse.adresseType = AdresseType.BESOK
		adresse.gateAdresse = "trondveien 18"
		adresse.postNummer = "1000"
		adresse.gyldigFom = new Date()
		adresse.kommuneNummer = "0301"
		adresse.gjeldende = true
		adresse.save(flush: true, failOnError: true)
		
		IntervjuObjekt intervjuObjekt = new IntervjuObjekt()
		intervjuObjekt.intervjuObjektNummer = "23456"
		intervjuObjekt.navn = "elvis"
		intervjuObjekt.familienummer = "22222222222"
		intervjuObjekt.addToAdresser( adresse )
		
		periode.addToIntervjuObjekter( intervjuObjekt )
		intervjuObjekt.save(failOnError:true, flush:true)
		
		def intervjuObjekter = IntervjuObjekt.list()
		def intervjuere = Intervjuer.list()
		oppdragService.tildelIntervjuereAutomatisk(intervjuObjekter, intervjuere, new Date(), new Integer(1), true, "spo" )
		
		def intervjuer1 = Intervjuer.findByInitialer("olo")
		def utvalgteIntervjuObjekter1 = intervjuerService.getUtvalgteCapiIntervjuObjekter( intervjuer1.initialer, Skjema.findBySkjemaKortNavn("lev") )
		
		assertEquals 1, utvalgteIntervjuObjekter1.size()
		
		def intervjuer2 = Intervjuer.findByInitialer("spo")
		def utvalgteIntervjuObjekter2 = intervjuerService.getUtvalgteCapiIntervjuObjekter( intervjuer2.initialer, Skjema.findBySkjemaKortNavn("lev") )
		
		assertEquals 1, utvalgteIntervjuObjekter2.size()
	}
	
	
	
	void testTildelIntervjuereAutomatiskMedMaksIoPrIntervjuerMedFamilieHensyn() {
		
		// setter fam nr paa eksisterende test io
		IntervjuObjekt eksIo = IntervjuObjekt.findByIntervjuObjektNummer("12345")
		eksIo.familienummer = "11111111111"
		eksIo.save(flush: true, failOnError: true)
		
		Adresse adresse = new Adresse()
		adresse.adresseType = AdresseType.BESOK
		adresse.gateAdresse = "trondveien 18"
		adresse.postNummer = "1000"
		adresse.gyldigFom = new Date()
		adresse.kommuneNummer = "0301"
		adresse.gjeldende = true
		adresse.save(flush: true, failOnError: true)
		
		IntervjuObjekt intervjuObjekt = new IntervjuObjekt()
		intervjuObjekt.intervjuObjektNummer = "23456"
		intervjuObjekt.navn = "elvis"
		intervjuObjekt.familienummer = "11111111111"
		intervjuObjekt.addToAdresser( adresse )
		
		periode.addToIntervjuObjekter( intervjuObjekt )
		intervjuObjekt.save(failOnError:true, flush:true)
		
		def intervjuObjekter = IntervjuObjekt.list()
		def intervjuere = Intervjuer.list()
		oppdragService.tildelIntervjuereAutomatisk(intervjuObjekter, intervjuere, new Date(), new Integer(1), true, "spo" )
		
		def intervjuer1 = Intervjuer.findByInitialer("olo")
		def utvalgteIntervjuObjekter1 = intervjuerService.getUtvalgteCapiIntervjuObjekter( intervjuer1.initialer, Skjema.findBySkjemaKortNavn("lev") )
		
		assertEquals 2, utvalgteIntervjuObjekter1.size()
		
		def intervjuer2 = Intervjuer.findByInitialer("spo")
		def utvalgteIntervjuObjekter2 = intervjuerService.getUtvalgteCapiIntervjuObjekter( intervjuer2.initialer, Skjema.findBySkjemaKortNavn("lev") )
		
		assertEquals 0, utvalgteIntervjuObjekter2.size()
	}
	
	
	
	
}