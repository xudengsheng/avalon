package com.zero.example.message;

import java.io.Serializable;

import com.zero.example.SessionLisenter;

/**
 * 对象管理
 * 
 * @author zero
 *
 */
public interface ObjectManagerMesage extends Serializable {
	/**
	 * 角色登录，验证的部分在loginManager处理
	 * 
	 * @author zero
	 *
	 */
	public class UserLogin implements ObjectManagerMesage {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4514813543152074330L;
		public final String name;
		public final SessionLisenter lisenterProcess;

		public UserLogin(String name, SessionLisenter lisenterProcess) {
			super();
			this.name = name;
			this.lisenterProcess = lisenterProcess;
		}

	}
}
