package com.zero.example.login.message;

import com.avalon.api.internal.IoMessage;

public class LoginRegeditBackMessage implements IoMessage {

	private static final long serialVersionUID = -4413615691395723309L;

	public final boolean success;

	public LoginRegeditBackMessage(boolean success) {
		super();
		this.success = success;
	}

}
