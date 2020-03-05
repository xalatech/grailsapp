package siv.type;

public enum AdresseType {
	BESOK("Besøk"), POST("Post");

	private final String guiName;


	AdresseType(String guiName) {
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
