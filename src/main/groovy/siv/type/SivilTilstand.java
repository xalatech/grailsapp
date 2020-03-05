package siv.type;

public enum SivilTilstand {
	GIFT("Gift"), UGIFT("Ugift"), SAMBOER("Samboer");

	private final String guiName;


	SivilTilstand(String guiName) {
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
