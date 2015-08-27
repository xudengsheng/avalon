package com.avalon.core.message;

import java.io.Serializable;
/**
 * 全部消息的订阅
 * @author zero
 *
 */
public interface AvalonTopticMessage extends Serializable{
	/**
	 * 全局的ping测试
	 * @author zero
	 *
	 */
	public class Ping implements AvalonTopticMessage{

		private static final long serialVersionUID = -1575169505098162322L;
		
	}
	
}
