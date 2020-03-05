package siv.type;

public enum FravaerType {

	EGENMELDING("Egenmelding"),
	SYKEMELDING("Sykemelding"),
	ANNET("Annet"),
	PC_TLF_PROB("PC og telefonproblem"),
	PERM("Permisjon"),
	PERM_BARN("Permisjon barn"),
	FERIE("Ferie");

	private final String guiName;


	FravaerType(String guiName) {
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
