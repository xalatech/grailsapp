package sivadm


import grails.test.mixin.TestFor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import siv.type.Kilde

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BlaiseHentEventsService)
class BlaiseHentEventsServiceTests {

    void testStartSessionEvent() {

        BlaiseHentEventsService blaiseHentEventsService = new BlaiseHentEventsService();
        def eventList = blaiseHentEventsService.parseRestResponse(new ResponseEntity(dummyJSONResponseStartSessionEvent(), HttpStatus.OK));

        assert eventList.size() == 1
        assert eventList.get(0).eventName.equals("StartSessionEvent")

    }

    void testEndSessionEvent() {

        BlaiseHentEventsService blaiseHentEventsService = new BlaiseHentEventsService();
        def eventList = blaiseHentEventsService.parseRestResponse(new ResponseEntity(dummyJSONResponseEndSessionEvent(), HttpStatus.OK));

        assert eventList.size() == 1
        assert eventList.get(0).eventName.equals("EndSessionEvent")
        assert eventList.get(0).resolveIntervjustatus().equals(11)
        assert eventList.get(0).meldingFraIntervjuer.equals("statusKommentar")
        assert eventList.get(0).intervjuer.equals("tbh")
        assert Kilde.valueOf(eventList.get(0).kilde) == Kilde.WEB

    }


    private static String dummyJSONResponseStartSessionEvent() {
        return "[" +
                "    {" +
                "        \"id\": 2," +
                "        \"eventName\": \"StartSessionEvent\"," +
                "        \"sessionId\": \"a59cdda9-e0f3-4aa4-add3-41dee1e453b2\"," +
                "        \"resumedSessionID\": \"resumedSessionId\"," +
                "        \"instrumentId\": \"a2e8b8bb-2afa-4138-abf5-bb678ef242bf\"," +
                "        \"mainInstrumentId\": \"mainInstrumentId\"," +
                "        \"primaryKeyValue\": \"primaryKeyValue\"," +
                "        \"interviewer\": \"interviewer\"," +
                "        \"reason\": \"reason\"," +
                "        \"status\": \"status\"," +
                "        \"rowStatus\": \"NEW\"," +
                "        \"timeStamp\": \"2018-08-03T11:30:24.5318794+02:00\" " +
                "    }" +
                "]"
    }

    private static String dummyJSONResponseEndSessionEvent() {
        return "[" +
                "    {" +
                "        \"id\": 2," +
                "        \"eventName\": \"EndSessionEvent\"," +
                "        \"sessionId\": \"a59cdda9-e0f3-4aa4-add3-41dee1e453b2\"," +
                "        \"resumedSessionID\": \"resumedSessionId\"," +
                "        \"instrumentId\": \"a2e8b8bb-2afa-4138-abf5-bb678ef242bf\"," +
                "        \"mainInstrumentId\": \"mainInstrumentId\"," +
                "        \"primaryKeyValue\": \"primaryKeyValue\"," +
                "        \"interviewer\": \"interviewer\"," +
                "        \"reason\": \"reason\"," +
                "        \"intervjustatus\": 11," +
                "        \"status\": \"status\"," +
                "        \"rowStatus\": \"NEW\"," +
                "        \"kilde\": \"WEB\"," +
                "        \"meldingFraIntervjuer\": \"statusKommentar\"," +
                "        \"intervjuer\": \"tbh\"," +
                "        \"timeStamp\": \"2018-08-03T11:30:24.5318794+02:00\" " +
                "    }" +
                "]"
    }


}
