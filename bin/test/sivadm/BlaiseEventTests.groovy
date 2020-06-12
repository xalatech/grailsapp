package sivadm

import grails.converters.JSON
import grails.test.mixin.TestFor
import siv.type.SkjemaStatus

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(BlaiseEvent)
class BlaiseEventTests {

    void testCreationFromJson() {
        String json = "{\"id\":1,\"blaiseEventId\":1,\"ioId\":null,\"eventName\":\"StartSessionEvent\",\"sessionId\":\"459cdda9-e0f3-4aa4-add3-41dee1e453b2\",\"resumedSessionID\":null,\"instrumentId\":\"92e8b8bb-2afa-4138-abf5-bb678ef242bf\",\"mainInstrumentId\":null,\"primaryKeyValue\":null,\"interviewer\":null,\"reason\":null,\"status\":null,\"rowStatus\":null,\"timeStamp\":null,\"new\":false}";
        def parse = JSON.parse(json)
        def event = new BlaiseEvent(parse)
        assertNotNull(event.blaiseEventId)
    }

    void testIntervjuStatus() {
        def startEvent = new BlaiseEvent(eventName: "StartSessionEvent")
        assertNull(startEvent.resolveIntervjustatus())
        def endEvent = new BlaiseEvent(eventName: "EndSessionEvent", reason: "Switched")
        assertNull(endEvent.resolveIntervjustatus())
        def endEventCompleted = new BlaiseEvent(eventName: "EndSessionEvent", reason: "Completed")
        assertNotNull(endEventCompleted.resolveIntervjustatus())
    }

}
