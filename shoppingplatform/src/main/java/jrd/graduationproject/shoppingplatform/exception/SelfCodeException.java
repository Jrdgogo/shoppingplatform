package jrd.graduationproject.shoppingplatform.exception;

public abstract class SelfCodeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SelfCodeException() {
		super();
	}
	public SelfCodeException(String message) {
        super(message);
    }
	public SelfCodeException(String message, Throwable cause) {
        super(message, cause);
    }
	public SelfCodeException(Throwable cause) {
		super(cause);
	}
}
