package sil.type;

public enum KravStatus {
	OPPRETTET("Opprettet", "Opprettet"),
	BESTOD_AUTOMATISK_KONTROLL("Bestod auto kont.", "Bestod automatiske kontroller"),
	FEILET_AUTOMATISK_KONTROLL("Feilet auto kont.", "Feilet automatiske kontroller"),
	TIL_MANUELL_KONTROLL("Manuell kont.", "Til manuell kontroll"),
	SENDES_TIL_INTERVJUER("Sendes intervjuer.", "Sendes intervjuer"),
	TIL_RETTING_INTERVJUER("Rettes av interv.", "Til retting av intervjuer"),
	RETTET_AV_INTERVJUER("Rettet av intervj.", "Rettet av intervjuer"),
	INAKTIV("Inaktiv", "Inaktiv"),
	GODKJENT("Godkjent", "Godkjent"),
	AVVIST("Avvist", "Avvist"),
	OVERSENDT_SAP("Oversendt SAP", "Oversendt SAP");
	
	private final String guiShortName;
	private final String guiLongName;


	KravStatus(String shortName, String longName) {
		this.guiShortName = shortName;
		this.guiLongName = longName;
	}


	public String getGuiShortName() {
		return guiShortName;
	}


	public String getGuiLongName() {
		return guiLongName;
	}


	public String toString() {
		return guiShortName;
	}
	
	public String plus(KravStatus k) {
		return k.toString();
	} 


	String getKey() {
		return name();
	}
}