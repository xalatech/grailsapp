package no.ssb.sivadm.view.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import no.ssb.sivadm.view.applet.SkjemaVersjonUtil;

@SuppressWarnings("serial")
public class FileCheckApplet extends Applet {

	private String blaiseSkjemaPath = null;
	private String skjemaVersjon = null;
	private String skjemaKortNavn = null;
	private boolean skjemaFileExist = false;


	/**
	 * Initialization method that will be called after the applet is loaded into
	 * the browser.
	 */
	public void init() {
		
		System.out.println("Versjon 1.8");
		
		blaiseSkjemaPath = this.getParameter("blaiseSkjemaPath");
		skjemaVersjon = this.getParameter("skjemaVersjon");
		skjemaKortNavn = this.getParameter("skjemaKortNavn");
		String checkType = this.getParameter("checkType");
		
		System.out.println( blaiseSkjemaPath + "__" + skjemaVersjon + "__" + skjemaKortNavn + "__" + checkType);

		if (checkType.equalsIgnoreCase("CAPI")) {
			checkIfCAPIFileExists();
		} else {
			// default CATI
			System.out.println("sjekker... cati");
			checkIfCATIFileExists();
		}

	}


	private void checkIfCATIFileExists() {

		
		
		if (skjemaVersjon == null || skjemaVersjon.trim().equals("")) {
			skjemaFileExist = false;
		} else {
			int v = Integer.parseInt(skjemaVersjon);
			String sFilePath = blaiseSkjemaPath + "\\" + skjemaKortNavn + "\\"
					+ SkjemaVersjonUtil.getCorrectSkjemaVersjonPath(v) + "\\Produksjon\\" + skjemaKortNavn
					+ ".bdb";
			
			System.out.println(sFilePath);
			
			skjemaFileExist = SkjemaVersjonUtil.checkIfFileExists(sFilePath);
		}
	}


	private void checkIfCAPIFileExists() {
		if (skjemaVersjon == null || skjemaVersjon.trim().equals("")) {
			skjemaFileExist = false;
		} else {
			int v = Integer.parseInt(skjemaVersjon);
			String directoryPath = blaiseSkjemaPath + "\\" + skjemaKortNavn + "\\"
					+ SkjemaVersjonUtil.getCorrectSkjemaVersjonPath(v) + "\\Produksjon\\";
			skjemaFileExist = SkjemaVersjonUtil.checkIfDirectoryContainsFiles(directoryPath);
		}
	}


	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (this.skjemaFileExist) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.RED);
		}

		g.fillRect(0, 0, 200, 200);
	}
}