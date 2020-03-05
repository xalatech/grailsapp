package siv.type;

public enum MeldingInnType {

	KONTAKT_INFORMASJON("Kontaktinformasjon"), STATUS("Status"), FULLFORT("Fullført"), INTERN_STATUS("Internstatus");

	private final String guiName;


	MeldingInnType(String guiName) {
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
