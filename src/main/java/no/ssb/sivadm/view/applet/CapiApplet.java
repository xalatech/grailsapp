package no.ssb.sivadm.view.applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class CapiApplet extends JApplet implements ActionListener {

	// private static String VERSION = "3.0.4";
	private static String VERSION = "3.0.5";

	public static String COMMA = ",";
	public static String DOUBLE_UNDERSCORE = "__";
	public static String NEWLINE = "\n";

	private static String ACTION_SYNC = "sync";
	private List<SkjemaVersjonBean> skjemaVersjonBeanList = null;
	private String blaiseSkjemaPath = "";
	private String blaiseApplicationPath = "";
	private String localSkjemaPath = "";
	private JTextArea textArea = null;
	JButton syncButton = null;	
	private List<CapiAppletSkjemaData> skjemaDataList = null;
	
	private String localSystemKommandoPath = null ;
	private String serverSystemKommandoPath = null ;
	private List<CapiAppletSystemKommandoData> systemKommandoDataList = null;
	
	private String intervjuerInitialer = null;
	
	private String serverSystemKommandoHttpPath = null ;
	
	private static String SYSTEM_KOMMANDO_BAT_FILE_NAME = "Start_sk.bat";
	private static String SYSTEM_KOMMANDO_SUKSESS_FILE_NAME = "Suksess.txt";
	
	
	
	/**
	 * Applet init metode. Laster inn applet properties og tegner opp GUI.
	 */
	public void init() {
		
		System.out.println("Versjon " + VERSION);
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					loadSkjemaListProperties();
					createGUI();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Kalles opp naar intervjuer klikke paa synkronisering.
	 * 
	 * Looper gjennom alle skjemaer som intervjueren er satt opp på: 1. Sjekker
	 * om lokal *.bmi fil eksisterer lokalt hos intverjuers pc 2. Hvis nei: -
	 * Last ned zip-fil fra server - Pakk ut paa lokal pc 2. Hvis ja: - Alt ok
	 * (trenger ikke gjore noe) 3.
	 */
	public void actionPerformed(ActionEvent e) {

		log("Applet Versjon: " + VERSION);
		syncButton.setEnabled(false);

		if (ACTION_SYNC.equals(e.getActionCommand())) {

			startSystemKommandoProsess();
			
			startCapiSynkProsess() ;
		}
	}


	private String createSystemKommandoMelding() {
		String msg1 = "En systemkommando er sendt deg og vil nå starte i et lite svart vindu.";
		String msg2 = "Synkronisering startes deretter hvis du har CAPI-IO.";
		String msg3 = "Klikk nå \"OK\" for å starte denne jobben!";
		return String.format(msg1 + "%n" + msg2 + "%n" + msg3);
	}

	private String createSynkroniseringsMelding() {
		String msg1 = "Klikk nå \"OK\" for å starte synkroniseringen og VENT deretter til du får melding om at synkroniseringen er ferdig!";
		return String.format(msg1);
	}


	/**
	 * Setter igang systemkommando prosessen hvis systemkommandoer finnes da.
	 */
	private void startSystemKommandoProsess() {

		// FINNES DET SYSTEMKOMMANDOER SOM SKAL KJØRES?

		// JA:
		// - SLETT LOKAL KATALOG
		// - KOPIER NED ZIP-FIL
		// - PAKK UT FILA
		// - START BAT-FILEN
		// - VENT ET GITT ANTALL SEKUNDER
		// - SE ETTER SUKSESSFIL
		// - GI BESKJED TILBAKE TIL SERVER OM:
		// - FEIL ELLER IKKE
		// - TIMESTAMP FOR KJØRING

		// GJENTA SAMME PROSESS HVIS DET ER FLERE SYSTEMKOMMANDOER SOM SKAL
		// UTFORES
		try {

			log("Sjekker om det er noen systemkommandoer som skal kjøres...");

			if (systemKommandoDataList != null) {
				
				// https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
				JOptionPane.showMessageDialog(null, createSystemKommandoMelding(), "Systemkommando", JOptionPane.WARNING_MESSAGE);

				for (CapiAppletSystemKommandoData systemKommando : systemKommandoDataList) {

					log("Fant systemkommandoer som må kjøres. Ikke naviger vekk fra denne siden før disse er ferdig utført. Starter kjøring...");
					log("SystemKommando : id " + systemKommando.id + " filnavn " + systemKommando.filnavn + " maksSekunder " + systemKommando.maksSekunder);
					
					// Slett lokal katalog
					log("Sletter filer i katalogen: " + localSystemKommandoPath);
					slettFilerIKatalog(localSystemKommandoPath);

					// Sjekk om fil eksisterer
					String systemKommandoZipFilePath = serverSystemKommandoPath + "\\" + systemKommando.filnavn;

					if (checkIfFileExists(systemKommandoZipFilePath)) {
						
						FileZipUtil.unZip(systemKommandoZipFilePath, localSystemKommandoPath + "\\");

						String command = "cmd.exe /c  start " + localSystemKommandoPath + "\\" + SYSTEM_KOMMANDO_BAT_FILE_NAME;
						
						log("Venter på at systemkommando skal bli ferdig...");

						String suksessFil = localSystemKommandoPath + "\\" + SYSTEM_KOMMANDO_SUKSESS_FILE_NAME;
						
						Runtime.getRuntime().exec(command);
						pollForSuksessFil(suksessFil, systemKommando.maksSekunder);
							
						boolean suksess = checkIfFileExists(suksessFil);
						registrerKjoringPaaServer(systemKommando.id, intervjuerInitialer, suksess);

					} else {
						log("Fant ikke fil: " + systemKommandoZipFilePath
								+ ". Ta kontakt med systemadministrator.");
					}
				}

				log("Systemkommandoer ferdig utført!");
			}

		} catch (Exception exc) {
			log("En feil har oppstått. Ta kontakt med systemadministrator. Feilmelding: " + exc.getMessage());
		}
	}
	
	
	private void pollForSuksessFil(String suksessFil, int maksSekunder) throws InterruptedException {
		
		File file = new File(suksessFil);
	    
	    int teller=0;
	    while (!file.exists() && teller < maksSekunder) { 
	    	teller++;
//	    	log ("teller : " + teller + ". Tid som gjenstår : " + (maksSekunder -  teller) + " sekunder. ");
	    	Thread.sleep(1000);
	    }
	    
	    if (teller==maksSekunder){
	    	log ("Systemkommando har oppnådd maks. antall sekunder: " + teller + ".");
	    }else {
	    	log ("Systemkommando er ferdig. Forbrukt tid: " + teller + " sekunder.");
	    }
	}

	
	/**
	 * Kobler opp mot serveren og registrerer en kjoring via http. 
	 * 
	 * @param suksess
	 */
	private void registrerKjoringPaaServer( String systemKommandoId, String intervjuerInitialer, boolean suksess ) {
		try {
			String params = lagParamsString( systemKommandoId, intervjuerInitialer, suksess);
			
			String url = serverSystemKommandoHttpPath + "?" + params ;  
			
			this.getStringData(url) ;
		}
		catch (Exception e) {
			System.out.println("Klarte ikke aa koble opp mot server. Feilmelding: " + e.getMessage());
		}
	}
	
	
	/**
	 * Oppretter en params string som skal sendes til serveren.
	 * 
	 * @param systemKommandoId
	 * @param initialer
	 * @param suksess
	 * @return params string
	 */
	private String lagParamsString(String systemKommandoId, String initialer, boolean suksess) {
		
		StringBuffer params = new StringBuffer();
		
		params.append("initialer=");
		params.append(initialer);
		params.append("&");
		
		params.append("systemKommandoId=");
		params.append(systemKommandoId);
		params.append("&");
		
		params.append("suksess=");
		params.append(suksess);
		
		return params.toString();
	}


	/**
	 * Setter igang synk prosessen for capi.
	 */
	private void startCapiSynkProsess() {
		
		log("Ser etter om CAPI-synkronisering skal kjøres...");
		
		if (!skjemaVersjonBeanList.isEmpty()) {
			
			JOptionPane.showMessageDialog(null, createSynkroniseringsMelding(), "CAPI synkronisering", JOptionPane.WARNING_MESSAGE);
			
			log("Fant ut at det er nødvendig. Starter CAPI-synkronisering...");

			skjemaDataList = new ArrayList<CapiAppletSkjemaData>();

			for (SkjemaVersjonBean skjemaVersjonBean : skjemaVersjonBeanList) {

				String skjemaKortNavn = skjemaVersjonBean.getSkjemaKortnavn();
				String skjemaVersjon = SkjemaVersjonUtil.getCorrectSkjemaVersjonPath(Integer
						.parseInt(skjemaVersjonBean.getVersjon()));

				textArea.append("Sjekker " + skjemaKortNavn + ", versjon " + skjemaVersjon + "..."
						+ NEWLINE);

				String skjemaFilePath = localSkjemaPath + "\\" + skjemaKortNavn + "\\" + skjemaVersjon
						+ "\\Produksjon\\" + skjemaKortNavn + ".bmi";

				boolean skjemaFileExistLocally = SkjemaVersjonUtil.checkIfFileExists(skjemaFilePath);

				if (!skjemaFileExistLocally) {
					textArea.append("Fant ikke skjema for " + skjemaKortNavn + " lokalt på "
							+ localSkjemaPath + NEWLINE);
					textArea.append("Henter da fra " + blaiseSkjemaPath + NEWLINE);

					// få reell path til zip-fil paa server
					String serverSkjemaFilePath = blaiseSkjemaPath + "\\" + skjemaKortNavn + "\\"
							+ skjemaVersjon + "\\zip\\" + skjemaKortNavn + ".zip";
					boolean skjemaFileExistBlaise = SkjemaVersjonUtil
							.checkIfFileExists(serverSkjemaFilePath);

					if (!skjemaFileExistBlaise) {
						textArea.append("Fant ikke filen på Blaise server, kontakt administrator "
								+ NEWLINE);
					} else {
						textArea.append("Pakker ut " + skjemaKortNavn + ".zip" + NEWLINE);
						// lagre unzippa fil lokalt
						String unzippedFilesLocation = localSkjemaPath + "\\" + skjemaKortNavn + "\\"
								+ skjemaVersjon + "\\" + "Produksjon\\";
						boolean fileUnZipped = FileZipUtil.unZip(serverSkjemaFilePath,
								unzippedFilesLocation);
						textArea.append("Unzip success: " + fileUnZipped + NEWLINE);
					}

				} else {
					textArea.append("Skjema for " + skjemaKortNavn + " ble funnet lokalt" + NEWLINE);
				}

				CapiAppletSkjemaData skjemaData = new CapiAppletSkjemaData(skjemaKortNavn, skjemaVersjon);
				skjemaDataList.add(skjemaData);

				textArea.append("---\n");
			}

			textArea.append("Starter synk.exe script for aa synkronisere data...");

			startScriptSync(skjemaDataList);

		} else {
			String msg = "Ingen skjema er tilordnet pålogget intervjuer";
			textArea.append(msg + "\n");
			JOptionPane.showMessageDialog(null, msg, "CAPI synkronisering", JOptionPane.INFORMATION_MESSAGE);
		}
	}


	/**
	 * Starter opp synk.exe script som vil kopiere intervjuobjekter ned til
	 * intervjuers lokale pc, i tillegg til aa synkronisere resultater inn til
	 * serveren.
	 * 
	 * Starter script paa denne maaten:
	 * 
	 * synk.exe piaac2011#v01 aku2011#v02 reisfer11k4#v01
	 * 
	 * @param skjemaDataList
	 */
	private void startScriptSync(List<CapiAppletSkjemaData> skjemaDataList) {

		try {
			StringBuffer synkExeParams = new StringBuffer();

			for (CapiAppletSkjemaData skjemaData : skjemaDataList) {
				synkExeParams.append(skjemaData.skjemaKortNavn);
				synkExeParams.append("#");
				synkExeParams.append(skjemaData.skjemaVersjon);
				synkExeParams.append(" ");
			}

			String syncScriptPath = "" + blaiseApplicationPath + "\\UTIL\\synk.exe "
					+ synkExeParams.toString();
			String command = "cmd.exe /c  " + syncScriptPath;

			textArea.append("Starter synk script paa denne maaten: ");
			textArea.append(command);

			Runtime.getRuntime().exec(command);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Tegner opp GUI i applet.
	 */
	private void createGUI() {

		syncButton = new JButton("Synkroniser skjema og data");
		syncButton.setActionCommand(ACTION_SYNC);
		syncButton.addActionListener(this);

		textArea = new JTextArea();
		textArea.setColumns(20);
		textArea.setLineWrap(true);
		textArea.setRows(5);
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea);
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.SequentialGroup h1 = layout.createSequentialGroup();
		GroupLayout.ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
		h2.addComponent(scrollPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212,
				Short.MAX_VALUE);
		h2.addComponent(syncButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212,
				Short.MAX_VALUE);

		h1.addContainerGap();
		h1.addGroup(h2);
		h1.addContainerGap();
		hGroup.addGroup(GroupLayout.Alignment.TRAILING, h1);
		layout.setHorizontalGroup(hGroup);

		GroupLayout.ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.SequentialGroup v1 = layout.createSequentialGroup();
		v1.addContainerGap();
		v1.addComponent(syncButton);
		v1.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
		v1.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
		v1.addContainerGap();
		vGroup.addGroup(v1);
		layout.setVerticalGroup(vGroup);
	}


	/**
	 * Laster inn applet properties fra web siden.
	 */
	private void loadSkjemaListProperties() {
		
		String intervjuerCapiSkjemaVersionList = getParameter("intervjuerCapiSkjemaVersionList");
		blaiseSkjemaPath = getParameter("blaiseSkjemaPath");
		blaiseApplicationPath = getParameter("blaiseApplicationPath");
		localSkjemaPath = getParameter("localSkjemaPath");
		
		localSystemKommandoPath = getParameter("localSystemKommandoPath");
		serverSystemKommandoPath = getParameter("serverSystemKommandoPath");
		intervjuerInitialer = getParameter("intervjuerInitialer");
		serverSystemKommandoHttpPath = getParameter("serverSystemKommandoHttpPath");
		String systemKommandoString = getParameter("systemKommandoList");

		skjemaVersjonBeanList = new ArrayList<SkjemaVersjonBean>();
		
		// parse capi skjemaer
		if (intervjuerCapiSkjemaVersionList != null && intervjuerCapiSkjemaVersionList.length() > 0) {
			String[] skjemaList = splitStringIntoStringArray(intervjuerCapiSkjemaVersionList, COMMA);

			for (String skjema : skjemaList) {
				String[] skjemaDetaljArray = splitStringIntoStringArray(skjema, DOUBLE_UNDERSCORE);
				SkjemaVersjonBean bean = new SkjemaVersjonBean();
				bean.setSkjemaKortnavn(skjemaDetaljArray[0]);
				bean.setVersjon(skjemaDetaljArray[1]);
				skjemaVersjonBeanList.add(bean);
			}
		}
		
		// parse systemkommandoer
		if( systemKommandoString != null && systemKommandoString.length() > 0) {
			systemKommandoDataList = new ArrayList<CapiAppletSystemKommandoData>();
			
			String[] systemKommandoer = splitStringIntoStringArray(systemKommandoString, COMMA);
			
			for (String systemKommando : systemKommandoer) {
				String[] systemKommandoDetaljArray = splitStringIntoStringArray(systemKommando, DOUBLE_UNDERSCORE);
				
				CapiAppletSystemKommandoData systemKommandoData = new CapiAppletSystemKommandoData();
				
				systemKommandoData.id = systemKommandoDetaljArray[0];
				systemKommandoData.filnavn = systemKommandoDetaljArray[1];
				systemKommandoData.maksSekunder = Integer.parseInt(systemKommandoDetaljArray[2]);
				
				systemKommandoDataList.add(systemKommandoData);
			}
		}
	}

	/**
	 * Sjekker om en fil finnes
	 * 
	 * @param path
	 * @return
	 */
	private boolean checkIfFileExists(String path) {
		return new File(path).exists();
	}

	/**
	 * Splits a string into an array of string
	 * 
	 * @param aString
	 *            String to be splitted
	 * @param splitter
	 *            Char used for split
	 * @return Array of strings
	 */
	@SuppressWarnings("null")
	private static String[] splitStringIntoStringArray(String aString, String splitter) {
		String[] splitArray = null;
		if (aString != null || !aString.equalsIgnoreCase("")) {
			splitArray = aString.split("\\" + splitter);
		}
		return splitArray;
	}
	
	
	
	/**
	 * Utforer en get request mot URL og henter ned response som en String.
	 * 
	 * @param endpoint
	 * @return
	 * @throws Exception
	 */
	private String getStringData(String path) throws Exception {
		
		URL endpoint = new URL(path) ;
		
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
	 * Sletter alle filer i gitt katalog (path)
	 * @param path full sti til katalog som skal slettes
	 */
	private void slettFilerIKatalog( String path ) throws Exception {
		
		File katalog = new File(path) ;
		
		File[] filerIKatalog = katalog.listFiles();
		
		if( filerIKatalog != null ) {
			for (File file : filerIKatalog) {
				
				if( file.delete() == false ) {
					log("Klarte ikke slette fil: " + file.getAbsolutePath() + ". Prøv å slette denne manuelt og kjør synkronisering på nytt, eller ta kontakt med systemadministrator.");
					throw new Exception("Klarte ikke slette fil");
				}
			}	
		}
		
		
	}
	
	
	
	/**
	 * Logge metode som sender tekst til vinduet i appleten. 
	 * 
	 * @param message
	 */
	private void log(String message) {
		textArea.append(message);
		textArea.append("\n\n");
	}

}


class CapiAppletSkjemaData {
	String skjemaKortNavn;
	String skjemaVersjon;

	public CapiAppletSkjemaData(String skjemaKortNavn, String skjemaVersjon) {
		super();
		this.skjemaKortNavn = skjemaKortNavn;
		this.skjemaVersjon = skjemaVersjon;
	}
}


class CapiAppletSystemKommandoData {
	String id;
	String filnavn;
	int maksSekunder;
	
}
