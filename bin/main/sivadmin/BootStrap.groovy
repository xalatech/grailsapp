package sivadm

import grails.gorm.transactions.Transactional
import grails.util.Environment
import sil.Lonnart
import sil.type.KontoTekstType
import sil.type.MarkedType
import siv.type.*

import java.text.DateFormat
import java.text.SimpleDateFormat
import grails.core.GrailsApplication

@Transactional
class BootStrap {

    def springSecurityService


    def dataForManuellTestingService
    def intervjuereService
    def intervjuereToService
    def intervjuereTreService
    def intervjuereFireService
    def intervjuereFemService
    def format = new SimpleDateFormat("yyyy-MM-dd")

    GrailsApplication grailsApplication

    DateFormat dateFormat

    def init = { servletContext ->
        opprettProsessLogg()

        if (Environment.getCurrent() == Environment.DEVELOPMENT) {
            dateFormat = new SimpleDateFormat("dd-MM-yyyy H:m:s")
            Map testObjekterMap = [:];

            if( Klynge.count() == 0 ) {
                opprettKlynger()
            }

            if( Rolle.count() == 0 ) {
                opprettRoller()
            }

            if( Bruker.count() == 0 ) {
                opprettTestBrukere()
            }

            if( Lonnart.count() == 0 ) {
                opprettLonnarter()
            }

            if( Produkt.count() == 0 ) {
                opprettProdukter()
            }

            if( Intervjuer.count() < 2 ) {
                intervjuereService.opprettIntervjuere()
                intervjuereToService.opprettIntervjuereTo()
                intervjuereTreService.opprettIntervjuereTre()
                intervjuereFireService.opprettIntervjuereFire()
                intervjuereFemService.opprettIntervjuereFem()
            }

            if( Prosjekt.count() == 0) {
                testObjekterMap << dataForManuellTestingService.opprettTestDataForManuellTesting()
            }

            if( IntervjuObjekt.count() == 0 ) {
                opprettIntervjuObjekt1(testObjekterMap['periode'])
                opprettIntervjuObjekt6(testObjekterMap['periode2skjema1'])
                opprettIntervjuObjekt7(testObjekterMap['periode3skjema1'])
                opprettIntervjuObjekt8(testObjekterMap['periode3skjema1'])

                opprettIntervjuObjekt2(testObjekterMap['periode'])
                opprettIntervjuObjekt3(testObjekterMap['periode3'])
                opprettIntervjuObjekt4(testObjekterMap['periode3'])
                opprettIntervjuObjekt5(testObjekterMap['periode42'])

            }

            opprettCatiGruppe()

            opprettSystemKommando()

            opprettIntHist()

            opprettUtlegg()

            opprettKjorebokerOgTimeforinger()

            opprettMeldingsheaderMaler()
        }
        else if (Environment.getCurrent() == Environment.TEST) {
            // ingen test data her - disse blir opprettet for hver integrasjonsstest
        }
        else if( Environment.getCurrent().getName() == "selenium") {
            opprettRoller()
            opprettAdminBruker()
            opprettLonnarter()
        }
        // JBOSS & ORACLE ENVIRONMENTS
        else {
            // ingen opprettelse av data her lenger - alt ligger i databasene
        }

    }

    def destroy = {
    }

    @Transactional
    def opprettSI4() {
        Bruker si4 = Bruker.findByUsername("si4")
        if(!si4) {
            Bruker bruker = new Bruker(navn: "Adm", username: 'si4', password: springSecurityService.encodePassword('S1vtest00'), enabled: true ).save(failOnError: true, flush: true)
            new BrukerRolle(bruker: bruker, rolle: rolleAdmin).save(failOnError: true, flush: true)
        }
    }
    @Transactional
    def opprettProsessLogg() {

        ProsessNavn.values().each {
            ProsessLogg eksisterendeLogg = ProsessLogg.findByProsessNavn( it )

            if( !eksisterendeLogg )
            {
                ProsessLogg initLogg = new ProsessLogg()
                initLogg.prosessNavn = it
                initLogg.tidStart = new Date()
                initLogg.suksess = true
                initLogg.save(failOnError: true)
            }
        }
    }
    @Transactional
    def opprettRoller() {
        Rolle rolleSil = new Rolle(authority: 'ROLE_SIL').save(failOnError: true, flush: true)
        Rolle rolleAdmin = new Rolle(authority: 'ROLE_ADMIN').save(failOnError: true, flush: true)
        Rolle rolleIntervjuer = new Rolle(authority: 'ROLE_INTERVJUER').save(failOnError: true, flush: true)
        Rolle rolleIntervjuerkontakt = new Rolle(authority: 'ROLE_INTERVJUERKONTAKT').save(failOnError: true, flush: true)
        Rolle rollePlanlegger = new Rolle(authority: 'ROLE_PLANLEGGER').save(failOnError: true, flush: true)
        Rolle rolleSporingsperson = new Rolle(authority: 'ROLE_SPORINGSPERSON').save(failOnError: true, flush: true)
        Rolle rolleCapiTildeling = new Rolle(authority: 'ROLE_CAPITILDELING').save(failOnError: true, flush: true)
        Rolle rolleSupervisor = new Rolle(authority: 'ROLE_SUPERVISOR').save(failOnError: true, flush: true)
    }
    @Transactional
    def opprettAdminBruker() {
        Rolle rolleAdmin = Rolle.findByAuthority("ROLE_ADMIN")

        Bruker si4 = Bruker.findByUsername("si4")
        print si4
        if(!si4) {
            Bruker bruker = new Bruker(navn: "Adm", username: 'si4', password: springSecurityService.encodePassword('S1vtest00'), enabled: true ).save(failOnError: true, flush: true)
            new BrukerRolle(bruker: bruker, rolle: rolleAdmin).save(failOnError: true, flush: true)
        }
    }
    @Transactional
    def opprettCatiGruppe(){
        def skjemaer = Skjema.list()
        def intervjuere = Intervjuer.list()
        CatiGruppe catiGruppe = new CatiGruppe(navn: "Cati gruppe 1", skjemaer: skjemaer, intervjuere: intervjuere)
        catiGruppe.save(flush: true)
    }
    @Transactional
    def opprettSystemKommando(){
        Calendar cal = Calendar.getInstance()
        cal.set Calendar.YEAR, 2015
        cal.set Calendar.MONTH, 1
        cal.set Calendar.DAY_OF_MONTH, 15
        SystemKommando sk1 = new SystemKommando(filnavn: "test1.zip", maksSekunder: 20,
                beskrivelse: "Her kommer beskrivelse for test1.zip", redigertAv: "vak", redigertDato: cal.getTime())
        sk1.save(failOnError: true, flush: true)

        cal.set Calendar.YEAR, 2015
        cal.set Calendar.MONTH, 1
        cal.set Calendar.DAY_OF_MONTH, 16
        SystemKommando sk2 = new SystemKommando(filnavn: "test2.zip", maksSekunder: 30,
                beskrivelse: "Her kommer beskrivelse for test2.zip", redigertAv: "vak", redigertDato: cal.getTime())
        sk2.save(failOnError: true, flush: true)
    }
    @Transactional
    def opprettTestBrukere() {
        Rolle rolleSil = Rolle.findByAuthority("ROLE_SIL")
        Rolle rolleAdmin = Rolle.findByAuthority("ROLE_ADMIN")
        Rolle rolleIntervjuer = Rolle.findByAuthority("ROLE_INTERVJUER")
        Rolle rolleIntervjuerkontakt = Rolle.findByAuthority("ROLE_INTERVJUERKONTAKT")
        Rolle rollePlanlegger = Rolle.findByAuthority("ROLE_PLANLEGGER")
        Rolle rolleSporingsperson = Rolle.findByAuthority("ROLE_SPORINGSPERSON")
        Rolle rolleCapiTildelingsperson = Rolle.findByAuthority("ROLE_CAPITILDELING")
        Rolle rolleSupervisor = Rolle.findByAuthority("ROLE_SUPERVISOR")

        Bruker admKrn = Bruker.findByUsername("krn")
        if(!admKrn) {
            Bruker bruker = new Bruker(navn: "Stian Karlsen", username: 'krn', password: "x", enabled: true ).save(failOnError: true, flush: true)
            new BrukerRolle(bruker: bruker, rolle: rolleSil).save(failOnError: true, flush: true)
            new BrukerRolle(bruker: bruker, rolle: rolleAdmin).save(failOnError: true, flush: true)
        }

        Intervjuer adminIntervjuerKrn = Intervjuer.findByInitialer("krn")
        if(!adminIntervjuerKrn) {
            opprettAdminIntervjuerKrn()
        }

        Bruker si1 = Bruker.findByUsername("si1")
        if(!si1) {
            Bruker bruker = new Bruker(navn: "Supervisor", username: 'si1', password: springSecurityService.encodePassword('S1vtest00'), enabled: true ).save(failOnError: true, flush: true)
            new BrukerRolle(bruker: bruker, rolle: rolleSupervisor).save(failOnError: true, flush: true)
        }

        Bruker si2 = Bruker.findByUsername("si2")
        if(!si2) {
            Bruker bruker = new Bruker(navn: "Sporing", username: 'si2', password: springSecurityService.encodePassword('S1vtest00'), enabled: true ).save(failOnError: true, flush: true)
            new BrukerRolle(bruker: bruker, rolle: rolleSporingsperson).save(failOnError: true, flush: true)
        }

        Bruker si3 = Bruker.findByUsername("si3")
        if(!si3) {
            Bruker bruker = new Bruker(navn: "Planlegger", username: 'si3', password: springSecurityService.encodePassword('S1vtest00'), enabled: true ).save(failOnError: true, flush: true)
            new BrukerRolle(bruker: bruker, rolle: rollePlanlegger).save(failOnError: true, flush: true)
        }

        Bruker si4 = Bruker.findByUsername("si4")
        if(!si4) {
            Bruker bruker = new Bruker(navn: "Adm", username: 'si4', password: springSecurityService.encodePassword('S1vtest00'), enabled: true ).save(failOnError: true, flush: true)
            new BrukerRolle(bruker: bruker, rolle: rolleAdmin).save(failOnError: true, flush: true)
        }

        Bruker si5 = Bruker.findByUsername("si5")
        if(!si5) {
            Bruker bruker = new Bruker(navn: "IK", username: 'si5', password: springSecurityService.encodePassword('S1vtest00'), enabled: true ).save(failOnError: true, flush: true)
            new BrukerRolle(bruker: bruker, rolle: rolleIntervjuerkontakt).save(failOnError: true, flush: true)
        }

        Bruker si6 = Bruker.findByUsername("si6")
        if(!si6) {
            Bruker bruker = new Bruker(navn: "Intervjuer", username: 'si6', password: springSecurityService.encodePassword('S1vtest00'), enabled: true ).save(failOnError: true, flush: true)
            new BrukerRolle(bruker: bruker, rolle: rolleIntervjuer).save(failOnError: true, flush: true)

            Intervjuer interv = Intervjuer.findByInitialer("si6")
            if(!interv) {
                opprettTestIntervjuer()
            }
        }

        Bruker si7 = Bruker.findByUsername("si7")
        if(!si7) {
            Bruker bruker = new Bruker(navn: "SIL", username: 'si7', password: springSecurityService.encodePassword('S1vtest00'), enabled: true ).save(failOnError: true, flush: true)
            new BrukerRolle(bruker: bruker, rolle: rolleSil).save(failOnError: true, flush: true)
        }

        Bruker si8 = Bruker.findByUsername("si8")
        if(!si8) {
            Bruker bruker = new Bruker(navn: "Capi Tildeling", username: 'si8', password: springSecurityService.encodePassword('S1vtest00'), enabled: true ).save(failOnError: true, flush: true)
            new BrukerRolle(bruker: bruker, rolle: rolleCapiTildelingsperson).save(failOnError: true, flush: true)
        }

    }
    @Transactional
    def opprettTestIntervjuer() {
        Kommune kommune
        kommune = Kommune.findByKommuneNummer("0301")
        if(!kommune) {
            kommune= new Kommune()
            kommune.kommuneNavn = "OSLO"
            kommune.kommuneNummer = "0301"
            kommune.save(failOnError: true, flush: true)
        }

        Klynge klynge
        klynge = Klynge.findByKlyngeNavn("Klynge Oslo")

        if(!klynge) {
            klynge = new Klynge()
            klynge.klyngeNavn = "Klynge Oslo"
            klynge.klyngeSjef = "Test"
            klynge.beskrivelse = "Klynge for Oslo"
            klynge.epost = "test_test@ssb.no"
            klynge.addToKommuner(kommune)
            klynge.save(failOnError: true, flush: true)
        }

        Intervjuer intervjuer = new Intervjuer()
        intervjuer.klynge = klynge
        intervjuer.initialer = "si4"
        intervjuer.intervjuerNummer = 112233
        intervjuer.navn = "Test Intervjuer"
        intervjuer.kjonn = Kjonn.MANN

        Calendar cal = Calendar.getInstance()
        cal.set Calendar.YEAR, 1970
        cal.set Calendar.MONTH, 1
        cal.set Calendar.DAY_OF_MONTH, 15
        intervjuer.fodselsDato = cal.getTime()

        intervjuer.gateAdresse = "Kongens Gate 11"
        intervjuer.postNummer = "0033"
        intervjuer.postSted = "Oslo"
        intervjuer.kommuneNummer = "0301"
        intervjuer.epostPrivat = "raz@ssb.no"
        intervjuer.epostJobb ="raz@ssb.no"
        intervjuer.status = IntervjuerStatus.AKTIV
        cal.set Calendar.YEAR, 2010
        cal.set Calendar.MONTH, 0
        cal.set Calendar.DAY_OF_MONTH, 0
        intervjuer.ansattDato = cal.getTime()
        intervjuer.avtaltAntallTimer = 1500
        intervjuer.arbeidsType = IntervjuerArbeidsType.BEGGE

        cal.set Calendar.YEAR, 2050
        cal.set Calendar.MONTH, 1
        cal.set Calendar.DAY_OF_MONTH, 1
        intervjuer.sluttDato = cal.getTime()

        intervjuer.save(failOnError: true, flush: true)

        // Lager 2 timeføringer for denne intervjuer

        Timeforing tf1 = new  Timeforing()
        tf1.intervjuer = intervjuer
        tf1.arbeidsType = ArbeidsType.REISE

        cal.set Calendar.YEAR, 2014
        cal.set Calendar.MONTH, 8
        cal.set Calendar.DAY_OF_MONTH, 15
        tf1.fra = cal.getTime()

        cal.set Calendar.YEAR, 2014
        cal.set Calendar.MONTH, 8
        cal.set Calendar.DAY_OF_MONTH, 16
        tf1.til = cal.getTime()

        tf1.produktNummer = "2090-03"
        tf1.save(failOnError: true, flush: true)

        Timeforing tf2 = new  Timeforing()
        tf2.intervjuer = intervjuer
        tf2.arbeidsType = ArbeidsType.TRENTE_LESTE_INSTRUKS

        cal.set Calendar.YEAR, 2014
        cal.set Calendar.MONTH, 8
        cal.set Calendar.DAY_OF_MONTH, 17
        tf2.fra = cal.getTime()

        cal.set Calendar.YEAR, 2014
        cal.set Calendar.MONTH, 8
        cal.set Calendar.DAY_OF_MONTH, 18
        tf2.til = cal.getTime()

        tf2.produktNummer = "2090-03"
        tf2.save(failOnError: true, flush: true)

    }
    @Transactional
    def opprettIntervjuObjekt1(Periode periode) {
       def gyldigFom = format.parse("2014-04-03")
        def gyldigTom = format.parse("2014-04-03")

        def adr1 = new Adresse(adresseType: AdresseType.BESOK, gateAdresse:"Olav Kyrres gt.", postNummer:"5000", postSted:"Bergen", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)
        def adr2 = new Adresse(adresseType: AdresseType.POST, gateAdresse:"Torgalmenningen 4", postNummer:"5000", postSted:"Bergen", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)


        def tlf1 = new Telefon(telefonNummer:"99999999", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf1.save(failOnError: true, flush: true)
        def tlf2 = new Telefon(telefonNummer:"88888888", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf2.save(failOnError: true, flush: true)
        def tlf3 = new Telefon(telefonNummer:"77777777", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf3.save(failOnError: true, flush: true)

        def iobj = new IntervjuObjekt(	intervjuObjektNummer:"11",
                navn:"Jens Stoltenberg",
                fodselsNummer:"11111111111",
                epost:"jens.stoltenberg@online.com",
                reservasjon: true,
                adresser:[adr1, adr2], telefoner:[tlf1, tlf2, tlf3], kilde: Kilde.CAPI, fullforingsGrad: 100)

        iobj.kontaktperiode = '03 04/11'
        iobj.intervjuStatus = 44
        iobj.periode = periode;
        iobj.maalform = 'B'
        iobj.varslingsstatus = 'KAN_VARSLES'
        iobj.intervjuer = 'krn'
        iobj.katSkjemaStatus = SkjemaStatus.Ubehandlet
        iobj.addToStatusHistorikk(new StatHist(skjemaStatus: SkjemaStatus.Pabegynt, intervjuStatus: null, redigertAv: "BlaiseWeb", dato: dateFormat.parse("23-08-2016 13:09:33")))
        iobj.addToStatusHistorikk(new StatHist(skjemaStatus: SkjemaStatus.Ubehandlet, intervjuStatus: 44, redigertAv: "krn", dato: dateFormat.parse("23-08-2016 14:10:33")))
        iobj.save(failOnError: true, flush: true)
    }
    @Transactional
    def opprettIntervjuObjekt2(Periode periode) {
        def gyldigFom = format.parse("2015-06-03")
        def gyldigTom = format.parse("2016-02-12")

        def adr1 = new Adresse(adresseType: AdresseType.BESOK, gateAdresse:"Karl Johan gt", postNummer:"1000", postSted:"Oslo", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)
        def adr2 = new Adresse(adresseType: AdresseType.POST, gateAdresse:"Storgata 4", postNummer:"1000", postSted:"Oslo", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)


        def tlf1 = new Telefon(telefonNummer:"66666666", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf1.save(failOnError: true, flush: true)
        def tlf2 = new Telefon(telefonNummer:"55555555", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf2.save(failOnError: true, flush: true)
        def tlf3 = new Telefon(telefonNummer:"44444444", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf3.save(failOnError: true, flush: true)

        def iobj = new IntervjuObjekt(	intervjuObjektNummer:"22",
                navn:"Erna Solberg",
                fodselsNummer:"22222222222",
                epost:"erna.solberg@online.com",
                reservasjon: false,
                adresser:[adr1, adr2], telefoner:[tlf1, tlf2, tlf3], kilde: Kilde.CATI, fullforingsGrad: 1000)

        iobj.delutvalg = 'NP'
        iobj.intervjuStatus = 38
        iobj.periode = periode;
        iobj.maalform = 'N'
        iobj.varslingsstatus = 'KAN_IKKE_VARSLES'
        iobj.katSkjemaStatus = SkjemaStatus.Utsendt_CATI_WEB
        iobj.save(failOnError: true, flush: true)

        iobj.addToStatusHistorikk(new StatHist(skjemaStatus: SkjemaStatus.Innlastet, intervjuStatus: null, redigertAv: 'TEST', dato: dateFormat.parse("23-08-2016 13:09:33")))
        iobj.katSkjemaStatus = SkjemaStatus.Innlastet
        iobj.save(failOnError: true, flush: true)
    }
    @Transactional
    def opprettIntervjuObjekt3(Periode periode) {
        def gyldigFom = format.parse("2015-11-03")
        def gyldigTom = format.parse("2017-02-12")

        def adr1 = new Adresse(adresseType: AdresseType.BESOK, gateAdresse:"Amtmann Leths gt", postNummer:"3100", postSted:"Molde", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)
        def adr2 = new Adresse(adresseType: AdresseType.POST, gateAdresse:"Storgata 4", postNummer:"1000", postSted:"Oslo", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)

        def tlf1 = new Telefon(telefonNummer:"66666666", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf1.save(failOnError: true, flush: true)
        def tlf2 = new Telefon(telefonNummer:"55555555", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf2.save(failOnError: true, flush: true)
        def tlf3 = new Telefon(telefonNummer:"44444444", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf3.save(failOnError: true, flush: true)

        def iobj = new IntervjuObjekt(	intervjuObjektNummer:"33",
                navn:"Kjell Magne Bondevik",
                fodselsNummer:"33333333333",
                epost:"kjellb@hotmail.com",
                reservasjon: false,
                adresser:[adr1, adr2], telefoner:[tlf1, tlf2, tlf3], kilde: Kilde.WEB)

        iobj.delutvalg = '12'
        iobj.kontaktperiode = '1'
        iobj.intervjuStatus = 9
        iobj.periode = periode;
        iobj.maalform = 'N'
        iobj.varslingsstatus = 'KAN_VARSLES'
        iobj.katSkjemaStatus = SkjemaStatus.Utsendt_CATI_WEB
        iobj.save(failOnError: true, flush: true)

        iobj.addToStatusHistorikk(new StatHist(skjemaStatus: SkjemaStatus.Innlastet, intervjuStatus: null, redigertAv: 'TEST', dato: new Date()))
        iobj.addToStatusHistorikk(new StatHist(skjemaStatus: SkjemaStatus.Pabegynt, intervjuStatus: null, redigertAv: 'TEST', dato: new Date()))
        iobj.addToStatusHistorikk(new StatHist(skjemaStatus: SkjemaStatus.Paa_vent, intervjuStatus: null, redigertAv: 'TEST', dato: new Date()))
        iobj.addToStatusHistorikk(new StatHist(skjemaStatus: SkjemaStatus.Utsendt_CATI_WEB, intervjuStatus: null, redigertAv: 'TEST', dato: new Date()))
        iobj.katSkjemaStatus = SkjemaStatus.Utsendt_CATI_WEB
        iobj.save(failOnError: true, flush: true)
    }
    @Transactional
    def opprettIntervjuObjekt4(Periode periode) {
        def gyldigFom = format.parse("2015-11-11")
        def gyldigTom = format.parse("2018-02-12")

        def adr1 = new Adresse(adresseType: AdresseType.BESOK, gateAdresse:"Karl Johan gt", postNummer:"0010", postSted:"Oslo", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)
        def adr2 = new Adresse(adresseType: AdresseType.POST, gateAdresse:"Storgata 4", postNummer:"1000", postSted:"Oslo", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)

        def tlf1 = new Telefon(telefonNummer:"66666666", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf1.save(failOnError: true, flush: true)
        def tlf2 = new Telefon(telefonNummer:"55555555", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf2.save(failOnError: true, flush: true)
        def tlf3 = new Telefon(telefonNummer:"44444444", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf3.save(failOnError: true, flush: true)

        def iobj = new IntervjuObjekt(	intervjuObjektNummer:"44",
                navn:"Jonas Gahr Støre",
                fodselsNummer:"44444444444",
                epost:"store@gmail.com",
                reservasjon: false,
                adresser:[adr1, adr2], telefoner:[tlf1, tlf2, tlf3])

        iobj.kontaktperiode = 'FERDIG'
        iobj.intervjuStatus = 10
        iobj.periode = periode;
        iobj.maalform = 'B'
        iobj.varslingsstatus = 'KAN_VARSLES'
        iobj.katSkjemaStatus = SkjemaStatus.Utsendt_CATI_WEB
        iobj.save(failOnError: true, flush: true)

        iobj.addToStatusHistorikk(new StatHist(skjemaStatus: SkjemaStatus.Innlastet, intervjuStatus: null, redigertAv: 'TEST', dato: dateFormat.parse("14-07-2016 12:46:30")))
        iobj.addToStatusHistorikk(new StatHist(skjemaStatus: SkjemaStatus.Pabegynt, intervjuStatus: 1, redigertAv: 'TEST', dato: dateFormat.parse("23-08-2016 10:01:00")))
        iobj.addToStatusHistorikk(new StatHist(skjemaStatus: SkjemaStatus.Utsendt_WEB, intervjuStatus: 2, redigertAv: 'TEST', dato: dateFormat.parse("23-08-2016 10:02:30")))
        iobj.addToStatusHistorikk(new StatHist(skjemaStatus: SkjemaStatus.Ferdig, intervjuStatus: 3, redigertAv: 'TEST', dato: dateFormat.parse("24-08-2016 11:15:19")))
        iobj.katSkjemaStatus = SkjemaStatus.Ferdig
        iobj.save(failOnError: true, flush: true)
    }
    @Transactional
    def opprettIntervjuObjekt5(Periode periode) {
        def gyldigFom = format.parse("2014-04-03")
        def gyldigTom = format.parse("2014-04-03")

        def adr1 = new Adresse(adresseType: AdresseType.BESOK, gateAdresse:"Olav Kyrres gt.", postNummer:"5000", postSted:"Bergen", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)
        def adr2 = new Adresse(adresseType: AdresseType.POST, gateAdresse:"Torgalmenningen 4", postNummer:"5000", postSted:"Bergen", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)


        def tlf1 = new Telefon(telefonNummer:"99999999", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf1.save(failOnError: true, flush: true)
        def tlf2 = new Telefon(telefonNummer:"88888888", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf2.save(failOnError: true, flush: true)
        def tlf3 = new Telefon(telefonNummer:"77777777", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf3.save(failOnError: true, flush: true)

        def iobj = new IntervjuObjekt(	intervjuObjektNummer:"12",
                navn:"Jens Stoltenberg",
                fodselsNummer:"11111111111",
                epost:"jens.stoltenberg@online.com",
                reservasjon: true,
                adresser:[adr1, adr2], telefoner:[tlf1, tlf2, tlf3], kilde: Kilde.CAPI, fullforingsGrad: 100)

        iobj.kontaktperiode = '03 04/11'
        iobj.intervjuStatus = 11
        iobj.periode = periode;
        iobj.maalform = 'B'
        iobj.varslingsstatus = ''
        iobj.katSkjemaStatus = SkjemaStatus.Utsendt_CAPI
        iobj.save(failOnError: true, flush: true)
    }
    @Transactional
    def opprettIntervjuObjekt6(Periode periode) {
        def gyldigFom = format.parse("2014-04-03")
        def gyldigTom = format.parse("2014-04-03")

        def adr1 = new Adresse(adresseType: AdresseType.BESOK, gateAdresse:"Olav Kyrres gt.", postNummer:"5000", postSted:"Bergen", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)
        def adr2 = new Adresse(adresseType: AdresseType.POST, gateAdresse:"Torgalmenningen 4", postNummer:"5000", postSted:"Bergen", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)


        def tlf1 = new Telefon(telefonNummer:"99999999", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf1.save(failOnError: true, flush: true)
        def tlf2 = new Telefon(telefonNummer:"88888888", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf2.save(failOnError: true, flush: true)
        def tlf3 = new Telefon(telefonNummer:"77777777", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf3.save(failOnError: true, flush: true)

        def iobj = new IntervjuObjekt(	intervjuObjektNummer:"1",
                navn:"Jens Jensen",
                fodselsNummer:"66666666666",
                epost:"jens.jensen@online.com",
                reservasjon: true,
                adresser:[adr1, adr2], telefoner:[tlf1, tlf2, tlf3], kilde: Kilde.CAPI, fullforingsGrad: 100)

        iobj.kontaktperiode = '03 04/11'
        iobj.intervjuStatus = 23
        iobj.periode = periode;
        iobj.varslingsstatus = 'KAN_VARSLES'
        iobj.katSkjemaStatus = SkjemaStatus.Utsendt_CATI_WEB
        iobj.save(failOnError: true, flush: true)
    }
    @Transactional
    def opprettIntervjuObjekt7(Periode periode) {
        def gyldigFom = format.parse("2014-04-03")
        def gyldigTom = format.parse("2014-04-03")

        def adr1 = new Adresse(adresseType: AdresseType.BESOK, gateAdresse:"Olav Kyrres gt.", postNummer:"5000", postSted:"Bergen", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)
        def adr2 = new Adresse(adresseType: AdresseType.POST, gateAdresse:"Torgalmenningen 4", postNummer:"5000", postSted:"Bergen", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)


        def tlf1 = new Telefon(telefonNummer:"99999999", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf1.save(failOnError: true, flush: true)
        def tlf2 = new Telefon(telefonNummer:"88888888", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf2.save(failOnError: true, flush: true)
        def tlf3 = new Telefon(telefonNummer:"77777777", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf3.save(failOnError: true, flush: true)

        def iobj = new IntervjuObjekt(	intervjuObjektNummer:"10",
                navn:"Nils Nilsen",
                fodselsNummer:"77777777777",
                epost:"nils.nilsen@online.com",
                reservasjon: true,
                adresser:[adr1, adr2], telefoner:[tlf1, tlf2, tlf3], kilde: Kilde.CAPI, fullforingsGrad: 100)

        iobj.kontaktperiode = '03 04/11'
        iobj.intervjuStatus = 89
        iobj.periode = periode;
        iobj.varslingsstatus = 'KAN_IKKE_VARSLES'
        iobj.katSkjemaStatus = SkjemaStatus.Utsendt_CATI_WEB
        iobj.save(failOnError: true, flush: true)
    }
    @Transactional
    def opprettIntervjuObjekt8(Periode periode) {
        def gyldigFom = format.parse("2014-04-03")
        def gyldigTom = format.parse("2014-04-03")

        def adr1 = new Adresse(adresseType: AdresseType.BESOK, gateAdresse:"Olav Kyrres gt.", postNummer:"5000", postSted:"Bergen", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)
        def adr2 = new Adresse(adresseType: AdresseType.POST, gateAdresse:"Torgalmenningen 4", postNummer:"5000", postSted:"Bergen", gyldigFom: gyldigFom, gyldigTom: gyldigTom, gjeldende: true)
        adr1.save(failOnError: true, flush: true)


        def tlf1 = new Telefon(telefonNummer:"99999999", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf1.save(failOnError: true, flush: true)
        def tlf2 = new Telefon(telefonNummer:"88888888", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf2.save(failOnError: true, flush: true)
        def tlf3 = new Telefon(telefonNummer:"77777777", kommentar:"Bla blu", kilde:"DIFI KRR", gjeldende: true)
        tlf3.save(failOnError: true, flush: true)

        def iobj = new IntervjuObjekt(	intervjuObjektNummer:"2",
                navn:"Herman Hermansen",
                fodselsNummer:"88888888888",
                epost:"herman.hermansen@online.com",
                reservasjon: true,
                adresser:[adr1, adr2], telefoner:[tlf1, tlf2, tlf3], kilde: Kilde.CAPI, fullforingsGrad: 100)

        iobj.kontaktperiode = '03 04/11'
        iobj.intervjuStatus = 56
        iobj.periode = periode;
        iobj.varslingsstatus = 'KAN_VARSLES'
        iobj.katSkjemaStatus = SkjemaStatus.Utsendt_CATI_WEB
        iobj.save(failOnError: true, flush: true)
    }

    @Transactional
    def opprettAdminIntervjuerKrn() {
        Kommune kommune
        kommune = Kommune.findByKommuneNummer("0301")
        if(!kommune) {
            kommune= new Kommune()
            kommune.kommuneNavn = "Oslo"
            kommune.kommuneNummer = "0301"
            kommune.save(failOnError: true, flush: true)
        }

        Klynge klynge
        klynge = Klynge.findByKlyngeNavn("Klynge Oslo")

        if(!klynge) {
            klynge = new Klynge()
            klynge.klyngeNavn = "Klynge Oslo"
            klynge.klyngeSjef = "Test"
            klynge.beskrivelse = "Klynge for Oslo"
            klynge.epost = "test_test@ssb.no"
            klynge.addToKommuner(kommune)
            klynge.save(failOnError: true, flush: true)
        }

        Intervjuer intervjuer = new Intervjuer()
        intervjuer.klynge = klynge
        intervjuer.initialer = "krn"
        intervjuer.intervjuerNummer = 113355
        intervjuer.navn = "Stian Karlsen"
        intervjuer.kjonn = Kjonn.MANN

        Calendar cal = Calendar.getInstance()
        cal.set Calendar.YEAR, 1974
        cal.set Calendar.MONTH, 2
        cal.set Calendar.DAY_OF_MONTH, 15
        intervjuer.fodselsDato = cal.getTime()

        intervjuer.gateAdresse = "Etterstadsletta 68"
        intervjuer.postNummer = "0659"
        intervjuer.postSted = "Oslo"
        intervjuer.kommuneNummer = "0301"
        intervjuer.epostPrivat = "texasslim@gmail.com"
        intervjuer.epostJobb ="krn@ssb.no"
        intervjuer.status = IntervjuerStatus.AKTIV
        cal.set Calendar.YEAR, 2010
        cal.set Calendar.MONTH, 0
        cal.set Calendar.DAY_OF_MONTH, 0
        intervjuer.ansattDato = cal.getTime()
        intervjuer.avtaltAntallTimer = 1500
        intervjuer.arbeidsType = IntervjuerArbeidsType.BEGGE

        cal.set Calendar.YEAR, 2050
        cal.set Calendar.MONTH, 1
        cal.set Calendar.DAY_OF_MONTH, 1
        intervjuer.sluttDato = cal.getTime()

        intervjuer.save(failOnError: true, flush: true)

        def fravaer

        def now = System.currentTimeMillis()

        for (int i=0; i<20; i++) {
            fravaer = new Fravaer(kommentar: 'Fravær '+(i+1), fraTidspunkt: new Date(now+i), tilTidspunkt: new Date(now+i), intervjuer: intervjuer, prosent: 100, fravaerType: FravaerType.ANNET, redigertAv: 'meg', redigertDato: new Date())
            fravaer.save(failOnError: true, flush: true)
        }

    }
    @Transactional
    def opprettAdminIntervjuer() {
        Kommune kommune
        kommune = Kommune.findByKommuneNummer("0301")
        if(!kommune) {
            kommune= new Kommune()
            kommune.kommuneNavn = "Oslo"
            kommune.kommuneNummer = "0301"
            kommune.save(failOnError: true, flush: true)
        }

        Klynge klynge
        klynge = Klynge.findByKlyngeNavn("Klynge Oslo")

        if(!klynge) {
            klynge = new Klynge()
            klynge.klyngeNavn = "Klynge Oslo"
            klynge.klyngeSjef = "Test"
            klynge.beskrivelse = "Klynge for Oslo"
            klynge.epost = "test_test@ssb.no"
            klynge.addToKommuner(kommune)
            klynge.save(failOnError: true, flush: true)
        }

        Intervjuer intervjuer = new Intervjuer()
        intervjuer.klynge = klynge
        intervjuer.initialer = "ad1"
        intervjuer.intervjuerNummer = 113356
        intervjuer.navn = "Admin Intervjuer"
        intervjuer.kjonn = Kjonn.MANN

        Calendar cal = Calendar.getInstance()
        cal.set Calendar.YEAR, 1960
        cal.set Calendar.MONTH, 1
        cal.set Calendar.DAY_OF_MONTH, 15
        intervjuer.fodselsDato = cal.getTime()

        intervjuer.gateAdresse = "Kongens Gate 11"
        intervjuer.postNummer = "0033"
        intervjuer.postSted = "Oslo"
        intervjuer.kommuneNummer = "0301"
        intervjuer.epostPrivat = "texasslim@gmail.com"
        intervjuer.epostJobb ="krn@ssb.no"
        intervjuer.status = IntervjuerStatus.AKTIV
        cal.set Calendar.YEAR, 2010
        cal.set Calendar.MONTH, 0
        cal.set Calendar.DAY_OF_MONTH, 0
        intervjuer.ansattDato = cal.getTime()
        intervjuer.avtaltAntallTimer = 1500
        intervjuer.arbeidsType = IntervjuerArbeidsType.BEGGE

        cal.set Calendar.YEAR, 2050
        cal.set Calendar.MONTH, 1
        cal.set Calendar.DAY_OF_MONTH, 1
        intervjuer.sluttDato = cal.getTime()

        intervjuer.save(failOnError: true, flush: true)
    }

    @Transactional
    def opprettProdukter() {
        // FASTE TIMEFØRINGS PROSJEKTER/PRODUKTER
        Produkt produktOne = new Produkt(navn: "Etter avtale med kontoret", produktNummer: "00980-0", finansiering: ProsjektFinansiering.STAT_MARKED, prosentStat: 50, prosentMarked: 50)
        produktOne.save(failOnError: true, flush: true)
        Produkt produktTwo = new Produkt(navn: "Opplæring", produktNummer: "00980-1", finansiering: ProsjektFinansiering.STAT_MARKED, prosentStat: 50, prosentMarked: 50)
        produktTwo.save(failOnError: true, flush: true)
        Produkt produktThree = new Produkt(navn: "SIV - System for intervjuvirksomhet, FOSS", produktNummer: "07413-0", finansiering: ProsjektFinansiering.STAT_MARKED, prosentStat: 50, prosentMarked: 50)
        produktThree.save(failOnError: true, flush: true)
        Produkt produktFour = new Produkt(navn: "Utredning av intervjuvirksomheten", produktNummer: "01209-0", finansiering: ProsjektFinansiering.STAT_MARKED, prosentStat: 50, prosentMarked: 50)
        produktFour.save(failOnError: true, flush: true)
        Produkt produktFive = new Produkt(navn: "Helse, miljø og sikkerhet", produktNummer: "01830-0", finansiering: ProsjektFinansiering.STAT_MARKED, prosentStat: 50, prosentMarked: 50)
        produktFive.save(failOnError: true, flush: true)
        Produkt produktSix = new Produkt(navn: "Problemer med PCen", produktNummer: "01860-0", finansiering: ProsjektFinansiering.STAT_MARKED, prosentStat: 50, prosentMarked: 50)
        produktSix.save(failOnError: true, flush: true)
        Produkt produktSeven = new Produkt(navn: "KPI: Prisinnsamling", produktNummer: "02480-9", finansiering: ProsjektFinansiering.STAT_MARKED, prosentStat: 50, prosentMarked: 50)
        produktSeven.save(failOnError: true, flush: true)
        Produkt produktEight = new Produkt(navn: "Fagforeningsarbeid", produktNummer: "03220-0", finansiering: ProsjektFinansiering.STAT_MARKED, prosentStat: 50, prosentMarked: 50)
        produktEight.save(failOnError: true, flush: true)
        Produkt produktNine = new Produkt(navn: "Administrasjon", produktNummer: "01004-0", finansiering: ProsjektFinansiering.STAT_MARKED, prosentStat: 50, prosentMarked: 50)
        produktNine.save(failOnError: true, flush: true)
    }
    @Transactional
    def opprettLonnarter() {
        /* LONNARTER */
        Lonnart lonn_1 = new Lonnart(lonnartNummer: "2003", navn: "Pensjonistavlønning", konto: "1620215020", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
        lonn_1.save(failOnError: true, flush: true)
        Lonnart lonn_2 = new Lonnart(lonnartNummer: "2003", navn: "Pensjonistavlønning", konto: "1620015020", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
        lonn_2.save(failOnError: true, flush: true)
        Lonnart lonn_3 = new Lonnart(lonnartNummer: "3110", navn: "Timelønn lokal", konto: "1620015020", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
        lonn_3.save(failOnError: true, flush: true)
        Lonnart lonn_4 = new Lonnart(lonnartNummer: "3110", navn: "Timelønn lokal", konto: "1620215020", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
        lonn_4.save(failOnError: true, flush: true)

        Lonnart lonn_3a = new Lonnart(lonnartNummer: "3192", navn: "Timelønn lokal etter 16:30", konto: "1620015020", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
        lonn_3a.save(failOnError: true, flush: true)
        Lonnart lonn_4a = new Lonnart(lonnartNummer: "3192", navn: "Timelønn lokal etter 16:30", konto: "1620215020", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
        lonn_4a.save(failOnError: true, flush: true)

        Lonnart lonn_5 = new Lonnart(lonnartNummer: "3112", navn: "Timelønn", konto: "1620015020", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
        lonn_5.save(failOnError: true, flush: true)
        Lonnart lonn_6 = new Lonnart(lonnartNummer: "3112", navn: "Timelønn", konto: "1620215020", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
        lonn_6.save(failOnError: true, flush: true)

        Lonnart lonn_5a = new Lonnart(lonnartNummer: "3196", navn: "Timelønn etter 16:30", konto: "1620015020", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
        lonn_5a.save(failOnError: true, flush: true)
        Lonnart lonn_6a = new Lonnart(lonnartNummer: "3196", navn: "Timelønn etter 16:30", konto: "1620215020", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
        lonn_6a.save(failOnError: true, flush: true)

        Lonnart lonn_9 = new Lonnart(lonnartNummer: "3116", navn: "Overtid", konto: "1620015020", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.STAT, redigertAv: "krn")
        lonn_9.save(failOnError: true, flush: true)
        Lonnart lonn_10 = new Lonnart(lonnartNummer: "3116", navn: "Overtid", konto: "1620215020", kontoTekst: KontoTekstType.LONNSUTGIFT ,markedType: MarkedType.MARKED, redigertAv: "krn")
        lonn_10.save(failOnError: true, flush: true)
        Lonnart lonn_19 = new Lonnart(lonnartNummer: "1462", navn: "Refusjon av div.", konto: "1620215990", kontoTekst: KontoTekstType.DIVERSE_UTGIFTER ,markedType: MarkedType.MARKED, redigertAv: "krn")
        lonn_19.save(failOnError: true, flush: true)
        Lonnart lonn_20 = new Lonnart(lonnartNummer: "1462", navn: "Refusjon av div.", konto: "1620015990", kontoTekst: KontoTekstType.DIVERSE_UTGIFTER ,markedType: MarkedType.STAT, redigertAv: "krn")
        lonn_20.save(failOnError: true, flush: true)
        Lonnart lonn_21 = new Lonnart(lonnartNummer: "5705", navn: "Reiseutlegg", konto: "1620217134", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.MARKED, redigertAv: "krn")
        lonn_21.save(failOnError: true, flush: true)
        Lonnart lonn_22 = new Lonnart(lonnartNummer: "5705", navn: "Reiseutlegg", konto: "1620217134", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.STAT, redigertAv: "krn")
        lonn_22.save(failOnError: true, flush: true)
        Lonnart lonn_23 = new Lonnart(lonnartNummer: "5711", navn: "Bil", kmKode: "1", konto: "1620217134", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.MARKED, redigertAv: "krn")
        lonn_23.save(failOnError: true, flush: true)
        Lonnart lonn_24 = new Lonnart(lonnartNummer: "5711", navn: "Bil", kmKode: "1", konto: "1620017134", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.STAT, redigertAv: "krn")
        lonn_24.save(failOnError: true, flush: true)
        Lonnart lonn_25 = new Lonnart(lonnartNummer: "5712", navn: "Passasjertillegg", kmKode: "10", konto: "1620017134", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.STAT, redigertAv: "krn")
        lonn_25.save(failOnError: true, flush: true)
        Lonnart lonn_26 = new Lonnart(lonnartNummer: "5712", navn: "Passasjertillegg", kmKode: "10", konto: "1620217134", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.MARKED, redigertAv: "krn")
        lonn_26.save(failOnError: true, flush: true)
        Lonnart lonn_27 = new Lonnart(lonnartNummer: "5715", navn: "Liten båt", kmKode: "4", konto: "1620217134", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.MARKED, redigertAv: "krn")
        lonn_27.save(failOnError: true, flush: true)
        Lonnart lonn_28 = new Lonnart(lonnartNummer: "5715", navn: "Liten båt", kmKode: "4", konto: "1620017134", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.STAT, redigertAv: "krn")
        lonn_28.save(failOnError: true, flush: true)
        Lonnart lonn_29 = new Lonnart(lonnartNummer: "5716", navn: "Motorsykkel", kmKode: "2", konto: "1620217134", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.MARKED, redigertAv: "krn")
        lonn_29.save(failOnError: true, flush: true)
        Lonnart lonn_30 = new Lonnart(lonnartNummer: "5716", navn: "Motorsykkel", kmKode: "2", konto: "1620017134", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.STAT, redigertAv: "krn")
        lonn_30.save(failOnError: true, flush: true)
        Lonnart lonn_31 = new Lonnart(lonnartNummer: "5717", navn: "Sykkel/moped", kmKode: "3", konto: "1620217134", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.MARKED, redigertAv: "krn")
        lonn_31.save(failOnError: true, flush: true)
        Lonnart lonn_32 = new Lonnart(lonnartNummer: "5717", navn: "Sykkel/moped", kmKode: "3", konto: "1620017134", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.STAT, redigertAv: "krn")
        lonn_32.save(failOnError: true, flush: true)
        Lonnart lonn_33 = new Lonnart(lonnartNummer: "5718", navn: "Km. godtgjørelse - sats for Tromsø", kmKode: "1", konto: "1620217134", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.MARKED, redigertAv: "krn")
        lonn_33.save(failOnError: true, flush: true)
        Lonnart lonn_34 = new Lonnart(lonnartNummer: "5718", navn: "Km. godtgjørelse - sats for Tromsø", kmKode: "1", konto: "1620017134", kontoTekst: KontoTekstType.KILOMETERTILLEGG ,markedType: MarkedType.STAT, redigertAv: "krn")
        lonn_34.save(failOnError: true, flush: true)
    }

    @Transactional
    def opprettKlynger() {
        Klynge klynge1 = new Klynge(klyngeNavn: "Klynge Oslo", klyngeSjef: "ato", epost: "klyngeoslo@ssb.no", beskrivelse: "Klynge for Oslo")
        Klynge klynge2 = new Klynge(klyngeNavn: "Klynge Østlandet", klyngeSjef: "hag", epost: "klyngeost@ssb.no", beskrivelse: "Klynge for Østlandet")
        Klynge klynge3 = new Klynge(klyngeNavn: "Klynge Innlandet", klyngeSjef: "aee", epost: "klyngeinnlandet@ssb.no", beskrivelse: "Klynge for Innlandet")
        Klynge klynge4 = new Klynge(klyngeNavn: "Klynge Sør-Vest", klyngeSjef: "hbl", epost: "klyngesorvest@ssb.no", beskrivelse: "Klynge for Sør-Vestlandet")
        Klynge klynge5 = new Klynge(klyngeNavn: "Klynge Vest", klyngeSjef: "hbs", epost: "klyngevest@ssb.no", beskrivelse: "Klynge for Vestlandet")
        Klynge klynge6 = new Klynge(klyngeNavn: "Klynge Midt-Norge", klyngeSjef: "kag", epost: "klyngemidtnorge@ssb.no", beskrivelse: "Klynge for Midt-Norge")
        Klynge klynge7 = new Klynge(klyngeNavn: "Klynge Nord", klyngeSjef: "hbl", epost: "klyngenord@ssb.no", beskrivelse: "Klynge for Nord-Norge")
        Klynge klynge8 = new Klynge(klyngeNavn: "Klynge Ringesenter Oslo", klyngeSjef: "ruw", epost: "klyngeringesenteroslo@ssb.no", beskrivelse: "Klynge for Ringesenter Oslo")
        Klynge klynge9 = new Klynge(klyngeNavn: "Klynge Ringesenter Kongsvinger", klyngeSjef: "ord", epost: "klyngeringesenterkongsvinger@ssb.no", beskrivelse: "Klynge for Ringesenter Kongsvinger")
        klynge1.save(failOnError: true, flush: true)
        klynge2.save(failOnError: true, flush: true)
        klynge3.save(failOnError: true, flush: true)
        klynge4.save(failOnError: true, flush: true)
        klynge5.save(failOnError: true, flush: true)
        klynge6.save(failOnError: true, flush: true)
        klynge7.save(failOnError: true, flush: true)
        klynge8.save(failOnError: true, flush: true)
        klynge9.save(failOnError: true, flush: true)

        // Opprette et par flere klynger
        def klynge = new Klynge()
        klynge.klyngeNavn = "Sluttet"
        klynge.save(failOnError: true, flush: true)

        klynge = new Klynge()
        klynge.klyngeNavn = "Kontoret"
        klynge.save(failOnError: true, flush: true)
    }
    @Transactional
    def knyttKlyngeKommuneKoblinger() {
        Klynge k1 = Klynge.findByKlyngeNavn("Klynge Oslo")
        Klynge k2 = Klynge.findByKlyngeNavn("Klynge Østlandet")
        Klynge k3 = Klynge.findByKlyngeNavn("Klynge Innlandet")
        Klynge k4 = Klynge.findByKlyngeNavn("Klynge Sør-Vest")
        Klynge k5 = Klynge.findByKlyngeNavn("Klynge Vest")
        Klynge k6 = Klynge.findByKlyngeNavn("Klynge Midt-Norge")
        Klynge k7 = Klynge.findByKlyngeNavn("Klynge Nord")
        Klynge k8 = Klynge.findByKlyngeNavn("Klynge Ringesenter Oslo")
        Klynge k9 = Klynge.findByKlyngeNavn("Klynge Ringesenter Kongsvinger")

        def kommuneListe1 = ['0301']
        def kommuneListe2 = ['0101' ,'0104' ,'0105' ,'0106' ,'0111' ,'0118' ,'0119' ,'0121' ,'0122' ,'0123' ,'0124' ,'0125' ,'0127' ,'0128' ,'0135' ,'0136' ,'0137' ,'0138' ,'0211' ,'0213' ,'0214' ,'0215' ,'0216' ,'0217' ,'0221' ,'0226' ,'0227' ,'0228' ,'0229' ,'0230' ,'0231' ,'0233' ,'0234' ,'0235' ,'0236' ,'0237' ,'0238' ,'0239' ,'0402' ,'0403' ,'0412' ,'0415' ,'0417' ,'0418' ,'0419' ,'0420' ,'0423' ,'0425' ,'0426' ,'0427' ,'0428' ,'0429' ,'0430' ,'0432' ,'0434']
        def kommuneListe3 = ['0219' ,'0220' ,'0501' ,'0502' ,'0513' ,'0514' ,'0515' ,'0516' ,'0517' ,'0519' ,'0520' ,'0521' ,'0522' ,'0528' ,'0529' ,'0532' ,'0533' ,'0534' ,'0536' ,'0538' ,'0540' ,'0541' ,'0542' ,'0543' ,'0544' ,'0545' ,'0602' ,'0604' ,'0605' ,'0612' ,'0615' ,'0616' ,'0617' ,'0618' ,'0619' ,'0620' ,'0621' ,'0622' ,'0623' ,'0624' ,'0625' ,'0626' ,'0627' ,'0628' ,'0631' ,'0632' ,'0633' ,'0701' ,'0702' ,'0704' ,'0706' ,'0709' ,'0711' ,'0713' ,'0714' ,'0716' ,'0719' ,'0720' ,'0722' ,'0723' ,'0728' ,'0805' ,'0806' ,'0807' ,'0811' ,'0814' ,'0815' ,'0817' ,'0819' ,'0821' ,'0822' ,'0826' ,'0827' ,'0828' ,'0829' ,'0830' ,'0831' ,'0833' ,'0834' ,'0901']
        def kommuneListe4 = ['0904' ,'0906' ,'0911' ,'0912' ,'0914' ,'0919' ,'0926' ,'0928' ,'0929' ,'0935' ,'0937' ,'0938' ,'0940' ,'0941' ,'1001' ,'1002' ,'1003' ,'1004' ,'1014' ,'1017' ,'1018' ,'1021' ,'1026' ,'1027' ,'1029' ,'1032' ,'1034' ,'1037' ,'1046' ,'1101' ,'1102' ,'1103' ,'1106' ,'1111' ,'1112' ,'1114' ,'1119' ,'1120' ,'1121' ,'1122' ,'1124' ,'1127' ,'1129' ,'1130' ,'1133' ,'1134' ,'1135' ,'1141' ,'1142' ,'1144' ,'1145' ,'1146' ,'1149' ,'1151' ,'1160' ,'1211' ,'1216' ,'1219' ,'1221' ,'1222' ,'1223' ,'1228']
        def kommuneListe5 = ['1201' ,'1224' ,'1227' ,'1231' ,'1232' ,'1233' ,'1234' ,'1235' ,'1238' ,'1241' ,'1242' ,'1243' ,'1244' ,'1245' ,'1246' ,'1247' ,'1251' ,'1252' ,'1253' ,'1256' ,'1259' ,'1260' ,'1263' ,'1264' ,'1265' ,'1266' ,'1401' ,'1411' ,'1412' ,'1413' ,'1416' ,'1417' ,'1418' ,'1419' ,'1420' ,'1421' ,'1422' ,'1424' ,'1426' ,'1428' ,'1429' ,'1430' ,'1431' ,'1432' ,'1433' ,'1438' ,'1439' ,'1441' ,'1443' ,'1444' ,'1445' ,'1449']
        def kommuneListe6 = ['0436' ,'0437' ,'0438' ,'0439' ,'0441' ,'0511' ,'0512' ,'1502' ,'1505' ,'1504' ,'1511' ,'1514' ,'1515' ,'1516' ,'1517' ,'1519' ,'1520' ,'1523' ,'1524' ,'1525' ,'1526' ,'1528' ,'1529' ,'1531' ,'1532' ,'1534' ,'1535' ,'1539' ,'1543' ,'1545' ,'1546' ,'1547' ,'1548' ,'1551' ,'1554' ,'1557' ,'1560' ,'1563' ,'1566' ,'1567' ,'1571' ,'1573' ,'1576' ,'1601' ,'1612' ,'1613' ,'1617' ,'1620' ,'1621' ,'1622' ,'1624' ,'1627' ,'1630' ,'1632' ,'1633' ,'1634' ,'1635' ,'1636' ,'1638' ,'1640' ,'1644' ,'1648' ,'1653' ,'1657' ,'1662' ,'1663' ,'1664' ,'1665' ,'1702' ,'1703' ,'1711' ,'1714' ,'1717' ,'1718' ,'1719' ,'1721' ,'1723' ,'1724' ,'1725' ,'1729' ,'1736' ,'1738' ,'1742' ,'1743' ,'1744' ,'1748' ,'1749' ,'1750' ,'1751' ,'1755' ,'1811']
        def kommuneListe7 = ['1739' ,'1740' ,'1804' ,'1805' ,'1812' ,'1813' ,'1815' ,'1816' ,'1818' ,'1820' ,'1822' ,'1824' ,'1825' ,'1826' ,'1827' ,'1828' ,'1832' ,'1833' ,'1834' ,'1835' ,'1836' ,'1837' ,'1838' ,'1839' ,'1840' ,'1841' ,'1845' ,'1848' ,'1849' ,'1850' ,'1851' ,'1852' ,'1853' ,'1854' ,'1856' ,'1857' ,'1859' ,'1860' ,'1865' ,'1866' ,'1867' ,'1868' ,'1870' ,'1871' ,'1874' ,'1901' ,'1902' ,'1911' ,'1913' ,'1915' ,'1917' ,'1919' ,'1920' ,'1922' ,'1923' ,'1924' ,'1925' ,'1926' ,'1927' ,'1928' ,'1929' ,'1931' ,'1933' ,'1936' ,'1938' ,'1939' ,'1940' ,'1941' ,'1942' ,'1943' ,'2002' ,'2003' ,'2004' ,'2011' ,'2012' ,'2014' ,'2015' ,'2017' ,'2018' ,'2019' ,'2020' ,'2021' ,'2022' ,'2023' ,'2024' ,'2025' ,'2027' ,'2028' ,'2030']
        def kommuneListe8 = ['0301']
        def kommuneListe9 = ['0402']

        def liste1 = Kommune.createCriteria().list {
            'in'("kommuneNummer", kommuneListe1)
        }
        if(liste1 && k1) {
            k1.kommuner = liste1
            k1.save(failOnError: true, flush: true)
        }

        def liste2 = Kommune.createCriteria().list {
            'in'("kommuneNummer", kommuneListe2)
        }
        if(liste2 && k2) {
            k2.kommuner = liste2
            k2.save(failOnError: true, flush: true)
        }

        def liste3 = Kommune.createCriteria().list {
            'in'("kommuneNummer", kommuneListe3)
        }
        if(liste3 && k3) {
            k3.kommuner = liste3
            k3.save(failOnError: true, flush: true)
        }

        def liste4 = Kommune.createCriteria().list {
            'in'("kommuneNummer", kommuneListe4)
        }
        if(liste4 && k4) {
            k4.kommuner = liste4
            k4.save(failOnError: true, flush: true)
        }

        def liste5 = Kommune.createCriteria().list {
            'in'("kommuneNummer", kommuneListe5)
        }
        if(liste5 && k5) {
            k5.kommuner = liste5
            k5.save(failOnError: true, flush: true)
        }

        def liste6 = Kommune.createCriteria().list {
            'in'("kommuneNummer", kommuneListe6)
        }
        if(liste6 && k6) {
            k6.kommuner = liste6
            k6.save(failOnError: true, flush: true)
        }

        def liste7 = Kommune.createCriteria().list {
            'in'("kommuneNummer", kommuneListe7)
        }
        if(liste7 && k7) {
            k7.kommuner = liste7
            k7.save(failOnError: true, flush: true)
        }

        def liste8 = Kommune.createCriteria().list {
            'in'("kommuneNummer", kommuneListe8)
        }
        if(liste8 && k8) {
            k8.kommuner = liste8
            k8.save(failOnError: true, flush: true)
        }

        def liste9 = Kommune.createCriteria().list {
            'in'("kommuneNummer", kommuneListe9)
        }
        if(liste9 && k9) {
            k9.kommuner = liste9
            k9.save(failOnError: true, flush: true)
        }

    }

    @Transactional
    def opprettIntHist () {
        IntHist intHist = new IntHist()
        intHist.intervjuer = Intervjuer.findById(2)
        intHist.intervjuStatus = 12
        intHist.registrertDato = new Date()
        intHist.intervjuObjekt = IntervjuObjekt.findById(1)
        intHist.save(failOnError: true, flush: true)

        intHist = new IntHist()
        intHist.intervjuer = Intervjuer.findById(2)
        intHist.intervjuStatus = 89
        intHist.registrertDato = new Date()
        intHist.intervjuObjekt = IntervjuObjekt.findById(2)
        intHist.save(failOnError: true, flush: true)

        intHist = new IntHist()
        intHist.intervjuer = Intervjuer.findById(2)
        intHist.intervjuStatus = 34
        intHist.registrertDato = new Date()
        intHist.intervjuObjekt = IntervjuObjekt.findById(3)
        intHist.save(failOnError: true, flush: true)
    }

    @Transactional
    def opprettUtlegg () {
        Utlegg utlegg = new Utlegg(belop: 900, dato: format.parse("2014-04-03"), produktNummer: '0980-0', utleggType: UtleggType.BILLETT, harKjorebok: true, intervjuer: Intervjuer.findById(1))
        utlegg.save(failOnError: true, flush: true)

        utlegg = new Utlegg(belop: 700, dato: format.parse("2012-02-01"), produktNummer: '0980-0', utleggType: UtleggType.BILLETT, harKjorebok: false, intervjuer: Intervjuer.findById(2))
        utlegg.save(failOnError: true, flush: true)

        utlegg = new Utlegg(belop: 500, dato: format.parse("2015-06-16"), produktNummer: '0980-0', utleggType: UtleggType.BILLETT, harKjorebok: false, intervjuer: Intervjuer.findById(3))
        utlegg.save(failOnError: true, flush: true)

        utlegg = new Utlegg(belop: 500, dato: format.parse("2016-08-18"), produktNummer: '0980-0', utleggType: UtleggType.BILLETT, harKjorebok: true, intervjuer: Intervjuer.findById(4))
        utlegg.save(failOnError: true, flush: true)

        utlegg = new Utlegg(belop: 500, dato: new Date(), produktNummer: '0980-0', utleggType: UtleggType.BILLETT, harKjorebok: false, intervjuer: Intervjuer.findById(5))
        utlegg.save(failOnError: true, flush: true)
    }

    @Transactional
    def opprettKjorebokerOgTimeforinger () {
        Date fra = format.parse("2014-04-03")
        Date til = format.parse("2014-04-04")
        Timeforing timeforing = new Timeforing(produktNummer: '0980-0', fra: fra, til: til, arbeidsType: ArbeidsType.ANNET)
        timeforing.save(failOnError: true, flush: true)
        Kjorebok kjorebok = new Kjorebok(timeforing: timeforing, fraPoststed: '0101', tilPoststed: '0202', fraTidspunkt: fra, tilTidspunkt: til, produktNummer: '0980-0', transportmiddel: TransportMiddel.EGEN_BIL, kjorteKilometer: 1)
        kjorebok.save(failOnError: true, flush: true)


        fra = format.parse("2015-05-28")
        til = format.parse("2015-05-29")
        timeforing = new Timeforing(produktNummer: '0980-0', fra: fra, til: til, arbeidsType: ArbeidsType.ANNET)
        timeforing.save(failOnError: true, flush: true)
        kjorebok = new Kjorebok(timeforing: timeforing, fraPoststed: '0101', tilPoststed: '0202', fraTidspunkt: fra, tilTidspunkt: til, produktNummer: '0980-0', transportmiddel: TransportMiddel.EGEN_BIL, kjorteKilometer: 2)
        kjorebok.save(failOnError: true, flush: true)


        fra = format.parse("2015-06-14")
        til = format.parse("2015-06-15")
        timeforing = new Timeforing(produktNummer: '0980-0', fra: fra, til: til, arbeidsType: ArbeidsType.ANNET)
        timeforing.save(failOnError: true, flush: true)
        kjorebok = new Kjorebok(timeforing: timeforing, fraPoststed: '0101', tilPoststed: '0202', fraTidspunkt: fra, tilTidspunkt: til, produktNummer: '0980-0', transportmiddel: TransportMiddel.EGEN_BIL, kjorteKilometer: 3)
        kjorebok.save(failOnError: true, flush: true)

        fra = new Date(System.currentTimeMillis()-(1000*60*60))
        til = new Date()
        timeforing = new Timeforing(produktNummer: '0980-0', fra: fra, til: til, arbeidsType: ArbeidsType.ANNET)
        timeforing.save(failOnError: true, flush: true)
        kjorebok = new Kjorebok(timeforing: timeforing, fraPoststed: '0101', tilPoststed: '0202', fraTidspunkt: fra, tilTidspunkt: til, produktNummer: '0980-0', transportmiddel: TransportMiddel.EGEN_BIL, kjorteKilometer: 4)
        kjorebok.save(failOnError: true, flush: true)
    }

    @Transactional
    def opprettMeldingsheaderMaler () {

        Bruker bruker = Bruker.findByUsername("si3")

        MeldingsheaderMal mal = new MeldingsheaderMal(malNavn: 'Utsending av IO-brev til Altinn på AKU', meldingsheader: 'Du er trukket ut til Statistisk sentralbyrås arbeidskraftsundersøkelse (AKU)', opprettet: new Date(), opprettetAv: bruker)
        mal.save(failOnError: true, flush: true)

        mal = new MeldingsheaderMal(malNavn: 'Nekterbrev på frivillige undersøkelser', meldingsheader: 'Statistisk sentralbyrå ønsker fortsatt å intervjue deg', opprettet: new Date(), opprettetAv: bruker)
        mal.save(failOnError: true, flush: true)

        mal = new MeldingsheaderMal(malNavn: 'MaksiMal', meldingsheader: 'Dette er en meldingsheader med maksimal lengde abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxy', opprettet: new Date(), opprettetAv: bruker)
        mal.save(failOnError: true, flush: true)

        mal = new MeldingsheaderMal(malNavn: 'Påminnelse på e-postutsending ved AES-undersøkelsen', meldingsheader: 'Statistisk sentralbyrå minner om undersøkelsen om XX', opprettet: new Date(), opprettetAv: bruker)
        mal.save(failOnError: true, flush: true)

        mal = new MeldingsheaderMal(malNavn: 'Hovedutsending til Altinn med IO-brev', meldingsheader: 'Du er trukket ut til undersøkelsen om XX', opprettet: new Date(), opprettetAv: bruker)
        mal.save(failOnError: true, flush: true)
    }

// Ikke i bruk noen steder?

//	def opprettKommuner()

// Koden er slettet om må evt. hentes fra grails 1.3.7

}