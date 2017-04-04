package jrd.graduationproject.shoppingplatform.exception;

public class UserOptionErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserOptionErrorException() {
		super();
	}
	public UserOptionErrorException(String message) {
        super(message);
    }
	public UserOptionErrorException(String message, Throwable cause) {
        super(message, cause);
    }
	public UserOptionErrorException(Throwable cause) {
		super(cause);
	}
}
