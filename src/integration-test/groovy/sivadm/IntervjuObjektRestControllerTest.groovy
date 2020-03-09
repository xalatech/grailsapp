package sivadm

import grails.test.GrailsMock
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.junit.Ignore
import siv.type.ProsjektFinansiering
import siv.type.ProsjektModus
import siv.type.ProsjektStatus
import siv.type.UndersokelsesType
import util.BasicAuthUtil

@TestFor(IntervjuObjektRestController)
@Mock(IntervjuObjekt)
class IntervjuObjektRestControllerTest{

    void setup() {
        def mockUtil = new GrailsMock(BasicAuthUtil)
        mockUtil.demand.static.erAutentisert() {request, brukernavn, passord -> true}
        createPeriodeAndSkjema()
    }

    void tearDown() {
    }


    @Ignore
    void testNoIntervjuObjekt() {
        when:
        params.ioId = "1"
        request.method = 'POST'
        controller.oppdaterSkjemaStatus()

        assert response.status == 404
        assert response.json.status == 'feil'
        assert response.json.data.melding == 'Fant ikke IntervjuObjekt'
    }

    void testNoInstrumentId() {
        when:
        params.ioId = 1
        request.method = 'POST'
        request.JSON = ["instrumentId" : "aaa"]
        controller.oppdaterSkjemaStatus()

        assert response.status == 400
        assert response.json.status == 'feil'
        assert response.json.data.melding == 'instrumentId må matche skjema tilknyttet intervjuobjekt'
    }

    void testEverythingIsOk() {
        def mockService = new GrailsMock(IntervjuObjektService)
        mockService.demand.sjekkStatusEndring { -> true}

        when:
        params.ioId = 1
        request.method = 'POST'
        request.JSON = ["instrumentId" : "1234",
                        "skjemaStatus" : "Pabegynt",
                        "redigertAv" : "Bruker"
        ]

        controller.oppdaterSkjemaStatus()

        assert response.status == 200
        assert response.json.status == 'suksess'
    }


    static def createPeriodeAndSkjema() {
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
        periode.periodeType = siv.type.PeriodeType.AAR
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
        skjemaVersjon.webskjemaGodkjentDato = new Date()
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
        skjema.instrumentId = 1234

        skjema.perioder = new ArrayList()
        skjema.perioder.add(periode)
        periode.skjema = skjema
        skjema.skjemaVersjoner = new HashSet()
        skjema.skjemaVersjoner.add(skjemaVersjon)
        //skjemaVersjon.skjema = skjema

        IntervjuObjekt intervjuObjekt = new IntervjuObjekt()
        intervjuObjekt.intervjuObjektNummer = "19901"
        intervjuObjekt.navn = "hulken"
        intervjuObjekt.periode = periode

//        prosjektLeder.save(flush: true)
//        prosjekt.save(flush: true)
//        skjema.save(flush: true)
//        skjemaVersjon.save(flush: true)
//        periode.save(flush: true)

        intervjuObjekt.save(flush: true)
    }
}
