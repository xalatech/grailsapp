package siv.type;

public enum Kilde {
	CATI("CATI"),
	CAPI("CAPI"),
	WEB("WEB");
	
	private final String guiName;

	Kilde(String guiName) {
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