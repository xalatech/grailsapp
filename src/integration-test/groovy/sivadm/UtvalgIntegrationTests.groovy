
package sivadm

import siv.model.translator.UtvalgTranslator;
import siv.type.*;
;
import parser.Utvalg;
import parser.UtvalgParser;
import service.UtvalgService;
import util.UtvalgUtil;
import java.util.Date;

class UtvalgIntegrationTests extends GroovyTestCase {

	public static final String VALID_UTVALG = 			"02130012010AAR  1  6012T0608483227606084832276045200016       FAGERENG KIRSTI                                                                 2010401-04 RINGVEIEN 16                                 1524MOSS                                                                                 6131000XTIL OG MED 2010GP   FAGERENG KIRSTI                         IntIntIntIntIntIntInt                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            31122099                                                                  6925495197040574        spo@ssb.no                                                                                                                                           pwd123   ";
	public static final String TELEFON_FEIL_UTVALG = 	"02130012010AAR  1  6012T0608483227606084832276045200016       FAGERENG KIRSTI                                                                 2010401-04 RINGVEIEN 16                                 1524MOSS                                                                                 6131000XTIL OG MED 2010GP   FAGERENG KIRSTI                         IntIntIntIntIntIntInt                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            31122099                                                                  692549519704057         spo@ssb.no                                                                                                                                           pwd123   ";
    public static final String INGEN_TELEFON_UTVALG = 	"02130012010AAR  1  6012T0608483227606084832276045200016       FAGERENG KIRSTI                                                                 2010401-04 RINGVEIEN 16                                 1524MOSS                                                                                 6131000XTIL OG MED 2010GP   FAGERENG KIRSTI                         IntIntIntIntIntIntInt                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            31122099                                                                                          spo@ssb.no                                                                                                                                           pwd123   ";
	public static final String INGEN_ADRESSE_UTVALG = 	"02130012010AAR  1  6012T0608483227606084832276045200016       FAGERENG KIRSTI                                                                 2010401-04 RINGVEIEN 16                                     MOSS                                                                                 6131000XTIL OG MED 2010GP   FAGERENG KIRSTI                         IntIntIntIntIntIntInt                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            31122099                                                                                          spo@ssb.no                                                                                                                                           pwd123   ";

	def utvalgImport

	protected void setup() {
		super.setUp()

		ProsjektLeder prosjektLeder = new ProsjektLeder()
		prosjektLeder.navn = "Paal Soreng"
		prosjektLeder.initialer = "spo"
		prosjektLeder.epost = "spo"


		Prosjekt prosjekt = new Prosjekt()
		prosjekt.panel = true
		prosjekt.produktNummer = "02130"
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
		skjema.delProduktNummer = "0213011"
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

		utvalgImport = new UtvalgImport()
		utvalgImport.setAntallFil 40
		utvalgImport.setAntallImportert 40
		utvalgImport.setImportDato (new Date())
		utvalgImport.setImportertAv "spo"
		utvalgImport.setSkjema skjema

		prosjektLeder.save(flush:true)
		prosjekt.save(flush:true)
		skjema.save(flush:true)
		skjemaVersjon.save(failOnError: true, flush: true)
		periode.save(flush:true)
		utvalgImport.save(flush:true)
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testCriteriaSearch() {
		def list = ProsjektLeder.withCriteria { eq('navn', "Paal Soreng") }

		assertEquals 1, list.size()
	}

	void testUtvalgImportModel() {
		assertEquals 1, UtvalgImport.list().size()
	}

	void testProsjektLederModel() {
		assertEquals 1, ProsjektLeder.list().size()
	}

	void testProsjektModel() {
		assertEquals 1, Prosjekt.list().size()
	}

	void testSkjemaModel() {
		assertEquals 1, Skjema.list().size()
	}

	void testPeriodeModel() {
		assertEquals 1, Periode.list().size()
	}

	void testSkjemaVersjonModel() {
		assertEquals 1, SkjemaVersjon.list().size()
	}

	void testModelRelations() {

		def list = Skjema.withCriteria {
			perioder {
				eq('periodeNummer', new Long(1))
			}
		}

		assertNotNull list
		assertEquals 1, list.size()
	}

	void testFindPeriodeBySkjemaAndPeriodeNummer() {
		def list = Skjema.list()
		Skjema skjema = list[0]
		assertNotNull skjema
		Periode periode = Periode.findPeriodeBySkjemaAndPNr(skjema, new Long(1))
		assertNotNull periode
	}


	void testParseUtvalg() {
		String source = VALID_UTVALG + "/n"
		List<Utvalg> utvalgList = UtvalgUtil.stringToList(source)
		assertEquals(1, utvalgList.size())
	}



	void testImportUtvalg() {
		String source = VALID_UTVALG + "/n"
		List<Utvalg> utvalgList = UtvalgUtil.stringToList(source)
		assertEquals(1, utvalgList.size())

		def list = Skjema.list()
		Skjema skjema = list[0]

		UtvalgService utvalgService = new UtvalgService()
		utvalgService.importUtvalg(utvalgList, "spo", skjema, utvalgImport)

		def periodeList = Periode.list()
		Periode p = periodeList[0]

		assertNotNull p

		IntervjuObjekt[] intervjuObjekter = p.getIntervjuObjekter()

		assertNotNull intervjuObjekter

		assertEquals 1, intervjuObjekter.size()
	}


	void testImportUtvalgMedTelefonFeil() {
		String source = TELEFON_FEIL_UTVALG + "/n"
		List<Utvalg> utvalgList = UtvalgUtil.stringToList(source)
		assertEquals(1, utvalgList.size())

		def list = Skjema.list()
		Skjema skjema = list[0]

		UtvalgService utvalgService = new UtvalgService()

		boolean feilet = false

		try {
			utvalgService.importUtvalg(utvalgList, "spo", skjema, utvalgImport)
		}
		catch(Exception e) {
			feilet = true
		}

		assertTrue(feilet)

		def periodeList = Periode.list()
		Periode periode = periodeList[0]

		assertNotNull periode

		def intervjuObjekter = periode.intervjuObjekter

		assertNull(intervjuObjekter)

		def utvalgImportList = UtvalgImport.list()

		assertEquals 1, utvalgImportList.size()
	}

	
	/**
	 * Tester hva som skjer med manglende telefon i utvalgsfil. Vi onsker da at intervjuobjektet
	 * skal bli satt til sporing (UBEHANDLET og status 34).
	 */
	void testImportUtvalgMedManglendeTelefon() {
		String source = INGEN_TELEFON_UTVALG + "/n"
		List<Utvalg> utvalgList = UtvalgUtil.stringToList(source)
		assertEquals(1, utvalgList.size())

		def list = Skjema.list()
		Skjema skjema = list[0]

		UtvalgService utvalgService = new UtvalgService()
		utvalgService.importUtvalg(utvalgList, "spo", skjema, utvalgImport)

		def periodeList = Periode.list()
		Periode p = periodeList[0]

		assertNotNull p

		IntervjuObjekt[] intervjuObjekter = p.getIntervjuObjekter()

		assertNotNull intervjuObjekter

		assertEquals 1, intervjuObjekter.size()
		
		// sjekker at io er opprettet
		IntervjuObjekt intervjuObjekt = intervjuObjekter[0]
		assertNotNull intervjuObjekt
		
		// sjekker at io er satt til sporing
		assertEquals(SkjemaStatus.Ubehandlet, intervjuObjekt.katSkjemaStatus)
		assertEquals(34, intervjuObjekt.intervjuStatus)
	}


	/**
	 * Adresse er et kriterium for at io skal bli lastet inn. IO maa ha postnummer.
	 */
	void testImportUtvalgMedManglendeAdresse() {
		String source = INGEN_ADRESSE_UTVALG + "/n"
		List<Utvalg> utvalgList = UtvalgUtil.stringToList(source)
		assertEquals(1, utvalgList.size())

		def list = Skjema.list()
		Skjema skjema = list[0]

		UtvalgService utvalgService = new UtvalgService()

		boolean feilet = false

		try {
			utvalgService.importUtvalg(utvalgList, "spo", skjema, utvalgImport)
		}
		catch(Exception e) {
			feilet = true
		}

		assertTrue(feilet)

		def periodeList = Periode.list()
		Periode periode = periodeList[0]

		assertNotNull periode

		def intervjuObjekter = periode.intervjuObjekter

		assertNull(intervjuObjekter)

		def utvalgImportList = UtvalgImport.list()

		assertEquals 1, utvalgImportList.size()
	}

	
	void testImportAndEksport() {

		String source = VALID_UTVALG + "/n"

		List<Utvalg> utvalgList = UtvalgUtil.stringToList(source)
		assertEquals(1, utvalgList.size())

		def list = Skjema.list()
		Skjema skjema = list[0]

		UtvalgService utvalgService = new UtvalgService()
		utvalgService.importUtvalg(utvalgList, "spo", skjema, utvalgImport)

		def periodeList = Periode.list()
		Periode p = periodeList[0]

		assertNotNull p

		IntervjuObjekt[] intervjuObjekter = p.getIntervjuObjekter()

		assertNotNull intervjuObjekter

		assertEquals 1, intervjuObjekter.size()

		UtvalgTranslator utvalgTranslator = new UtvalgTranslator()

		Utvalg ekportertUtvalg = utvalgTranslator.translateToUtvalg(null, intervjuObjekter[0], skjema, false)

		List<Utvalg> utvalgEksportList = new ArrayList()
		utvalgEksportList.add ekportertUtvalg

		assertEquals 1, utvalgEksportList.size()

		UtvalgParser parser = new UtvalgParser()
		String exportSource = parser.writeToString(utvalgEksportList)

		this.compareSourceAndExportUtvalg(source, exportSource)
	}



	/**
	 * Sammenligner import- og eksportlinje i utvalg (minus telefon og genert id).
	 * 
	 * @param source
	 * @param export
	 * @return
	 */
	private boolean compareSourceAndExportUtvalg(String source, String export) {
		StringBuilder bSource = new StringBuilder(source)
		bSource.delete 1251, source.length()
		bSource.delete 1069, 1093 // sletter telefoner fordi disse kommer i tilfeldig rekkefolge
		println "Sammenligner import med eksport:"
		println bSource.toString()

		StringBuilder bExport = new StringBuilder(export)
		bExport.delete 1251, export.length()
		bExport.delete 1069, 1093 // sletter telefoner fordi disse kommer i tilfeldig rekkefolge
		println bExport.toString()

		assertEquals bSource.toString().trim(), bExport.toString().trim()
	}
}
