package com.avalon.core.message;

import java.io.Serializable;

/**
 * 操作avalon的事件消息
 * 
 * @author zero
 *
 */
public interface AvalonMessage extends Serializable {
	/**
	 * 初始化Avalon
	 * @author zero
	 *
	 */
	public static class InitAvalon implements TransportSupervisorMessage {

		private static final long serialVersionUID = 5353260825760665736L;
		
	}



	

}
