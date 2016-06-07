package com.avalon.db.exception;

public class NotManagedObject extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -602112024649387224L;

	public NotManagedObject(String message) {
		super(message);
	}

	public NotManagedObject(String message, Throwable cause) {
		super(message, cause);
	}
}
