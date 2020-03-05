package no.ssb.sivadm.parser;

public class Utvalg {

	private String delprodnr;
	private String aar;
	private String periodeType;
	private Long periodeNr;
	private String ioNr;
	private String intType;
	private String fNummer;
	private String famNr;
	private String gateGaard;
	private String husBruk;
	private String bokstavFnr;
	private String undernr;
	private String navn;
	private String navn2;
	private String kjonn;
	private String kommune;
	private String utvalgsomr;
	private String adresse;
	private String bolignr;
	private String postnr;
	private String poststed;
	private String brevadr;
	private String brevpostnr;
	private String brevpostst;
	private Long alder;
	private String sivilstand;
	private String perskode;
	private String statsbrg;
	private String erIo;
	private String kPeriode;
	private String delutvalg;
	private String prefInt;
	private String refNavn;
	private String resu1;
	private String resu2;
	private String resu3;
	private String resu4;
	private String resu5;
	private String resu6;
	private String resu7;
	private String hushold1;
	private String hushold1Fnr;
	private String hushold1Perskode;
	private String hushold2;
	private String hushold2Fnr;
	private String hushold2Perskode;
	private String hushold3;
	private String hushold3Fnr;
	private String hushold3Perskode;
	private String hushold4;
	private String hushold4Fnr;
	private String hushold4Perskode;
	private String hushold5;
	private String hushold5Fnr;
	private String hushold5Perskode;
	private String hushold6;
	private String hushold6Fnr;
	private String hushold6Perskode;
	private String hushold7;
	private String hushold7Fnr;
	private String hushold7Perskode;
	private String hushold8;
	private String hushold8Fnr;
	private String hushold8Perskode;
	private String hushold9;
	private String hushold9Fnr;
	private String hushold9Perskode;
	private String hushold10;
	private String hushold10Fnr;
	private String hushold10Perskode;
	private String avtaledato;
	private String avtaletid;
	private Integer avtaletype;
	private String avtalekomm;
	
	private String telefon1;
	private String kommentar1;
	private String kilde1;
	
	private String telefon2;
	private String kommentar2;
	private String kilde2;
	
	private String telefon3;
	private String kommentar3;
	private String kilde3;
	
	private String smsNummer;
	
	private String reservasjon;
	
	private String epostadresse;
	private String nace;
	private Long antallAnsatte;
	private String melding;
	private String overioNr;
	private String passordWeb;
	private String rotasjonGrp;
	private Long intervjuObjektId; // global id for IO
	
	private Long fullforingsGrad;
	private String kilde;

	private String maalform;
	private String varslingsstatus;


	/**
	 * @return the delprodnr
	 */
	public String getDelprodnr() {
		return delprodnr;
	}


	/**
	 * @param delprodnr
	 *            the delprodnr to set
	 */
	public void setDelprodnr(String delprodnr) {
		this.delprodnr = delprodnr;
	}


	/**
	 * @return the aar
	 */
	public String getAar() {
		return aar;
	}


	/**
	 * @param aar
	 *            the aar to set
	 */
	public void setAar(String aar) {
		this.aar = aar;
	}


	/**
	 * @return the periodeType
	 */
	public String getPeriodeType() {
		return periodeType;
	}


	/**
	 * @param periodeType
	 *            the periodeType to set
	 */
	public void setPeriodeType(String periodeType) {
		this.periodeType = periodeType;
	}


	/**
	 * @return the periodeNr
	 */
	public Long getPeriodeNr() {
		return periodeNr;
	}

	public String getPeriodeNrAsZeroFilledString() {
		String nr = '0' + periodeNr.toString();
		return nr.substring(nr.length()-2);
	}


	/**
	 * @param periodeNr
	 *            the periodeNr to set
	 */
	public void setPeriodeNr(Long periodeNr) {
		this.periodeNr = periodeNr;
	}


	/**
	 * @return the ioNr
	 */
	public String getIoNr() {
		return ioNr;
	}


	/**
	 * @param ioNr
	 *            the ioNr to set
	 */
	public void setIoNr(String ioNr) {
		this.ioNr = ioNr;
	}


	/**
	 * @return the intType
	 */
	public String getIntType() {
		return intType;
	}


	/**
	 * @param intType
	 *            the intType to set
	 */
	public void setIntType(String intType) {
		this.intType = intType;
	}


	/**
	 * @return the fNummer
	 */
	public String getfNummer() {
		return fNummer;
	}


	/**
	 * @param fNummer
	 *            the fNummer to set
	 */
	public void setfNummer(String fNummer) {
		this.fNummer = fNummer;
	}


	/**
	 * @return the famNr
	 */
	public String getFamNr() {
		return famNr;
	}


	/**
	 * @param famNr
	 *            the famNr to set
	 */
	public void setFamNr(String famNr) {
		this.famNr = famNr;
	}


	/**
	 * @return the gateGaard
	 */
	public String getGateGaard() {
		return gateGaard;
	}


	/**
	 * @param gateGaard
	 *            the gateGaard to set
	 */
	public void setGateGaard(String gateGaard) {
		this.gateGaard = gateGaard;
	}


	/**
	 * @return the husBruk
	 */
	public String getHusBruk() {
		return husBruk;
	}


	/**
	 * @param husBruk
	 *            the husBruk to set
	 */
	public void setHusBruk(String husBruk) {
		this.husBruk = husBruk;
	}


	/**
	 * @return the bokstavFnr
	 */
	public String getBokstavFnr() {
		return bokstavFnr;
	}


	/**
	 * @param bokstavFnr
	 *            the bokstavFnr to set
	 */
	public void setBokstavFnr(String bokstavFnr) {
		this.bokstavFnr = bokstavFnr;
	}


	/**
	 * @return the undernr
	 */
	public String getUndernr() {
		return undernr;
	}


	/**
	 * @param undernr
	 *            the undernr to set
	 */
	public void setUndernr(String undernr) {
		this.undernr = undernr;
	}


	/**
	 * @return the navn
	 */
	public String getNavn() {
		return navn;
	}


	/**
	 * @param navn
	 *            the navn to set
	 */
	public void setNavn(String navn) {
		this.navn = navn;
	}


	/**
	 * @return the navn2
	 */
	public String getNavn2() {
		return navn2;
	}


	/**
	 * @param navn2
	 *            the navn2 to set
	 */
	public void setNavn2(String navn2) {
		this.navn2 = navn2;
	}


	/**
	 * @return the kjonn
	 */
	public String getKjonn() {
		return kjonn;
	}


	/**
	 * @param kjonn
	 *            the kjonn to set
	 */
	public void setKjonn(String kjonn) {
		this.kjonn = kjonn;
	}


	/**
	 * @return the kommune
	 */
	public String getKommune() {
		return kommune;
	}


	/**
	 * @param kommune
	 *            the kommune to set
	 */
	public void setKommune(String kommune) {
		this.kommune = kommune;
	}


	/**
	 * @return the utvalgsomr
	 */
	public String getUtvalgsomr() {
		return utvalgsomr;
	}


	/**
	 * @param utvalgsomr
	 *            the utvalgsomr to set
	 */
	public void setUtvalgsomr(String utvalgsomr) {
		this.utvalgsomr = utvalgsomr;
	}


	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}


	/**
	 * @param adresse
	 *            the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	/**
	 * @return the bolignr
	 */
	public String getBolignr() {
		return bolignr;
	}


	/**
	 * @param bolignr
	 *            the bolignr to set
	 */
	public void setBolignr(String bolignr) {
		this.bolignr = bolignr;
	}


	/**
	 * @return the postnr
	 */
	public String getPostnr() {
		return postnr;
	}


	/**
	 * @param postnr
	 *            the postnr to set
	 */
	public void setPostnr(String postnr) {
		this.postnr = postnr;
	}


	/**
	 * @return the poststed
	 */
	public String getPoststed() {
		return poststed;
	}


	/**
	 * @param poststed
	 *            the poststed to set
	 */
	public void setPoststed(String poststed) {
		this.poststed = poststed;
	}


	/**
	 * @return the brevadr
	 */
	public String getBrevadr() {
		return brevadr;
	}


	/**
	 * @param brevadr
	 *            the brevadr to set
	 */
	public void setBrevadr(String brevadr) {
		this.brevadr = brevadr;
	}


	/**
	 * @return the brevpostnr
	 */
	public String getBrevpostnr() {
		return brevpostnr;
	}


	/**
	 * @param brevpostnr
	 *            the brevpostnr to set
	 */
	public void setBrevpostnr(String brevpostnr) {
		this.brevpostnr = brevpostnr;
	}


	/**
	 * @return the brevpostst
	 */
	public String getBrevpostst() {
		return brevpostst;
	}


	/**
	 * @param brevpostst
	 *            the brevpostst to set
	 */
	public void setBrevpostst(String brevpostst) {
		this.brevpostst = brevpostst;
	}


	/**
	 * @return the alder
	 */
	public Long getAlder() {
		return alder;
	}


	/**
	 * @param alder
	 *            the alder to set
	 */
	public void setAlder(Long alder) {
		this.alder = alder;
	}


	/**
	 * @return the sivilstand
	 */
	public String getSivilstand() {
		return sivilstand;
	}


	/**
	 * @param sivilstand
	 *            the sivilstand to set
	 */
	public void setSivilstand(String sivilstand) {
		this.sivilstand = sivilstand;
	}


	/**
	 * @return the perskode
	 */
	public String getPerskode() {
		return perskode;
	}


	/**
	 * @param perskode
	 *            the perskode to set
	 */
	public void setPerskode(String perskode) {
		this.perskode = perskode;
	}


	/**
	 * @return the statsbrg
	 */
	public String getStatsbrg() {
		return statsbrg;
	}


	/**
	 * @param statsbrg
	 *            the statsbrg to set
	 */
	public void setStatsbrg(String statsbrg) {
		this.statsbrg = statsbrg;
	}


	/**
	 * @return the erIo
	 */
	public String getErIo() {
		return erIo;
	}


	/**
	 * @param erIo
	 *            the erIo to set
	 */
	public void setErIo(String erIo) {
		this.erIo = erIo;
	}


	/**
	 * @return the kPeriode
	 */
	public String getkPeriode() {
		return kPeriode;
	}


	/**
	 * @param kPeriode
	 *            the kPeriode to set
	 */
	public void setkPeriode(String kPeriode) {
		this.kPeriode = kPeriode;
	}


	/**
	 * @return the delutvalg
	 */
	public String getDelutvalg() {
		return delutvalg;
	}


	/**
	 * @param delutvalg
	 *            the delutvalg to set
	 */
	public void setDelutvalg(String delutvalg) {
		this.delutvalg = delutvalg;
	}


	/**
	 * @return the prefInt
	 */
	public String getPrefInt() {
		return prefInt;
	}


	/**
	 * @param prefInt
	 *            the prefInt to set
	 */
	public void setPrefInt(String prefInt) {
		this.prefInt = prefInt;
	}


	/**
	 * @return the refNavn
	 */
	public String getRefNavn() {
		return refNavn;
	}


	/**
	 * @param refNavn
	 *            the refNavn to set
	 */
	public void setRefNavn(String refNavn) {
		this.refNavn = refNavn;
	}


	/**
	 * @return the resu1
	 */
	public String getResu1() {
		return resu1;
	}


	/**
	 * @param resu1
	 *            the resu1 to set
	 */
	public void setResu1(String resu1) {
		this.resu1 = resu1;
	}


	/**
	 * @return the resu2
	 */
	public String getResu2() {
		return resu2;
	}


	/**
	 * @param resu2
	 *            the resu2 to set
	 */
	public void setResu2(String resu2) {
		this.resu2 = resu2;
	}


	/**
	 * @return the resu3
	 */
	public String getResu3() {
		return resu3;
	}


	/**
	 * @param resu3
	 *            the resu3 to set
	 */
	public void setResu3(String resu3) {
		this.resu3 = resu3;
	}


	/**
	 * @return the resu4
	 */
	public String getResu4() {
		return resu4;
	}


	/**
	 * @param resu4
	 *            the resu4 to set
	 */
	public void setResu4(String resu4) {
		this.resu4 = resu4;
	}


	/**
	 * @return the resu5
	 */
	public String getResu5() {
		return resu5;
	}


	/**
	 * @param resu5
	 *            the resu5 to set
	 */
	public void setResu5(String resu5) {
		this.resu5 = resu5;
	}


	/**
	 * @return the resu6
	 */
	public String getResu6() {
		return resu6;
	}


	/**
	 * @param resu6
	 *            the resu6 to set
	 */
	public void setResu6(String resu6) {
		this.resu6 = resu6;
	}


	/**
	 * @return the resu7
	 */
	public String getResu7() {
		return resu7;
	}


	/**
	 * @param resu7
	 *            the resu7 to set
	 */
	public void setResu7(String resu7) {
		this.resu7 = resu7;
	}


	/**
	 * @return the hushold1
	 */
	public String getHushold1() {
		return hushold1;
	}


	/**
	 * @param hushold1
	 *            the hushold1 to set
	 */
	public void setHushold1(String hushold1) {
		this.hushold1 = hushold1;
	}


	/**
	 * @return the hushold1Fnr
	 */
	public String getHushold1Fnr() {
		return hushold1Fnr;
	}


	/**
	 * @param hushold1Fnr
	 *            the hushold1Fnr to set
	 */
	public void setHushold1Fnr(String hushold1Fnr) {
		this.hushold1Fnr = hushold1Fnr;
	}


	/**
	 * @return the hushold1Perskode
	 */
	public String getHushold1Perskode() {
		return hushold1Perskode;
	}


	/**
	 * @param hushold1Perskode
	 *            the hushold1Perskode to set
	 */
	public void setHushold1Perskode(String hushold1Perskode) {
		this.hushold1Perskode = hushold1Perskode;
	}


	/**
	 * @return the hushold2
	 */
	public String getHushold2() {
		return hushold2;
	}


	/**
	 * @param hushold2
	 *            the hushold2 to set
	 */
	public void setHushold2(String hushold2) {
		this.hushold2 = hushold2;
	}


	/**
	 * @return the hushold2Fnr
	 */
	public String getHushold2Fnr() {
		return hushold2Fnr;
	}


	/**
	 * @param hushold2Fnr
	 *            the hushold2Fnr to set
	 */
	public void setHushold2Fnr(String hushold2Fnr) {
		this.hushold2Fnr = hushold2Fnr;
	}


	/**
	 * @return the hushold2Perskode
	 */
	public String getHushold2Perskode() {
		return hushold2Perskode;
	}


	/**
	 * @param hushold2Perskode
	 *            the hushold2Perskode to set
	 */
	public void setHushold2Perskode(String hushold2Perskode) {
		this.hushold2Perskode = hushold2Perskode;
	}


	/**
	 * @return the hushold3
	 */
	public String getHushold3() {
		return hushold3;
	}


	/**
	 * @param hushold3
	 *            the hushold3 to set
	 */
	public void setHushold3(String hushold3) {
		this.hushold3 = hushold3;
	}


	/**
	 * @return the hushold3Fnr
	 */
	public String getHushold3Fnr() {
		return hushold3Fnr;
	}


	/**
	 * @param hushold3Fnr
	 *            the hushold3Fnr to set
	 */
	public void setHushold3Fnr(String hushold3Fnr) {
		this.hushold3Fnr = hushold3Fnr;
	}


	/**
	 * @return the hushold3Perskode
	 */
	public String getHushold3Perskode() {
		return hushold3Perskode;
	}


	/**
	 * @param hushold3Perskode
	 *            the hushold3Perskode to set
	 */
	public void setHushold3Perskode(String hushold3Perskode) {
		this.hushold3Perskode = hushold3Perskode;
	}


	/**
	 * @return the hushold4
	 */
	public String getHushold4() {
		return hushold4;
	}


	/**
	 * @param hushold4
	 *            the hushold4 to set
	 */
	public void setHushold4(String hushold4) {
		this.hushold4 = hushold4;
	}


	/**
	 * @return the hushold4Fnr
	 */
	public String getHushold4Fnr() {
		return hushold4Fnr;
	}


	/**
	 * @param hushold4Fnr
	 *            the hushold4Fnr to set
	 */
	public void setHushold4Fnr(String hushold4Fnr) {
		this.hushold4Fnr = hushold4Fnr;
	}


	/**
	 * @return the hushold4Perskode
	 */
	public String getHushold4Perskode() {
		return hushold4Perskode;
	}


	/**
	 * @param hushold4Perskode
	 *            the hushold4Perskode to set
	 */
	public void setHushold4Perskode(String hushold4Perskode) {
		this.hushold4Perskode = hushold4Perskode;
	}


	/**
	 * @return the hushold5
	 */
	public String getHushold5() {
		return hushold5;
	}


	/**
	 * @param hushold5
	 *            the hushold5 to set
	 */
	public void setHushold5(String hushold5) {
		this.hushold5 = hushold5;
	}


	/**
	 * @return the hushold5Fnr
	 */
	public String getHushold5Fnr() {
		return hushold5Fnr;
	}


	/**
	 * @param hushold5Fnr
	 *            the hushold5Fnr to set
	 */
	public void setHushold5Fnr(String hushold5Fnr) {
		this.hushold5Fnr = hushold5Fnr;
	}


	/**
	 * @return the hushold5Perskode
	 */
	public String getHushold5Perskode() {
		return hushold5Perskode;
	}


	/**
	 * @param hushold5Perskode
	 *            the hushold5Perskode to set
	 */
	public void setHushold5Perskode(String hushold5Perskode) {
		this.hushold5Perskode = hushold5Perskode;
	}


	/**
	 * @return the hushold6
	 */
	public String getHushold6() {
		return hushold6;
	}


	/**
	 * @param hushold6
	 *            the hushold6 to set
	 */
	public void setHushold6(String hushold6) {
		this.hushold6 = hushold6;
	}


	/**
	 * @return the hushold6Fnr
	 */
	public String getHushold6Fnr() {
		return hushold6Fnr;
	}


	/**
	 * @param hushold6Fnr
	 *            the hushold6Fnr to set
	 */
	public void setHushold6Fnr(String hushold6Fnr) {
		this.hushold6Fnr = hushold6Fnr;
	}


	/**
	 * @return the hushold6Perskode
	 */
	public String getHushold6Perskode() {
		return hushold6Perskode;
	}


	/**
	 * @param hushold6Perskode
	 *            the hushold6Perskode to set
	 */
	public void setHushold6Perskode(String hushold6Perskode) {
		this.hushold6Perskode = hushold6Perskode;
	}


	/**
	 * @return the hushold7
	 */
	public String getHushold7() {
		return hushold7;
	}


	/**
	 * @param hushold7
	 *            the hushold7 to set
	 */
	public void setHushold7(String hushold7) {
		this.hushold7 = hushold7;
	}


	/**
	 * @return the hushold7Fnr
	 */
	public String getHushold7Fnr() {
		return hushold7Fnr;
	}


	/**
	 * @param hushold7Fnr
	 *            the hushold7Fnr to set
	 */
	public void setHushold7Fnr(String hushold7Fnr) {
		this.hushold7Fnr = hushold7Fnr;
	}


	/**
	 * @return the hushold7Perskode
	 */
	public String getHushold7Perskode() {
		return hushold7Perskode;
	}


	/**
	 * @param hushold7Perskode
	 *            the hushold7Perskode to set
	 */
	public void setHushold7Perskode(String hushold7Perskode) {
		this.hushold7Perskode = hushold7Perskode;
	}


	/**
	 * @return the hushold8
	 */
	public String getHushold8() {
		return hushold8;
	}


	/**
	 * @param hushold8
	 *            the hushold8 to set
	 */
	public void setHushold8(String hushold8) {
		this.hushold8 = hushold8;
	}


	/**
	 * @return the hushold8Fnr
	 */
	public String getHushold8Fnr() {
		return hushold8Fnr;
	}


	/**
	 * @param hushold8Fnr
	 *            the hushold8Fnr to set
	 */
	public void setHushold8Fnr(String hushold8Fnr) {
		this.hushold8Fnr = hushold8Fnr;
	}


	/**
	 * @return the hushold8Perskode
	 */
	public String getHushold8Perskode() {
		return hushold8Perskode;
	}


	/**
	 * @param hushold8Perskode
	 *            the hushold8Perskode to set
	 */
	public void setHushold8Perskode(String hushold8Perskode) {
		this.hushold8Perskode = hushold8Perskode;
	}


	/**
	 * @return the hushold9
	 */
	public String getHushold9() {
		return hushold9;
	}


	/**
	 * @param hushold9
	 *            the hushold9 to set
	 */
	public void setHushold9(String hushold9) {
		this.hushold9 = hushold9;
	}


	/**
	 * @return the hushold9Fnr
	 */
	public String getHushold9Fnr() {
		return hushold9Fnr;
	}


	/**
	 * @param hushold9Fnr
	 *            the hushold9Fnr to set
	 */
	public void setHushold9Fnr(String hushold9Fnr) {
		this.hushold9Fnr = hushold9Fnr;
	}


	/**
	 * @return the hushold9Perskode
	 */
	public String getHushold9Perskode() {
		return hushold9Perskode;
	}


	/**
	 * @param hushold9Perskode
	 *            the hushold9Perskode to set
	 */
	public void setHushold9Perskode(String hushold9Perskode) {
		this.hushold9Perskode = hushold9Perskode;
	}


	/**
	 * @return the hushold10
	 */
	public String getHushold10() {
		return hushold10;
	}


	/**
	 * @param hushold10
	 *            the hushold10 to set
	 */
	public void setHushold10(String hushold10) {
		this.hushold10 = hushold10;
	}


	/**
	 * @return the hushold10Fnr
	 */
	public String getHushold10Fnr() {
		return hushold10Fnr;
	}


	/**
	 * @param hushold10Fnr
	 *            the hushold10Fnr to set
	 */
	public void setHushold10Fnr(String hushold10Fnr) {
		this.hushold10Fnr = hushold10Fnr;
	}


	/**
	 * @return the hushold10Perskode
	 */
	public String getHushold10Perskode() {
		return hushold10Perskode;
	}


	/**
	 * @param hushold10Perskode
	 *            the hushold10Perskode to set
	 */
	public void setHushold10Perskode(String hushold10Perskode) {
		this.hushold10Perskode = hushold10Perskode;
	}


	/**
	 * @return the avtaledato
	 */
	public String getAvtaledato() {
		return avtaledato;
	}


	/**
	 * @param avtaledato
	 *            the avtaledato to set
	 */
	public void setAvtaledato(String avtaledato) {
		this.avtaledato = avtaledato;
	}


	/**
	 * @return the avtaletid
	 */
	public String getAvtaletid() {
		return avtaletid;
	}


	/**
	 * @param avtaletid
	 *            the avtaletid to set
	 */
	public void setAvtaletid(String avtaletid) {
		this.avtaletid = avtaletid;
	}


	/**
	 * @return the avtaletype
	 */
	public Integer getAvtaletype() {
		return avtaletype;
	}


	/**
	 * @param avtaletype
	 *            the avtaletype to set
	 */
	public void setAvtaletype(Integer avtaletype) {
		this.avtaletype = avtaletype;
	}


	/**
	 * @return the avtalekomm
	 */
	public String getAvtalekomm() {
		return avtalekomm;
	}


	/**
	 * @param avtalekomm
	 *            the avtalekomm to set
	 */
	public void setAvtalekomm(String avtalekomm) {
		this.avtalekomm = avtalekomm;
	}


	/**
	 * @return the telefon1
	 */
	public String getTelefon1() {
		return telefon1;
	}


	/**
	 * @param telefon1
	 *            the telefon1 to set
	 */
	public void setTelefon1(String telefon1) {
		this.telefon1 = telefon1;
	}
	
	public String getKommentar1() {
		return kommentar1;
	}

	public void setKommentar1(String kommentar1) {
		this.kommentar1 = kommentar1;
	}

	public String getKilde1() {
		return kilde1;
	}

	public void setKilde1(String kilde1) {
		this.kilde1 = kilde1;
	}

	/**
	 * @return the telefon2
	 */
	public String getTelefon2() {
		return telefon2;
	}

	/**
	 * @param telefon2
	 *            the telefon2 to set
	 */
	public void setTelefon2(String telefon2) {
		this.telefon2 = telefon2;
	}
	
	public String getKommentar2() {
		return kommentar2;
	}

	public void setKommentar2(String kommentar2) {
		this.kommentar2 = kommentar2;
	}
	
	public String getKilde2() {
		return kilde2;
	}

	public void setKilde2(String kilde2) {
		this.kilde2 = kilde2;
	}

	/**
	 * @return the telefon3
	 */
	public String getTelefon3() {
		return telefon3;
	}


	/**
	 * @param telefon3
	 *            the telefon3 to set
	 */
	public void setTelefon3(String telefon3) {
		this.telefon3 = telefon3;
	}
	
	public String getKommentar3() {
		return kommentar3;
	}

	public void setKommentar3(String kommentar3) {
		this.kommentar3 = kommentar3;
	}

	public String getKilde3() {
		return kilde3;
	}

	public void setKilde3(String kilde3) {
		this.kilde3 = kilde3;
	}
	
	public String getSmsNummer() {
		return smsNummer;
	}

	public void setSmsNummer(String smsNummer) {
		this.smsNummer = smsNummer;
	}
	
	public String getReservasjon() {
		return reservasjon;
	}

	public void setReservasjon(String reservasjon) {
		this.reservasjon = reservasjon;
	}

	public Boolean getReservasjonAsBoolean() {
		if ("JA".equals(reservasjon.trim()) || "ja".equals(reservasjon.trim()) || "J".equals(reservasjon.trim()) || "j".equals(reservasjon.trim())){
			return true;
		}
		return false;
	}

	/**
	 * @return the epostadresse
	 */
	public String getEpostadresse() {
		return epostadresse;
	}


	/**
	 * @param epostadresse
	 *            the epostadresse to set
	 */
	public void setEpostadresse(String epostadresse) {
		this.epostadresse = epostadresse;
	}


	/**
	 * @return the nace
	 */
	public String getNace() {
		return nace;
	}


	/**
	 * @param nace
	 *            the nace to set
	 */
	public void setNace(String nace) {
		this.nace = nace;
	}


	/**
	 * @return the antallAnsatte
	 */
	public Long getAntallAnsatte() {
		return antallAnsatte;
	}


	/**
	 * @param antallAnsatte
	 *            the antallAnsatte to set
	 */
	public void setAntallAnsatte(Long antallAnsatte) {
		this.antallAnsatte = antallAnsatte;
	}


	/**
	 * @return the melding
	 */
	public String getMelding() {
		return melding;
	}


	/**
	 * @param melding
	 *            the melding to set
	 */
	public void setMelding(String melding) {
		this.melding = melding;
	}


	/**
	 * @return the overioNr
	 */
	public String getOverioNr() {
		return overioNr;
	}


	/**
	 * @param overioNr
	 *            the overioNr to set
	 */
	public void setOverioNr(String overioNr) {
		this.overioNr = overioNr;
	}


	/**
	 * @return the passordWeb
	 */
	public String getPassordWeb() {
		return passordWeb;
	}


	/**
	 * @param passordWeb
	 *            the passordWeb to set
	 */
	public void setPassordWeb(String passordWeb) {
		this.passordWeb = passordWeb;
	}


	/**
	 * @return the rotasjonGrp
	 */
	public String getRotasjonGrp() {
		return rotasjonGrp;
	}


	/**
	 * @param rotasjonGrp
	 *            the rotasjonGrp to set
	 */
	public void setRotasjonGrp(String rotasjonGrp) {
		this.rotasjonGrp = rotasjonGrp;
	}


	public Long getIntervjuObjektId() {
		return intervjuObjektId;
	}


	public void setIntervjuObjektId(Long intervjuObjektId) {
		this.intervjuObjektId = intervjuObjektId;
	}
	
	public Long getFullforingsGrad() {
		return fullforingsGrad;
	}


	public void setFullforingsGrad(Long fullforingsGrad) {
		this.fullforingsGrad = fullforingsGrad;
	}
	
	public String getKilde() {
		return kilde;
	}


	public void setKilde(String kilde) {
		this.kilde = kilde;
	}

	/**
	 * @return the maalform
	 */
	public String getMaalform() {
		return maalform;
	}


	/**
	 * @param maalform
	 *            the maalform to set
	 */
	public void setMaalform(String maalform) {
		this.maalform = maalform;
	}

	/**
	 * @return the varslingsstatus
	 */
	public String getVarslingsstatus() {
		return varslingsstatus;
	}


	/**
	 * @param varslingsstatus
	 *            the varslingsstatus to set
	 */
	public void setVarslingsstatus(String varslingsstatus) {
		this.varslingsstatus = varslingsstatus;
	}
}