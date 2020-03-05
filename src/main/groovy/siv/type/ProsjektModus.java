package siv.type;

public enum ProsjektModus {
	ENMODUS("En"), MIXMODUS("Mix"), MULTIMODUS("Multi");

	private final String guiName;


	ProsjektModus(String guiName) {
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
