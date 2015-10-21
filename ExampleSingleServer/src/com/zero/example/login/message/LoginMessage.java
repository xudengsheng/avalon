package com.zero.example.login.message;

import java.io.Serializable;

import com.example.protocol.javabean.CS_LoginJavaBean;
import com.zero.example.SessionLisenter;

public class LoginMessage implements Serializable {
	private static final long serialVersionUID = -1072875717701111611L;
	
	public final CS_LoginJavaBean bean;
	public final SessionLisenter lisenterProcess;

	public LoginMessage(CS_LoginJavaBean bean, SessionLisenter lisenterProcess) {
		super();
		this.bean = bean;
		this.lisenterProcess = lisenterProcess;
	}

}
