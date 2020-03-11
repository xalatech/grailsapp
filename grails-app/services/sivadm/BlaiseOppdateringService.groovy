package sivadm


import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate
import siv.type.AdresseType
import siv.type.AvtaleType
import siv.type.Kjonn
import util.RestTemplateErrorHandler

import java.text.SimpleDateFormat

class BlaiseOppdateringService {

    static transactional = true

    private final int PASSWORD_LENGTH = 8
    private final int ID_LENGTH = 8
    private final String PADDING = ' '

    RestTemplateErrorHandler errorHandler = new RestTemplateErrorHandler();

    def grailsApplication

    def activate(IntervjuObjekt intervjuObjekt) {
        def request = JsonOutput.toJson([
                instrumentId: intervjuObjekt.periode?.skjema?.instrumentId,
                primaryKeyValue: getPrimaryKeyValue(intervjuObjekt.id, intervjuObjekt.passordWeb)
        ])
        def endpoint = '/activate'
        doRestPost(endpoint, request)
    }

    def complete(IntervjuObjekt intervjuObjekt) {
        def request = JsonOutput.toJson([
                instrumentId: intervjuObjekt.periode?.skjema?.instrumentId,
                primaryKeyValue: getPrimaryKeyValue(intervjuObjekt.id, intervjuObjekt.passordWeb),
                intervjustatus: intervjuObjekt.intervjuStatus
        ])
        def endpoint = '/complete'
        doRestPost(endpoint, request)
    }

    def onlyWeb(IntervjuObjekt intervjuObjekt) {
        def request = JsonOutput.toJson([
                instrumentId: intervjuObjekt.periode?.skjema?.instrumentId,
                primaryKeyValue: getPrimaryKeyValue(intervjuObjekt.id, intervjuObjekt.passordWeb)
        ])
        def endpoint = '/onlyWeb'
        doRestPost(endpoint, request)
    }

    def oppdaterTelefoner(IntervjuObjekt intervjuObjekt) {
        def telefonList = intervjuObjekt.telefoner
        def telefonListIBruk = new ArrayList<Telefon>()

        telefonList.sort{a,b-> b.redigertDato<=>a.redigertDato}

        telefonList.each {
            Telefon telefon = it
            if(telefon.gjeldende == null || telefon.gjeldende ) {
                telefonListIBruk.add telefon
            }
        }

        def request = JsonOutput.toJson([
                instrumentId: intervjuObjekt.periode?.skjema?.instrumentId,
                primaryKeyValue: getPrimaryKeyValue(intervjuObjekt.id, intervjuObjekt.passordWeb),
                telefon1: telefonListIBruk[0]?.telefonNummer,
                kommentarTelefon1: telefonListIBruk[0]?.kommentar,
                kildeTelefon1: telefonListIBruk[0]?.kilde,
                telefon2: telefonListIBruk[1]?.telefonNummer,
                kommentarTelefon2: telefonListIBruk[1]?.kommentar,
                kildeTelefon2: telefonListIBruk[1]?.kilde,
                telefon3: telefonListIBruk[2]?.telefonNummer,
                kommentarTelefon3: telefonListIBruk[2]?.kommentar,
                kildeTelefon3: telefonListIBruk[2]?.kilde
        ])

        def endpoint = '/oppdaterTelefoner'
        doRestPost(endpoint, request)
    }

    def oppdaterAdresse(IntervjuObjekt io) {
        Adresse besokAdresse = io.findGjeldendeBesokAdresse()

        def adresse = besokAdresse?.gateAdresse? besokAdresse.gateAdresse : ""
        def boligNummer = besokAdresse?.boligNummer? besokAdresse.boligNummer : ""
        def kommuneNummer = besokAdresse?.kommuneNummer? besokAdresse.kommuneNummer : ""
        def postNummer = besokAdresse?.postNummer? besokAdresse.postNummer : ""
        def postSted = besokAdresse?.postSted? besokAdresse.postSted : ""
        def tilleggsAdresse = besokAdresse?.tilleggsAdresse? besokAdresse.tilleggsAdresse : ""

        def request = JsonOutput.toJson([
                instrumentId: io.periode?.skjema?.instrumentId,
                primaryKeyValue: getPrimaryKeyValue(io.id, io.passordWeb),
                gateadresse: adresse,
                bolignr: boligNummer,
                kommune: kommuneNummer,
                postnr: postNummer,
                poststed: postSted,
                navn2: tilleggsAdresse
        ])

        def endpoint = '/oppdaterAdresse'
        doRestPost(endpoint, request)
    }

    def oppdaterEndringObjektBlaise(BlaiseEndring endring) {
        def request = JsonOutput.toJson([
                instrumentId: endring.instrumentId,
                primaryKeyValue: endring.primaryKeyValue,
                mainSurveyId: endring.mainSurveyId,
                id: endring.id_nr
        ])

        def endpoint = '/oppdaterEndring'
        doRestPost(endpoint, request)
    }

    def oppdaterIntervjuObjekt(IntervjuObjekt io) {
        def kontaktPeriode = io.kontaktperiode? io.kontaktperiode : ""
        def navn = io.navn? io.navn : ""
        def epost = io.epost? io.epost : ""
        def referansePerson = io.referansePerson? io.referansePerson : ""
        def kjonn = getKjonnInBlaiseFormat( io.kjonn )
        def alder = io.alder? io.alder : ""
        def fdato = io.fodselsNummer? io.fodselsNummer : ""
        def melding = io.meldingTilIntervjuer? io.meldingTilIntervjuer : ""

        def request = JsonOutput.toJson([
                instrumentId: io.periode?.skjema?.instrumentId,
                primaryKeyValue: getPrimaryKeyValue(io.id, io.passordWeb),
                kontaktperiode: kontaktPeriode,
                fornavn: navn,
                epostadresse: epost,
                referanseperson: referansePerson,
                kjonn: kjonn,
                alder: alder,
                fdato: fdato,
                meldingTilIntervjuer: melding
        ])

        def endpoint = '/oppdaterIntervjuObjekt'
        doRestPost(endpoint, request)
    }

    def oppdaterIntervjuObjektTildelCAPI(IntervjuObjekt intervjuObjekt, String initialer) {
        def request = JsonOutput.toJson([
                instrumentId: intervjuObjekt.periode?.skjema?.instrumentId,
                primaryKeyValue: getPrimaryKeyValue(intervjuObjekt.id, intervjuObjekt.passordWeb),
                intervjuer: initialer

        ])
        def endpoint = '/tildelCapi'
        doRestPost(endpoint, request)
    }

    def doRestPost (def endpoint, def request) {
        def host = grailsApplication.config.getProperty("blaise.connector.url")
        def url = host + endpoint

        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        def entity = new HttpEntity<String>(request ,headers)

        def restTemplate = new RestTemplate()
        restTemplate.setErrorHandler(errorHandler)

        try {
            return restTemplate.postForEntity(url,entity, String.class)
        } catch (ResourceAccessException ex) {
            log.info ex.message
        }
    }

     String doRestGet (def endpoint) {
        def host = grailsApplication.config.getProperty("blaise.connector.url")
        String url = host + endpoint

         def restTemplate = new RestTemplate()
        restTemplate.setErrorHandler(errorHandler)

         try {
             return restTemplate.getForObject(url, String.class);
         } catch (ResourceAccessException ex) {
             log.info ex.message
         }
    }

    def getPrimaryKeyValue (Long id, String password) {
        def paddedId = Long.toString(id).padLeft(ID_LENGTH, PADDING)
        def paddedPassword = password.padRight(PASSWORD_LENGTH, PADDING)
        paddedId + paddedPassword
    }

    def setEventInactive(BlaiseEvent blaiseEvent) {
        log.info blaiseEvent.blaiseEventId

        def request = JsonOutput.toJson([
                eventId: blaiseEvent.blaiseEventId
        ])

        def endpoint = '/event/' + blaiseEvent.blaiseEventId
        doRestPost(endpoint, request)
    }

    def sjekkAvtaler(IntervjuObjekt intervjuObjekt, String instrumentId) {
        String endPoint = "/hentBlaiseAvtaler" + "/" + instrumentId + "/" + intervjuObjekt.getId().toString() + intervjuObjekt.passordWeb
        String o = doRestGet(endPoint)

        log.info "Avtaler hentet fra blaise connector"
        def parser = new JsonSlurper()

        if(o == null) {
            log.info "Ingen avtaler eller tilkobling mislykes"
            return
        }

        AvtaleDto avtaleDto = parser.parseText(o) as AvtaleDto;
        log.info "Oppdater intervjuobjekt med Avtale fra blaise ${avtaleDto}"

        Avtale nyAvtale = lagNyAvtale(avtaleDto, intervjuObjekt)
        log.info "Ny Avtale for ${intervjuObjekt.id} laget som ${nyAvtale.toString()}"
        log.info "Avtale for ${intervjuObjekt.id} lagret i SIV"
    }

    Avtale lagNyAvtale(AvtaleDto avtale, IntervjuObjekt intervjuObjekt) {
        def datePattern = "dd-MM-yyyy HH:mm:ss"
        def defaultTimePattern = "00:00:00"

        intervjuObjekt.avtale = new Avtale()

        if(AvtaleDto.isHardAvtale(avtale)) {
            intervjuObjekt.avtale.avtaleType = AvtaleType.HARD
        } else {
            intervjuObjekt.avtale.avtaleType = AvtaleType.LOS
        }

        if(avtale.dateStart != null && !avtale.dateStart.isEmpty()) {
            if(avtale.timeStart == null && avtale.timeStart.isEmpty()) {
                avtale.timeStart = defaultTimePattern;
            }

            intervjuObjekt.avtale.dateStart = new SimpleDateFormat(datePattern).parse(avtale.dateStart + " " + avtale.timeStart)
            intervjuObjekt.avtale.timeStart = avtale.timeStart
        }

        if(avtale.dateEnd != null && !avtale.dateEnd.isEmpty()) {
            if(avtale.timeEnd == null && avtale.timeEnd.isEmpty()) {
                avtale.timeEnd = defaultTimePattern
            }

            intervjuObjekt.avtale.dateEnd = new SimpleDateFormat(datePattern).parse(avtale.dateEnd + " " + avtale.timeEnd)
            intervjuObjekt.avtale.timeEnd = avtale.timeEnd
        }

        intervjuObjekt.avtale.avtaleMelding = avtale.avtaleMelding;
        intervjuObjekt.avtale.whoMade = avtale.whoMade;
        intervjuObjekt.avtale.weekDays = avtale.weekDays;

        log.info intervjuObjekt.avtale
        intervjuObjekt.save();
        return intervjuObjekt.avtale
    }

    def hentFullForingsStatus() {
        String o = doRestGet("/hentBlaiseFullForingsStatus")
        log.info "Fullføringsstatus hentet fra blaise connector"
        def parser = new JsonSlurper()

        if(o == null) {
            log.info "Ingen  eller tilkobling mislykes"
            return
        }

        BlaiseFullForingsStatus[] blaiseFullForingsStatuser = parser.parseText(o) as BlaiseFullForingsStatus[];
        for(BlaiseFullForingsStatus status in blaiseFullForingsStatuser) {
            // finne intervjuObjektet i SIV
            Long intervjuObjektId = new Long(endring.io_idnr)
            IntervjuObjekt intervjuObjekt = IntervjuObjekt.get(intervjuObjektId)

            // Gå videre om intervjuojektet finnes ikke i blaise
            if(intervjuObjekt == null) {
                log.info "Intervjuobjekt finnes ikke i Blaise"
                return
            }

            log.info "Oppdater intervjuobjekt med fullføringstatus fra blaise for primaryKey: ${endring.primaryKeyValue}"

            // erstate verdier med objekt fra blaise
            intervjuObjekt.setFullforingsStatus(status.fullForingsStatus)

            log.info "Oppdater fullføringsstatus med endring fra blaise"

            // Lagre endringer i Intervjobjektet
            if(intervjuObjekt.save(true)) {
                // sette endret verdie på Blaise Ring objekt til 0 slik at den ikke kan hentes igjen
                oppdaterEndringObjektBlaise(endring)
            }
        }
    }

    def sjekkEndringer() {
        String o = doRestGet("/hentBlaiseEndringer")
        log.info "Endringene hentet fra blaise connector"
        def parser = new JsonSlurper()

        if(o == null) {
            log.info "Ingen endringer eller tilkobling mislykes"
            return
        }

        BlaiseEndring[] blaiseEndringer = parser.parseText(o) as BlaiseEndring[];
        for(BlaiseEndring endring in blaiseEndringer) {

            // finne intervjuObjektet i SIV
            Long intervjuObjektId = new Long(endring.io_idnr)
            IntervjuObjekt intervjuObjekt = IntervjuObjekt.get(intervjuObjektId)

            // Gå videre om intervjuojektet finnes ikke i blaise
            if(intervjuObjekt == null) {
                log.info "Intervjuobjekt finnes ikke i Blaise"
                return
            }

            log.info "Oppdater intervjuobjekt med endring fra blaise for primaryKey: ${endring.primaryKeyValue}"

            // Sjekk og oppdater fullføringsstatus
            if(endring.fullForingsStatus != null && !endring.fullForingsStatus.isEmpty()) {
                intervjuObjekt.fullforingsStatus = Long.parseLong(endring.fullForingsStatus)
            }

            // Sjekk om Kontaktinfo er endret, hvis ikke gå tilbake og stopp endringer
            if(endring.kontaktInfoEndring != null && !endring.kontaktInfoEndring.isEmpty() && endring.kontaktInfoEndring == "1") {
                log.info "Oppdater kontaktinfo endring fra blaise for primaryKey: ${endring.primaryKeyValue}"
                intervjuObjekt = oppdaterEndringerFraBlaise(endring, intervjuObjekt)
            }

            // Lagre endringer i Intervjobjektet
            if(intervjuObjekt.save(true)) {
                // sette endret verdie på Blaise Ring objekt til 0 slik at den ikke kan hentes igjen
                oppdaterEndringObjektBlaise(endring)
            }
        }

    }

    IntervjuObjekt oppdaterEndringerFraBlaise(BlaiseEndring endring, IntervjuObjekt intervjuObjekt) {
        // erstate verdier med objekt fra blaise
        intervjuObjekt.setNavn(endring.fornavn);
        intervjuObjekt.setEpost(endring.epostadresse);

        log.info "Oppdater telefoner med endring fra blaise"

        intervjuObjekt.telefoner.eachWithIndex { it, index ->
            if(endring.telefon1 != null && !endring.telefon1.isEmpty()) {
                if(index == 0 && it.telefonNummer != null && !it.telefonNummer.isEmpty()) {
                    it.setGjeldende(false);
                }
            }

            if(endring.telefon2 != null && !endring.telefon2.isEmpty()) {
                if(index == 1 && it.telefonNummer != null && !it.telefonNummer.isEmpty()) {
                    it.setGjeldende(false);
                }
            }

            if(endring.telefon3 != null && !endring.telefon3.isEmpty()) {
                if(index == 2 && it.telefonNummer != null && !it.telefonNummer.isEmpty()) {
                    it.setGjeldende(false);
                }
            }
        }

        if(endring.telefon1 != null && !endring.telefon1.isEmpty()) {
            intervjuObjekt.addToTelefoner(hentTelefon1Objekt(endring))
        }

        if(endring.telefon2 != null && !endring.telefon2.isEmpty()) {
            intervjuObjekt.addToTelefoner(hentTelefon2Objekt(endring))
        }

        if(endring.telefon3 != null && !endring.telefon3.isEmpty()) {
            intervjuObjekt.addToTelefoner(hentTelefon3Objekt(endring))
        }

        log.info "Oppdater adresse med endring fra blaise"
        // sette alle adresse til ugjeldende
        intervjuObjekt.adresser.each { a ->
            if( (a.adresseType == AdresseType.BESOK) && (a.gjeldende == true )) {
                a.setGjeldende(false)
                a.save()
            }
        }

        // Legg til adresse fra blaise som gjeldende
        def adresse = hentAdresseObjekt(endring);

        intervjuObjekt.addToAdresser(adresse)
        return intervjuObjekt;
    }

    Adresse hentAdresseObjekt(BlaiseEndring endring) {
        Adresse adresse = new Adresse()
        adresse.setGateAdresse(endring.gateadresse)
        adresse.setBoligNummer(endring.bolignr)
        adresse.setPostNummer(endring.postnr)
        adresse.setPostSted(endring.poststed)
        adresse.setKommuneNummer(endring.kommune)
        adresse.setGjeldende(true)
        adresse.setAdresseType(AdresseType.BESOK)
        adresse.setTilleggsAdresse(endring.navn2)
        adresse.gyldigFom = new Date()

        if(!adresse.postSted || adresse.postSted.equals("")) {
            log.info "Poststed må fylles ut"
            return
        }

        if(!adresse.postNummer || adresse.postNummer.equals("")) {
            log.info "Postkode må fylles ut"
            return
        }

        adresse.save(true);
        return adresse;
    }

    Telefon hentTelefon1Objekt(BlaiseEndring endring) {
        Telefon telefon = new Telefon()
        telefon.setTelefonNummer(endring.telefon1)
        telefon.setKilde(endring.kildeTelefon1)
        telefon.setKommentar(endring.kommentarTelefon1)

        if(endring.ikkeBruk1 != null && !endring.ikkeBruk1.isEmpty()) {
            telefon.setGjeldende(false)
        } else {
            telefon.setGjeldende(true)
        }

        telefon.save();
        return telefon;
    }

    Telefon hentTelefon2Objekt(BlaiseEndring endring) {
        Telefon telefon = new Telefon()
        telefon.setTelefonNummer(endring.telefon2)
        telefon.setKilde(endring.kildeTelefon2)
        telefon.setKommentar(endring.kommentarTelefon2)

        if(endring.ikkeBruk2 != null && !endring.ikkeBruk2.isEmpty()) {
            telefon.setGjeldende(false)
        } else {
            telefon.setGjeldende(true)
        }

        telefon.save();

        return telefon;
    }

    Telefon hentTelefon3Objekt(BlaiseEndring endring) {
        Telefon telefon = new Telefon()
        telefon.setTelefonNummer(endring.telefon3)
        telefon.setKilde(endring.kildeTelefon3)
        telefon.setKommentar(endring.kommentarTelefon3)

        if(endring.ikkeBruk3 != null && !endring.ikkeBruk3.isEmpty()) {
            telefon.setGjeldende(false)
        } else {
            telefon.setGjeldende(true)
        }

        telefon.save();

        return telefon;
    }

    String getKjonnInBlaiseFormat(Kjonn kjonn ) {
        if( kjonn == null ) {
            return null
        }
        else if( kjonn.equals(Kjonn.MANN)) {
            return "1"
        }
        else {
            return "2"
        }
    }
}
