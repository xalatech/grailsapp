package sivadm

import sil.Krav
import sil.SilMelding
import siv.type.TimeforingStatus;
import siv.type.TransportMiddel;
import siv.type.UtleggType;

class KjorebokService {

    static transactional = true

	def grailsApplication
	
	/**
	* Opprett, endre eller slett utlegg for utgifter spesifisert i
	* kjørebokskjma. Verdien fra feltene i skjema er definert i createCommand.
	* Returnerer en liste med utlegg som skal slettes.
	*/
   def oppdaterUtleggKjorebok = { boolean silRetting, Date dato, Double bompenger, Double parkering, Double ferje, Double belop, Kjorebok kjorebokInstance, int cntUtlegg, int cntUtleggFeilet, String redigertAv ->
	   def slettListe = []
	   
	   if(bompenger && bompenger != 0D) {
		   // Utlegg for bompenger er fylt ut og ikke null
		   
		   def utleggBom
		   
		   // Hvis utlegg for bompenger finnes på kjøreboken fra før og er endret
		   // rediger dette utlegget, hvis ikke opprett nytt utlegg.
		   if(kjorebokInstance?.utleggBom) {
			   utleggBom = kjorebokInstance.utleggBom
			   utleggBom.belop = bompenger
		   }
		   else {
			   utleggBom = new Utlegg(intervjuer: kjorebokInstance.intervjuer, timeforingStatus: TimeforingStatus.IKKE_GODKJENT, redigertDato: new Date(), redigertAv: redigertAv, belop: bompenger)
			   utleggBom.dato = dato
			   utleggBom.utleggType = UtleggType.BOMPENGER
			   utleggBom.spesifisering = UtleggType.BOMPENGER.getGuiName()
			   kjorebokInstance.utleggBom = utleggBom
			   utleggBom.harKjorebok = true
		   }
		   
		   utleggBom.produktNummer = kjorebokInstance.produktNummer
		   
		   if(silRetting) {
			   utleggBom.timeforingStatus = TimeforingStatus.SENDT_INN
		   }
					   
		   if(utleggBom.save(flush: true)) {
			   cntUtlegg++
		   }
		   else {
			   cntUtleggFeilet++
		   }
	   }
	   else if(kjorebokInstance?.utleggBom) {
		   // Ingen bompenger registert -> slett utlegg som gjelder bompenger som er
		   // registert på kjøreboken
		   slettListe << kjorebokInstance.utleggBom
		   kjorebokInstance.utleggBom = null
	   }
			   
	   if(parkering && parkering != 0D) {
		   // Utlegg for parkering er fylt ut og ikke null
		   
		   def utleggParkering
		   
		   // Hvis utlegg for parkering finnes på kjøreboken fra før og er endret
		   // rediger dette utlegget, hvis ikke opprett nytt utlegg.
		   if(kjorebokInstance?.utleggParkering) {
			   utleggParkering = kjorebokInstance.utleggParkering
			   utleggParkering.belop = parkering
		   }
		   else {
			   utleggParkering = new Utlegg(intervjuer: kjorebokInstance.intervjuer, timeforingStatus: TimeforingStatus.IKKE_GODKJENT, redigertDato: new Date(), redigertAv: redigertAv, belop: parkering)
			   utleggParkering.dato = dato
			   utleggParkering.utleggType = UtleggType.PARKERING
			   utleggParkering.spesifisering = UtleggType.PARKERING.getGuiName()
			   kjorebokInstance.utleggParkering = utleggParkering
			   utleggParkering.harKjorebok = true
		   }
		   
		   utleggParkering.produktNummer = kjorebokInstance.produktNummer
		   
		   if(silRetting) {
			   utleggParkering.timeforingStatus = TimeforingStatus.SENDT_INN
		   }
		   
		   if(utleggParkering.save(flush: true)) {
			   cntUtlegg++
		   }
		   else {
			   cntUtleggFeilet++
		   }
	   }
	   else if(kjorebokInstance?.utleggParkering) {
		   // Ingen parkering registert -> slett utlegg som gjelder parkering som er
		   // registert på kjøreboken
		   slettListe << kjorebokInstance.utleggParkering
		   kjorebokInstance.utleggParkering = null
	   }
	   
	   if(ferje && ferje != 0D) {
		   // Utlegg for ferje er fylt ut og ikke null
		   
		   def utleggFerje
		   
		   // Hvis utlegg for ferje finnes på kjøreboken fra før og er endret
		   // rediger dette utlegget, hvis ikke opprett nytt utlegg.
		   if(kjorebokInstance?.utleggFerge) {
			   utleggFerje = kjorebokInstance.utleggFerge
			   utleggFerje.belop = ferje
		   }
		   else {
			   utleggFerje = new Utlegg(intervjuer: kjorebokInstance.intervjuer, timeforingStatus: TimeforingStatus.IKKE_GODKJENT, redigertDato: new Date(), redigertAv: redigertAv, belop: ferje)
			   utleggFerje.dato = dato
			   utleggFerje.utleggType = UtleggType.BILLETT
			   utleggFerje.spesifisering = TransportMiddel.FERJE.getGuiName()
			   kjorebokInstance.utleggFerge = utleggFerje
			   utleggFerje.harKjorebok = true
		   }
		   
		   utleggFerje.produktNummer = kjorebokInstance.produktNummer
		   
		   if(silRetting) {
			   utleggFerje.timeforingStatus = TimeforingStatus.SENDT_INN
		   }
					   
		   if(utleggFerje.save(flush: true)) {
			   cntUtlegg++
		   }
		   else {
			   cntUtleggFeilet++
		   }
	   }
	   else if(kjorebokInstance?.utleggFerge) {
		   // Ingen ferje registert -> slett utlegg som gjelder ferje som er
		   // registert på kjøreboken
		   slettListe << kjorebokInstance.utleggFerge
		   kjorebokInstance.utleggFerge = null
	   }

	   if(belop && belop != 0D) {
		   // Utlegg for belop er fylt ut og ikke 0
		   
		   def utleggBelop
		   
		   // Hvis utlegg for belop finnes på kjøreboken fra før og er endret
		   // rediger dette utlegget, hvis ikke opprett nytt utlegg.
		   if(kjorebokInstance?.utleggBelop) {
			   utleggBelop = kjorebokInstance.utleggBelop
			   utleggBelop.belop = belop
		   }
		   else {
			   utleggBelop = new Utlegg(intervjuer: kjorebokInstance.intervjuer, timeforingStatus: TimeforingStatus.IKKE_GODKJENT, redigertDato: new Date(), redigertAv: redigertAv, belop: belop)
			   utleggBelop.dato = dato
			   utleggBelop.utleggType = UtleggType.BILLETT
			   kjorebokInstance.utleggBelop = utleggBelop
			   utleggBelop.harKjorebok = true
		   }
		   
		   utleggBelop.produktNummer = kjorebokInstance.produktNummer
		   
		   if(kjorebokInstance.transportmiddel == TransportMiddel.BUSS_TRIKK) {
			   utleggBelop.utleggType = UtleggType.BILLETT
			   utleggBelop.spesifisering = TransportMiddel.BUSS_TRIKK.getGuiName()
		   }
		   else if(kjorebokInstance.transportmiddel == TransportMiddel.TOG) {
			   utleggBelop.utleggType = UtleggType.BILLETT
			   utleggBelop.spesifisering = TransportMiddel.TOG.getGuiName()
		   }
		   else if(kjorebokInstance.transportmiddel == TransportMiddel.TAXI) {
			   utleggBelop.utleggType = UtleggType.TAXI
			   utleggBelop.spesifisering = TransportMiddel.TAXI.getGuiName()
		   }
		   
		   if(silRetting) {
			   utleggBelop.timeforingStatus = TimeforingStatus.SENDT_INN
		   }
					   
		   if(utleggBelop.save(flush: true)) {
			   cntUtlegg++
		   }
		   else {
			   cntUtleggFeilet++
		   }
	   }
	   else if(kjorebokInstance?.utleggBelop) {
		   // Ingen belop registert -> slett utlegg som gjelder belop som er
		   // registert på kjøreboken
		   slettListe << kjorebokInstance.utleggBelop
		   kjorebokInstance.utleggBelop = null
	   }

	   return slettListe
   }

	def ryddKjoreboker = {

		String antallAar = grailsApplication.config.behold.fravaer.antall.aar
		log.info("Kjører ryddKjoreboker, sletter kjoreboker eldre enn " + antallAar + " år")

		Calendar cal = Calendar.getInstance()
		cal.add(Calendar.YEAR, (-1 * Integer.parseInt(antallAar)))


		slettKjorebokerEldreEnn(cal.getTime())


	}

	def slettKjorebokerEldreEnn (Date dato) {

		def kjorebokerSomSkalSlettes = Kjorebok.findAllByFraTidspunktLessThan(dato)
		def antall = 0

		kjorebokerSomSkalSlettes.each { Kjorebok kjorebok ->
			try {
                slettKrav(kjorebok)
			} catch (Exception ex) {
				log.error('Kunne ikke slette krav for kjorebok med id: ' + kjorebok.id + ', ' + ex.getMessage())
			}
		}

        Kjorebok.executeUpdate("delete from Kjorebok where fraTidspunkt < :dato", [dato: dato])
		log.info('Har slettet ' + antall + ' kjoreboker med dato eldre enn ' + dato)

	}

    def slettKrav(Kjorebok kjorebok) {
        Krav.executeUpdate("DELETE FROM Krav WHERE kjorebok = :kjorebok", [kjorebok: kjorebok]);
    }
}
