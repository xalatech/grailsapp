package siv.type;

public enum ResultatStatus {
	NEKTERE("Nektere"), 
	NEKTERE_TIL_REUTSENDING("Nektere til reutsending"),
	TIL_SPORING("Til sporing"),
	KLARERT_FOR_REUTSENDING("Klarert for reutsending"),
	OVERFORING("Overføring"),
	AVGANG("Avgang"),
	LANGVARIG_SYK("Langvarig syk"),
	SPRAK_PROBLEMER("Språk problemer"),
	ANNET_FRAFALL("Annet frafall"),
	PAA_VENT("På vent"),
	PARKERTE("Parkerte"),
	FERDIGE("Ferdige"), 
	INTERVJU("Intervju"),
	ALLE_IKKE_FERDIGE("Alle ikke ferdige");
	
	private final String guiName;


	ResultatStatus(String guiName) {
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
