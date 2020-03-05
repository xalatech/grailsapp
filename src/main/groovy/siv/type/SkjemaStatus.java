package siv.type;

public enum SkjemaStatus {
	Innlastet("Innlastet"),
	Ubehandlet("Ubehandlet"),
	Pabegynt("Påbegynt"),
	Ferdig("Ferdig"),
	Paa_vent("På vent"),
	Utsendt_CATI("Utsendt CATI"),
	Utsendt_CATI_WEB("Utsendt CATI web"),
	Utsendt_CAPI("Utsendt CAPI"),
	Utsendt_WEB("Utsendt web"),
	Reut_CATI("Nektere på vent");
	
	private final String guiName;

	SkjemaStatus(String guiName) {
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
