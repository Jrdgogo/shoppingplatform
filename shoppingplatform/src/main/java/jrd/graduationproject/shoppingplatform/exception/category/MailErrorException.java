package jrd.graduationproject.shoppingplatform.exception.category;

import jrd.graduationproject.shoppingplatform.exception.OpenJarException;

public class MailErrorException extends OpenJarException {

	
	public MailErrorException() {
		super();
	}
	public MailErrorException(String message) {
        super(message);
    }
	public MailErrorException(String message, Throwable cause) {
        super(message, cause);
    }
	public MailErrorException(Throwable cause) {
		super(cause);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
