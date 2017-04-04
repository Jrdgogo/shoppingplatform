package jrd.graduationproject.shoppingplatform.exception;

public abstract class OpenJarException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OpenJarException() {
		super();
	}
	public OpenJarException(String message) {
        super(message);
    }
	public OpenJarException(String message, Throwable cause) {
        super(message, cause);
    }
	public OpenJarException(Throwable cause) {
		super(cause);
	}
}
