package siv.type;

public enum TimeforingStatus {
	GODKJENT("Godkjent"), IKKE_GODKJENT("Ikke godkjent"), SENDT_INN("Sendt inn"), AVVIST("Avvist"), BEHANDLET("Behandlet");
	
	private final String guiName;


	TimeforingStatus(String guiName) {
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