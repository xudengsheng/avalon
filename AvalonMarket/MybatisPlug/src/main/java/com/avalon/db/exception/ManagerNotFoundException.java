package com.avalon.db.exception;

public class ManagerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4332437193715858987L;

	public ManagerNotFoundException(String message) {
		super(message);
	}

	public ManagerNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}