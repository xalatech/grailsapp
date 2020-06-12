package sivadm

import java.util.Calendar;
import java.util.Date;


import groovy.util.GroovyTestCase;
import siv.type.ArbeidsType
import siv.type.IntervjuerArbeidsType
import siv.type.IntervjuerStatus
import siv.type.Kjonn
import siv.type.TimeforingStatus
import siv.type.TransportMiddel
import siv.type.UtleggType
import sivadm.Timeforing
import sivadm.Kjorebok
import sivadm.Utlegg
import sivadm.Intervjuer
import sil.Krav
import sil.AutomatiskKontroll
import sil.type.KravType;

class AutomatiskKontrollServiceTest extends GroovyTestCase { // GrailsUnitTestCase {
	
	def automatiskKontrollService
	def kravService
	
	Krav utleggKrav
	Krav kjorebokKrav
	Krav timeKrav
	
	Calendar cal
	
	@Override
	protected void setup() {
		super.setUp()
		cal = Calendar.getInstance()
		
		Klynge klynge = new Klynge()
		klynge.klyngeNavn = "Oslo"
		klynge.klyngeSjef = "Ola"
		klynge.beskrivelse = "Klynge for Oslo"
		klynge.epost = "spo@ssb.no"
		klynge.save(failOnError: true, flush: true)
		
		Intervjuer intervjuer = new Intervjuer()
		intervjuer.klynge = klynge
		intervjuer.initialer = "spo"
		intervjuer.intervjuerNummer = 1
		intervjuer.navn = "paal soreng"
		intervjuer.kjonn = Kjonn.MANN
		intervjuer.fodselsDato = new Date()
		intervjuer.gateAdresse = "bollerveien 30"
		intervjuer.gateAdresse2 = "bollerveien 40"
		intervjuer.postNummer = "6789"
		intervjuer.kommuneNummer = "0301"
		intervjuer.epostPrivat = "pal.soreng@gmail.com"
		intervjuer.epostJobb ="spo@ssb.no"
		intervjuer.status = IntervjuerStatus.AKTIV
		intervjuer.ansattDato = new Date()
		intervjuer.avtaltAntallTimer = 700
		intervjuer.arbeidsType = IntervjuerArbeidsType.BESOK
		intervjuer.sluttDato = new Date()
		intervjuer.save(failOnError: true, flush: true)
		
		Timeforing time = new Timeforing()
		time.arbeidsType = ArbeidsType.INTERVJUE
		time.intervjuer = intervjuer
		time.timeforingStatus = TimeforingStatus.SENDT_INN
		time.produktNummer = "666-66"
		cal.set Calendar.HOUR_OF_DAY, 9
		cal.set Calendar.MINUTE, 0
		cal.set Calendar.SECOND, 0
		time.fra = cal.getTime()
		cal.add(Calendar.MINUTE, 70)
		time.til = cal.getTime()
		time.redigertAv = "spo"
		time.redigertDato = new Date()
		time.save(failOnError:true, flush:true)
		
		Kjorebok kjorebok = new Kjorebok()
		kjorebok.intervjuer = intervjuer
		kjorebok.transportmiddel = TransportMiddel.EGEN_BIL
		kjorebok.timeforingStatus = TimeforingStatus.SENDT_INN
		kjorebok.fraTidspunkt = cal.getTime()
		cal.add(Calendar.MINUTE, 30)
		kjorebok.tilTidspunkt = cal.getTime()
		kjorebok.fraAdresse = "Storgata 100"
		kjorebok.tilAdresse = "Parkveien 200"
		kjorebok.fraPoststed = "Oslo"
		kjorebok.tilPoststed = "Oslo"
		kjorebok.produktNummer = "1234-5"
		kjorebok.kjorteKilometer = 10
		kjorebok.redigertAv = "krn"
		kjorebok.redigertDato = new Date()
		kjorebok.merknad = "En test"
		kjorebok.kjorteHjem = Boolean.FALSE
		kjorebok.timeforing = kjorebok.opprettTimeforing()
		kjorebok.save(failOnError:true, flush: true)
		
		Utlegg utlegg = new Utlegg()
		utlegg.intervjuer = intervjuer
		utlegg.dato = new Date()
		utlegg.produktNummer = "3456-7"
		utlegg.utleggType = UtleggType.BILLETT
		utlegg.spesifisering = "Test"
		utlegg.merknad = "En test"
		utlegg.belop = 250
		utlegg.redigertAv = "krn"
		utlegg.redigertDato = new Date()
		utlegg.timeforingStatus = TimeforingStatus.SENDT_INN
		utlegg.save(failOnError:true, flush: true)
		
		timeKrav = kravService.konverterTimeforingTilKrav(time)
		kjorebokKrav = kravService.konverterKjorebokTilKrav(kjorebok)
		utleggKrav = kravService.konverterUtleggTilKrav(utlegg)
		
		timeKrav.save(failOnError:true, flush: true)
		kjorebokKrav.save(failOnError:true, flush: true)
		utleggKrav.save(failOnError:true, flush: true)
	}

	@Override
	protected void tearDown() {
		super.tearDown()
		
		utleggKrav = null
		kjorebokKrav = null
		timeKrav = null
		cal = null
	}
	
	void testBestodAutomatiskKontroll() {
		AutomatiskKontroll aKontOne = new AutomatiskKontroll() 
		aKontOne.kontrollNavn = "test"
		aKontOne.grenseVerdi = 300
		aKontOne.kravType = KravType.U
		aKontOne.produktNummer = null 
		aKontOne.feilmelding = "Error"
		aKontOne.gyldigFra = null
		aKontOne.gyldigTil = null
		
		boolean bestodAutoKontroll = automatiskKontrollService.bestodAutomatiskKontroll(utleggKrav, aKontOne)
		
		assertEquals true, bestodAutoKontroll
	}
	
	void testFeiletAutomatiskKontroll() {
		AutomatiskKontroll aKontOne = new AutomatiskKontroll()
		aKontOne.kontrollNavn = "test"
		aKontOne.grenseVerdi = 200
		aKontOne.kravType = KravType.U
		aKontOne.produktNummer = null
		aKontOne.feilmelding = "Error"
		aKontOne.gyldigFra = null
		aKontOne.gyldigTil = null
		
		boolean bestodAutoKontroll = automatiskKontrollService.bestodAutomatiskKontroll(utleggKrav, aKontOne)
		
		assertEquals false, bestodAutoKontroll
	}
	
	void testBestodAutomatiskKontrollRettProduktNummer() {
		AutomatiskKontroll aKontOne = new AutomatiskKontroll()
		aKontOne.kontrollNavn = "test"
		aKontOne.grenseVerdi = 15
		aKontOne.kravType = KravType.K
		aKontOne.produktNummer = "1234-5"
		aKontOne.feilmelding = "Error"
		aKontOne.gyldigFra = null
		aKontOne.gyldigTil = null
		
		boolean bestodAutoKontroll = automatiskKontrollService.bestodAutomatiskKontroll(kjorebokKrav, aKontOne)
	
		assertEquals true, bestodAutoKontroll
	}
	
	void testFeiletAutomatiskKontrollRettProduktNummer() {
		AutomatiskKontroll aKontOne = new AutomatiskKontroll()
		aKontOne.kontrollNavn = "test"
		aKontOne.grenseVerdi = 5
		aKontOne.kravType = KravType.K
		aKontOne.produktNummer = "1234-5"
		aKontOne.feilmelding = "Error"
		aKontOne.gyldigFra = null
		aKontOne.gyldigTil = null
		
		boolean feiletAutoKontroll = automatiskKontrollService.feiletAutomatiskKontroll(kjorebokKrav, aKontOne)
	
		assertEquals true, feiletAutoKontroll
	}
	
	void testBestodAutomatiskKontrollFeilProduktNummer() {
		// Skal bestå kontrollen selv om grenseverdien er minde enn
		// verdien på kravet fordi produktNummer ikke matcher og kontrollen
		// skal da ignoreres
		AutomatiskKontroll aKontOne = new AutomatiskKontroll()
		aKontOne.kontrollNavn = "test"
		aKontOne.grenseVerdi = 5
		aKontOne.kravType = KravType.K
		aKontOne.transportmiddel = TransportMiddel.EGEN_BIL
		aKontOne.produktNummer = "1234-5xxxx"
		aKontOne.feilmelding = "Error"
		aKontOne.gyldigFra = null
		aKontOne.gyldigTil = null
		
		boolean bestodAutoKontroll = automatiskKontrollService.bestodAutomatiskKontroll(kjorebokKrav, aKontOne)
	
		assertEquals true, bestodAutoKontroll
	}
	
	void testBestodAutomatiskKontrollRettProduktNummerOgGyldigeDatoer() {
		AutomatiskKontroll aKontOne = new AutomatiskKontroll()
		aKontOne.kontrollNavn = "test"
		aKontOne.grenseVerdi = 90
		aKontOne.kravType = KravType.T
		aKontOne.produktNummer = "666-66"
		aKontOne.feilmelding = "Error"
		cal.setTime(new Date())
		cal.add Calendar.DATE, -10
		aKontOne.gyldigFra = cal.getTime()
		cal.add Calendar.DATE, 20
		aKontOne.gyldigTil = cal.getTime()
		
		boolean bestodAutoKontroll = automatiskKontrollService.bestodAutomatiskKontroll(timeKrav, aKontOne)
	
		assertEquals true, bestodAutoKontroll
	}
	
	void testBestodAutomatiskKontrollUgyldigFraDato() {
		// Skal bestå kontrollen selv om grenseverdien er minde enn
		// verdien på kravet fordi fra dato er ugyldig og kontrollen
		// skal da ignoreres
		AutomatiskKontroll aKontOne = new AutomatiskKontroll()
		aKontOne.kontrollNavn = "test"
		aKontOne.grenseVerdi = 90
		aKontOne.kravType = KravType.T
		aKontOne.produktNummer = null
		aKontOne.feilmelding = "Error"
		cal.setTime(new Date())
		cal.add Calendar.DATE, 1
		aKontOne.gyldigFra = cal.getTime()
		aKontOne.gyldigTil = null
		
		boolean bestodAutoKontroll = automatiskKontrollService.bestodAutomatiskKontroll(timeKrav, aKontOne)
	
		assertEquals true, bestodAutoKontroll
	}
	
	void testBestodAutomatiskKontrollUgyldigTilDato() {
		// Skal bestå kontrollen selv om grenseverdien er minde enn
		// verdien på kravet fordi til dato er ugyldig og kontrollen
		// skal da ignoreres
		AutomatiskKontroll aKontOne = new AutomatiskKontroll()
		aKontOne.kontrollNavn = "test"
		aKontOne.grenseVerdi = 90
		aKontOne.kravType = KravType.T
		aKontOne.produktNummer = null
		aKontOne.feilmelding = "Error"
		cal.setTime(new Date())
		cal.add Calendar.DATE, -1
		aKontOne.gyldigTil = cal.getTime()
		aKontOne.gyldigFra = null
		
		boolean bestodAutoKontroll = automatiskKontrollService.bestodAutomatiskKontroll(timeKrav, aKontOne)
	
		assertEquals true, bestodAutoKontroll
	}
	
	void testFeiletAutomatiskKontrollTransportmiddel() {
		AutomatiskKontroll aKontOne = new AutomatiskKontroll()
		aKontOne.kontrollNavn = "test"
		aKontOne.grenseVerdi = 5
		aKontOne.kravType = KravType.K
		aKontOne.produktNummer = null
		aKontOne.transportmiddel = TransportMiddel.EGEN_BIL
		aKontOne.feilmelding = "Error"
		aKontOne.gyldigFra = null
		aKontOne.gyldigTil = null
		
		boolean bestodAutoKontroll = automatiskKontrollService.bestodAutomatiskKontroll(kjorebokKrav, aKontOne)
		
		assertEquals false, bestodAutoKontroll
	}
}
