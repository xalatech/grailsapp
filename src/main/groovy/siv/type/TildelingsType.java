package siv.type;

public enum TildelingsType {
	
	ORDINAR("I"), OPPFOLGING("F"), OVERFORING("O"), EKSTRA("E");

	private final String key;


	TildelingsType(String key) {
		this.key = key;
	}


	public String toString() {
		return key;
	}
}
