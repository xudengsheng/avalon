package com.avalon.exception;
/**
 * 无法找到对应的管理器
 * @author ZERO
 *
 */
public class ManagerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3861547344642024902L;

	public ManagerNotFoundException(String message)
	{
		super(message);
	}

	public ManagerNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
