package view.applet;

import java.io.File;

/**
 * Common methods for verifying location and version of schemas
 */
public class SkjemaVersjonUtil {

	public static boolean checkIfFileExists(String path) {
		return new File(path).exists();
	}


	public static boolean checkIfDirectoryContainsFiles(String path) {
		File file = new File(path);

		if (file.isDirectory()) {
			String[] files = file.list();
			if (files.length > 0) {
				return true;
			}
		}
		return false;
	}


	public static String getCorrectSkjemaVersjonPath(int skjemaVersjon) {
		String skjemaVersjonPath = null;

		if (skjemaVersjon < 10) {
			skjemaVersjonPath = "V0" + skjemaVersjon;
		} else {
			skjemaVersjonPath = "V" + skjemaVersjon;
		}

		return skjemaVersjonPath;
	}

}
