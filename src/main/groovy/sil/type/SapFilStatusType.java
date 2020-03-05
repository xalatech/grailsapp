package sil.type;

public enum SapFilStatusType {
	OK("Ok"), FEILET("Feilet"), REGENERERT("Regenerert");

	private final String guiName;


	SapFilStatusType(String guiName) {
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