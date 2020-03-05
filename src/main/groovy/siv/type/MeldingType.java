package siv.type;

public enum MeldingType {
	ADRESSE("Adresse"), 
	STATUS("Status"), 
	TELEFON("Telefon"), 
	KOMMENTAR_TIL_INTERVJUER("Kommentar til intervjuer"), 
	PERSON("Person"),
	AKTIVER("Aktiver"),
	DEAKTIVER("De-aktiver"),
	CAPI("Capi");
		
	private final String guiName;


	MeldingType(String guiName) {
		this.guiName = guiName;
	}


	public String getGuiName() {
		return guiName;
	}


	public String toString() {
		return guiName;
	}


	String getKey() {
		return name();
	}
}
