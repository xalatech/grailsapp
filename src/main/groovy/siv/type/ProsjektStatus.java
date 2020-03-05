package siv.type;

public enum ProsjektStatus {
	PLANLEGGING("Planlegging"), TEST("Test"), AKTIV("Aktiv"), AVSLUTTET("Avsluttet");

	private final String guiName;


	ProsjektStatus(String guiName) {
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
