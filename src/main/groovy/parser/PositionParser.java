package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import exception.SivAdmException;

public abstract class PositionParser<T> {

	protected static final int NR_OF_CHARS_PER_LINE_IMPORT = 1673;
	protected static final int NR_OF_CHARS_PER_LINE_EXPORT = 1673;

	private static Pattern EMPTY_LINE = Pattern.compile("[\n\r ]*");

	/**
	 * Parse string based on position selection map.
	 * 
	 * @param src
	 * @param selectionMap
	 * @return list
	 */
	public List<T> parse(String src, Map<String, Position> selectionMap) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String[] lines = src.split("\n");

		int lineCount = 0;

		try {

			for (String line : lines) {

				lineCount++;

				if (!EMPTY_LINE.matcher(line).matches()
						&& line.length() >= NR_OF_CHARS_PER_LINE_IMPORT) {
					Map<String, String> map = new HashMap<String, String>();
					for (Map.Entry<String, Position> e : selectionMap.entrySet()) {
						String key = e.getKey();
						Position p = e.getValue();
						int beginIndex = p.getStart() - 1;
						int endIndex = beginIndex + p.getLength();
						if (!(beginIndex >= NR_OF_CHARS_PER_LINE_IMPORT)) {

							String value = null;

							try {
								value = line.substring(beginIndex, endIndex)
										.trim();
							} catch (Exception ex) {
								value = null;
							}

							map.put(key, value);
						}
					}
					list.add(map);
				} else {
					throw new SivAdmException("Linje " + lineCount
							+ " inneholder ikke nok tegn ("
							+ NR_OF_CHARS_PER_LINE_EXPORT
							+ ") i henhold til utvalgsspesifikasjon.");
				}
			}

		} catch (Exception e) {
			throw new SivAdmException("Linje " + lineCount
					+ " forte til en feil under innlesning. Feilmelding: "
					+ e.getMessage());
		}
		return mapToResultList(list);
	}


	/**
	 * Maps result to list.
	 * 
	 * @param list
	 * @return list
	 */
	protected abstract List<T> mapToResultList(List<Map<String, String>> list);
}
