package sivadm

import grails.test.mixin.TestFor
import groovy.json.JsonOutput
import org.junit.Before
import org.junit.Test
import org.springframework.web.client.RestTemplate
import siv.type.SkjemaStatus

import static org.junit.Assert.*

@TestFor(BlaiseOppdateringService)
class BlaiseOppdateringServiceTests{

    RestTemplate restTemplate = new RestTemplate();

    @Before
    void setup() {
        grailsApplication.config.blaise.connector.url = "http://localhost:8090"
    }

    @Test
    void testOppdaterIntervjuObjektMedSIVData_Activate() {

        def blaiseOppdateringService = mockService(BlaiseOppdateringService)
        IntervjuObjekt io = new IntervjuObjekt()

        def request = JsonOutput.toJson([
                instrumentId: "cfbcc0d2-2646-414a-a247-e355c0d103c5",
                primaryKeyValue: "      20aaaaaaaa",
                telefon1: "99999999",
                kommentarTelefon1: "ingen kommentar",
                telefon2: null,
                kommentarTelefon2: null,
                telefon3: null,
                kommentarTelefon3: null
        ]);

        io.passordWeb = 'test'
        io.id = 15

        blaiseOppdateringService.activate(io)

        assertNotNull(io.katSkjemaStatus)
        assertEquals(SkjemaStatus.Pabegynt, io.katSkjemaStatus)
        assertNull(io.intervjuStatus)
    }
}
