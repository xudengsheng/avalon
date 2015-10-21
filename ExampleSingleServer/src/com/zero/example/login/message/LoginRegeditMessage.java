package com.zero.example.login.message;

import java.io.Serializable;

import com.example.protocol.javabean.CS_RegeditJavaBean;
import com.zero.example.SessionLisenter;

public class LoginRegeditMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7323740909908572033L;
	
	public final CS_RegeditJavaBean javaBean;
	public final SessionLisenter lisenterProcess;

	public LoginRegeditMessage(CS_RegeditJavaBean javaBean, SessionLisenter lisenterProcess) {
		super();
		this.javaBean = javaBean;
		this.lisenterProcess = lisenterProcess;
	}

}
