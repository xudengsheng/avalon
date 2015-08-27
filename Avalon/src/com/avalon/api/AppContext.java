package com.avalon.api;

import com.avalon.api.internal.InternalContext;
import com.avalon.exception.ManagerNotFoundException;


/**
 * 引擎应用的上下文
 * 
 * @author zhaoxiaolong
 * 
 */
public final class AppContext {

	private AppContext()
	{}

	public static <T> T getManager(Class<T> type)
	{
		try
		{
			return InternalContext.getManagerLocator().getManager(type);
		} catch (IllegalStateException ise)
		{
			throw new ManagerNotFoundException("ManagerLocator is " + "unavailable", ise);
		}
	}

	
}
