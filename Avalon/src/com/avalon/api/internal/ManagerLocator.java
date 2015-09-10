package com.avalon.api.internal;

import com.avalon.api.GlobleTaskManager;

/**
 * 外层API调用核心
 * 
 * @author ZERO
 * 
 */
public interface ManagerLocator {

	<T> T getManager(Class<T> type);

	/**
	 * 获得全局任务管理器
	 * 
	 * @return
	 */
	GlobleTaskManager getGlobleTaskManager();

	/**
	 * 放入自定义的服务（公用服务）
	 * 
	 * @param type
	 * @return
	 */
	void setManager(IService type);
}
