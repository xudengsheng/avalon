package com.avalon.api.internal;


/**
 * 系统组件管理器
 * 
 * @author ZERO
 * 
 */
public interface ComponentRegistry extends Iterable<IService> {
	/**
	 * 根据CLASS类型获得已经注册的组件
	 * 
	 * @param type
	 * @return
	 */
	<T> T getComponent(Class<T> type);

}
