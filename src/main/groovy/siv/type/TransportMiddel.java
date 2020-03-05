package siv.type;

public enum TransportMiddel {
	EGEN_BIL("Egen bil", true),
	LEIEBIL("Leiebil", false),
	BUSS_TRIKK("Buss/trikk", false),
	TOG("Tog", false),
	FERJE("Ferje", false),
	MOTOR_BAAT("Båt", true),
	MOTORSYKKEL("Motorsykkel", true),
	SNOSCOOTER("Snøscooter", true),
	TAXI("Taxi", false),
	MOPED_SYKKEL("Moped/sykkel", true),
	GIKK("Gikk", false);
	
	private final String guiName;
	private final boolean isKm;


	TransportMiddel(String guiName, boolean isKm) {
		this.guiName = guiName;
		this.isKm = isKm;
	}


	public String getGuiName() {
		return guiName;
	}


	public boolean isKm() {
		return isKm;
	}


	public boolean getIsKm() {
		return isKm;
	}


	public String toString() {
		return guiName;
	}


	String getKey() {
		return name();
	}
}