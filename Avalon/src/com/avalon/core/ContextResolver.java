package com.avalon.core;

import com.avalon.api.AppListener;
import com.avalon.api.DistributedTaskManager;
import com.avalon.api.internal.IService;
import com.avalon.setting.AvalonServerMode;
import com.avalon.util.PropertiesWrapper;

public final class ContextResolver {

	private static KernelContext context;

	private ContextResolver()
	{}

	public static PropertiesWrapper getPropertiesWrapper()
	{
		return context.getPropertiesWrapper();
	}

	public static AvalonServerMode getServerMode()
	{
		return context.getServerMode();
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

	public static DistributedTaskManager getGlobleTaskManager()
	{
		return context.getGlobleTaskManager();
	}

	public static void setManager(IService type)
	{
		context.setManager(type);
	}

}
