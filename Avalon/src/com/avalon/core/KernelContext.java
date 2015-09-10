package com.avalon.core;

import java.util.MissingResourceException;

import com.avalon.api.AppListener;
import com.avalon.api.GlobleTaskManager;
import com.avalon.api.internal.ComponentRegistry;
import com.avalon.api.internal.IService;
import com.avalon.component.ComponentRegistryImpl;
import com.avalon.core.service.SystemInfoService;
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
	private GlobleTaskManager globleTaskManager;
	private SystemInfoService infoService;
	protected final AvalonServerMode serverMode;

	protected final ComponentRegistry managerComponents;
	protected final ComponentRegistry serviceComponents;

	private AppListener appListener;

	KernelContext(KernelContext context)
	{
		this(context.applicationName, context.serviceComponents, context.managerComponents, context.propertieswrapper, context.serverMode);
	}

	protected KernelContext(String applicationName, ComponentRegistry serviceComponents, ComponentRegistry managerComponents,
			PropertiesWrapper propertieswrapper, AvalonServerMode serverMode)
	{
		this.applicationName = applicationName;
		this.serviceComponents = serviceComponents;
		this.managerComponents = managerComponents;
		this.propertieswrapper = propertieswrapper;
		this.serverMode = serverMode;
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

	public GlobleTaskManager getGlobleTaskManager()
	{
		return globleTaskManager;
	}

	public SystemInfoService getInfoService()
	{
		return infoService;
	}

	public void setInfoService(SystemInfoService infoService)
	{
		this.infoService = infoService;
	}

	public void setGlobleTaskManager(GlobleTaskManager globleTaskManager)
	{
		this.globleTaskManager = globleTaskManager;
	}

	public void setManager(IService type)
	{
		((ComponentRegistryImpl) managerComponents).addComponent(type);
	}

}
