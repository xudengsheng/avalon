package com.zero.example.login.message;

import com.avalon.api.internal.IoMessage;

public class LoginBackMessage implements IoMessage {

	private static final long serialVersionUID = -7672767038563741024L;

	public final int state;

	public final byte[] bs;

	public LoginBackMessage(int state) {
		super();
		this.state = state;
		this.bs = null;
	}

	public LoginBackMessage(int state, byte[] bs) {
		super();
		this.state = state;
		this.bs = bs;
	}

}
