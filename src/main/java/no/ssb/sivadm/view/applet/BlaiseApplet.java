package no.ssb.sivadm.view.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

/**
 * Applet for starting Blaise.
 */
public class BlaiseApplet extends Applet {

	private static final long serialVersionUID = -798058857286816020L;

	private String version = "1.2";

	// Applet input parameters
	private String blaiseApplicationPath = null;
	private String blaiseSkjemaPath = null;
	private String retningslinjerFilePath = null;
	private String skjemaVersjon = null;
	private String skjemaKortNavn = null;
	private String intervuObjektId = null;
	private String whatToDo = "BLAISE";

	// Internal parameters
	private StringBuffer message = new StringBuffer();
	private boolean runInDemo = false;

	// Internal constants
	private final static String BLAISE_WITH_SKJEMA = "BLAISE";
	private final static String BLAISE_TEST_WITH_SKJEMA = "BLAISE_TEST";
	private final static String BLAISE_WITH_SKJEMA_IO = "BLAISE_WITH_IO";
	private final static String BLAISE_WITH_SKJEMA_RETNINGSLINJER = "RETNINGSLINJER";


	/**
	 * Initialization method that will be called after the applet is loaded into
	 * the browser.
	 */
	public void init() {

		initializeFromInputParameters();

		if (isBlaiseValidationOk() == true) {
			if (whatToDo.equalsIgnoreCase(BLAISE_WITH_SKJEMA) == true) {
				startBlaiseWithSkjema();
			} else if (whatToDo.equalsIgnoreCase(BLAISE_TEST_WITH_SKJEMA) == true) {
				startBlaiseTestWithSkjema();
			} else if (whatToDo.equalsIgnoreCase(BLAISE_WITH_SKJEMA_RETNINGSLINJER) == true) {
				startBlaiseTreningWithSkjema();

				if (isRetningslinjerValidationOk() == true) {
					startWord();
				}
			} else if (whatToDo.equalsIgnoreCase(BLAISE_WITH_SKJEMA_IO) == true) {
				startBlaiseWithSkjemaAndIntervjuObject();
			}
		}
	}


	/**
	 * Applet paint method. Draws status messages and parameter info to screen.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 910, 150);
		g.setColor(Color.BLACK);
		g.drawString("V." + version + "Melding: " + message.toString(), 20, 20);
		g.drawString("Blaise: " + blaiseApplicationPath, 20, 50);
		// g.drawString("Skjema: " + getSchemaPath(), 20, 80);

		if (isGoingToStartBlaiseWithSkjemaAndIntervjuObjekt() == true) {
			g.drawString("Intervjuobjektnummer: " + intervuObjektId, 20, 110);
		}

		if (isGoingToStartBlaiseWithSkjemaAndRetningslinjer() == true) {
			g.drawString("Retningslinjer: " + retningslinjerFilePath, 20, 110);
		}
	}


	private void startBlaiseWithSkjema() {

		try {
			String applicationPath = this.blaiseApplicationPath;
			String skjemakortNavn = this.skjemaKortNavn;

			int nrVersjon = Integer.parseInt(this.skjemaVersjon);
			String versjon = this.getCorrectSkjemaVersjonPath(nrVersjon);

			String blaiseCommand = "cmd.exe /C start " + applicationPath + "\\util\\manipula "
					+ applicationPath + "\\faste\\Start_BDB_prod /Kskjema=" + applicationPath + "\\skjema\\"
					+ skjemakortNavn + "\\" + versjon + "\\produksjon\\" + skjemakortNavn + " /I"
					+ applicationPath + "\\skjema\\" + skjemakortNavn + "\\" + versjon + "\\Produksjon\\"
					+ skjemakortNavn + " /E" + applicationPath + "\\Koder\\";

			Runtime.getRuntime().exec(blaiseCommand, null, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void startBlaiseTestWithSkjema() {

		try {
			String applicationPath = this.blaiseApplicationPath;
			String skjemakortNavn = this.skjemaKortNavn;

			int nrVersjon = Integer.parseInt(this.skjemaVersjon);
			String versjon = this.getCorrectSkjemaVersjonPath(nrVersjon);

			String blaiseCommand = "cmd.exe /C start " + applicationPath + "\\util\\manipula "
					+ applicationPath + "\\faste\\Start_BDB /Kskjema=" + applicationPath + "\\skjema\\"
					+ skjemakortNavn + "\\" + versjon + "\\testing\\" + skjemakortNavn + " /I"
					+ applicationPath + "\\skjema\\" + skjemakortNavn + "\\" + versjon + "\\testing\\"
					+ skjemakortNavn + " /E" + applicationPath + "\\Koder\\";

			Runtime.getRuntime().exec(blaiseCommand, null, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void startBlaiseTreningWithSkjema() {

		try {
			String applicationPath = this.blaiseApplicationPath;
			String skjemakortNavn = this.skjemaKortNavn;

			int nrVersjon = Integer.parseInt(this.skjemaVersjon);
			String versjon = this.getCorrectSkjemaVersjonPath(nrVersjon);

			String blaiseCommand = "cmd.exe /C start " + applicationPath + "\\util\\manipula "
					+ applicationPath + "\\faste\\Start_BDB /Kskjema=" + applicationPath + "\\skjema\\"
					+ skjemakortNavn + "\\" + versjon + "\\trening\\" + skjemakortNavn + " /I"
					+ applicationPath + "\\skjema\\" + skjemakortNavn + "\\" + versjon + "\\trening\\"
					+ skjemakortNavn + " /E" + applicationPath + "\\Koder\\";

			Runtime.getRuntime().exec(blaiseCommand, null, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Starter Blaise med et IO som input i tillegg til skjema
	 */
	private void startBlaiseWithSkjemaAndIntervjuObject() {

		try {
			String applicationPath = this.blaiseApplicationPath;
			String skjemakortNavn = this.skjemaKortNavn;

			int nrVersjon = Integer.parseInt(this.skjemaVersjon);
			String versjon = this.getCorrectSkjemaVersjonPath(nrVersjon);

			String blaiseCommand = "cmd.exe /C start " + applicationPath + "\\util\\manipula "
					+ applicationPath + "\\faste\\Start_BDB_prod /Kskjema=" + applicationPath + "\\skjema\\"
					+ skjemakortNavn + "\\" + versjon + "\\produksjon\\" + skjemakortNavn + " /I"
					+ applicationPath + "\\skjema\\" + skjemakortNavn + "\\" + versjon + "\\produksjon\\"
					+ skjemakortNavn + " /E" + applicationPath + "\\Koder\\ /P" + this.intervuObjektId.trim();

			Runtime.getRuntime().exec(blaiseCommand, null, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Starter word.
	 */
	private void startWord() {

		try {
			String command = "cmd.exe /c " + retningslinjerFilePath;

			Runtime.getRuntime().exec(command);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Reads input parameters from web page and sets them into the applet
	 */
	private void initializeFromInputParameters() {
		blaiseApplicationPath = getParameter("blaiseApplicationPath");
		blaiseSkjemaPath = getParameter("blaiseSkjemaPath");
		skjemaKortNavn = getParameter("skjemaKortNavn");
		skjemaVersjon = getParameter("skjemaVersjon");
		retningslinjerFilePath = getParameter("retningslinjerFilePath");
		intervuObjektId = getParameter("intervjuObjektId");
		whatToDo = getParameter("whatToDo");

		if (runInDemo == true) {
			retningslinjerFilePath = "c:\\test.doc";
		}
	}


	/**
	 * 
	 * @param skjemaVersjon
	 * @return
	 */
	protected String getCorrectSkjemaVersjonPath(int skjemaVersjon) {
		String skjemaVersjonPath = null;

		if (skjemaVersjon < 10) {
			skjemaVersjonPath = "V0" + skjemaVersjon;
		} else {
			skjemaVersjonPath = "V" + skjemaVersjon;
		}

		return skjemaVersjonPath;
	}


	/**
	 * Performs a validation of Blaise parameters.
	 * 
	 * @return true if validation is ok
	 */
	private boolean isBlaiseValidationOk() {
		if (checkIfFileExists(blaiseApplicationPath) == false) {
			message.append("Finner ikke blaise applikasjon. Kontakt system admin. ");
			return false;
		} else {
			return true;
		}
	}


	/**
	 * Performs a validation of Retningslinjer parameters.
	 * 
	 * @return true if validation is ok
	 */
	private boolean isRetningslinjerValidationOk() {
		if ((checkIfFileExists(retningslinjerFilePath) == false)) {
			message.append("Finner ikke retningslinjer dokument. Kontakt system admin. ");
			return false;
		} else {
			message.append("Retningslinjer funnet.");
			return true;
		}
	}


	/**
	 * Check if start Blaise with IO configuration is set to true
	 * 
	 * @return true if start Blaise configuration
	 */
	private boolean isGoingToStartBlaiseWithSkjemaAndIntervjuObjekt() {
		if (whatToDo.equalsIgnoreCase(BLAISE_WITH_SKJEMA_IO)) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Check if start Blaise with Retningslinjer configuration is set to true
	 * 
	 * @return true if start Blaise configuration
	 */
	private boolean isGoingToStartBlaiseWithSkjemaAndRetningslinjer() {
		if (whatToDo.equalsIgnoreCase(BLAISE_WITH_SKJEMA_RETNINGSLINJER)) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Performs a check to see if a specified file exists.
	 * 
	 * @param path
	 *            file
	 * @return true if file exists
	 */
	private boolean checkIfFileExists(String path) {
		return new File(path).exists();
	}

}
