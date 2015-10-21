package com.zero.example.hall.message;

import com.avalon.api.internal.IoMessage;

public class UserJoinHallBack implements IoMessage {

	private static final long serialVersionUID = -773659379562867097L;
	public final byte[] bs;

	public UserJoinHallBack(byte[] byteArray) {
		this.bs = byteArray;
	}

}
