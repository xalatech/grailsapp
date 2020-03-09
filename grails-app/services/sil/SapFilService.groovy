package sil

import sivadm.Timeforing

import java.text.SimpleDateFormat

import exception.FinnerIkkeProduktNummerException;

import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPConnectionClosedException
import org.apache.commons.net.io.CopyStreamException

import sil.type.FilType
import sil.type.KravStatus
import sil.type.KravType
import sil.type.MarkedType
import sil.type.SapFilStatusType
import siv.type.ProsjektFinansiering
import siv.type.TransportMiddel
import siv.type.UtleggType
import sivadm.Intervjuer
import sivadm.Produkt
import sivadm.Prosjekt
import sivadm.Skjema

class SapFilService {
	
	def grailsApplication

	private final static String LONNART_OVERTID = "3116"
	private final static String LONNART_EGEN_BIL = "5711"
	private final static String LONNART_EGEN_BIL_TROMSO = "5718"
	private final static String LONNART_PASSASJERTILLEGG = "5712"
	private final static String KOMMUNENUMMER_TROMSO = "1902"

	private final static String LONNARTNUMMER_LOKALT_INTERVJU = "3110"
	private final static String LONNARTNUMMER_LOKALT_INTERVJU_ETTER_TIDSPUNKT = "3192"
	private final static String LONNARTNUMMER_SENTRALT_INTERVJU = "3112"
	private final static String LONNARTNUMMER_SENTRALT_INTERVJU_ETTER_TIDSPUNKT = "3196"

	private final static int TIDSPUNKT_LONNARTSKIFTE_HOUR_OF_DAY = 16
	private final static int TIDSPUNKT_LONNARTSKIFTE_MINUTE = 30
	private final static int TIDSPUNKT_LONNARTSKIFTE_SECOND = 0

	def utilService
	def brukerService

	def skrivGodkjenteKravTilKostGodtgjorelseRapport() throws FinnerIkkeProduktNummerException {
		
		def c = Krav.createCriteria()

		def kravListeKostGodtgjorelse = c.listDistinct {
			eq("kravStatus", KravStatus.GODKJENT)
			
			utlegg {
				eq( "utleggType", UtleggType.KOST_GODT )
			}
		}
		
		genererKostGodtgjorelseRapport(kravListeKostGodtgjorelse)
		
		return kravListeKostGodtgjorelse
	}


	def genererKostGodtgjorelseRapport(List<Krav> kravListe) throws FinnerIkkeProduktNummerException {
		kravListe.each { Krav krav ->
			KostGodtgjorelse kost = new KostGodtgjorelse()
			kost.behandlet = false
			kost.initialer = krav.intervjuer.initialer
			kost.intervjuerNummer = krav.intervjuer.intervjuerNummer
			kost.klynge = krav.intervjuer.klynge.klyngeNavn
			kost.kostGodtDato = krav.utlegg.dato
			kost.navn = krav.intervjuer.navn
			kost.godkjent = new Date()
			kost.godkjentAv = brukerService.getCurrentUserName()
			kost.produktNummer = krav.produktNummer
			kost.utleggType = krav.utlegg.utleggType.getGuiName()
			kost.fraTid = krav.utlegg.kostFraTid
			kost.tilTid = krav.utlegg.kostTilTid
			kost.tilSted = krav.utlegg.kostTilSted

			Skjema skjema = Skjema.findByDelProduktNummer(krav.produktNummer)
			
			if(skjema == null ) {
				throw new FinnerIkkeProduktNummerException(krav.produktNummer, "Kan ikke opprette kostgodtgjorelsesrapport fordi skjema med produktnummer " + krav.produktNummer + " mangler.")
			}
			
			kost.finansiering = skjema.prosjekt.finansiering.guiName

			kost.save(failOnError: true)

			krav.kravStatus = KravStatus.OVERSENDT_SAP
			krav.save(failOnError: true)
		}
	}


	def skrivGodkjenteKravTilSapFiler() throws FinnerIkkeProduktNummerException {
		List<Krav> kravListeTime = Krav.findAllByKravStatusAndKravType(KravStatus.GODKJENT, KravType.T)
		log.info("Fant " + kravListeTime.size() + " krav til SAP file TIME")

		List<Krav> kravListeReise = Krav.findAllByTimeforingIsNullAndKravStatus(KravStatus.GODKJENT)
		log.info("Fant " + kravListeReise.size() + " krav til SAP file REISE")

		if(!kravListeTime && ! kravListeReise) {
			log.debug("Ingen time- og ingen reise/utlegg-krav å skrive til SAP filer, ingen filer skrevet")
			return null
		}

		def sapFilListe = []

		if(!kravListeTime) {
			log.debug("Ingen time-krav å skrive til SAP fil, ingen time fil skrevet")
		}
		else {
			sapFilListe << genererSapFil(kravListeTime, true)
		}

		if(!kravListeReise) {
			log.debug("Ingen reise/utlegg-krav å skrive til SAP fil, ingen reise fil skrevet")
		}
		else {
			sapFilListe << genererSapFil(kravListeReise, false)
		}

		return sapFilListe
	}


	SapFil genererSapFil(List<Krav> kravListe, boolean erTime) throws FinnerIkkeProduktNummerException {
		if(!kravListe) {
			return null
		}

		StringWriter linjer = new StringWriter()
		int antLinjer = kravListe.size()
		kravListe.each { krav ->

			boolean kravErKostGodtGjorelse = erKostGodtgjorelse(krav)

			/*
			 Hvis kravet gjelder en kjørebok som ikke er km-basert
			 skal denne ignoreres da tilhørende krav for utlegg/time
			 dekker disse.
			 */
			if(!erTime && krav.kravType == KravType.K && !krav.kjorebok?.erKm()) {
				// Dummy -> ikke gjør noe utover å trekke fra dette kravet fra antall linjer (siden kravet ikke skal med i fila)
				antLinjer --
			}
			/*
			 Hvis kravet gjelder et utlegg av typen kostgodtgjørelse skal denne ignoreres da dette blir eksportert 
			 til egen fil i en annen funksjon (enn saa lenge). 
			 */
			else if(kravErKostGodtGjorelse) {
				antLinjer --
			}
			else {
				linjer.append("\n")
				
				String s = genererKravLinje(krav)
				
				if(s) {
					antLinjer = antLinjer + s.count("\n")
					linjer.append(s)
				}
			}
		}

		String forsteLinje = genererForsteLinjeSapFil(antLinjer, (erTime ? FilType.TIME: FilType.REISE))
		String fileName = genererFilnavnSapFil( (erTime ? FilType.TIME: FilType.REISE) )

		SapFil fil = new SapFil()
		fil.setFilType (erTime ? FilType.TIME: FilType.REISE)
		fil.setAntallKrav kravListe.size()
		fil.setAntallLinjer antLinjer

		// SAP filene skal lagres lokalt
		if(grailsApplication.config.sil.sap.fil.lagre.lokalt) {
			OutputStreamWriter output = null
			try {
				if(!grailsApplication.config.sil.sap.fil.lokal.katalog.endsWith("\\") && !grailsApplication.config.sil.sap.fil.lokal.katalog.endsWith("/")) {
					if(grailsApplication.config.sil.sap.fil.lokal.katalog.startsWith("/")) {
						fileName = "/" + fileName
					}
					else {
						fileName = "\\" + fileName
					}
				}
				OutputStream fout= new FileOutputStream((grailsApplication.config.sil.sap.fil.lokal.katalog + fileName));
				OutputStream bout= new BufferedOutputStream(fout);
				output = new OutputStreamWriter(bout, grailsApplication.config.sil.sap.fil.encoding);
				output.write(forsteLinje);
				output.write(linjer.toString());
				output.flush();  // Don't forget to flush!
				log.info("Har skrevet fil '" + fileName + "'")
			}
			catch (UnsupportedEncodingException e) {
				log.error("This VM does not support the UTF8 character set.");
				fil.setStatus SapFilStatusType.FEILET
				fil.setStatusmelding e.getMessage()
			}
			catch (IOException e) {
				log.error("IOException: " + e.getMessage());
				fil.setStatus SapFilStatusType.FEILET
				fil.setStatusmelding e.getMessage()
			}
			finally {
				if(output) {
					output.close()
				}
			}
		}
		else {
			// SAP filene skal flyttes til remote server via FTP
			def ftpClient = new FTPClient()

			try {
				ftpClient.connect(grailsApplication.config.sil.sap.fil.ftp.host, grailsApplication.config.sil.sap.fil.ftp.port)
				boolean isLoggedIn = ftpClient.login(grailsApplication.config.sil.sap.fil.ftp.bruker, grailsApplication.config.sil.sap.fil.ftp.passord)

				if(isLoggedIn) {
					if(grailsApplication.config.sil.sap.fil.ftp.katalog && !grailsApplication.config.sil.sap.fil.ftp.katalog.equals("")) {
						ftpClient.changeWorkingDirectory(grailsApplication.config.sil.sap.fil.ftp.katalog)
					}

					String str = forsteLinje + linjer.toString()
					ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes())
					boolean fileStored = ftpClient.storeFile(fileName, bais)
					ftpClient.disconnect()
					log.info("Har skrevet fil '" + fileName + "' til " + grailsApplication.config.sil.sap.fil.ftp.host)
				}
				else {
					// KUNNE IKKE LOGGE PÅ FTP
					String str = "Kunne ikke logge på ftp " + grailsApplication.config.sil.sap.fil.ftp.host + 
								 ":" + grailsApplication.config.sil.sap.fil.ftp.port + 
								 ", med bruker " + grailsApplication.config.sil.sap.fil.ftp.bruker
					log.error(str)
					fil.setStatus SapFilStatusType.FEILET
					fil.setStatusmelding str
				}
			}
			catch(SocketException se) {
				log.error("SocketException: " + se.getMessage());
				fil.setStatus SapFilStatusType.FEILET
				fil.setStatusmelding("SocketException: " + se.getMessage())
			}
			catch(FTPConnectionClosedException fcce) {
				log.error("FTPConnectionClosedException: " + fcce.getMessage());
				fil.setStatus SapFilStatusType.FEILET
				fil.setStatusmelding("FTPConnectionClosedException: " + fcce.getMessage())
			}
			catch(CopyStreamException cse) {
				log.error("CopyStreamException: " + cse.getMessage());
				fil.setStatus SapFilStatusType.FEILET
				fil.setStatusmelding("CopyStreamException: " + cse.getMessage())
			}
			catch(IOException ioe) {
				log.error("CopyStreamException: " + ioe.getMessage());
				fil.setStatus SapFilStatusType.FEILET
				fil.setStatusmelding("CopyStreamException: " + ioe.getMessage())
			}
		}

		kravListe.each { krav ->
			if(!fil.status) {
				krav.setKravStatus KravStatus.OVERSENDT_SAP
				krav.save(failOnError: true)
			}
			fil.addToKravListe(krav)
		}

		if(!fil.status) {
			fil.setStatus SapFilStatusType.OK
			fil.setFil fileName
		}

		fil.save(failOnError: true)

		return fil
	}

    def ryddSapFil() {
        String antallAar = grailsApplication.config.behold.fravaer.antall.aar
        log.info("Kjører ryddSapFil, sletter filer eldre enn " + (antallAar - 1) + " og et halvt år")

        Calendar cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, (-1 * Integer.parseInt(antallAar)))
        //Legger på så SAP-filer som er seks måneder nyere enn kravene som skal slettes, for SAP-filer
        // refererer til krav som er eldre.
        cal.add(Calendar.MONTH, 6)

        slettSapFilEldreEnn(cal.getTime())
    }

    def slettSapFilEldreEnn (Date dato) {

        def sapFilesToDelete = SapFil.findAllByDatoLessThan(dato)
        def antall = 0

        sapFilesToDelete.each { SapFil sapFil ->
            try {
                sapFil.delete(flush: true)
                antall++
            } catch (Exception ex) {
                log.error('Kunne ikke slette sapFil med id: ' + sapFil.id + ', ' + ex.getMessage())
            }
        }
        log.info('Har slettet ' + antall + ' sapfiler med dato eldre enn ' + dato)

    }

	/**
	 * En metode for å generere en eller flere linjer til SAP filene
	 * (time og reise). I de fleste tilfeller blir det en linje pr. krav,
	 * men i tilfeller med f.eks. overtid, fordeling stat/marked eller
	 * passasjertillegg kan det bli flere linjer pr krav.
	 * @param krav Kravet det skal genereres linje(r) til SAP filen for
	 * @return String med en eller flere linjer til SAP filen
	 */
	String genererKravLinje(Krav krav) throws FinnerIkkeProduktNummerException { 
		
		Prosjekt prosjekt = Prosjekt.findByProduktNummer(krav.produktNummer)
		
		def listeMedSammeDelProduktNum = Skjema.findAllByDelProduktNummer(krav.produktNummer, [sort: "oppstartDataInnsamling", order: "desc"])
		
		Skjema skjema = listeMedSammeDelProduktNum.size()==0 ? null : listeMedSammeDelProduktNum[0]
		if (skjema!=null){
			log.info ('--> Valgt skjema id: '  + skjema.id )
		}
		
		Produkt produkt = Produkt.findByProduktNummer(krav.produktNummer)

		if(!krav) {
			log.error("Kan ikke skrive krav linje når krav er NULL")
			return null
		}

		if(!produkt && !prosjekt && !skjema ) {
			log.error("Kan ikke skrive krav linje når produkt, prosjekt og skjema er NULL")
			throw new FinnerIkkeProduktNummerException(krav.produktNummer, "Kan ikke skrive krav linje fordi prosjekt, skjema eller produkt med produktnummer " + krav.produktNummer + " mangler.")
		}

		if(!prosjekt && !produkt) {
			prosjekt = skjema.prosjekt
		}

		boolean isTime = krav.timeforing ?: false
		int statProsent = prosjekt ? prosjekt.prosentStat : produkt.prosentStat
		int markedProsent = prosjekt ? prosjekt.prosentMarked : produkt.prosentMarked
		ProsjektFinansiering finansiering = prosjekt?.finansiering ?: produkt.finansiering

		String arbeidsOrdreNummer = ""
		if(skjema) {
			arbeidsOrdreNummer = skjema.delProduktNummer
		}
		else if (produkt){
			arbeidsOrdreNummer = produkt.produktNummer
		}else {
			arbeidsOrdreNummer = prosjekt.produktNummer
		}

		Lonnart lonnart = null
		Lonnart lonnart_2 = null

		if(finansiering == ProsjektFinansiering.STAT_MARKED) {
			if(statProsent == 100) {
				lonnart = finnLonnart(krav, ProsjektFinansiering.STAT)
			}
			else if(markedProsent == 100) {
				lonnart = finnLonnart(krav, ProsjektFinansiering.MARKED)
			}
			else {
				lonnart = finnLonnart(krav, ProsjektFinansiering.STAT)
				lonnart_2 = finnLonnart(krav, ProsjektFinansiering.MARKED)
			}
		}
		else {
			lonnart = finnLonnart(krav, finansiering)
		}

		if(!lonnart) {
			// TODO: ERROR
			log.error("Kan ikke skrive krav linje uten lønnart")
			return null
		}

		// Hvis lønnart 5711 og intervjuer bor i Tromsø (kommunenummer = 1902),
		// bytt til lønnart 5718, dette fordi Tromsø har egen sats for km.
		// godgjørelse
		if(lonnart.lonnartNummer == LONNART_EGEN_BIL && krav?.intervjuer?.kommuneNummer == KOMMUNENUMMER_TROMSO) {

			// Hvis lonnart_2 ikke er null er det snakk om
			// stat/marked fordeling -> må finne 2 lønnarter
			if(lonnart_2) {
				lonnart = Lonnart.findByLonnartNummerAndMarkedType(LONNART_EGEN_BIL_TROMSO, MarkedType.STAT)
				lonnart_2 = Lonnart.findByLonnartNummerAndMarkedType(LONNART_EGEN_BIL_TROMSO, MarkedType.MARKED)
			}
			else {
				// Finn ut markedstype og finn dermed lønnart
				if(finansiering == ProsjektFinansiering.STAT) {
					lonnart = Lonnart.findByLonnartNummerAndMarkedType(LONNART_EGEN_BIL_TROMSO, MarkedType.STAT)
				}
				else {
					lonnart = Lonnart.findByLonnartNummerAndMarkedType(LONNART_EGEN_BIL_TROMSO, MarkedType.MARKED)
				}
			}
		}

		String kontoart = finnKontoart(lonnart)
		String kontoart_2 = lonnart_2 ? finnKontoart(lonnart_2) : null

		// Intervjuernr 11
		String s = fyllMedNuller(krav.intervjuer.intervjuerNummer.toString(), 11, true)

		// Blanke 29
		s += fyllMedBlanke("", 29, true)

		// Blanke Time 8
		if(isTime) {
			s += fyllMedBlanke("", 8, true)
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd")

		// Arbeidsdato 8
		s += sdf.format(krav.dato)

		if(isTime) {
			// Blanke Time 1
			s += fyllMedBlanke("", 1, true)
		}
		else {
			// Blanke Reise 8
			s += fyllMedBlanke("", 8, true)
		}

		String overtidStr = null

		if(isTime && skjema?.overtid && erHelg(krav.dato) && krav.intervjuer.lokal) {
			overtidStr = s + LONNART_OVERTID
		}

		// Lønnartnr 4
		s += lonnart.lonnartNummer

		// Hvis lonnart_2 ikke er null er det snakk om et stat/marked prosjekt
		boolean isStatMarked = (lonnart_2 ? true : false)

		// [0] = antall/beløp stat eller marked
		// [1] = antall/beløp marked hvis type er stat/marked, null hvis ikke stat/marked
		def intArray = beregnAntallBelop(krav.antall, krav.kravType, statProsent, isStatMarked)

		if(isTime) {
			// Blanke Time 7
			s += fyllMedBlanke("", 7, true)
			if(overtidStr) {
				overtidStr += fyllMedBlanke("", 7, true)
			}

			// Antall Time 7
			s += fyllMedNuller(intArray[0].toString(), 7, true)
			if(overtidStr) {
				overtidStr += fyllMedNuller(intArray[0].toString(), 7, true)
			}
		}
		else {
			// Beløp Reise 7
			if(krav.kravType == KravType.U) {
				s += fyllMedNuller(intArray[0].toString(), 7, true)
			}
			else {
				// Beløp Kjørebok 0'er 7
				s += fyllMedNuller("", 7, true)
			}

			// Antall Reise 7
			if(krav.kravType == KravType.K) {
				s += fyllMedNuller(intArray[0].toString(), 7, true)
			}
			else {
				// Antall Utlegg 0'er 7
				s += fyllMedNuller("", 7, true)
			}
		}

		// Kostnadssted 10
		s += fyllMedBlanke(grailsApplication.config.sil.sap.kostnadssted, 10, true)
		if(overtidStr) {
			overtidStr += fyllMedBlanke(grailsApplication.config.sil.sap.kostnadssted, 10, true)
		}

		// K-element-7 12
        // Her kommer det en blank etter arbeidsOrdreNummer
        s += "${fyllMedBlanke(arbeidsOrdreNummer, 11, true)} "
		if(overtidStr) {
			overtidStr += "${fyllMedBlanke(arbeidsOrdreNummer, 11, true)} "
		}

		// K-element-6 24
		s += fyllMedBlanke("", 24, true)
		if(overtidStr) {
			overtidStr += fyllMedBlanke("", 24, true)
		}

		// K-element-4 10
		if(finansiering == ProsjektFinansiering.STAT || finansiering == ProsjektFinansiering.STAT_MARKED) {
			s += fyllMedBlanke(grailsApplication.config.sil.sap.kontopost.stat, 10, true)
			if(overtidStr) {
				overtidStr += fyllMedBlanke(grailsApplication.config.sil.sap.kontopost.stat, 10, true)
			}
		}
		else if(finansiering == ProsjektFinansiering.MARKED) {
			s += fyllMedBlanke(grailsApplication.config.sil.sap.kontopost.marked, 10, true)
			if(overtidStr) {
				overtidStr += fyllMedBlanke(grailsApplication.config.sil.sap.kontopost.marked, 10, true)
			}
		}

		// K-element-5 16
		s += fyllMedBlanke("-", 16, true)
		if(overtidStr) {
			overtidStr += fyllMedBlanke("-", 16, true)
		}

		// Kontoart 24
		s += fyllMedBlanke(kontoart, 24, true)
		if(overtidStr) {
			overtidStr += fyllMedBlanke(kontoart, 24, true)
		}

		String passasjerTilleggLinjeStat = null
		String passasjerTilleggLinjeMarked = null

		// Hvis lønnart er Egen bil 5711 og antallPassasjerer er mer enn 0
		// -> skriv egne linjer for lønnart 5712 passasjertillegg
		if(lonnart.lonnartNummer == LONNART_EGEN_BIL && krav.kjorebok?.antallPassasjerer > 0) {
			Lonnart passTilleggStat = Lonnart.findByLonnartNummerAndMarkedType(LONNART_PASSASJERTILLEGG, MarkedType.STAT)
			// Ta med alt fram til lønnart
			passasjerTilleggLinjeStat = s.substring(0, 56)
			//Lønnart 4
			passasjerTilleggLinjeStat += passTilleggStat.lonnartNummer
			// Beløp 7 0'er
			passasjerTilleggLinjeStat += fyllMedNuller("", 7, true)
			// Antall 7
			passasjerTilleggLinjeStat += fyllMedNuller(intArray[0].toString() , 7, true)
			// Ta med alt mellom antall og kontopost (kostnadsted, blanke og produktnummer)
			passasjerTilleggLinjeStat += s.substring(74, 120)
			// Kontopost 10
			passasjerTilleggLinjeStat += fyllMedBlanke(grailsApplication.config.sil.sap.kontopost.stat, 10, true)
			// Stiplet 16
			passasjerTilleggLinjeStat += fyllMedBlanke("-", 16, true)
			// Kontoart 24
			passasjerTilleggLinjeStat += fyllMedBlanke(finnKontoart(passTilleggStat), 24, true)

			if(isStatMarked) {
				Lonnart passTilleggMarked = Lonnart.findByLonnartNummerAndMarkedType(LONNART_PASSASJERTILLEGG, MarkedType.MARKED)
				// Ta med alt fram til lønnart
				passasjerTilleggLinjeMarked = s.substring(0, 56)
				//Lønnart 4
				passasjerTilleggLinjeMarked += passTilleggMarked.lonnartNummer
				// Beløp 7 0'er
				passasjerTilleggLinjeMarked += fyllMedNuller("", 7, true)
				// Antall 7
				passasjerTilleggLinjeMarked += fyllMedNuller(intArray[1].toString(), 7, true)
				// Ta med alt mellom antall og kontopost (kostnadsted, blanke og produktnummer)
				passasjerTilleggLinjeMarked += s.substring(74, 120)
				// Kontopost 10
				passasjerTilleggLinjeMarked += fyllMedBlanke(grailsApplication.config.sil.sap.kontopost.marked, 10, true)
				// Stiplet 16
				passasjerTilleggLinjeMarked += fyllMedBlanke("-", 16, true)
				// Kontoart 24
				passasjerTilleggLinjeMarked += fyllMedBlanke(finnKontoart(passTilleggMarked), 24, true)
			}
		}

		String retStr

		if(isStatMarked) {
			// Endre antall,belop,lonnart,kontoart,marked
			retStr = s + "\n"
			if(overtidStr) {
				retStr += overtidStr + "\n"
			}

			// Ta med alt fram til lonnart
			retStr += s.substring(0, isTime ? 57 : 56)
			// Legg til lonnart_2
			retStr += lonnart_2.lonnartNummer

			if(isTime) {
				// Legg til 7 blanke
				retStr += fyllMedBlanke("", 7, true)
				// Antall 7
				retStr += fyllMedNuller(intArray[1].toString(), 7, true)
				// Ta med alt mellom antall og kontopost (kostnadsted, blanke og produktnummer)
				retStr += s.substring(75, 121)
				// Kontopost 10
				retStr += fyllMedBlanke(grailsApplication.config.sil.sap.kontopost.marked, 10, true)
				// Stiplet 16
				retStr += fyllMedBlanke("-", 16, true)
				// Kontoart 24
				retStr += fyllMedBlanke(kontoart_2, 24, true)

				if(overtidStr) {
					// Legg til ny linje for marked hvis overtid
					retStr += "\n" + overtidStr.substring(0,68)
					// Antall 7
					retStr += fyllMedNuller(intArray[1].toString(), 7, true)
					retStr += overtidStr.substring(75, 121)
					// Kontopost 10 (marked)
					retStr += fyllMedBlanke(grailsApplication.config.sil.sap.kontopost.marked, 10, true)
					retStr += overtidStr.substring(131)
				}
			}
			else {
				if(krav.kravType == KravType.U) {
					// Beløp 7
					retStr += fyllMedNuller(intArray[1].toString(), 7, true)
				}
				else {
					retStr += fyllMedNuller("", 7, true)
				}

				if(krav.kravType == KravType.K) {
					// Antall 7
					retStr += fyllMedNuller(intArray[1].toString(), 7, true)
				}
				else {
					retStr += fyllMedNuller("", 7, true)
				}

				// Ta med alt mellom antall og kontopost (kostnadsted, blanke og produktnummer)
				retStr += s.substring(74, 120)
				// Kontopost 10
				retStr += fyllMedBlanke(grailsApplication.config.sil.sap.kontopost.marked, 10, true)
				// Stiplet 16
				retStr += fyllMedBlanke("-", 16, true)
				// Kontoart 24
				retStr += fyllMedBlanke(kontoart_2, 24, true)
			}
			// Hvis det er snakk om passasjertillegg, legg til en linje pr passasjer
			if(passasjerTilleggLinjeStat) {
				for(int i=0; i<krav.kjorebok.antallPassasjerer; i++) {
					retStr += "\n" + passasjerTilleggLinjeStat
				}
			}

			// Hvis det er snakk om passasjertillegg, legg til en linje pr passasjer
			if(passasjerTilleggLinjeMarked) {
				for(int i=0; i<krav.kjorebok.antallPassasjerer; i++) {
					retStr += "\n" + passasjerTilleggLinjeMarked
				}
			}
		}
		else {
			retStr = (overtidStr ? (s + "\n" + overtidStr) : s)
			// Hvis det er snakk om passasjertillegg, legg til en linje pr passasjer
			if(passasjerTilleggLinjeStat) {
				for(int i=0; i<krav.kjorebok.antallPassasjerer; i++) {
					retStr += "\n" + passasjerTilleggLinjeStat
				}
			}

			// Hvis det er snakk om passasjertillegg, legg til en linje pr passasjer
			if(passasjerTilleggLinjeMarked) {
				for(int i=0; i<krav.kjorebok.antallPassasjerer; i++) {
					retStr += "\n" + passasjerTilleggLinjeMarked
				}
			}
		}
		if(overskriderTidspunktForLonnartskifte(krav.timeforing))
			retStr = splittIntervjuKravlinjerPaaToLonnarter(retStr, krav, isStatMarked, statProsent)

		return retStr
	}

	private String splittIntervjuKravlinjerPaaToLonnarter(String kravlinjer, Krav krav, boolean isStatMarked, int statProsent) {

		def kravlinjerUt = ''
		def kravlinjeArray = kravlinjer.split('\n')
		for (int i = 0; i < kravlinjeArray.size(); i++) {
			def lonnartNummer = kravlinjeArray[i].substring(57,61)

			if (lonnartNummer == LONNARTNUMMER_LOKALT_INTERVJU || lonnartNummer == LONNARTNUMMER_SENTRALT_INTERVJU)
				kravlinjerUt += splittKravlinje(kravlinjeArray[i], krav, isStatMarked, statProsent)
			else
				kravlinjerUt += kravlinjeArray[i]

			kravlinjerUt += i<kravlinjeArray.size()-1 ? '\n' : ''
		}

		return kravlinjerUt
	}

    private String splittKravlinje(String kravlinje, Krav krav, boolean isStatMarked, int statProsent) {

		Date fra = krav.timeforing.fra
        Date til = krav.timeforing.til
		Date tidspunkt = tidspunktForLonnartskifte(krav.timeforing)
        double minutterBeforeTidspunkt = (tidspunkt.getTime() - fra.getTime())/(1000*60)
        double minutterAfterTidspunkt = (til.getTime() - tidspunkt.getTime())/(1000*60)

		def prosent = 100
		if (isStatMarked) {
			def kontopost = kravlinje.substring(125, 131)
			if (kontopost == grailsApplication.config.sil.sap.kontopost.stat)
				prosent = statProsent
			else
				prosent = 100 - statProsent
		}

		String retStr = byttAntall(kravlinje, minutterBeforeTidspunkt, prosent, krav.kravType) + '\n'
		retStr += byttIntervjuLonnart(byttAntall(kravlinje, minutterAfterTidspunkt, prosent, krav.kravType))

		return retStr
    }

	private String byttAntall(String kravlinje, double minutter, int prosent, KravType kravType) {
		def antall = beregnAntallBelop(minutter*(prosent/100), kravType, 100, false)
		String antallStr = fyllMedNuller(''+antall[0], 7, true)
		return kravlinje.substring(0,68) + antallStr + kravlinje.substring(75)
	}

    private String byttIntervjuLonnart(String kravlinje) {
		def lonnartNummer = kravlinje.substring(57,61)

		if (lonnartNummer == LONNARTNUMMER_SENTRALT_INTERVJU)
			return kravlinje.substring(0, 57) + LONNARTNUMMER_SENTRALT_INTERVJU_ETTER_TIDSPUNKT + kravlinje.substring(61)
		else if (lonnartNummer == LONNARTNUMMER_LOKALT_INTERVJU)
			return kravlinje.substring(0, 57) + LONNARTNUMMER_LOKALT_INTERVJU_ETTER_TIDSPUNKT + kravlinje.substring(61)
		else
			return kravlinje
	}


	Lonnart finnLonnart(Krav krav, ProsjektFinansiering finansiering) {
		String lonnartNummer = "3112"
		MarkedType markedType = finansiering == ProsjektFinansiering.STAT ? MarkedType.STAT : MarkedType.MARKED

		if(krav.kravType == KravType.T) {
			if(krav.intervjuer.lokal) {

				if (erEtterTidspunktForLonnartskifte(krav.timeforing))
					lonnartNummer = "3192"
				else
					lonnartNummer = "3110"

			} else {

				if (erEtterTidspunktForLonnartskifte(krav.timeforing))
					lonnartNummer = "3196"
				else
					lonnartNummer = "3112"

			}
			if(krav.intervjuer.pensjonistLonn || erPensjonist(krav.intervjuer)) {
				lonnartNummer = "2003"
			}
		}
		else if(krav.kravType == KravType.K) {
			//TransportMiddel.EGEN_BIL
			lonnartNummer = "5711"
			// TODO: Snøscooter og passasjertillegg
			if(krav.kjorebok.transportmiddel == TransportMiddel.MOTORSYKKEL || krav.kjorebok.transportmiddel == TransportMiddel.SNOSCOOTER) {
				lonnartNummer = "5716"
			}
			else if(krav.kjorebok.transportmiddel == TransportMiddel.MOPED_SYKKEL) {
				lonnartNummer = "5717"
			}
			else if(krav.kjorebok.transportmiddel == TransportMiddel.MOTOR_BAAT) {
				lonnartNummer = "5715"
			}
		}
		else if(krav.kravType == KravType.U) {
			// TODO: Få inn utlegg for ferje, tog osv.
			if(	krav.utlegg.utleggType == UtleggType.BILLETT ||
			krav.utlegg.utleggType == UtleggType.BOMPENGER ||
			krav.utlegg.utleggType == UtleggType.PARKERING ||
			krav.utlegg.utleggType == UtleggType.TAXI) {

				// LØNNART Reiseutlegg
				lonnartNummer = "5705"
			}
			else {
				// Refusjon av div.
				lonnartNummer = "1462"
			}
		}

		return Lonnart.findByLonnartNummerAndMarkedType(lonnartNummer, markedType)
	}

	String finnKontoart(Lonnart lonnart) {
		if(!lonnart) {
			log.error("Kan ikke finne kontorart når lønnart er null")
			return ""
		}

		if(!lonnart.konto) {
			log.error("Kan ikke finne kontorart når konto ikke er satt på lønnart")
			return ""
		}

		return lonnart.konto.substring((lonnart.konto.size()-4))
	}

	String genererFilnavnSapFil(FilType filType) {
		String navn = grailsApplication.config.sil.sap.firmakode

		if(filType == FilType.TIME) {
			navn = navn + grailsApplication.config.sil.sap.kode.time
		}
		else {
			navn = navn + grailsApplication.config.sil.sap.kode.reise
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd")

		navn = navn + sdf.format(new Date()) + finnLopenummerFil(new Date(), filType) + grailsApplication.config.sil.sap.fil.extension

		return navn
	}

	String genererForsteLinjeSapFil(Integer antall, FilType filType) {
		String forsteLinje = grailsApplication.config.sil.sap.firmakode + "      "

		if(filType == FilType.TIME) {
			forsteLinje = forsteLinje + grailsApplication.config.sil.sap.kode.time + " "
		}
		else {
			forsteLinje = forsteLinje + grailsApplication.config.sil.sap.kode.reise + " "
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd")

		forsteLinje = forsteLinje + sdf.format(new Date())

		forsteLinje = forsteLinje + finnLopenummerFil(new Date(), filType) + "           "

		String linjer = antall.toString();

		linjer = fyllMedNuller(linjer, 9, true)

		forsteLinje = forsteLinje + linjer + "000000000"

		return forsteLinje
	}

	Boolean erHelg(Date dato) {
		if(!dato) {
			return false
		}

		Calendar cal = Calendar.getInstance()
		cal.setTime(dato)

		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			return true

		return false
	}

	// Metode for å beregne antall eller beløp gitt antall (minutter, km eller beløp) fra kravet,
	// kravtype og om det gjelder stat/marked
	def beregnAntallBelop(Double antall, KravType kravType, int statProsent, boolean isStatMarked) {
		def returnArray = []

		if(isStatMarked) {
			int totalt = antall * 100

			int stat = totalt * (statProsent/100)
			int marked = totalt - stat

			if(kravType == KravType.T) {
				// Finn minutter i timer, f.eks. 30 min = 0.5 timer
				double totTimer = antall/60
				double dobStat = totTimer * (statProsent/100)

				stat = Math.round( dobStat * 100 )

				// Finner marked ved å ta totalen minus
				// stat, da får man rest antall/beløp og
				// unngår avrundingsfeil som kunne oppstått
				// hvis man brukte prosenten på begge
				int tot = Math.round(totTimer * 100)
				marked = tot - stat
			}

			returnArray << new Integer(stat)
			returnArray << new Integer(marked)
		}
		else {
			// Fjern desimaler ved å multiplisere med 100 og runde av til integer
			Double d = new Double((antall*100))
			int i = antall * 100

			if(kravType == KravType.T) {
				// Finn antall minutter i antall timer
				double dob = ( antall.doubleValue() / 60 ) * 100
				i = Math.round(dob)
			}
			returnArray << new Integer(i)
		}

		return returnArray
	}

	// Første mnd etter fylte 70 år skal alle ha pensjonistlønn
	Boolean erPensjonist(Intervjuer intervjuer) {
		if(!intervjuer || !intervjuer.fodselsDato) {
			return false
		}

		Calendar cal = Calendar.getInstance()

		Long year = new Long(cal.get(Calendar.YEAR))

		Calendar calTwo = Calendar.getInstance()
		calTwo.setTime intervjuer.fodselsDato

		Long fAar = calTwo.get(Calendar.YEAR)

		if((year - fAar) > 70) {
			return true
		}
		else if((year - fAar) == 70L) {
			if(cal.get(Calendar.MONTH) > calTwo.get(Calendar.MONTH)) {
				return true
			}
		}

		return false
	}


	private boolean erKostGodtgjorelse(Krav krav) {

		if( krav.utlegg == null ) {
			return false
		}

		UtleggType utleggType = krav.utlegg.utleggType

		if( utleggType == UtleggType.KOST_GODT ) {
			return true
		}
		else {
			return false
		}
	}

	private String finnLopenummerFil(Date dato, FilType filType) {
		def crit = SapFil.createCriteria()

		def liste = crit {
			eq("filType", filType)
			eq("status", SapFilStatusType.OK)
			between("dato", utilService.getFromDate(dato), utilService.getToDate(dato))
		}

		if(!liste) {
			return grailsApplication.config.sil.sap.forste.lopenummer
		}
		else {
			int lNr = Integer.parseInt(grailsApplication.config.sil.sap.forste.lopenummer) + liste.size()
			return lNr.toString()
		}
	}

	private String fyllMedNuller(String str, int totalStringLengde, boolean foran) {
		int x = str.size()

		for(int i=x; i<totalStringLengde; i++) {
			if(foran)
				str = "0" + str
			else
				str = str + "0"
		}

		return str
	}

	private String fyllMedBlanke(String str, int totalStringLengde, boolean foran) {
		int x = str.size()

		for(int i=x; i<totalStringLengde; i++) {
			if(foran)
				str = " " + str
			else
				str = str + " "
		}

		return str
	}

	private boolean erEtterTidspunktForLonnartskifte(Timeforing timeforing) {
		if (!timeforing)
			return false

		Date fra = timeforing.fra
		Date tidspunkt = tidspunktForLonnartskifte(timeforing)

		return !fra.before(tidspunkt)
	}

	private boolean overskriderTidspunktForLonnartskifte(Timeforing timeforing) {
		if (!timeforing)
			return false

		Date fra = timeforing.fra
		Date til = timeforing.til
		Date tidspunkt = tidspunktForLonnartskifte(timeforing)

		return (fra.before(tidspunkt) && til.after(tidspunkt))
	}

	private Date tidspunktForLonnartskifte (Timeforing timeforing) {
		Calendar cal = timeforing.fra.toCalendar()
		cal.set(Calendar.HOUR_OF_DAY, TIDSPUNKT_LONNARTSKIFTE_HOUR_OF_DAY)
		cal.set(Calendar.MINUTE, TIDSPUNKT_LONNARTSKIFTE_MINUTE)
		cal.set(Calendar.SECOND, TIDSPUNKT_LONNARTSKIFTE_SECOND)
		return cal.getTime()
	}
}