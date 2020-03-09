package exception;

public class SivAdmException extends RuntimeException {

	private static final long serialVersionUID = -6450564219451879249L;


	public SivAdmException(String message) {
		super(message);
	}


	public SivAdmException(Throwable cause) {
		super(cause);
	}


	public SivAdmException(String message, Throwable cause) {
		super(message, cause);
	}

}
