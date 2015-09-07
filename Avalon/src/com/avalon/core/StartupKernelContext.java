package com.avalon.core;

import com.avalon.api.internal.IService;
import com.avalon.component.ComponentRegistryImpl;
import com.avalon.setting.AvalonServerMode;
import com.avalon.util.PropertiesWrapper;


final class StartupKernelContext extends KernelContext {

	public StartupKernelContext(String applicationName, ComponentRegistryImpl systemRegistry,PropertiesWrapper propertieswrapper,AvalonServerMode serverMode)
	{
		super(applicationName, systemRegistry, new ComponentRegistryImpl(),propertieswrapper,serverMode);
	}

	void addManager(IService manager)
	{
		((ComponentRegistryImpl) managerComponents).addComponent(manager);
	}

	void addService(IService service)
	{
		((ComponentRegistryImpl) serviceComponents).addComponent(service);
	}

}
