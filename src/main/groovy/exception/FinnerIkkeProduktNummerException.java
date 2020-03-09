package exception;

public class FinnerIkkeProduktNummerException extends Exception {

	protected String manglendeProduktNummer = null;
	
	public FinnerIkkeProduktNummerException(String manglendeProduktNummer, String message) {
		super(message);
		this.manglendeProduktNummer = manglendeProduktNummer;
	}
}
