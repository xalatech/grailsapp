package parser;

import java.util.ArrayList;
import java.util.List;

public class ExtendedUtvalg extends Utvalg {
	private String delimiter = ";";

	private String skjemaStatus;
	private Integer intervjuStatus;
	private String skjemaNavn;

	// Rekkefølgen på feltene ved uttak bestemmes i static-delen lenger ned
	private static final String SKJEMASTATUS = "SKJEMASTATUS";
	private static final String INTERVJUSTATUS = "INTERVJUSTATUS";
	private static final String SKJEMANAVN = "SKJEMANAVN";
	private static final String DELPRODNR = "DELPRODNR";
	private static final String AAR = "AAR";
	private static final String PERIODE_TYPE = "PERIODE_TYPE";
	private static final String PERIODE_NR = "PERIODE_NR";
	private static final String IO_NR = "IO_NR";
	private static final String INT_TYPE = "INT_TYPE";
	private static final String F_NUMMER = "F_NUMMER";
	private static final String FAM_NR = "FAM_NR";
	private static final String GATE_GAARD = "GATE_GAARD";
	private static final String HUS_BRUK = "HUS_BRUK";
	private static final String BOKSTAV_FNR = "BOKSTAV_FNR";
	private static final String UNDERNR = "UNDERNR";
	private static final String NAVN = "NAVN";
	private static final String NAVN2 = "NAVN2";
	private static final String KJONN = "KJONN";
	private static final String KOMMUNE = "KOMMUNE";
	private static final String UTVALGSOMR = "UTVALGSOMR";
	private static final String ADRESSE = "ADRESSE";
	private static final String BOLIGNR = "BOLIGNR";
	private static final String POSTNR = "POSTNR";
	private static final String POSTSTED = "POSTSTED";
	private static final String BREVADR = "BREVADR";
	private static final String BREVPOSTNR = "BREVPOSTNR";
	private static final String BREVPOSTST = "BREVPOSTST";
	private static final String ALDER = "ALDER";
	private static final String SIVILSTAND = "SIVILSTAND";
	private static final String PERSKODE = "PERSKODE";
	private static final String STATSBRG = "STATSBRG";
	private static final String ER_IO = "ER_IO";
	private static final String K_PERIODE = "K_PERIODE";
	private static final String DELUTVALG = "DELUTVALG";
	private static final String PREF_INT = "PREF_INT";
	private static final String REF_NAVN = "REF_NAVN";
	private static final String RESU1 = "RESU1";
	private static final String RESU2 = "RESU2";
	private static final String RESU3 = "RESU3";
	private static final String RESU4 = "RESU4";
	private static final String RESU5 = "RESU5";
	private static final String RESU6 = "RESU6";
	private static final String RESU7 = "RESU7";
	private static final String HUSHOLD1 = "HUSHOLD1";
	private static final String HUSHOLD1_FNR = "HUSHOLD1_FNR";
	private static final String HUSHOLD1_PERSKODE = "HUSHOLD1_PERSKODE";
	private static final String HUSHOLD2 = "HUSHOLD2";
	private static final String HUSHOLD2_FNR = "HUSHOLD2_FNR";
	private static final String HUSHOLD2_PERSKODE = "HUSHOLD2_PERSKODE";
	private static final String HUSHOLD3 = "HUSHOLD3";
	private static final String HUSHOLD3_FNR = "HUSHOLD3_FNR";
	private static final String HUSHOLD3_PERSKODE = "HUSHOLD3_PERSKODE";
	private static final String HUSHOLD4 = "HUSHOLD4";
	private static final String HUSHOLD4_FNR = "HUSHOLD4_FNR";
	private static final String HUSHOLD4_PERSKODE = "HUSHOLD4_PERSKODE";
	private static final String HUSHOLD5 = "HUSHOLD5";
	private static final String HUSHOLD5_FNR = "HUSHOLD5_FNR";
	private static final String HUSHOLD5_PERSKODE = "HUSHOLD5_PERSKODE";
	private static final String HUSHOLD6 = "HUSHOLD6";
	private static final String HUSHOLD6_FNR = "HUSHOLD6_FNR";
	private static final String HUSHOLD6_PERSKODE = "HUSHOLD6_PERSKODE";
	private static final String HUSHOLD7 = "HUSHOLD7";
	private static final String HUSHOLD7_FNR = "HUSHOLD7_FNR";
	private static final String HUSHOLD7_PERSKODE = "HUSHOLD7_PERSKODE";
	private static final String HUSHOLD8 = "HUSHOLD8";
	private static final String HUSHOLD8_FNR = "HUSHOLD8_FNR";
	private static final String HUSHOLD8_PERSKODE = "HUSHOLD8_PERSKODE";
	private static final String HUSHOLD9 = "HUSHOLD9";
	private static final String HUSHOLD9_FNR = "HUSHOLD9_FNR";
	private static final String HUSHOLD9_PERSKODE = "HUSHOLD9_PERSKODE";
	private static final String HUSHOLD10 = "HUSHOLD10";
	private static final String HUSHOLD10_FNR = "HUSHOLD10_FNR";
	private static final String HUSHOLD10_PERSKODE = "HUSHOLD10_PERSKODE";
	private static final String AVTALEDATO = "AVTALEDATO";
	private static final String AVTALETID = "AVTALETID";
	private static final String AVTALETYPE = "AVTALETYPE";
	private static final String AVTALEKOMM = "AVTALEKOMM";
	
	private static final String TELEFON1 = "TELEFON1";
	private static final String KOMMENTAR1 = "KOMMENTAR1";
	private static final String KILDE1 = "KILDE1";
	
	
	private static final String TELEFON2 = "TELEFON2";
	private static final String KOMMENTAR2 = "KOMMENTAR2";
	private static final String KILDE2 = "KILDE2";
	
	
	private static final String TELEFON3 = "TELEFON3";
	private static final String KOMMENTAR3 = "KOMMENTAR3";
	private static final String KILDE3 = "KILDE3";
	
	private static final String RESERVASJON = "RESERVASJON";
	
	private static final String EPOSTADRESSE = "EPOSTADRESSE";
	private static final String NACE = "NACE";
	private static final String ANTALL_ANSATTE = "ANTALL_ANSATTE";
	private static final String MELDING = "MELDING";
	private static final String OVERIO_NR = "OVERIO_NR";
	private static final String PASSORD_WEB = "PASSORD_WEB";
	private static final String ROTASJON_GRP = "ROTASJON_GRP";
	private static final String INTERVJUOBJEKT_ID = "INTERVJUOBJEKT_ID";
	
	private static final String FULLFORINGSGRAD= "FULLFORINGSGRAD";
	private static final String KILDE = "KILDE";

//	public final static List<String> propertyList = new LinkedList<String>();
	public final static List<String> propertyList = new ArrayList<String>();

	static {
		propertyList.add(IO_NR);
		propertyList.add(SKJEMANAVN);
		propertyList.add(DELPRODNR);
		propertyList.add(PERIODE_NR);
		propertyList.add(INTERVJUOBJEKT_ID);
		propertyList.add(NAVN);
		propertyList.add(ADRESSE);
		propertyList.add(POSTNR);
		propertyList.add(POSTSTED);
		propertyList.add(SKJEMASTATUS);
		propertyList.add(INTERVJUSTATUS);
		propertyList.add(DELUTVALG);

		propertyList.add(AAR);
		propertyList.add(PERIODE_TYPE);
		propertyList.add(INT_TYPE);
		propertyList.add(F_NUMMER);
		propertyList.add(FAM_NR);
		propertyList.add(GATE_GAARD);
		propertyList.add(HUS_BRUK);
		propertyList.add(BOKSTAV_FNR);
		propertyList.add(UNDERNR);
		propertyList.add(NAVN2);
		propertyList.add(KJONN);
		propertyList.add(KOMMUNE);
		propertyList.add(UTVALGSOMR);
		propertyList.add(BOLIGNR);
		propertyList.add(BREVADR);
		propertyList.add(BREVPOSTNR);
		propertyList.add(BREVPOSTST);
		propertyList.add(ALDER);
		propertyList.add(SIVILSTAND);
		propertyList.add(PERSKODE);
		propertyList.add(STATSBRG);
		propertyList.add(ER_IO);
		propertyList.add(K_PERIODE);
		propertyList.add(PREF_INT);
		propertyList.add(REF_NAVN);
		propertyList.add(RESU1);
		propertyList.add(RESU2);
		propertyList.add(RESU3);
		propertyList.add(RESU4);
		propertyList.add(RESU5);
		propertyList.add(RESU6);
		propertyList.add(RESU7);
		propertyList.add(HUSHOLD1);
		propertyList.add(HUSHOLD1_FNR);
		propertyList.add(HUSHOLD1_PERSKODE);
		propertyList.add(HUSHOLD2);
		propertyList.add(HUSHOLD2_FNR);
		propertyList.add(HUSHOLD2_PERSKODE);
		propertyList.add(HUSHOLD3);
		propertyList.add(HUSHOLD3_FNR);
		propertyList.add(HUSHOLD3_PERSKODE);
		propertyList.add(HUSHOLD4);
		propertyList.add(HUSHOLD4_FNR);
		propertyList.add(HUSHOLD4_PERSKODE);
		propertyList.add(HUSHOLD5);
		propertyList.add(HUSHOLD5_FNR);
		propertyList.add(HUSHOLD5_PERSKODE);
		propertyList.add(HUSHOLD6);
		propertyList.add(HUSHOLD6_FNR);
		propertyList.add(HUSHOLD6_PERSKODE);
		propertyList.add(HUSHOLD7);
		propertyList.add(HUSHOLD7_FNR);
		propertyList.add(HUSHOLD7_PERSKODE);
		propertyList.add(HUSHOLD8);
		propertyList.add(HUSHOLD8_FNR);
		propertyList.add(HUSHOLD8_PERSKODE);
		propertyList.add(HUSHOLD9);
		propertyList.add(HUSHOLD9_FNR);
		propertyList.add(HUSHOLD9_PERSKODE);
		propertyList.add(HUSHOLD10);
		propertyList.add(HUSHOLD10_FNR);
		propertyList.add(HUSHOLD10_PERSKODE);
		propertyList.add(AVTALEDATO);
		propertyList.add(AVTALETID);
		propertyList.add(AVTALETYPE);
		propertyList.add(AVTALEKOMM);
		
		propertyList.add(TELEFON1);  
		propertyList.add(KOMMENTAR1);
		propertyList.add(KILDE1);
		
		propertyList.add(TELEFON2);
		propertyList.add(KOMMENTAR2);
		propertyList.add(KILDE2);
		
		propertyList.add(TELEFON3);
		propertyList.add(KOMMENTAR3);
		propertyList.add(KILDE3);
		
		propertyList.add(RESERVASJON);
		
		propertyList.add(EPOSTADRESSE);
		propertyList.add(NACE);
		propertyList.add(ANTALL_ANSATTE);
		propertyList.add(MELDING);
		propertyList.add(OVERIO_NR);
		propertyList.add(PASSORD_WEB);
		propertyList.add(ROTASJON_GRP);
		
		propertyList.add(FULLFORINGSGRAD);
		propertyList.add(KILDE);
	}


	
	public void setSkjemaStatus (String status) {
		this.skjemaStatus = status;
	}
	
	public void setIntervjuStatus (Integer status) {
			this.intervjuStatus = status;
	}
	
	public void setSkjemaNavn(String navn) {
		this.skjemaNavn = navn;
	}
	
	public String getSkjemaStatus() {
		return this.skjemaStatus;
	}
	
	public Integer getIntervjuStatus() {
		return this.intervjuStatus;
	}
	
	public String getSkjemaNavn() {
		return this.skjemaNavn;
	}
	
	public String getHeading() {
		StringBuilder heading = new StringBuilder();
		
		for (String listElement : propertyList) {
			heading.append(listElement + delimiter);
		}
		heading = heading.deleteCharAt(heading.length()-1);
		heading.append("\n");
		return heading.toString();
	}
	
	public StringBuilder toCSVString() {
		StringBuilder content = new StringBuilder();
		
		for (String listElement : propertyList) {
			String value = new String();
			if (listElement.equals(DELPRODNR)) {
				value = getDelprodnr();
			} else if (listElement.equals(SKJEMANAVN)) {
				value = getSkjemaNavn();
			} else if (listElement.equals(INTERVJUSTATUS)) {
				value = getIntegerValue(getIntervjuStatus());
			} else if (listElement.equals(SKJEMASTATUS)) {
				value = getSkjemaStatus();
			} else if (listElement.equals(AAR)) {
				value = getAar();
			} else if (listElement.equals(PERIODE_TYPE)) {
				value = getPeriodeType();
			} else if (listElement.equals(PERIODE_NR)) {
				value = getLongValue(getPeriodeNr());
			} else if (listElement.equals(IO_NR)) {
				value = getIoNr();
			} else if (listElement.equals(INT_TYPE)) {
				value = getIntType();
			} else if (listElement.equals(F_NUMMER)) {
				value = getfNummer();
			} else if (listElement.equals(FAM_NR)) {
				value = getFamNr();
			} else if (listElement.equals(GATE_GAARD)) {
				value = getGateGaard();
			} else if (listElement.equals(HUS_BRUK)) {
				value = getHusBruk();
			} else if (listElement.equals(BOKSTAV_FNR)) {
				value = getBokstavFnr();
			} else if (listElement.equals(UNDERNR)) {
				value = getUndernr();
			} else if (listElement.equals(NAVN)) {
				value = getNavn();
			} else if (listElement.equals(NAVN2)) {
				value = getNavn2();
			} else if (listElement.equals(KJONN)) {
				value = getKjonn();
			} else if (listElement.equals(KOMMUNE)) {
				value = getKommune();
			} else if (listElement.equals(UTVALGSOMR)) {
				value = getUtvalgsomr();
			} else if (listElement.equals(ADRESSE)) {
				value = getAdresse();
			} else if (listElement.equals(BOLIGNR)) {
				value = getBolignr();
			} else if (listElement.equals(POSTNR)) {
				value = getPostnr();
			} else if (listElement.equals(POSTSTED)) {
				value = getPoststed();
			} else if (listElement.equals(BREVADR)) {
				value = getBrevadr();
			} else if (listElement.equals(BREVPOSTNR)) {
				value = getBrevpostnr();
			} else if (listElement.equals(BREVPOSTST)) {
				value = getBrevpostst();
			} else if (listElement.equals(ALDER)) {
				value = getLongValue(getAlder());
			} else if (listElement.equals(SIVILSTAND)) {
				value = getSivilstand();
			} else if (listElement.equals(PERSKODE)) {
				value = getPerskode();
			} else if (listElement.equals(STATSBRG)) {
				value = getStatsbrg();
			} else if (listElement.equals(ER_IO)) {
				value = getErIo();
			} else if (listElement.equals(K_PERIODE)) {
				value = getkPeriode();
			} else if (listElement.equals(DELUTVALG)) {
				value = getDelutvalg();
			} else if (listElement.equals(PREF_INT)) {
				value = getPrefInt();
			} else if (listElement.equals(REF_NAVN)) {
				value = getRefNavn();
			} else if (listElement.equals(RESU1)) {
				value = getResu1();
			} else if (listElement.equals(RESU2)) {
				value = getResu2();
			} else if (listElement.equals(RESU3)) {
				value = getResu3();
			} else if (listElement.equals(RESU4)) {
				value = getResu4();
			} else if (listElement.equals(RESU5)) {
				value = getResu5();
			} else if (listElement.equals(RESU6)) {
				value = getResu6();
			} else if (listElement.equals(RESU7)) {
				value = getResu7();
			} else if (listElement.equals(HUSHOLD1)) {
				value = getHushold1();
			} else if (listElement.equals(HUSHOLD1_FNR)) {
				value = getHushold1Fnr();
			} else if (listElement.equals(HUSHOLD1_PERSKODE)) {
				value = getHushold1Perskode();
			} else if (listElement.equals(HUSHOLD2)) {
				value = getHushold2();
			} else if (listElement.equals(HUSHOLD2_FNR)) {
				value = getHushold2Fnr();
			} else if (listElement.equals(HUSHOLD2_PERSKODE)) {
				value = getHushold2Perskode();
			} else if (listElement.equals(HUSHOLD3)) {
				value = getHushold3();
			} else if (listElement.equals(HUSHOLD3_FNR)) {
				value = getHushold3Fnr();
			} else if (listElement.equals(HUSHOLD3_PERSKODE)) {
				value = getHushold3Perskode();
			} else if (listElement.equals(HUSHOLD4)) {
				value = getHushold4();
			} else if (listElement.equals(HUSHOLD4_FNR)) {
				value = getHushold4Fnr();
			} else if (listElement.equals(HUSHOLD4_PERSKODE)) {
				value = getHushold4Perskode();
			} else if (listElement.equals(HUSHOLD5)) {
				value = getHushold5();
			} else if (listElement.equals(HUSHOLD5_FNR)) {
				value = getHushold5Fnr();
			} else if (listElement.equals(HUSHOLD5_PERSKODE)) {
				value = getHushold5Perskode();
			} else if (listElement.equals(HUSHOLD6)) {
				value = getHushold6();
			} else if (listElement.equals(HUSHOLD6_FNR)) {
				value = getHushold6Fnr();
			} else if (listElement.equals(HUSHOLD6_PERSKODE)) {
				value = getHushold6Perskode();
			} else if (listElement.equals(HUSHOLD7)) {
				value = getHushold7();
			} else if (listElement.equals(HUSHOLD7_FNR)) {
				value = getHushold7Fnr();
			} else if (listElement.equals(HUSHOLD7_PERSKODE)) {
				value = getHushold7Perskode();
			} else if (listElement.equals(HUSHOLD8)) {
				value = getHushold8();
			} else if (listElement.equals(HUSHOLD8_FNR)) {
				value = getHushold8Fnr();
			} else if (listElement.equals(HUSHOLD8_PERSKODE)) {
				value = getHushold8Perskode();
			} else if (listElement.equals(HUSHOLD9)) {
				value = getHushold9();
			} else if (listElement.equals(HUSHOLD9_FNR)) {
				value = getHushold9Fnr();
			} else if (listElement.equals(HUSHOLD9_PERSKODE)) {
				value = getHushold9Perskode();
			} else if (listElement.equals(HUSHOLD10)) {
				value = getHushold10();
			} else if (listElement.equals(HUSHOLD10_FNR)) {
				value = getHushold10Fnr();
			} else if (listElement.equals(HUSHOLD10_PERSKODE)) {
				value = getHushold10Perskode();
			} else if (listElement.equals(AVTALEDATO)) {
				value = getAvtaledato();
			} else if (listElement.equals(AVTALETYPE)) {
				value = getIntegerValue(getAvtaletype());
			} else if (listElement.equals(AVTALETID)) {
				value = getAvtaletid();
			} else if (listElement.equals(AVTALEKOMM)) {
				value = getAvtalekomm();
			} else if (listElement.equals(TELEFON1)) {
				value = getTelefon1();
			} else if (listElement.equals(KOMMENTAR1)) {
				value = getKommentar1();
			} else if (listElement.equals(KILDE1)) {
				value = getKilde1();
			} else if (listElement.equals(TELEFON2)) {
				value = getTelefon2();
			} else if (listElement.equals(KOMMENTAR2)) {
				value = getKommentar2();
			} else if (listElement.equals(KILDE2)) {
				value = getKilde2();
			} else if (listElement.equals(TELEFON3)) {
				value = getTelefon3();
			} else if (listElement.equals(KOMMENTAR3)) {
				value = getKommentar3();
			} else if (listElement.equals(KILDE3)) {
				value = getKilde3();
			} else if (listElement.equals(RESERVASJON)) {
				value = getReservasjon();
			} else if (listElement.equals(EPOSTADRESSE)) {
				value = getEpostadresse();
			} else if (listElement.equals(NACE)) {
				value = getNace();
			} else if (listElement.equals(ANTALL_ANSATTE)) {
				value = getLongValue(getAntallAnsatte());
			} else if (listElement.equals(MELDING)) {
				value = getMelding();
				if (value != null) {
					value = value.replaceAll("\\r?\\n", " ");
					value = value.replace(";", ",");
				}
			} else if (listElement.equals(OVERIO_NR)) {
				value = getOverioNr();
			} else if (listElement.equals(PASSORD_WEB)) {
				value = getPassordWeb();
			} else if (listElement.equals(ROTASJON_GRP)) {
				value = getRotasjonGrp();
			} else if (listElement.equals(INTERVJUOBJEKT_ID)) {
				value = getLongValue(getIntervjuObjektId());
			} else if (listElement.equals(FULLFORINGSGRAD)) {
				value = getLongValue(getFullforingsGrad());
			} else if (listElement.equals(KILDE)) {
				value = getKilde();
			}
			
			if (value != null) content.append(value);
			content.append(delimiter);
		}
		
		content = content.deleteCharAt(content.length()-1);
		content.append("\n");
		return content;
	}
	
	
	private String getIntegerValue(Integer variable) {
		if (variable == null) return null;
		else return variable.toString();
	}

	private String getLongValue(Long variable) {
		String result = variable == null ? "" : String.valueOf(variable);
		return result;
	}
}
