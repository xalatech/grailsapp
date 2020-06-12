

package sivadm

import parser.Utvalg;
import service.UtvalgService;
import util.UtvalgUtil;
import siv.search.IntervjuObjektSearch;
import siv.type.*;
import grails.test.*

class IntervjuObjektServiceIntegrationTests extends GroovyTestCase {
	
	def intervjuObjektService
	def skjema
	def periode
	
	public static final String VALID_UTVALG = "0213-12010AAR  1  6012T0608483227606084832276045200016       FAGERENG KIRSTI                                                                 2010401-04 RINGVEIEN 16                                 1524MOSS                                                                                 6131000XTIL OG MED 2010GP   FAGERENG KIRSTI                         IntIntIntIntIntIntInt                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            31122099                                                                  6925495197040574                                                                                                                                                                      ";
	
	def utvalgImport
	
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
		
		
		Periode periode = new Periode()
		periode.aar = "2010"
		periode.periodeNummer = 1
		periode.periodeType = PeriodeType.AAR
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
		
		
		Skjema skjema = new Skjema()
		skjema.prosjekt = prosjekt
		skjema.undersokelseType = UndersokelsesType.BEDRIFT
		skjema.delProduktNummer = "0213-1"
		skjema.skjemaNavn = "levekaar_skjema"
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
		//skjemaVersjon.skjema = skjema
		
		utvalgImport = new UtvalgImport()
		utvalgImport.setAntallFil 40
		utvalgImport.setAntallImportert 40
		utvalgImport.setImportDato (new Date())
		utvalgImport.setImportertAv "spo"
		utvalgImport.setSkjema skjema
		
		prosjektLeder.save(flush:true)
		prosjekt.save(flush:true)
		
		skjema.save(flush:true)
		skjemaVersjon.save(flush:true)
		periode.save(flush:true)
		utvalgImport.save(flush:true)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSearchForSkjemaTypeBesok() {
		// imports intervjuobjekt into database (via utvalg)
		String source = VALID_UTVALG + "/n"
		List<Utvalg> utvalgList = UtvalgUtil.stringToList(source)
		assertEquals(1, utvalgList.size())
		
		def list = Skjema.list()
		Skjema skjema = list[0]
		
		UtvalgService utvalgService = new UtvalgService()
		utvalgService.importUtvalg(utvalgList, "spo", skjema, utvalgImport)
		
		assertEquals(1, IntervjuObjekt.count())
		
		// test search
		IntervjuObjektSearch intervjuObjektSearch = new IntervjuObjektSearch()
		intervjuObjektSearch.typeBesok = new Boolean(true)
		
		def pagination = ['dummy':false]
		
		def intervjuObjektList = intervjuObjektService.search( intervjuObjektSearch, pagination )
		
		assertEquals 1, intervjuObjektList.size()
		
	}
	
	void testLaasIoNull() {
		IntervjuObjekt intervjuObjekt
		
		boolean b = intervjuObjektService.laasIo(intervjuObjekt, "krn")
	
		assertEquals false, b		
	}

	void testLaasIoIdNull() {
		Long ioId
		
		boolean b = intervjuObjektService.laasIo(ioId, "krn")
	
		assertEquals false, b
	}
	
	void testLaasIoBrukerNull() {
		Long ioId = 2L
				
		boolean b = intervjuObjektService.laasIo(ioId, null)
	
		assertEquals false, b
	}
		
	void testLaasIoOgLaasOppIo() {
		IntervjuObjekt intervjuObjekt = new IntervjuObjekt()
		intervjuObjekt.intervjuObjektNummer = "12345"
		intervjuObjekt.navn = "hulken"
		
		intervjuObjekt.save(failOnError: true, flush: true)
		
		Long ioId = intervjuObjekt.id
		
		assertNotNull intervjuObjekt
		
		boolean b = intervjuObjektService.laasIo(ioId, "krn")
	
		assertEquals true, b
		
		IntervjuObjekt io = IntervjuObjekt.get(ioId)
		
		assertNotNull io
		
		assertEquals "krn", io.laastAv
		assertNotNull io.laastTidspunkt
		
		//IO-et er låst forsøk å lås på nytt. Forventer false fra låsing
		boolean bo = intervjuObjektService.laasIo(ioId, "xxx")
		assertEquals false, bo
		
		boolean bool = intervjuObjektService.laasOppIo(ioId)
		
		//Forventer true på låsOpp og null på laastAv og laastTidspunkt
		assertEquals true, bool
		
		IntervjuObjekt ioTwo = IntervjuObjekt.get(ioId)
		
		assertNull ioTwo.laastAv
		assertNull ioTwo.laastTidspunkt
	}
	
	void testLaasOppIoIdNull() {
		Long ioId = null
		
		boolean bool = intervjuObjektService.laasOppIo(ioId)
		
		// Forventer false når io id er null
		assertEquals false, bool
	}
	
	void testLaasOppIoNull() {
		IntervjuObjekt io
		
		boolean bool = intervjuObjektService.laasOppIo(io)
		
		// Forventer false når io er null
		assertEquals false, bool
	}
}