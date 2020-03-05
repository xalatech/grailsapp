package siv.type;

public enum InfoHendelseType {
	RETNINGSLINJER("Retningslinjer"), KURS("Kurs");

	private final String guiName;


	InfoHendelseType(String guiName) {
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
