package siv.type;

public enum PeriodeType {
	//Key'en kan ikke være lengre enn 4 tegn pga restriksjon i utvalgsfilen
	AAR("År"), KVRT("Kvartal"), MND("Måned"), UKE("Uke"), TILF("Tilfeldig");
	
	private final String guiName;


	PeriodeType(String guiName) {
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