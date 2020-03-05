package siv.type;

public enum ProsjektFinansiering {
	STAT("Stat"), MARKED("Marked"), STAT_MARKED("Stat/marked");

	private final String guiName;


	ProsjektFinansiering(String guiName) {
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