package siv.model.translator

import parser.ExtendedUtvalg
import sivadm.*
import util.UtvalgUtil
/**
 * Oversetter intern domenemodell til utvalg.
 */
public class UtvalgTranslator {

	public static ExtendedUtvalg translateToUtvalg(ExtendedUtvalg utvalg, IntervjuObjekt intervjuObjekt, Skjema skjema, boolean isExtendedUtvalg) {
		Skjema locSkjema = skjema	// lokal kopi av skjema

		utvalg = new ExtendedUtvalg();

		// if-utsagnet under er satt inn for å sikre tilgang til husholdninger, telefoner osv
		if (!intervjuObjekt.isAttached()) {
			intervjuObjekt.attach()
		}

		if (isExtendedUtvalg) {
			utvalg.setIntervjuStatus(intervjuObjekt.getIntervjuStatus());
			utvalg.setSkjemaStatus(intervjuObjekt?.getKatSkjemaStatus()?.getGuiName());
		}

		//Periode
		Periode periode = intervjuObjekt.getPeriode();
		if (null != periode) {
			utvalg.setPeriodeType(periode.getPeriodeType().getKey());
			utvalg.setPeriodeNr(periode.getPeriodeNummer());
			utvalg.setAar(periode.getAar());

			if (locSkjema == null) {
				locSkjema = periode.getSkjema();
				if (isExtendedUtvalg) {
					utvalg.setSkjemaNavn(locSkjema.getSkjemaNavn());
				}
			}

			//Skjema
			if (locSkjema != null) {
				utvalg.setDelprodnr(locSkjema.getDelProduktNummer());

				if(locSkjema.getIntervjuTypeTelefon()) utvalg.setIntType("T");
				else if(locSkjema.getIntervjuTypeBesok()) utvalg.setIntType("B");
				else if(locSkjema.getIntervjuTypePapir()) utvalg.setIntType("P");
				else if(locSkjema.getIntervjuTypeWeb()) utvalg.setIntType("W");
			}
		}

		//Husholdning
		Husholdning[] husholdningList = intervjuObjekt.husholdninger ;

		for (Husholdning husholdning : husholdningList) {
			if (husholdning.getHusholdNummer() == 1) {
				utvalg.setHushold1(husholdning.getNavn());
				utvalg.setHushold1Fnr(husholdning.getFodselsNummer());
				utvalg.setHushold1Perskode(husholdning.getPersonKode());
			} else if (husholdning.getHusholdNummer() == 2) {
				utvalg.setHushold2(husholdning.getNavn());
				utvalg.setHushold2Fnr(husholdning.getFodselsNummer());
				utvalg.setHushold2Perskode(husholdning.getPersonKode());
			} else if (husholdning.getHusholdNummer() == 3) {
				utvalg.setHushold3(husholdning.getNavn());
				utvalg.setHushold3Fnr(husholdning.getFodselsNummer());
				utvalg.setHushold3Perskode(husholdning.getPersonKode());
			} else if (husholdning.getHusholdNummer() == 4) {
				utvalg.setHushold4(husholdning.getNavn());
				utvalg.setHushold4Fnr(husholdning.getFodselsNummer());
				utvalg.setHushold4Perskode(husholdning.getPersonKode());
			} else if (husholdning.getHusholdNummer() == 5) {
				utvalg.setHushold5(husholdning.getNavn());
				utvalg.setHushold5Fnr(husholdning.getFodselsNummer());
				utvalg.setHushold5Perskode(husholdning.getPersonKode());
			} else if (husholdning.getHusholdNummer() == 6) {
				utvalg.setHushold6(husholdning.getNavn());
				utvalg.setHushold6Fnr(husholdning.getFodselsNummer());
				utvalg.setHushold6Perskode(husholdning.getPersonKode());
			} else if (husholdning.getHusholdNummer() == 7) {
				utvalg.setHushold7(husholdning.getNavn());
				utvalg.setHushold7Fnr(husholdning.getFodselsNummer());
				utvalg.setHushold7Perskode(husholdning.getPersonKode());
			} else if (husholdning.getHusholdNummer() == 8) {
				utvalg.setHushold8(husholdning.getNavn());
				utvalg.setHushold8Fnr(husholdning.getFodselsNummer());
				utvalg.setHushold8Perskode(husholdning.getPersonKode());
			} else if (husholdning.getHusholdNummer() == 9) {
				utvalg.setHushold9(husholdning.getNavn());
				utvalg.setHushold9Fnr(husholdning.getFodselsNummer());
				utvalg.setHushold9Perskode(husholdning.getPersonKode());
			} else if (husholdning.getHusholdNummer() == 10) {
				utvalg.setHushold10(husholdning.getNavn());
				utvalg.setHushold10Fnr(husholdning.getFodselsNummer());
				utvalg.setHushold10Perskode(husholdning.getPersonKode());
			}
		}

		utvalg.setKjonn(UtvalgUtil.mapKjonnReverse(intervjuObjekt.getKjonn()));
		utvalg.setfNummer(intervjuObjekt.getFodselsNummer());

		Telefon[] telefonList = intervjuObjekt.telefoner

		def sortertTelefonList = telefonList.sort { it.id }

		int telefonCount = 1

		for (Telefon tlf : sortertTelefonList) {

			if( telefonCount == 1 && tlf.getGjeldende() == true ) {
				utvalg.setTelefon1(tlf.getTelefonNummer());
				utvalg.setKommentar1(tlf.getKommentar());
				utvalg.setKilde1(tlf.getKilde());
				telefonCount ++
			}
			else if( telefonCount == 2 && tlf.getGjeldende() == true ) {
				utvalg.setTelefon2(tlf.getTelefonNummer());
				utvalg.setKommentar2(tlf.getKommentar());
				utvalg.setKilde2(tlf.getKilde());
				telefonCount ++
			}
			else if( telefonCount == 3 && tlf.getGjeldende() == true ) {
				utvalg.setTelefon3(tlf.getTelefonNummer());
				utvalg.setKommentar3(tlf.getKommentar());
				utvalg.setKilde3(tlf.getKilde());
				telefonCount ++
			}
		}

		def datoSortertTelefonList =  telefonList.sort { a, b -> a.redigertDato <=> b.redigertDato }

		if (datoSortertTelefonList.size()>0) {
			// Ved innlesing av utvalgsfil får Telefon1, Telefon2 og Telefon3 et avvik på noen millisekunder i "redigertDato".
			// Setter derfor en grense ved 1 sekund, for å skille mellom redigerte og innleste telefonnummer.
			boolean fraUtvalgsfil = (datoSortertTelefonList.last().redigertDato.getTime()-datoSortertTelefonList.first().redigertDato.getTime()) < 1000
			def smsNummer

			if (fraUtvalgsfil) {
				smsNummer = utvalg.getTelefon1()
				if (!isMobilTlfNr(smsNummer)) {
					smsNummer = utvalg.getTelefon2()
					if (!isMobilTlfNr(smsNummer)) {
						smsNummer = utvalg.getTelefon3()
					}
				}
			} else {
				datoSortertTelefonList.each {
					if (it.gjeldende && isMobilTlfNr(it.telefonNummer)) {
						smsNummer = it.telefonNummer
					}
				}
			}
			utvalg.setSmsNummer(smsNummer)
		}

		// Adresse
		Adresse besoksAdresse = intervjuObjekt.findGjeldendeBesokAdresse()
		Adresse brevAdresse = intervjuObjekt.findGjeldendePostAdresse()

		if (null != besoksAdresse) {
			utvalg.setAdresse(besoksAdresse.getGateAdresse());
			utvalg.setNavn2(besoksAdresse.getTilleggsAdresse());
			utvalg.setPostnr(besoksAdresse.getPostNummer());
			utvalg.setPoststed(besoksAdresse.getPostSted());
			utvalg.setGateGaard(besoksAdresse.getGateGaardNummer()) ;
			utvalg.setHusBruk(besoksAdresse.getHusBruksNummer());
			utvalg.setKommune(besoksAdresse.getKommuneNummer());
			utvalg.setBolignr(besoksAdresse.getBoligNummer());
			utvalg.setBokstavFnr(besoksAdresse.getBokstavFeste());
			utvalg.setUndernr(besoksAdresse.getUnderNummer());
		}

		if (null != brevAdresse) {
			utvalg.setBrevadr("");
			utvalg.setBrevpostnr("");
			utvalg.setBrevpostst("");
		}


		//Historie
		Historie[] historieList = intervjuObjekt.historier;

		for (Historie historie : historieList) {
			if (historie.getHistorieNummer() == 1) utvalg.setResu1(historie.getResultat());
			else if (historie.getHistorieNummer() == 2) utvalg.setResu2(historie.getResultat());
			else if (historie.getHistorieNummer() == 3) utvalg.setResu3(historie.getResultat());
			else if (historie.getHistorieNummer() == 4) utvalg.setResu4(historie.getResultat());
			else if (historie.getHistorieNummer() == 5) utvalg.setResu5(historie.getResultat());
			else if (historie.getHistorieNummer() == 6) utvalg.setResu6(historie.getResultat());
			else if (historie.getHistorieNummer() == 7) utvalg.setResu7(historie.getResultat());
		}

		//Intervjuobjekt
		utvalg.setIntervjuObjektId(intervjuObjekt.id);
		utvalg.setDelutvalg(intervjuObjekt.delutvalg);
		utvalg.setIoNr(intervjuObjekt.intervjuObjektNummer);
		utvalg.setAlder(intervjuObjekt.alder);
		utvalg.setPerskode(intervjuObjekt.personKode);
		utvalg.setMelding(intervjuObjekt.getMeldingTilIntervjuer());
		utvalg.setFamNr( intervjuObjekt.familienummer )

		utvalg.setNavn(intervjuObjekt.navn);
		utvalg.setSivilstand(intervjuObjekt.sivilstand);
		utvalg.setStatsbrg(intervjuObjekt.statsborgerskap);
		utvalg.setkPeriode(intervjuObjekt.kontaktperiode);

		if(intervjuObjekt.personKode.equals("1")) {
			utvalg.setErIo("X");
			utvalg.setRefNavn(intervjuObjekt.navn);
		} else {
			utvalg.setErIo("Y");
			utvalg.setRefNavn(intervjuObjekt.referansePerson);
		}

		utvalg.setUtvalgsomr(intervjuObjekt.getUtvalgsOmraade());
		utvalg.setAvtaledato(intervjuObjekt.getUtvalgAvtaleDato());

		utvalg.setPrefInt("");
		utvalg.setAvtaletype(null);
		utvalg.setAntallAnsatte(null);
		utvalg.setNace("");
		utvalg.setAvtalekomm("");
		utvalg.setAvtaletid("");
		utvalg.setReservasjon(reservasjonSomString(intervjuObjekt.getReservasjon()));
		utvalg.setEpostadresse(intervjuObjekt.getEpost() ?: "");
		utvalg.setPassordWeb(intervjuObjekt.getPassordWeb() ?: "");
		utvalg.setRotasjonGrp("");
		utvalg.setFullforingsGrad(intervjuObjekt.fullforingsGrad);
		utvalg.setKilde(intervjuObjekt.kilde?.getGuiName());

		return utvalg;
	}


	private static String findFNrForHusHoldning1(List<Husholdning> husholdningList) {
		for (Husholdning husholdning : husholdningList) {
			if(husholdning.getHusholdNummer() == 1) {
				return husholdning.getFodselsNummer();
			}
		}
		return "";
	}

	private static String reservasjonSomString(Boolean variable) {
		if (variable==null){
			return "";
		}else if (variable==true){
			return "JA";
		}else {
			return "NEI";
		}
	}

	private static boolean isMobilTlfNr (String tlfNr) {
		tlfNr?.startsWith("4") || tlfNr?.startsWith("9")
	}
}
