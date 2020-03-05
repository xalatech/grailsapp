package siv.type;

public enum TelefonType {
	PRIVAT("Privat"), JOBB("Jobb"), MOBIL("Mobil"), MOR_FAR("Mor/Far");
	
	private final String guiName;


	TelefonType(String guiName) {
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
