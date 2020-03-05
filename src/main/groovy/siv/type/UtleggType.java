package siv.type;

public enum UtleggType {
	BILLETT("Billett"),
	BOMPENGER("Bompenger"),
	PARKERING("Parkering"),
	TAXI("Taxi"),
	FRIMERKER("Frimerker"),
	KART("Kart"),
	TELEFON("Telefon"),
	KOST_GODT("Kostgodtgjørelse"),
	ANNET("Annet");
	
	// Billett, bompenger og telefon trenger ikke bilag
	
	private final String guiName;


	UtleggType(String guiName) {
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
