package jrd.graduationproject.shoppingplatform.exception.category;

import jrd.graduationproject.shoppingplatform.exception.SelfCodeException;

public class NotFindEnumException extends SelfCodeException {

	public NotFindEnumException() {
		super();
	}
	public NotFindEnumException(String message) {
        super(message);
    }
	public NotFindEnumException(String message, Throwable cause) {
        super(message, cause);
    }
	public NotFindEnumException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
