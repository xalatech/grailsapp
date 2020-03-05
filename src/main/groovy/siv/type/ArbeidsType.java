package siv.type;

public enum ArbeidsType {
	
	INTERVJUE("Intervjuarbeid", "Intervjuarbeid"),
	SPORING("Sporing", "Sporing"),
	KURS("Briefing/kurs", "Kurs"),
	TRENTE_LESTE_INSTRUKS("Øving/opplæring", "Øving/opplæring"),
	MØTE("Møte", "Møte"),
	TESTET_SKJEMA("Teste skjema", "Teste skjema"),
	ARBEIDSLEDELSE("Arbeidsledelse", "Arbeidsledelse"),
	REISE("Reise", "Reise"), 
	ANNET("Annet", "Annet");
	
	private final String guiName;
	private final String guiShortName;


	ArbeidsType(String guiName, String guiShortName) {
		this.guiName = guiName;
		this.guiShortName = guiShortName;
	}


	public String getGuiName() {
		return guiName;
	}


	public String getGuiShortName() {
		return guiShortName;
	}


	public String toString() {
		return guiName;
	}


	String getKey() {
		return name();
	}
}
