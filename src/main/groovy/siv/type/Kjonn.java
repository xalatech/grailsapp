package siv.type;

public enum Kjonn {
	MANN("Mann"), KVINNE("Kvinne");

	private final String guiName;


	Kjonn(String guiName) {
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
