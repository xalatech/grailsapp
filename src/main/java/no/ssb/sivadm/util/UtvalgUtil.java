package no.ssb.sivadm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import siv.type.Kjonn;
import no.ssb.sivadm.exception.SivAdmException;
import no.ssb.sivadm.parser.Utvalg;
import no.ssb.sivadm.parser.UtvalgParser;

public class UtvalgUtil {

	public static List<Utvalg> stringToList(String s) {
		UtvalgParser parser = new UtvalgParser();
		List<Utvalg> utvalg = parser.parse(s);
		return utvalg;
	}


	public static Date getFDatoFromFnr(String fnr) {
		Date fdato = null;

		if (fnr != null && !fnr.isEmpty()) {
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
			String fdm = fnr.substring(0, 4);
			String fy = fnr.substring(4, 6);
			int pnr = StringUtil.stringToInteger(fnr.substring(6, 9));

			try {
				if (pnr < 500) {
					fdato = formatter.parse(fdm + "19" + fy);

				} else if (Integer.parseInt(fy) > 54) {
					fdato = formatter.parse(fdm + "18" + fy);

				} else {
					fdato = formatter.parse(fdm + "20" + fy);
				}

			} catch (Exception e) {
				throw new SivAdmException(e);
			}
		}
		return fdato;
	}


	public static Boolean getTelefoneType(String telefon) {
		String type = telefon.substring(0, 1);
		if (type.equals("8") || type.equals("9")) {
			return true;
		} else {
			return false;
		}
	}


	public static Kjonn mapKjonn(String kjonn) {
		if (kjonn.equalsIgnoreCase("1")) {
			return Kjonn.MANN;
		} else if (kjonn.equalsIgnoreCase("2")) {
			return Kjonn.KVINNE;
		} else {
			return null;
		}
	}


	public static String mapKjonnReverse(Kjonn kjonn) {
		if (kjonn == null) {
			return "";
		} else if (kjonn.equals(Kjonn.MANN)) {
			return "1";
		} else if (kjonn.equals(Kjonn.KVINNE)) {
			return "2";
		} else {
			return "";
		}
	}

}
