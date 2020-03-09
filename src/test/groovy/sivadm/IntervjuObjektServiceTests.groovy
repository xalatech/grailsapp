package sivadm

import siv.type.SkjemaStatus
import static org.junit.Assert.*
import org.junit.*

class IntervjuObjektServiceTests {

    IntervjuObjektService intervjuObjektService

     @Before
    void setup() {
         intervjuObjektService = new IntervjuObjektService()
    }

    @Test
    void testOppdaterIntervjuObjektMedBlaisedata_StartSessionEvent() {
        BlaiseEvent event = new BlaiseEvent(
                eventName: "StartSessionEvent",
                intervjustatus: new Integer(0))
        IntervjuObjekt io = new IntervjuObjekt()
        intervjuObjektService.oppdaterIntervjuObjektMedBlaisedata(io, event)

        assertNotNull(io.katSkjemaStatus)
        assertEquals(SkjemaStatus.Pabegynt, io.katSkjemaStatus)
        assertNull(io.intervjuStatus)
    }

    @Test
    void testOppdaterIntervjuObjektMedBlaisedata_KeyvalueDeterminedEvent() {
        BlaiseEvent event = new BlaiseEvent(
                eventName: "KeyvalueDeterminedEvent",
                intervjustatus: new Integer(0))
        IntervjuObjekt io = new IntervjuObjekt()
        intervjuObjektService.oppdaterIntervjuObjektMedBlaisedata(io, event)

        assertNotNull(io.katSkjemaStatus)
        assertEquals(SkjemaStatus.Pabegynt, io.katSkjemaStatus)
        assertNull(io.intervjuStatus)
    }

    @Test
    void testOppdaterIntervjuObjektMedBlaisedata_CAWI_EndSessionEvent_Completed_intervjustatus_1() {
        BlaiseEvent event = new BlaiseEvent(
                eventName: "EndSessionEvent",
                reason: "Completed",
                kilde: "WEB",
                intervjustatus: new Integer(1))
        IntervjuObjekt io = new IntervjuObjekt()
        intervjuObjektService.oppdaterIntervjuObjektMedBlaisedata(io, event)

        assertNotNull(io.intervjuStatus)
        assertEquals(0, io.intervjuStatus)
    }

    @Test
    void testOppdaterIntervjuObjektMedBlaisedata_CATI_EndSessionEvent_Completed_intervjustatus_1() {
        BlaiseEvent event = new BlaiseEvent(
                eventName: "EndSessionEvent",
                reason: "Completed",
                kilde: "CATI",
                intervjustatus: new Integer(1),
                interviewer: "xyz")
        IntervjuObjekt io = new IntervjuObjekt()
        intervjuObjektService.oppdaterIntervjuObjektMedBlaisedata(io, event)

        assertNotNull(io.intervjuStatus)
        assertNotNull(io.katSkjemaStatus)
        assertNotNull(io.intervjuer)
        assertEquals("xyz", io.intervjuer)
        assertEquals(0, io.intervjuStatus)
        assertEquals(SkjemaStatus.Ferdig, io.katSkjemaStatus)
    }

    @Test
    void testOppdaterIntervjuObjektMedBlaisedata_CATI_EndSessionEvent_Completed_intervjustatus_24() {
        BlaiseEvent event = new BlaiseEvent(
                eventName: "EndSessionEvent",
                reason: "Completed",
                kilde: "CATI",
                intervjustatus: new Integer(24),
                meldingFraIntervjuer: "Duck",
                interviewer: "xyz")
        IntervjuObjekt io = new IntervjuObjekt()
        intervjuObjektService.oppdaterIntervjuObjektMedBlaisedata(io, event)

        assertNotNull(io.intervjuStatus)
        assertNotNull(io.katSkjemaStatus)
        assertNotNull(io.statusKommentar)
        assertNotNull(io.intervjuer)
        assertEquals("xyz", io.intervjuer)
        assertEquals(24, io.intervjuStatus)
        assertEquals("Duck", io.statusKommentar)
        assertEquals(SkjemaStatus.Ubehandlet, io.katSkjemaStatus)
    }

}
