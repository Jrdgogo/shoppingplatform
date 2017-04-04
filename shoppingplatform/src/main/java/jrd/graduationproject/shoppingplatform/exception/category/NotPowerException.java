package jrd.graduationproject.shoppingplatform.exception.category;

import jrd.graduationproject.shoppingplatform.exception.UserOptionErrorException;

public class NotPowerException extends UserOptionErrorException {

	public NotPowerException() {
		super();
	}

	public NotPowerException(String message) {
		super(message);
	}

	public NotPowerException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotPowerException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}