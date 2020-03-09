package view.applet;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author thf Contains method for unzipping a zip-file
 */
public class FileZipUtil {

	/**
	 * Unzips given file
	 * 
	 * @param filename
	 *            Path to zipfile
	 * @param localSkjemaPath
	 * @return boolean success or failure
	 */
	public static boolean unZip(String filename, String localSkjemaPath) {
		@SuppressWarnings("rawtypes")
		Enumeration entries;
		ZipFile zipFile;

		try {
			zipFile = new ZipFile(filename);
			entries = zipFile.entries();

			// extract directories
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();

				String destPath = localSkjemaPath + entry.getName();
				File fileToExtract = new File(destPath);

				if (entry.isDirectory() && !fileToExtract.exists()) {
					fileToExtract.mkdirs();
				} else if (!entry.isDirectory()) {
					if (!fileToExtract.getParentFile().exists()) {
						fileToExtract.getParentFile().mkdirs();
					}
					copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(
							new FileOutputStream(destPath)));
				}

			}

			zipFile.close();
			return true;
		} catch (IOException ioe) {
			System.err.println("Unhandled exception:");
			ioe.printStackTrace();
			return false;
		}
	}


	private static void copyInputStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		in.close();
		out.close();
	}

}
