package siv.type;

public enum UndersokelsesType {
	PERSON("Person"), BEDRIFT("Bedrift"), HUSHOLDNING("Husholdning"), ADRESSE("Adresse");

	private final String guiName;


	UndersokelsesType(String guiName) {
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
