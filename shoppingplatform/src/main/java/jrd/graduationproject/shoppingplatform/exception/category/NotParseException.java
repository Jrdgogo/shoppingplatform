package jrd.graduationproject.shoppingplatform.exception.category;

import jrd.graduationproject.shoppingplatform.exception.SelfCodeException;

public class NotParseException extends SelfCodeException{

	public NotParseException() {
		super();
	}

	public NotParseException(String message) {
		super(message);
	}

	public NotParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotParseException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}