package no.ssb.sivadm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import no.ssb.sivadm.exception.SivAdmException;
import no.ssb.sivadm.parser.Utvalg;
import no.ssb.sivadm.util.UtvalgUtil;
import siv.type.AdresseType;
import siv.type.SkjemaStatus;
import siv.type.TelefonType;
import siv.util.IntervjuObjektUtil;
import sivadm.Adresse;
import sivadm.Historie;
import sivadm.Husholdning;
import sivadm.IntervjuObjekt;
import sivadm.Periode;
import sivadm.Skjema;
import sivadm.Telefon;
import sivadm.UtvalgImport;

public class UtvalgService {

	/**
	 * Import av utvalg inn i SIVADM database fra utvalgsfil.
	 * 
	 * @param enhetType
	 * @param utvalgList
	 * @param remoteUser
	 * @param fileName
	 * @param skjema
	 * @return antall intervjuobjekt importert
	 */
	public int importUtvalg(List<Utvalg> utvalgList, String remoteUser,
			Skjema skjema, UtvalgImport utvalgImport) {

		int lineCount = 0;

		try {
			Periode periode = null;
			Long pNr = null;
			
			for (Utvalg utvalg : utvalgList) {

				lineCount++;

				if (validateDelProduktNummer(skjema, utvalg) == false) {
					throw new SivAdmException(
							"Arbeidsordrenummer stemmer ikke overens med utvalg. Skjema: "
									+ skjema.getDelProduktNummer()
									+ " Utvalg: " + utvalg.getDelprodnr());
				}

				// Hent opp ny periode hvis periode i utvalg ikke er samme som
				// periode i forrige utvalg
				if (pNr == null || pNr.intValue() != utvalg.getPeriodeNr().intValue()) {
					periode = Periode.findPeriodeBySkjemaAndPNr(skjema,
							utvalg.getPeriodeNr());
					pNr = periode.getPeriodeNummer();
				}

				if (periode != null) {
					
					List<Adresse> adresser = createAdresser(utvalg, remoteUser);

					List<Telefon> telefoner = createTelefoner(utvalg, remoteUser);

					List<Husholdning> husholdninger = createHusholdning(utvalg,
							remoteUser);

					List<Historie> historier = createHistorie(utvalg);

					IntervjuObjekt intervjuObjekt = createIntervjuobjekt(
							periode, utvalg, remoteUser, lineCount);

					// sjekk om intervjuobjekt skal settes til sporing
					boolean tilSporing = IntervjuObjektUtil
							.skalIntervjuObjektTilSporing(telefoner, adresser,
									skjema);

					if (tilSporing) {
						IntervjuObjektUtil
								.settIntervjuObjektTilSporing(intervjuObjekt);
					}

					IntervjuObjektUtil.setIntervjuerRelationsAndSaveAll(
							periode, intervjuObjekt, husholdninger, telefoner,
							adresser, historier, utvalgImport, lineCount);

				} else {
					throw new SivAdmException("Finner ikke periode nr "
							+ utvalg.getPeriodeNr() + " for skjema: "
							+ skjema.getSkjemaNavn());
				}
			}

		} catch (Exception e) {
			
			throw new SivAdmException(
					"En feil har oppstått ved lesing av linje "
							+ lineCount
							+ " i utvalgsfil. Feilmelding: "
							+ e.getMessage()
							+ ". "
							+ (lineCount - 1)
							+ " intervjuobjekt ble importert fra utvalget før feilen oppstod.");
		}

		return lineCount;
	}


	public boolean utvalgInneholderIntervjuObjektDuplikat(List<Utvalg> utvalgListe, Skjema skjema) {
		if (utvalgListe == null || utvalgListe.isEmpty()
				|| utvalgListe.size() == 0) {
			return false;
		}

		Periode periode = null;
		Long pNr = null;
		for (Utvalg u : utvalgListe) {
			// Hent opp ny periode hvis periode i utvalg ikke er samme som
			// periode i forrige utvalg
			if (pNr == null || pNr.intValue() != u.getPeriodeNr().intValue()) {
				periode = Periode.findPeriodeBySkjemaAndPNr(skjema,
						u.getPeriodeNr());

				if (periode == null) {
					throw new SivAdmException("Finner ikke periode nr "
							+ u.getPeriodeNr() + " for skjema: "
							+ skjema.getSkjemaNavn());
				} else {
					pNr = periode.getPeriodeNummer();
				}
			}

			IntervjuObjekt io = IntervjuObjekt.findByIoNummerAndPeriode(
					u.getIoNr(), periode);
			if (io != null) {
				return true;
			}
		}

		return false;
	}


	private boolean validateDelProduktNummer(Skjema skjema, Utvalg u) {
		if (skjema.getDelProduktNummer().equalsIgnoreCase(u.getDelprodnr()))
			return true;
		else
			return false;
	}


	/**
	 * Oppretter telefoner for intervjuobjekt
	 * 
	 * @param utvalg
	 * @param remoteUser
	 * @return en liste med telefoner
	 */
	private List<Telefon> createTelefoner(Utvalg utvalg, String remoteUser) {
		
		List<Telefon> telefoner = new ArrayList<Telefon>();

		if (!utvalg.getTelefon1().equals("")) {
			Telefon telefon = new Telefon();
			telefon.setTelefonNummer(utvalg.getTelefon1());
			telefon.setKommentar(utvalg.getKommentar1());
			telefon.setKilde(utvalg.getKilde1());
			telefon.setOriginal(true);
			telefon.setGjeldende(true);
			telefon.setResepsjon(false);
			telefon.setTelefonType(TelefonType.PRIVAT);
			telefoner.add(telefon);
		}

		if (!utvalg.getTelefon2().equals("")) {
			Telefon telefon = new Telefon();
			telefon.setTelefonNummer(utvalg.getTelefon2());
			telefon.setKommentar(utvalg.getKommentar2());
			telefon.setKilde(utvalg.getKilde2());
			telefon.setOriginal(true);
			telefon.setGjeldende(true);
			telefon.setResepsjon(false);
			telefon.setTelefonType(TelefonType.PRIVAT);
			telefoner.add(telefon);
		}

		if (!utvalg.getTelefon3().equals("")) {
			Telefon telefon = new Telefon();
			telefon.setTelefonNummer(utvalg.getTelefon3());
			telefon.setKommentar(utvalg.getKommentar3());
			telefon.setKilde(utvalg.getKilde3());
			telefon.setOriginal(true);
			telefon.setGjeldende(true);
			telefon.setResepsjon(false);
			telefon.setTelefonType(TelefonType.PRIVAT);
			telefoner.add(telefon);
		}

		return telefoner;
	}


	/**
	 * Oppretter post og besoksadresse for intervjuobjekt
	 * 
	 * @param utvalg
	 * @param remoteUser
	 * @return En liste med adresser
	 */
	protected List<Adresse> createAdresser(Utvalg utvalg, String remoteUser) {

		List<Adresse> adresser = new ArrayList<Adresse>();

		Adresse adresseBesok = null;

		if (!utvalg.getAdresse().equals("")
				|| !utvalg.getGateGaard().equals("")
				|| !utvalg.getHusBruk().equals("")
				|| !utvalg.getPostnr().equals("")
				|| !utvalg.getKommune().equals("")) {

			adresseBesok = new Adresse();

			adresseBesok.setGateAdresse(utvalg.getAdresse());
			adresseBesok.setGateGaardNummer(utvalg.getGateGaard());
			adresseBesok.setHusBruksNummer(utvalg.getHusBruk());
			adresseBesok.setBoligNummer(utvalg.getBolignr());
			adresseBesok.setPostNummer(utvalg.getPostnr());
			adresseBesok.setPostSted(utvalg.getPoststed());
			adresseBesok.setKommuneNummer(utvalg.getKommune());
			adresseBesok.setAdresseType(AdresseType.BESOK);
			adresseBesok.setOriginal(true);
			adresseBesok.setGyldigFom(new Date());

			if (!utvalg.getPostnr().equals("")) {
				adresseBesok.setGjeldende(true);
				adresseBesok.setTilleggsAdresse(utvalg.getNavn2());
			} else {
				adresseBesok.setGjeldende(false);
			}

			adresser.add(adresseBesok);
		}

		if (!utvalg.getBrevpostnr().equals("")
				|| !utvalg.getAdresse().equals("")
				|| !utvalg.getPostnr().equals("")
				|| !utvalg.getKommune().equals("")) {

			Adresse adressePost = new Adresse();

			/* Saa postadresse dersom den eksister */
			if (!utvalg.getBrevpostnr().equals("")) {
				adressePost.setGateAdresse(utvalg.getBrevadr());
				adressePost.setPostNummer(utvalg.getBrevpostnr());
				adressePost.setPostSted(utvalg.getBrevpostst());

			} else {
				/*
				 * brevadresse er ikke gitt eksplisitt og besoksadresse er ogsaa
				 * brev/postadresse
				 */
				adressePost.setGateAdresse(utvalg.getAdresse());
				adressePost.setPostNummer(utvalg.getPostnr());
				adressePost.setPostSted(utvalg.getPoststed());
			}

			adressePost.setKommuneNummer(utvalg.getKommune());
			adressePost.setAdresseType(AdresseType.POST);
			adressePost.setOriginal(true);
			adressePost.setGyldigFom(new Date());

			if (adresseBesok != null
					&& !likeAdresser(adressePost, adresseBesok)) {
				if ((!utvalg.getBrevadr().equals("") && !utvalg.getBrevpostnr()
						.equals(""))
						|| (!utvalg.getAdresse().equals("") && !utvalg
								.getPostnr().equals(""))) {

					adressePost.setGjeldende(true);
				} else {
					adressePost.setGjeldende(false);
				}

				adresser.add(adressePost);
			}
		}

		return adresser;
	}


	private List<Husholdning> createHusholdning(Utvalg utvalg, String remoteUser) {
		List<Husholdning> husholdninger = new ArrayList<Husholdning>();

		if (!utvalg.getHushold1().equals("")) {
			Husholdning hushold = new Husholdning();
			hushold.setHusholdNummer(1);
			hushold.setNavn(utvalg.getHushold1());
			hushold.setFodselsNummer(utvalg.getHushold1Fnr());
			hushold.setPersonKode(utvalg.getHushold1Perskode());
			hushold.setFodselsDato(UtvalgUtil.getFDatoFromFnr(utvalg
					.getHushold1Fnr()));
			husholdninger.add(hushold);
		}

		if (!utvalg.getHushold2().equals("")) {
			Husholdning hushold = new Husholdning();
			hushold.setHusholdNummer(2);
			hushold.setNavn(utvalg.getHushold2());
			hushold.setFodselsNummer(utvalg.getHushold2Fnr());
			hushold.setPersonKode(utvalg.getHushold2Perskode());
			hushold.setFodselsDato(UtvalgUtil.getFDatoFromFnr(utvalg
					.getHushold2Fnr()));
			husholdninger.add(hushold);
		}

		if (!utvalg.getHushold3().equals("")) {
			Husholdning hushold = new Husholdning();
			hushold.setHusholdNummer(3);
			hushold.setNavn(utvalg.getHushold3());
			hushold.setFodselsNummer(utvalg.getHushold3Fnr());
			hushold.setPersonKode(utvalg.getHushold3Perskode());
			hushold.setFodselsDato(UtvalgUtil.getFDatoFromFnr(utvalg
					.getHushold3Fnr()));
			husholdninger.add(hushold);
		}

		if (!utvalg.getHushold4().equals("")) {
			Husholdning hushold = new Husholdning();
			hushold.setHusholdNummer(4);
			hushold.setNavn(utvalg.getHushold4());
			hushold.setFodselsNummer(utvalg.getHushold4Fnr());
			hushold.setPersonKode(utvalg.getHushold4Perskode());
			hushold.setFodselsDato(UtvalgUtil.getFDatoFromFnr(utvalg
					.getHushold4Fnr()));
			husholdninger.add(hushold);
		}

		if (!utvalg.getHushold5().equals("")) {
			Husholdning hushold = new Husholdning();
			hushold.setHusholdNummer(5);
			hushold.setNavn(utvalg.getHushold5());
			hushold.setFodselsNummer(utvalg.getHushold5Fnr());
			hushold.setPersonKode(utvalg.getHushold5Perskode());
			hushold.setFodselsDato(UtvalgUtil.getFDatoFromFnr(utvalg
					.getHushold5Fnr()));
			husholdninger.add(hushold);
		}

		if (!utvalg.getHushold6().equals("")) {
			Husholdning hushold = new Husholdning();
			hushold.setHusholdNummer(6);
			hushold.setNavn(utvalg.getHushold6());
			hushold.setFodselsNummer(utvalg.getHushold6Fnr());
			hushold.setPersonKode(utvalg.getHushold6Perskode());
			hushold.setFodselsDato(UtvalgUtil.getFDatoFromFnr(utvalg
					.getHushold6Fnr()));
			husholdninger.add(hushold);
		}

		if (!utvalg.getHushold7().equals("")) {
			Husholdning hushold = new Husholdning();
			hushold.setHusholdNummer(7);
			hushold.setNavn(utvalg.getHushold7());
			hushold.setFodselsNummer(utvalg.getHushold7Fnr());
			hushold.setPersonKode(utvalg.getHushold7Perskode());
			hushold.setFodselsDato(UtvalgUtil.getFDatoFromFnr(utvalg
					.getHushold7Fnr()));
			husholdninger.add(hushold);
		}

		if (!utvalg.getHushold8().equals("")) {
			Husholdning hushold = new Husholdning();
			hushold.setHusholdNummer(8);
			hushold.setNavn(utvalg.getHushold8());
			hushold.setFodselsNummer(utvalg.getHushold8Fnr());
			hushold.setPersonKode(utvalg.getHushold8Perskode());
			hushold.setFodselsDato(UtvalgUtil.getFDatoFromFnr(utvalg
					.getHushold8Fnr()));
			husholdninger.add(hushold);
		}

		if (!utvalg.getHushold9().equals("")) {
			Husholdning hushold = new Husholdning();
			hushold.setHusholdNummer(9);
			hushold.setNavn(utvalg.getHushold9());
			hushold.setFodselsNummer(utvalg.getHushold9Fnr());
			hushold.setPersonKode(utvalg.getHushold9Perskode());
			hushold.setFodselsDato(UtvalgUtil.getFDatoFromFnr(utvalg
					.getHushold9Fnr()));
			husholdninger.add(hushold);
		}

		if (!utvalg.getHushold10().equals("")) {
			Husholdning hushold = new Husholdning();
			hushold.setHusholdNummer(10);
			hushold.setNavn(utvalg.getHushold10());
			hushold.setFodselsNummer(utvalg.getHushold10Fnr());
			hushold.setPersonKode(utvalg.getHushold10Perskode());
			hushold.setFodselsDato(UtvalgUtil.getFDatoFromFnr(utvalg
					.getHushold10Fnr()));
			husholdninger.add(hushold);
		}

		return husholdninger;
	}


	/**
	 * Oppretter et intervjuobjekt basert paa utvalg.
	 * 
	 * @param periode
	 * @param utvalg
	 * @param remoteUser
	 * @return Intervjuobjekt
	 */
	private IntervjuObjekt createIntervjuobjekt(Periode periode, Utvalg utvalg,
			String remoteUser, int lineCount) {

		IntervjuObjekt intervjuObjekt = new IntervjuObjekt();

		intervjuObjekt.setPeriode(periode);
		intervjuObjekt.setKatSkjemaStatus(SkjemaStatus.Innlastet);

		String erIntervjuObjekt = utvalg.getErIo();

		if (erIntervjuObjekt.equalsIgnoreCase("X")
				|| erIntervjuObjekt.equalsIgnoreCase("Y")) {

			intervjuObjekt.setDelutvalg(utvalg.getDelutvalg());
			intervjuObjekt.setIntervjuObjektNummer(utvalg.getIoNr());
			intervjuObjekt.setFodselsNummer(utvalg.getfNummer());
			intervjuObjekt.setNavn(utvalg.getNavn());
			intervjuObjekt.setAlder(utvalg.getAlder());
			intervjuObjekt.setSivilstand(utvalg.getSivilstand());
			intervjuObjekt.setPersonKode(utvalg.getPerskode());
			intervjuObjekt.setReferansePerson(utvalg.getRefNavn());
			intervjuObjekt.setStatsborgerskap(utvalg.getStatsbrg());
			intervjuObjekt.setKontaktperiode(utvalg.getkPeriode());
			intervjuObjekt.setMeldingTilIntervjuer(utvalg.getMelding());
			intervjuObjekt.setFamilienummer(utvalg.getFamNr());
			intervjuObjekt.setUtvalgsOmraade(utvalg.getUtvalgsomr());
			intervjuObjekt.setUtvalgAvtaleDato(utvalg.getAvtaledato());
			intervjuObjekt.setOverIoNummer(utvalg.getOverioNr());
			intervjuObjekt.setPassordWeb(utvalg.getPassordWeb());
			intervjuObjekt.setReservasjon(utvalg.getReservasjonAsBoolean());
			intervjuObjekt.setEpost(utvalg.getEpostadresse());
			intervjuObjekt.setKjonn(UtvalgUtil.mapKjonn(utvalg.getKjonn()));
			intervjuObjekt.setMaalform(utvalg.getMaalform());
			intervjuObjekt.setVarslingsstatus(utvalg.getVarslingsstatus());
		} else {
			throw new SivAdmException(
					"Ikke et gyldig intervjuobjekt. Mangler X eller Y på ErIntervjuobjekt.");
		}
		return intervjuObjekt;
	}


	private List<Historie> createHistorie(Utvalg utvalg) {

		List<Historie> histories = new ArrayList<Historie>(7);

		if (!utvalg.getResu1().equals("")) {
			Historie historie1 = new Historie();
			historie1.setHistorieNummer(1);
			historie1.setResultat(utvalg.getResu1());
			histories.add(historie1);
		}

		if (!utvalg.getResu2().equals("")) {
			Historie historie2 = new Historie();
			historie2.setHistorieNummer(2);
			historie2.setResultat(utvalg.getResu2());
			histories.add(historie2);
		}

		if (!utvalg.getResu3().equals("")) {
			Historie historie3 = new Historie();
			historie3.setHistorieNummer(3);
			historie3.setResultat(utvalg.getResu3());
			histories.add(historie3);
		}

		if (!utvalg.getResu4().equals("")) {
			Historie historie4 = new Historie();
			historie4.setHistorieNummer(4);
			historie4.setResultat(utvalg.getResu4());
			histories.add(historie4);
		}

		if (!utvalg.getResu5().equals("")) {
			Historie historie5 = new Historie();
			historie5.setHistorieNummer(5);
			historie5.setResultat(utvalg.getResu5());
			histories.add(historie5);
		}

		if (!utvalg.getResu6().equals("")) {
			Historie historie6 = new Historie();
			historie6.setHistorieNummer(6);
			historie6.setResultat(utvalg.getResu6());
			histories.add(historie6);
		}

		if (!utvalg.getResu7().equals("")) {
			Historie historie7 = new Historie();
			historie7.setHistorieNummer(7);
			historie7.setResultat(utvalg.getResu7());
			histories.add(historie7);
		}
		return histories;
	}


	private boolean likeAdresser(Adresse aEn, Adresse aTo) {
		if (aEn.getGateAdresse() != null && aTo.getGateAdresse() != null
				&& !aEn.getGateAdresse().equalsIgnoreCase(aTo.getGateAdresse())) {
			return false;
		}

		if (aEn.getPostNummer() != null && aTo.getPostNummer() != null
				&& !aEn.getPostNummer().equalsIgnoreCase(aTo.getPostNummer())) {
			return false;
		}

		if (aEn.getPostSted() != null && aTo.getPostSted() != null
				&& !aEn.getPostSted().equalsIgnoreCase(aTo.getPostSted())) {
			return false;
		}

		if (aEn.getKommuneNummer() != null
				&& aTo.getKommuneNummer() != null
				&& !aEn.getKommuneNummer().equalsIgnoreCase(
						aTo.getKommuneNummer())) {
			return false;
		}

		return true;
	}
	
	
}
