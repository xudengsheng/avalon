package com.avalon.core;

import com.avalon.api.GlobleTaskManager;
import com.avalon.api.internal.ManagerLocator;


/**
 * 开放给逻辑层的内容管理器
 * 
 * @author zhaoxiaolong
 * 
 */
class ManagerLocatorImpl implements ManagerLocator {

	@Override
	public <T> T getManager(Class<T> type)
	{
		return ContextResolver.getManager(type);
	}

	@Override
	public GlobleTaskManager getGlobleTaskManager() {
		return ContextResolver.getGlobleTaskManager();
	}


}
