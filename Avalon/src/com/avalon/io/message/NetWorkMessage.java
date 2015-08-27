package com.avalon.io.message;

import java.io.Serializable;

public interface NetWorkMessage extends Serializable {
	public class SessionOnline implements NetWorkMessage {
		private static final long serialVersionUID = -5827345826280076750L;
	}
	
	public class SessionOutline implements NetWorkMessage {
		private static final long serialVersionUID = -6858332163554289611L;
	}
}
