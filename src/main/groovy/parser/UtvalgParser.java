package parser;

import exception.SivAdmException;
import util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class UtvalgParser extends PositionParser<Utvalg> {
	
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

	private static final String MAALFORM = "MAALFORM";
	private static final String VARSLINGSSTATUS = "VARSLINGSSTATUS";

	public final static Map<String, Position> selectionMap = new LinkedHashMap<String, Position>();

	static {
		selectionMap.put(DELPRODNR, new Position(1, 7));
		selectionMap.put(AAR, new Position(8, 4));
		selectionMap.put(PERIODE_TYPE, new Position(12, 4));
		selectionMap.put(PERIODE_NR, new Position(16, 2));
		selectionMap.put(IO_NR, new Position(18, 6));
		selectionMap.put(INT_TYPE, new Position(24, 1));
		selectionMap.put(F_NUMMER, new Position(25, 11));
		selectionMap.put(FAM_NR, new Position(36, 11));
		selectionMap.put(GATE_GAARD, new Position(47, 5));
		selectionMap.put(HUS_BRUK, new Position(52, 4));
		selectionMap.put(BOKSTAV_FNR, new Position(56, 4));
		selectionMap.put(UNDERNR, new Position(60, 3));
		selectionMap.put(NAVN, new Position(63, 40));
		selectionMap.put(NAVN2, new Position(103, 40));
		selectionMap.put(KJONN, new Position(143, 1));
		selectionMap.put(KOMMUNE, new Position(144, 4));
		selectionMap.put(UTVALGSOMR, new Position(148, 6));
		selectionMap.put(ADRESSE, new Position(154, 40));
		selectionMap.put(BOLIGNR, new Position(194, 5));
		selectionMap.put(POSTNR, new Position(199, 4));
		selectionMap.put(POSTSTED, new Position(203, 20));
		selectionMap.put(BREVADR, new Position(223, 40));
		selectionMap.put(BREVPOSTNR, new Position(263, 4));
		selectionMap.put(BREVPOSTST, new Position(267, 20));
		selectionMap.put(ALDER, new Position(287, 3));
		selectionMap.put(SIVILSTAND, new Position(290, 1));
		selectionMap.put(PERSKODE, new Position(291, 1));
		selectionMap.put(STATSBRG, new Position(292, 3));
		selectionMap.put(ER_IO, new Position(295, 1));
		selectionMap.put(K_PERIODE, new Position(296, 15));
		selectionMap.put(DELUTVALG, new Position(311, 2));
		selectionMap.put(PREF_INT, new Position(313, 3));
		selectionMap.put(REF_NAVN, new Position(316, 40));
		selectionMap.put(RESU1, new Position(356, 3));
		selectionMap.put(RESU2, new Position(359, 3));
		selectionMap.put(RESU3, new Position(362, 3));
		selectionMap.put(RESU4, new Position(365, 3));
		selectionMap.put(RESU5, new Position(368, 3));
		selectionMap.put(RESU6, new Position(371, 3));
		selectionMap.put(RESU7, new Position(374, 3));
		selectionMap.put(HUSHOLD1, new Position(377, 50));
		selectionMap.put(HUSHOLD1_FNR, new Position(427, 11));
		selectionMap.put(HUSHOLD1_PERSKODE, new Position(438, 1));
		selectionMap.put(HUSHOLD2, new Position(439, 50));
		selectionMap.put(HUSHOLD2_FNR, new Position(489, 11));
		selectionMap.put(HUSHOLD2_PERSKODE, new Position(500, 1));
		selectionMap.put(HUSHOLD3, new Position(501, 50));
		selectionMap.put(HUSHOLD3_FNR, new Position(551, 11));
		selectionMap.put(HUSHOLD3_PERSKODE, new Position(562, 1));
		selectionMap.put(HUSHOLD4, new Position(563, 50));
		selectionMap.put(HUSHOLD4_FNR, new Position(613, 11));
		selectionMap.put(HUSHOLD4_PERSKODE, new Position(624, 1));
		selectionMap.put(HUSHOLD5, new Position(625, 50));
		selectionMap.put(HUSHOLD5_FNR, new Position(675, 11));
		selectionMap.put(HUSHOLD5_PERSKODE, new Position(686, 1));
		selectionMap.put(HUSHOLD6, new Position(687, 50));
		selectionMap.put(HUSHOLD6_FNR, new Position(737, 11));
		selectionMap.put(HUSHOLD6_PERSKODE, new Position(748, 1));
		selectionMap.put(HUSHOLD7, new Position(749, 50));
		selectionMap.put(HUSHOLD7_FNR, new Position(799, 11));
		selectionMap.put(HUSHOLD7_PERSKODE, new Position(810, 1));
		selectionMap.put(HUSHOLD8, new Position(811, 50));
		selectionMap.put(HUSHOLD8_FNR, new Position(861, 11));
		selectionMap.put(HUSHOLD8_PERSKODE, new Position(872, 1));
		selectionMap.put(HUSHOLD9, new Position(873, 50));
		selectionMap.put(HUSHOLD9_FNR, new Position(923, 11));
		selectionMap.put(HUSHOLD9_PERSKODE, new Position(934, 1));
		selectionMap.put(HUSHOLD10, new Position(935, 50));
		selectionMap.put(HUSHOLD10_FNR, new Position(985, 11));
		selectionMap.put(HUSHOLD10_PERSKODE, new Position(996, 1));
		selectionMap.put(AVTALEDATO, new Position(997, 8));
		selectionMap.put(AVTALETID, new Position(1005, 5));
		selectionMap.put(AVTALETYPE, new Position(1010, 1));
		selectionMap.put(AVTALEKOMM, new Position(1011, 60));
		
		selectionMap.put(TELEFON1, new Position(1071, 8));
		selectionMap.put(KOMMENTAR1, new Position(1261, 80));
		selectionMap.put(KILDE1, new Position(1341, 50));
		
		selectionMap.put(TELEFON2, new Position(1079, 8));
		selectionMap.put(KOMMENTAR2, new Position(1391, 80));
		selectionMap.put(KILDE2, new Position(1471, 50));
		
		selectionMap.put(TELEFON3, new Position(1087, 8));
		selectionMap.put(KOMMENTAR3, new Position(1521, 80));
		selectionMap.put(KILDE3, new Position(1601, 50));
		
		selectionMap.put(RESERVASJON, new Position(1651, 3));
		
		selectionMap.put(EPOSTADRESSE, new Position(1095, 50));
		selectionMap.put(NACE, new Position(1145, 6));
		selectionMap.put(ANTALL_ANSATTE, new Position(1151, 7));
		selectionMap.put(MELDING, new Position(1158, 80));
		selectionMap.put(OVERIO_NR, new Position(1238, 6));
		selectionMap.put(PASSORD_WEB, new Position(1244, 8));
		selectionMap.put(ROTASJON_GRP, new Position(1252, 1));
		selectionMap.put(INTERVJUOBJEKT_ID, new Position(1253, 8));

		selectionMap.put(MAALFORM, new Position(1654, 1));
		selectionMap.put(VARSLINGSSTATUS, new Position(1655, 20));
	}


	protected List<Utvalg> mapToResultList(List<Map<String, String>> list) {
		List<Utvalg> utvalgList = new ArrayList<Utvalg>();

		int lineCount = 1;
		
		try {	
			for (Map<String, String> map : list) {
				Utvalg utvalg = new Utvalg();

				for (Map.Entry<String, String> entry : map.entrySet()) {
					String name = entry.getKey();
					String value = entry.getValue();

					if (name.equals(DELPRODNR)) {
						utvalg.setDelprodnr(value);

					} else if (name.equals(AAR)) {
						utvalg.setAar(value);

					} else if (name.equals(PERIODE_TYPE)) {
						utvalg.setPeriodeType(value);

					} else if (name.equals(PERIODE_NR)) {
						utvalg.setPeriodeNr(StringUtil.stringToLong(value));

					} else if (name.equals(IO_NR)) {
						utvalg.setIoNr(StringUtils.stripStart(value, "0"));

					} else if (name.equals(INT_TYPE)) {
						utvalg.setIntType(value);

					} else if (name.equals(F_NUMMER)) {
						utvalg.setfNummer(value);

					} else if (name.equals(FAM_NR)) {
						utvalg.setFamNr(value);

					} else if (name.equals(GATE_GAARD)) {
						utvalg.setGateGaard(value);

					} else if (name.equals(HUS_BRUK)) {
						utvalg.setHusBruk(value);

					} else if (name.equals(BOKSTAV_FNR)) {
						utvalg.setBokstavFnr(value);

					} else if (name.equals(UNDERNR)) {
						utvalg.setUndernr(value);

					} else if (name.equals(NAVN)) {
						utvalg.setNavn(value);

					} else if (name.equals(NAVN2)) {
						utvalg.setNavn2(value);

					} else if (name.equals(KJONN)) {
						utvalg.setKjonn(value);

					} else if (name.equals(KOMMUNE)) {
						utvalg.setKommune(value);

					} else if (name.equals(UTVALGSOMR)) {
						utvalg.setUtvalgsomr(value);

					} else if (name.equals(ADRESSE)) {
						utvalg.setAdresse(value);

					} else if (name.equals(BOLIGNR)) {
						utvalg.setBolignr(value);

					} else if (name.equals(POSTNR)) {
						utvalg.setPostnr(value);

					} else if (name.equals(POSTSTED)) {
						utvalg.setPoststed(value);

					} else if (name.equals(BREVADR)) {
						utvalg.setBrevadr(value);

					} else if (name.equals(BREVPOSTNR)) {
						utvalg.setBrevpostnr(value);

					} else if (name.equals(BREVPOSTST)) {
						utvalg.setBrevpostst(value);

					} else if (name.equals(ALDER)) {
						if(! value.equalsIgnoreCase(".")) {
							utvalg.setAlder(StringUtil.stringToLong(value));
						}
					} else if (name.equals(SIVILSTAND)) {
						utvalg.setSivilstand(value);

					} else if (name.equals(PERSKODE)) {
						utvalg.setPerskode(value);

					} else if (name.equals(STATSBRG)) {
						utvalg.setStatsbrg(value);

					} else if (name.equals(ER_IO)) {
						utvalg.setErIo(value);

					} else if (name.equals(K_PERIODE)) {
						utvalg.setkPeriode(value);

					} else if (name.equals(DELUTVALG)) {
						utvalg.setDelutvalg(value);

					} else if (name.equals(PREF_INT)) {
						utvalg.setPrefInt(value);

					} else if (name.equals(REF_NAVN)) {
						utvalg.setRefNavn(value);

					} else if (name.equals(RESU1)) {
						utvalg.setResu1(value);

					} else if (name.equals(RESU2)) {
						utvalg.setResu2(value);

					} else if (name.equals(RESU3)) {
						utvalg.setResu3(value);

					} else if (name.equals(RESU4)) {
						utvalg.setResu4(value);

					} else if (name.equals(RESU5)) {
						utvalg.setResu5(value);

					} else if (name.equals(RESU6)) {
						utvalg.setResu6(value);

					} else if (name.equals(RESU7)) {
						utvalg.setResu7(value);

					} else if (name.equals(HUSHOLD1)) {
						utvalg.setHushold1(value);

					} else if (name.equals(HUSHOLD1_FNR)) {
						utvalg.setHushold1Fnr(value);

					} else if (name.equals(HUSHOLD1_PERSKODE)) {
						utvalg.setHushold1Perskode(value);

					} else if (name.equals(HUSHOLD2)) {
						utvalg.setHushold2(value);

					} else if (name.equals(HUSHOLD2_FNR)) {
						utvalg.setHushold2Fnr(value);

					} else if (name.equals(HUSHOLD2_PERSKODE)) {
						utvalg.setHushold2Perskode(value);

					} else if (name.equals(HUSHOLD3)) {
						utvalg.setHushold3(value);

					} else if (name.equals(HUSHOLD3_FNR)) {
						utvalg.setHushold3Fnr(value);

					} else if (name.equals(HUSHOLD3_PERSKODE)) {
						utvalg.setHushold3Perskode(value);

					} else if (name.equals(HUSHOLD4)) {
						utvalg.setHushold4(value);

					} else if (name.equals(HUSHOLD4_FNR)) {
						utvalg.setHushold4Fnr(value);

					} else if (name.equals(HUSHOLD4_PERSKODE)) {
						utvalg.setHushold4Perskode(value);

					} else if (name.equals(HUSHOLD5)) {
						utvalg.setHushold5(value);

					} else if (name.equals(HUSHOLD5_FNR)) {
						utvalg.setHushold5Fnr(value);

					} else if (name.equals(HUSHOLD5_PERSKODE)) {
						utvalg.setHushold5Perskode(value);

					} else if (name.equals(HUSHOLD6)) {
						utvalg.setHushold6(value);

					} else if (name.equals(HUSHOLD6_FNR)) {
						utvalg.setHushold6Fnr(value);

					} else if (name.equals(HUSHOLD6_PERSKODE)) {
						utvalg.setHushold6Perskode(value);

					} else if (name.equals(HUSHOLD7)) {
						utvalg.setHushold7(value);

					} else if (name.equals(HUSHOLD7_FNR)) {
						utvalg.setHushold7Fnr(value);

					} else if (name.equals(HUSHOLD7_PERSKODE)) {
						utvalg.setHushold7Perskode(value);

					} else if (name.equals(HUSHOLD8)) {
						utvalg.setHushold8(value);

					} else if (name.equals(HUSHOLD8_FNR)) {
						utvalg.setHushold8Fnr(value);

					} else if (name.equals(HUSHOLD8_PERSKODE)) {
						utvalg.setHushold8Perskode(value);

					} else if (name.equals(HUSHOLD9)) {
						utvalg.setHushold9(value);

					} else if (name.equals(HUSHOLD9_FNR)) {
						utvalg.setHushold9Fnr(value);

					} else if (name.equals(HUSHOLD9_PERSKODE)) {
						utvalg.setHushold9Perskode(value);

					} else if (name.equals(HUSHOLD10)) {
						utvalg.setHushold10(value);

					} else if (name.equals(HUSHOLD10_FNR)) {
						utvalg.setHushold10Fnr(value);

					} else if (name.equals(HUSHOLD10_PERSKODE)) {
						utvalg.setHushold10Perskode(value);

					} else if (name.equals(AVTALEDATO)) {
						utvalg.setAvtaledato(value);

					} else if (name.equals(AVTALETYPE)) {
						utvalg.setAvtaletype(StringUtil.stringToInteger(value));

					} else if (name.equals(AVTALETID)) {
						utvalg.setAvtaletid(value);

					} else if (name.equals(AVTALEKOMM)) {
						utvalg.setAvtalekomm(value);

					} else if (name.equals(TELEFON1)) {
						utvalg.setTelefon1(value);

					} else if (name.equals(KOMMENTAR1)) {
						utvalg.setKommentar1(value);

					} else if (name.equals(KILDE1)) {
						utvalg.setKilde1(value);

					} else if (name.equals(TELEFON2)) {
						utvalg.setTelefon2(value);

					} else if (name.equals(KOMMENTAR2)) {
						utvalg.setKommentar2(value);

					} else if (name.equals(KILDE2)) {
						utvalg.setKilde2(value);

					} else if (name.equals(TELEFON3)) {
						utvalg.setTelefon3(value);

					} else if (name.equals(KOMMENTAR3)) {
						utvalg.setKommentar3(value);

					} else if (name.equals(KILDE3)) {
						utvalg.setKilde3(value);

					} else if (name.equals(RESERVASJON)) {
						utvalg.setReservasjon(value);

					} else if (name.equals(EPOSTADRESSE)) {
						utvalg.setEpostadresse(value);

					} else if (name.equals(NACE)) {
						utvalg.setNace(value);

					} else if (name.equals(ANTALL_ANSATTE)) {
						utvalg.setAntallAnsatte(StringUtil.stringToLong(value));

					} else if (name.equals(MELDING)) {
						utvalg.setMelding(value);

					} else if (name.equals(OVERIO_NR)) {
						utvalg.setOverioNr(value);

					} else if (name.equals(PASSORD_WEB)) {
						utvalg.setPassordWeb(value);

					} else if (name.equals(ROTASJON_GRP)) {
						utvalg.setRotasjonGrp(value);

					} else if (name.equals(MAALFORM)) {
						utvalg.setMaalform(value);

					} else if (name.equals(VARSLINGSSTATUS)) {
						utvalg.setVarslingsstatus(value);
					}
				}
				utvalgList.add(utvalg);
				lineCount ++ ;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new SivAdmException( "En feil har oppstått ved lesing av linje " + lineCount + " i utvalgsfil. Feilmelding: " + e.getMessage() + ". " );
		}
				
		return utvalgList;
	}


	public List<Utvalg> parse(String src) {
		return super.parse(src, selectionMap);
	}
	
	public String writeToString(List<Utvalg> utvalgList) {
		StringBuilder content = new StringBuilder();
		for (Utvalg utvalg : utvalgList) {
			StringBuilder row = new StringBuilder();
			row.append(StringUtil.createSpaces(NR_OF_CHARS_PER_LINE_EXPORT));

			for (Map.Entry<String, Position> e : selectionMap.entrySet()) {
				String name = e.getKey();
				Position p = e.getValue();
				int beginIndex = p.getStart() - 1;
				int endIndex = beginIndex + p.getLength();
				int length = endIndex - beginIndex;

				if (name.equals(DELPRODNR)) {
					String value = getUtvalgStringValue(utvalg.getDelprodnr(),
							length, true);
					row.replace(beginIndex, endIndex, value);

				} else if (name.equals(AAR)) {
					String value = getUtvalgStringValue(utvalg.getAar(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(PERIODE_TYPE)) {
					String value = getUtvalgStringValue(
							utvalg.getPeriodeType(), length, false);
					row.replace(beginIndex, endIndex, value);
				}

				else if (name.equals(PERIODE_NR)) {
					String value = getUtvalgLongValue(utvalg.getPeriodeNr(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(IO_NR)) {
					String value = getUtvalgStringValue(utvalg.getIoNr(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				}

				else if (name.equals(INT_TYPE)) {
					String value = getUtvalgStringValue(utvalg.getIntType(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				}

				else if (name.equals(F_NUMMER)) {
					String value = getUtvalgStringValue(utvalg.getfNummer(),
							length, true, '0');
					row.replace(beginIndex, endIndex, value);

				} else if (name.equals(FAM_NR)) {
					String value = getUtvalgStringValue(utvalg.getFamNr(),
							length, true, '0');
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(GATE_GAARD)) {
					String value = getUtvalgStringValue(utvalg.getGateGaard(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUS_BRUK)) {
					String value = getUtvalgStringValue(utvalg.getHusBruk(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(BOKSTAV_FNR)) {
					String value = getUtvalgStringValue(utvalg.getBokstavFnr(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(UNDERNR)) {
					String value = getUtvalgStringValue(utvalg.getUndernr(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(NAVN)) {
					String value = getUtvalgStringValue(utvalg.getNavn(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(NAVN2)) {
					String value = getUtvalgStringValue(utvalg.getNavn2(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(KJONN)) {
					String value = getUtvalgStringValue(utvalg.getKjonn(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(KOMMUNE)) {
					String value = getUtvalgStringValue(utvalg.getKommune(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(UTVALGSOMR)) {
					String value = getUtvalgStringValue(utvalg.getUtvalgsomr(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(ADRESSE)) {
					String value = getUtvalgStringValue(utvalg.getAdresse(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(BOLIGNR)) {
					String value = getUtvalgStringValue(utvalg.getBolignr(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(POSTNR)) {
					String value = getUtvalgStringValue(utvalg.getPostnr(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(POSTSTED)) {
					String value = getUtvalgStringValue(utvalg.getPoststed(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(BREVADR)) {
					String value = getUtvalgStringValue(utvalg.getBrevadr(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(BREVPOSTNR)) {
					String value = getUtvalgStringValue(utvalg.getBrevpostnr(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(BREVPOSTST)) {
					String value = getUtvalgStringValue(utvalg.getBrevpostst(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(ALDER)) {
					String value = getUtvalgLongValue(utvalg.getAlder(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(SIVILSTAND)) {
					String value = getUtvalgStringValue(utvalg.getSivilstand(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(PERSKODE)) {
					String value = getUtvalgStringValue(utvalg.getPerskode(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(STATSBRG)) {
					String value = getUtvalgStringValue(utvalg.getStatsbrg(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(ER_IO)) {
					String value = getUtvalgStringValue(utvalg.getErIo(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(K_PERIODE)) {
					String value = getUtvalgStringValue(utvalg.getkPeriode(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				}

				else if (name.equals(DELUTVALG)) {
					String value = getUtvalgStringValue(utvalg.getDelutvalg(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(PREF_INT)) {
					String value = getUtvalgStringValue(utvalg.getPrefInt(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(REF_NAVN)) {
					String value = getUtvalgStringValue(utvalg.getRefNavn(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(RESU1)) {
					String value = getUtvalgStringValue(utvalg.getResu1(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(RESU2)) {
					String value = getUtvalgStringValue(utvalg.getResu2(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(RESU3)) {
					String value = getUtvalgStringValue(utvalg.getResu3(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(RESU4)) {
					String value = getUtvalgStringValue(utvalg.getResu4(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(RESU5)) {
					String value = getUtvalgStringValue(utvalg.getResu5(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(RESU6)) {
					String value = getUtvalgStringValue(utvalg.getResu6(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(RESU7)) {
					String value = getUtvalgStringValue(utvalg.getResu7(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD1)) {
					String value = getUtvalgStringValue(utvalg.getHushold1(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD1_FNR)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold1Fnr(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD1_PERSKODE)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold1Perskode(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD2)) {
					String value = getUtvalgStringValue(utvalg.getHushold2(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD2_FNR)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold2Fnr(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD2_PERSKODE)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold2Perskode(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD3)) {
					String value = getUtvalgStringValue(utvalg.getHushold3(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD3_FNR)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold3Fnr(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD3_PERSKODE)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold3Perskode(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD4)) {
					String value = getUtvalgStringValue(utvalg.getHushold4(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD4_FNR)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold4Fnr(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD4_PERSKODE)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold4Perskode(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD5)) {
					String value = getUtvalgStringValue(utvalg.getHushold5(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD5_FNR)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold5Fnr(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD5_PERSKODE)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold5Perskode(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD6)) {
					String value = getUtvalgStringValue(utvalg.getHushold6(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD6_FNR)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold6Fnr(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD6_PERSKODE)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold6Perskode(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD7)) {
					String value = getUtvalgStringValue(utvalg.getHushold7(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD7_FNR)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold7Fnr(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD7_PERSKODE)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold7Perskode(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD8)) {
					String value = getUtvalgStringValue(utvalg.getHushold8(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD8_FNR)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold8Fnr(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD8_PERSKODE)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold8Perskode(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD9)) {
					String value = getUtvalgStringValue(utvalg.getHushold9(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD9_FNR)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold9Fnr(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD9_PERSKODE)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold9Perskode(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD10)) {
					String value = getUtvalgStringValue(utvalg.getHushold10(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD10_FNR)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold10Fnr(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(HUSHOLD10_PERSKODE)) {
					String value = getUtvalgStringValue(
							utvalg.getHushold10Perskode(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(AVTALEDATO)) {
					String value = getUtvalgStringValue(utvalg.getAvtaledato(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(AVTALETYPE)) {
					String value = getUtvalgIntValue(utvalg.getAvtaletype(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(AVTALETID)) {
					String value = getUtvalgStringValue(utvalg.getAvtaletid(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(AVTALEKOMM)) {
					String value = getUtvalgStringValue(utvalg.getAvtalekomm(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(TELEFON1)) {
					String value = getUtvalgStringValue(utvalg.getTelefon1(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(KOMMENTAR1)) {
					String value = getUtvalgStringValue(utvalg.getKommentar1(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(KILDE1)) {
					String value = getUtvalgStringValue(utvalg.getKilde1(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(TELEFON2)) {
					String value = getUtvalgStringValue(utvalg.getTelefon2(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(KOMMENTAR2)) {
					String value = getUtvalgStringValue(utvalg.getKommentar2(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(KILDE2)) {
					String value = getUtvalgStringValue(utvalg.getKilde2(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(TELEFON3)) {
					String value = getUtvalgStringValue(utvalg.getTelefon3(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(KOMMENTAR3)) {
					String value = getUtvalgStringValue(utvalg.getKommentar3(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(KILDE3)) {
					String value = getUtvalgStringValue(utvalg.getKilde3(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(RESERVASJON)) {
					String value = getUtvalgStringValue(utvalg.getReservasjon(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(EPOSTADRESSE)) {
					String value = getUtvalgStringValue(
							utvalg.getEpostadresse(), length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(NACE)) {
					String value = getUtvalgStringValue(utvalg.getNace(),
							length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(ANTALL_ANSATTE)) {
					String value = getUtvalgLongValue(
							utvalg.getAntallAnsatte(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(MELDING)) {
					String value = getUtvalgStringValue(utvalg.getMelding(),
							length, false);
					value = value.replaceAll("\\r?\\n", " ");
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(OVERIO_NR)) {
					String value = getUtvalgStringValue(utvalg.getOverioNr(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(PASSORD_WEB)) {
					String value = getUtvalgStringValue(utvalg.getPassordWeb(),
							length, false);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(ROTASJON_GRP)) {
					String value = getUtvalgStringValue(
							utvalg.getRotasjonGrp(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(INTERVJUOBJEKT_ID)) {
					String value = getUtvalgLongValue(
							utvalg.getIntervjuObjektId(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(MAALFORM)) {
					String value = getUtvalgStringValue(
							utvalg.getMaalform(), length, true);
					row.replace(beginIndex, endIndex, value);
				} else if (name.equals(VARSLINGSSTATUS)) {
					String value = getUtvalgStringValue(
							utvalg.getVarslingsstatus(), length, true);
					row.replace(beginIndex, endIndex, value);
				}
			}

			row.append("\n");
			content.append(row);
		}
		return content.toString();
	}


	// all values are right-aligned
	private String getUtvalgStringValue(String variable, int length,
			boolean rightAlign) {
		String result = (variable == null || "null".equals(variable)) ? ""
				: variable;
		return StringUtil.padAlign(result, length, ' ', rightAlign);
	}


	// all values are right-aligned
	private String getUtvalgStringValue(String variable, int length,
			boolean rightAlign, char padChar) {
		String result = (variable == null || "null".equals(variable)) ? ""
				: variable;
		return StringUtil.padAlign(result, length, padChar, rightAlign);
	}


	private String getUtvalgLongValue(Long variable, int length,
			boolean rightAlign) {
		String result = variable == null ? "" : String.valueOf(variable);
		return StringUtil.padAlign(result, length, ' ', rightAlign);
	}


	private String getUtvalgIntValue(Integer variable, int length,
			boolean rightAlign) {
		String result = variable == null ? "" : String.valueOf(variable);
		return StringUtil.padAlign(result, length, ' ', rightAlign);
	}
	
}
