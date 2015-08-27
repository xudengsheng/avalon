package com.avalon.core;


import com.avalon.api.AppListener;
import com.avalon.util.PropertiesWrapper;


public final class ContextResolver {

	private static KernelContext context;

	private ContextResolver()
	{}
	
	public static PropertiesWrapper getPropertiesWrapper()
	{
		return context.getPropertiesWrapper();
	}
	
	public static AppListener getAppListener()
	{
		return context.getAppListener();
	}

	public static <T> T getManager(Class<T> type)
	{
		return context.getManager(type);
	}

	public static <T> T getComponent(Class<T> type)
	{
		return context.getComponent(type);
	}

	static KernelContext getContext()
	{
		return context;
	}

	static void setTaskState(KernelContext ctx)
	{
		context = ctx;
	}


}
