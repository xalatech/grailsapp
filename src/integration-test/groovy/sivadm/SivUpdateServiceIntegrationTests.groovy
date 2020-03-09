package sivadm

import siv.service.data.IntervjuStatusServiceData;
import siv.service.data.KontaktInformasjonServiceData;
import siv.type.AdresseType;
import siv.type.Kjonn;
import siv.type.SkjemaStatus;
;

class SivUpdateServiceIntegrationTests extends GroovyTestCase {
	
	SivUpdateService sivUpdateService
	def intervjuObjekt 
	
	protected void setup() {
		super.setUp()
		
		Adresse adresse = new Adresse()
		adresse.adresseType = AdresseType.BESOK
		adresse.gateAdresse = "bolerlia 8"
		adresse.postNummer = "2000"
		adresse.postSted = "oslo"
		adresse.gyldigFom = new Date()
		adresse.kommuneNummer = "0301"
		adresse.gjeldende = true
		adresse.save(failOnError: true, flush: true)
		
		Telefon telefon = new Telefon()
		telefon.telefonNummer = "41517343"
		telefon.save(failOnError: true, flush: true)
		
		intervjuObjekt = new IntervjuObjekt()
		intervjuObjekt.intervjuObjektNummer = "12345"
		intervjuObjekt.navn = "hulken"
		intervjuObjekt.intervjuStatus = 0
		intervjuObjekt.katSkjemaStatus = SkjemaStatus.Innlastet
		intervjuObjekt.kjonn = Kjonn.MANN
		intervjuObjekt.kontaktperiode = "lør til søn"
		
		intervjuObjekt.addToAdresser( adresse )
		intervjuObjekt.addToTelefoner( telefon )
		intervjuObjekt.save(failOnError: true, flush: true)
		
	}
	
	protected void tearDown() {
		super.tearDown()
	}
	
	void testModel() {
		assertEquals(1, Adresse.count())
		assertEquals(1, IntervjuObjekt.count())
		
		def ioAdresser = intervjuObjekt.adresser
		assertEquals(1, ioAdresser.size())
		
		def ioTelefoner = intervjuObjekt.telefoner
		assertEquals(1, ioTelefoner.size())
	}
	
	void testUpdateKontaktInformasjon() {
		def ioList = IntervjuObjekt.list()
		def io = ioList.get (0)
		
		KontaktInformasjonServiceData data = new KontaktInformasjonServiceData()
		data.intervjuObjektId = io.id
		data.alder = 37
		data.familieNummer = 12345678901
		data.fodselsNummer = 11111111111
		data.gateAdresse = "bolerlia 8"
		data.postNummer = "2004"
		data.kommuneNummer = "1234"
		data.kjonn = "2"
		data.telefon1 = "41517343"
		data.telefon2 = "12341234"
		data.telefon2 = "12341234"
		data.kontaktperiode = "fre til man"
		
		sivUpdateService.updateKontaktInformasjon (data)
		
		assertEquals("11111111111", io.fodselsNummer)
		assertEquals("12345678901", io.familienummer)
		assertEquals("fre til man", io.kontaktperiode)
		assertEquals(Kjonn.KVINNE, io.kjonn)
		assertEquals(37, io.alder)
		
		assertEquals(2, io.adresser.size())
		assertEquals(2, io.telefoner.size())
	}
	
	void testUpdateStatus_frafall() {
		def ioList = IntervjuObjekt.list()
		def io = ioList.get (0)
		
		sivUpdateService.updateStatus(io.id, 33, "fikk ikke tak i io", "spo")
		
		io.refresh()
		
		assertEquals(33, io.intervjuStatus)
		assertEquals(SkjemaStatus.Ubehandlet, io.katSkjemaStatus)
		assertEquals("fikk ikke tak i io", io.statusKommentar )
	}
	
	void testUpdateStatus_avgang() {
		def ioList = IntervjuObjekt.list()
		def io = ioList.get (0)
		
		sivUpdateService.updateStatus(io.id, 94, "fikk ikke tak i io", "spo")
		
		io.refresh()
		
		assertEquals(94, io.intervjuStatus)
		assertEquals(SkjemaStatus.Ferdig, io.katSkjemaStatus)
		assertEquals("fikk ikke tak i io", io.statusKommentar )
	}
	
	void testUpdateStatus_overgang() {
		def ioList = IntervjuObjekt.list()
		def io = ioList.get (0)
		
		sivUpdateService.updateStatus(io.id, 84, "fikk ikke tak i io", "spo")
		
		io.refresh()
		
		assertEquals(84, io.intervjuStatus)
		assertEquals(SkjemaStatus.Ubehandlet, io.katSkjemaStatus)
		assertEquals("fikk ikke tak i io", io.statusKommentar )
	}
	
	void testUpdateAdresse() {
		def ioList = IntervjuObjekt.list()
		def io = ioList.get(0)
				
		sivUpdateService.updateAdresse (io, "bolerlia 12", null, "1234", "oslo", "1234")
		
		assertEquals(2, io.adresser.size())
				
		Adresse eksisterendeAdresse = Adresse.findByGateAdresse("bolerlia 8")
		
		// gammel er blitt satt til ikke i bruk
		assertFalse(eksisterendeAdresse.gjeldende)
	}
	
	void testUpdateAdresse_eksisterer_fra_for() {
		def ioList = IntervjuObjekt.list()
		def io = ioList.get(0)
		
		sivUpdateService.updateAdresse (io, "BoleRlia 8", null, "2000", "oSlo", "1234")
		
		assertEquals(1, io.adresser.size())
		
		Adresse eksisterendeAdresse = Adresse.findByGateAdresse("bolerlia 8")
		
		// gammel er fotsatt i bruk
		assertTrue(eksisterendeAdresse?.gjeldende)
	}
	
	
	void testUpdateTelefoner_ny() {	
		def ioList = IntervjuObjekt.list()
		def io = ioList.get (0)
		
		sivUpdateService.updateTelefoner (io, "12341234", "", null)
		
		io.refresh()
		def telefonList = io.telefoner
		
		// sjekk at nytt nummer er lagt til og at det naa er 2 nr paa listen
		assertEquals(2, telefonList.size())
		
		// hent eksisterende nummer
		Telefon t = Telefon.findByTelefonNummer("41517343")
		
		assertNotNull(t)
		
		// eksisterende telefonnr bor naa vaere satt til ikke gjeldende (update metoden hadde ikke
		// gammelt nr som parameter)
		assertFalse(t.getGjeldende())
	}
	
	void testUpdateTelefoner_ny_og_eksisterende() {
		def ioList = IntervjuObjekt.list()
		def io = ioList.get (0)
		
		sivUpdateService.updateTelefoner (io, "12341234", "41517343", null)
		
		io.refresh()
		def telefonList = io.telefoner
		
		// sjekk at nytt nummer er lagt til og at det naa er 2 nr paa listen
		assertEquals(2, telefonList.size())
		
		// hent eksisterende nummer
		Telefon t = Telefon.findByTelefonNummer("41517343")
		
		assertNotNull(t)
		
		// eksisterende telefonnr bor fortsatt vare gjeldende
		boolean gjeldende = t.getGjeldende() ?: false
		assertTrue(gjeldende)
	}
	
	void testUpdateTelefoner_eksisterende() {
		def ioList = IntervjuObjekt.list()
		def io = ioList.get (0)
		
		Telefon eksisterendeTelefon = Telefon.findByTelefonNummer("41517343")
		eksisterendeTelefon.setGjeldende(false)
		
		sivUpdateService.updateTelefoner (io, "", null, "41517343")
		
		io.refresh()
		def telefonList = io.telefoner
		
		// sjekk at nytt nummer er lagt til og at det naa er 2 nr paa listen
		assertEquals(1, telefonList.size())
		
		// hent eksisterende nummer
		Telefon t = Telefon.findByTelefonNummer("41517343")
		
		assertNotNull(t)
		
		// eksisterende telefonnr bor vare satt til gjeldende
		boolean gjeldende = t.getGjeldende() ?: false	
		assertTrue(gjeldende)
	}
	
	void testUpdateTelefoner_3_nye() {
		def ioList = IntervjuObjekt.list()
		def io = ioList.get (0)
		
		sivUpdateService.updateTelefoner (io, "11111111", "22222222", "33333333")
		
		io.refresh()
		def telefonList = io.telefoner
		
		assertEquals(4, telefonList.size())
		
		// hent eksisterende nummer
		Telefon t = Telefon.findByTelefonNummer("41517343")
		
		assertNotNull(t)
		
		// eksisterende telefonnr bor vare satt til ikke gjeldende
		assertFalse(t.getGjeldende())
		
		Telefon tNy1 = Telefon.findByTelefonNummer("11111111")
		
		// nytt telefonnr bor vare satt til gjeldende
		assertTrue(tNy1.getGjeldende())
	}
}
