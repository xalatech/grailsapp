package siv.util

import siv.type.SkjemaStatus;
import sivadm.UtvalgImport;
import sivadm.Historie;
import exception.SivAdmException;
import sivadm.Husholdning;
import sivadm.IntervjuObjekt;
import sivadm.Skjema;
import sivadm.Telefon;
import sivadm.Adresse;
import sivadm.Periode;

class IntervjuObjektUtil {
	
	/**
	 * Sjekker om et intervjuobjekt skal sendes til sporing eller ikke.
	 * 
	 * @param telefoner
	 * @param adresser
	 * @return true hvis det skal til sporing
	 */
	public static boolean skalIntervjuObjektTilSporing(List<Telefon> telefoner, List<Adresse> adresser, Skjema skjema) {

		boolean tilSporing = false

		boolean intervjuTypeBesok = skjema.intervjuTypeBesok != null ? skjema.intervjuTypeBesok : false

		if( ( !intervjuTypeBesok && !validerTelefon(telefoner) ) || !validerAdresse(adresser) ) {
			tilSporing = true
		}
		else {
			tilSporing = false
		}

		return tilSporing
	}

	
	/**
	 * Sjekker om vi har iallefall minst en stk gjeldende telefon
	 * 
	 * @param telefoner
	 * @return true hvis vi har minst en gjeldende telefon
	 */
	private static boolean validerTelefon( List<Telefon> telefoner ) {
		boolean ok = false

		if( telefoner != null && telefoner.size() > 0 ) {
			telefoner.each{ Telefon telefon ->
				if( telefon.gjeldende != null && telefon.gjeldende == true) {
					ok = true
				}
			}
		}
		else {
			ok = false
		}

		return ok
	}

	
	/**
	 * Sjekker om vi har en gyldig adresse
	 * 
	 * @param adresser
	 * @return
	 */
	private static boolean validerAdresse( List<Adresse> adresser) {
		boolean ok = false

		if( adresser != null && adresser.size() > 0) {
			adresser.each { Adresse adresse ->
				if( adresse.gjeldende == true ) {
					ok = true
				}
			}
		}

		return ok
	}

	
	/**
	 * Setter et intervjuobjekt til sporing
	 * 
	 * @param intervjuObjekt
	 */
	public static void settIntervjuObjektTilSporing( IntervjuObjekt intervjuObjekt ) {
		intervjuObjekt.katSkjemaStatus = SkjemaStatus.Ubehandlet
		intervjuObjekt.intervjuStatus = 34
	}

	
	/**
	 * Setter relasjoner og lagrer intervjuObjektet. Fikk ikke dette til i Java saa matte gjore det i groovy. Derfor
	 * er dette trukket ut av Java-koden for utvalg.
	 * 
	 * @param intervjuObjekt
	 * @param husholdninger
	 * @param telefoner
	 * @param adresser
	 */
	public static void setIntervjuerRelationsAndSaveAll(Periode periode, IntervjuObjekt intervjuObjekt,
	List<Husholdning> husholdninger, List<Telefon> telefoner, List<Adresse> adresser,
	List<Historie> historier, UtvalgImport utvalgImport, int lineCount ) {

		String utvalgLinjeError = "Utvalgfil linje: " + lineCount + " "

		for(Husholdning husholdning : husholdninger ) {
			intervjuObjekt.addToHusholdninger( husholdning )
			if( ! husholdning.save() ) {
				throw new SivAdmException(utvalgLinjeError +"Klarte ikke opprette husholdning. Feilmelding: " + husholdning.errors)
			}
		}

		for(Telefon telefon : telefoner) {
			intervjuObjekt.addToTelefoner(telefon)
			if( ! telefon.save() ) {
				throw new SivAdmException(utvalgLinjeError +"Klarte ikke opprette telefon. Feilmelding: " + telefon.errors)
			}
		}

		for(Adresse adresse : adresser ) {
			intervjuObjekt.addToAdresser(adresse)
			if( ! adresse.save() ) {
				throw new SivAdmException(utvalgLinjeError +"Klarte ikke opprette adresse. Feilmelding: " + adresse.errors)
			}
		}

		for(Historie historie : historier ) {
			intervjuObjekt.addToHistorier(historie)

			if( ! historie.save() ) {
				throw new SivAdmException(utvalgLinjeError +"Klarte ikke opprette historie. Feilmelding: " + historie.errors)
			}
		}

		intervjuObjekt.setUtvalgImport(utvalgImport)

		if( ! intervjuObjekt.save() ) {
			println intervjuObjekt.errors
			throw new SivAdmException(utvalgLinjeError + "Klarte ikke opprette intervjuobjekt. Feilmelding: " + intervjuObjekt.errors)
		}

		periode.addToIntervjuObjekter( intervjuObjekt )
		if( ! periode.save() ) {
			throw new SivAdmException(utvalgLinjeError + "Klarte ikke opprette periode. Feilmelding: " + periode.errors)
		}

		utvalgImport.antallImportert = lineCount

		// flusher antall importert hver 50. gang pga ytelse
		if( (lineCount % 100) == 0 ) {
			utvalgImport.save(flush: true)			
		}
		else {
			utvalgImport.save()
		}
	}
}
