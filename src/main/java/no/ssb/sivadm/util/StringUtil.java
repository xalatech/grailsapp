package no.ssb.sivadm.util;

import no.ssb.sivadm.exception.SivAdmException;

import java.util.Arrays;

public class StringUtil {

	public static String EMPTY_STRING = "";
	public static String COMMA = ",";


	private StringUtil() {
	}


	public static Long stringToLong(String s) {
		Long retVal = null;
		try {
			if (s != null && !s.equals("")) {
				retVal = Long.parseLong(s);
			}
		} catch (NumberFormatException e) {
			throw new SivAdmException(e);
		}
		return retVal;
	}


	public static Integer stringToInteger(String s) {
		Integer retVal = null;
		try {
			if (s != null && !s.equals("")) {
				retVal = Integer.parseInt(s);
			}
		} catch (NumberFormatException e) {
			throw new SivAdmException(e);
		}
		return retVal;
	}


	public static String padAlign(String string, int fieldLength, char padding, boolean alignRight) {
		int length = fieldLength - string.length();

		if (length <= 0)
			return string.substring(0, string.length());

		StringBuffer buffer = new StringBuffer(fieldLength);

		for (int i = 0; i < length; i++)
			buffer.append(padding);

		if (alignRight)
			buffer.append(string);
		else
			buffer.insert(0, string);

		return new String(buffer);
	}


	/**
	 * Using stringBuffer.setLength() to ensure a given length to each row fills
	 * up the row with \u0000 Using this method to ensure correct row length.
	 * 
	 * @param spaceCount
	 * @return
	 */
	public static String createSpaces(int spaceCount) {
		char[] spaces = new char[spaceCount];
		Arrays.fill(spaces, ' ');
		return new String(spaces);
	}


	/**
	 * Removes the last comma in a string containing comma-separated values
	 * 
	 * @param list
	 *            String with comma-separated values
	 * @return String with comma-separated values, no comma at the end
	 */
	public static String trimCommaSeparatedString(String list) {
		if (!list.isEmpty()) {
			return list.substring(0, list.lastIndexOf(COMMA));
		} else {
			return "";
		}
	}


	/**
	 * Metode for aa returnere kun de forste 250 tegn av en string. Brukes for
	 * aa hindre for store inserts i databasen.
	 * 
	 * @param value
	 * @return
	 */
	public static String get255FirstChars(String value) {
		if (value != null && value.length() > 254) {
			return value.substring(0, 250);
		} else {
			return value;
		}
	}

}