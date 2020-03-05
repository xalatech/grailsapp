package sil.type;

public enum KravType {
	T("Time"), K("Kjørebok"), U("Utlegg");

	private final String guiName;


	KravType(String guiName) {
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