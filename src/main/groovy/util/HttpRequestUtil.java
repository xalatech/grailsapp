package util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class HttpRequestUtil {

	/**
	 * Utforer en get request mot URL og henter ned response som en String.
	 * 
	 * @param endpoint
	 * @return
	 * @throws Exception
	 */
	public static String getStringData(URL endpoint) throws Exception {
		URLConnection urlConnection = endpoint.openConnection();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				urlConnection.getInputStream()));

		StringBuffer buffer = new StringBuffer();
		String inputLine;

		while ((inputLine = reader.readLine()) != null) {
			buffer.append(inputLine);
		}

		reader.close();

		return buffer.toString();
	}


	/**
	 * Utforer en post-request mot URL og med gitt string data som input.
	 * 
	 * @param data
	 * @param endpoint
	 * @return
	 * @throws Exception
	 */
	public static String postStringData(String data, URL endpoint)
			throws Exception {
		InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
		InputStreamReader isr = new InputStreamReader(is);
		StringWriter sw = new StringWriter();
		postData(isr, endpoint, sw);
		return sw.toString();
	}


	/**
	 * Reads data from the data reader and posts it to a server via POST
	 * request. data - The data you want to send endpoint - The server's address
	 * output - writes the server's response to output
	 * 
	 * @throws Exception
	 */
	private static void postData(Reader data, URL endpoint, Writer output)
			throws Exception {
		HttpURLConnection urlc = null;
		try {
			urlc = (HttpURLConnection) endpoint.openConnection();
			try {
				urlc.setRequestMethod("POST");
			} catch (ProtocolException e) {
				throw new Exception(
						"Shouldn't happen: HttpURLConnection doesn't support POST??",
						e);
			}
			urlc.setDoOutput(true);
			urlc.setDoInput(true);
			urlc.setUseCaches(false);
			urlc.setAllowUserInteraction(false);
			urlc.setRequestProperty("Content-type", "text/xml; charset=UTF-8");

			OutputStream out = urlc.getOutputStream();
			try {
				Writer writer = new OutputStreamWriter(out, "UTF-8");
				pipe(data, writer);
				writer.close();
			} catch (IOException e) {
				throw new Exception("IOException while posting data", e);
			} finally {
				if (out != null)
					out.close();
			}

			InputStream in = urlc.getInputStream();
			try {
				Reader reader = new InputStreamReader(in);
				pipe(reader, output);
				reader.close();
			} catch (IOException e) {
				throw new Exception("IOException while reading response", e);
			} finally {
				if (in != null)
					in.close();
			}
		} catch (IOException e) {
			throw new Exception("Connection error (is server running at "
					+ endpoint + " ?): " + e);
		} finally {
			if (urlc != null)
				urlc.disconnect();
		}
	}


	/**
	 * Pipes everything from the reader to the writer via a buffer
	 */
	private static void pipe(Reader reader, Writer writer) throws IOException {
		char[] buf = new char[1024];
		int read = 0;
		while ((read = reader.read(buf)) >= 0) {
			writer.write(buf, 0, read);
		}
		writer.flush();
	}
}