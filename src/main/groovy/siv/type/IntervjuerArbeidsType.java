package siv.type;

public enum IntervjuerArbeidsType {
	BESOK("Besøk"), TELEFON("Telefon"), BEGGE("Begge");

	private final String guiName;


	IntervjuerArbeidsType(String guiName) {
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
