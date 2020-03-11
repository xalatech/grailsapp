package sivadm

import grails.converters.JSON
import org.springframework.http.ResponseEntity
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate

class BlaiseHentEventsService {

    static transactional = true

    def grailsApplication
    IntervjuObjektService intervjuObjektService
    SynkroniseringService synkroniseringService
    BlaiseOppdateringService blaiseOppdateringService

    def serviceMethod() {
        //Lag REST-client
        def lastBlaiseEventId = getLastBlaiseEventId();
        def restTemplate = new RestTemplate()
        def response = getRestResponseWithId(restTemplate, lastBlaiseEventId)
        def blaiseEventList = parseRestResponse(response)
        blaiseEventList.each {blaiseEvent ->

            log.info blaiseEvent.mainInstrumentId
            log.info "Sjekk om event er aktiv: ${blaiseEvent.blaiseEventId} ${blaiseEvent.getActive()}"
            log.info "Intervjuobjekt id: ${blaiseEvent.ioId}"

            if(!blaiseEvent.getActive()) {
                log.info "Blaiseevent er blitt behandlet"// Blaiseevent er blitt behandlet
                return
            }

            if(blaiseEvent.getEventName() == "AppointmentMadeCatiEvent") {
                IntervjuObjekt intervjuObjekt = IntervjuObjekt.get(blaiseEvent.ioId);
                blaiseOppdateringService.sjekkAvtaler(intervjuObjekt, blaiseEvent.mainInstrumentId)
            }

            def intervjuObjektId = intervjuObjektService.oppdaterSkjemaStatus(blaiseEvent)
            if (intervjuObjektId) {
                synkroniseringService.synkroniserIntervjuObjektEndring(intervjuObjektId, false)
            }
            lastBlaiseEventId.blaiseId = blaiseEvent.blaiseEventId
            lastBlaiseEventId.save(flush: true)

            blaiseOppdateringService.setEventInactive(blaiseEvent)
        }
    }

    def getLastBlaiseEventId() {
        def lastBlaiseEventId = BlaiseEventId.get(1)
        if (lastBlaiseEventId) {
            lastBlaiseEventId
        } else {
            return new BlaiseEventId(blaiseId: 0)
        }
    }

    def getRestResponseWithId(RestTemplate restTemplate, BlaiseEventId lastBlaiseEventId) {
        def host = grailsApplication.config.getProperty("blaise.connector.url")
        def url = host + "/cawi_events/" + lastBlaiseEventId.blaiseId

        try {
            return restTemplate.getForEntity(url, String.class)
        } catch (ResourceAccessException ex) {
            log.info ex.message
        }

    }

    def parseRestResponse(ResponseEntity responseEntity) {
        if(responseEntity?.getBody() == null) {
            return
        }

        def jsonArray = JSON.parse(responseEntity.getBody())
        def blaiseEventList = jsonArray.collect{jsonEvent ->
            return new BlaiseEvent(jsonEvent)
        }
        return blaiseEventList
    }


}
