package com.zero.example.message;

import com.avalon.api.internal.SerializableMessage;
import com.example.protocol.javabean.CS_LoginJavaBean;
import com.example.protocol.javabean.CS_RegeditJavaBean;
import com.zero.example.SessionLisenter;
/**
 * 用户登入，并没有对应的游戏角色
 * @author zero
 *
 */
public interface LoginManagerMessage extends SerializableMessage{
	/**
	 * 用户登录
	 * @author zero
	 *
	 */
	public class LoginMessage implements LoginManagerMessage {
		private static final long serialVersionUID = -1072875717701111611L;
		
		public final CS_LoginJavaBean bean;
		public final SessionLisenter lisenterProcess;

		public LoginMessage(CS_LoginJavaBean bean, SessionLisenter lisenterProcess) {
			super();
			this.bean = bean;
			this.lisenterProcess = lisenterProcess;
		}

	}
	/**
	 * 用户注册
	 * @author zero
	 *
	 */
	public class LoginRegeditMessage implements LoginManagerMessage {
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


}
