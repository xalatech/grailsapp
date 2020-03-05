package siv.type;

public enum TimeforingProdukt {
	INTERVJUING("Intervjuing"), REISING("Reising"), KURS("Kurs"), LESE_RETNINGSLINJER("Lese retningslinjer");
	
	private final String guiName;


	TimeforingProdukt(String guiName) {
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
