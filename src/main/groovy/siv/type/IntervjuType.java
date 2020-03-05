package siv.type;

public enum IntervjuType {
	TELEFON("Telefon"), BESOK("Besøk"), PAPIR("Papir"), WEB("Web"), TELEFON_OG_PAPIR("Telefon og papri"), CAPI_TELEFON(
			"Capi telefon");

	private final String guiName;


	IntervjuType(String guiName) {
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
