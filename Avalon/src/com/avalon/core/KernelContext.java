package com.avalon.core;

import java.util.MissingResourceException;

import com.avalon.api.AppListener;
import com.avalon.api.GlobleTaskManager;
import com.avalon.api.internal.ComponentRegistry;
import com.avalon.exception.ManagerNotFoundException;
import com.avalon.setting.AvalonServerMode;
import com.avalon.util.PropertiesWrapper;

/**
 * 
 * @author ZERO
 *
 */
class KernelContext {

	private final String applicationName;
	protected final PropertiesWrapper propertieswrapper;
	protected final AvalonServerMode serverMode;
	
	
	protected final ComponentRegistry managerComponents;
	protected final ComponentRegistry serviceComponents;

	private AppListener appListener;
	private GlobleTaskManager globleTaskManager;
	
	KernelContext(KernelContext context)
	{
		this(context.applicationName, context.serviceComponents, context.managerComponents, context.propertieswrapper,context.serverMode);
	}

	protected KernelContext(String applicationName, ComponentRegistry serviceComponents, ComponentRegistry managerComponents,
			PropertiesWrapper propertieswrapper, AvalonServerMode serverMode)
	{
		this.applicationName = applicationName;
		this.serviceComponents = serviceComponents;
		this.managerComponents = managerComponents;
		this.propertieswrapper = propertieswrapper;
		this.serverMode = serverMode;
		
		GlobleTaskManager globleTaskManager;
		try
		{
			globleTaskManager = managerComponents.getComponent(GlobleTaskManager.class);
		} catch (MissingResourceException mre)
		{
			globleTaskManager = null;
		}
		this.globleTaskManager = globleTaskManager;

	}

	<T> T getManager(Class<T> type)
	{
		try
		{
			return managerComponents.getComponent(type);
		} catch (MissingResourceException mre)
		{
			throw new ManagerNotFoundException("couldn't find manager: " + type.getName());
		}
	}

	<T> T getComponent(Class<T> type)
	{
		return serviceComponents.getComponent(type);
	}

	public AppListener getAppListener()
	{
		return appListener;
	}

	public void setAppListener(AppListener appListener)
	{
		this.appListener = appListener;
	}

	public String toString()
	{
		return applicationName;
	}

	public PropertiesWrapper getPropertiesWrapper()
	{
		return propertieswrapper;
	}

	public AvalonServerMode getServerMode()
	{
		return serverMode;
	}

	public GlobleTaskManager getGlobleTaskManager() {
		return globleTaskManager;
	}

}
