package exception;


public class DuplikatIntervjuObjektException extends Exception {
	
	private static final long serialVersionUID = -5179367208334904406L;

	public DuplikatIntervjuObjektException(String message) {
		super(message);
	}

    public DuplikatIntervjuObjektException(Throwable cause) {
        super(cause);
    }    

	public DuplikatIntervjuObjektException(String message, Throwable cause) {
		super(message, cause);
	}
}
