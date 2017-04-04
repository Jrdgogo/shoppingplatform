package jrd.graduationproject.shoppingplatform.exception.category;

import jrd.graduationproject.shoppingplatform.exception.OpenJarException;

public class NotSaveException extends OpenJarException {

	
	public NotSaveException() {
		super();
	}
	public NotSaveException(String message) {
        super(message);
    }
	public NotSaveException(String message, Throwable cause) {
        super(message, cause);
    }
	public NotSaveException(Throwable cause) {
		super(cause);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
