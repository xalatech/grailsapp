package siv.type;

public enum IntervjuerStatus {
	AKTIV("Aktiv"), SLUTTET("Sluttet"), OPPLAERING("Opplæring"), PERMISJON("Permisjon");

	private final String guiName;


	IntervjuerStatus(String guiName) {
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
