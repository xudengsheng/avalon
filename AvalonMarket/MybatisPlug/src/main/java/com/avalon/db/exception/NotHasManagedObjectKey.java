package com.avalon.db.exception;

public class NotHasManagedObjectKey extends RuntimeException {

	private static final long serialVersionUID = 576704015766056169L;

	public NotHasManagedObjectKey(String message) {
		super(message);
	}

	public NotHasManagedObjectKey(String message, Throwable cause) {
		super(message, cause);
	}
}
