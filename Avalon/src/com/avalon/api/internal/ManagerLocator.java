package com.avalon.api.internal;

/**
 * 外层API调用核心
 * 
 * @author ZERO
 * 
 */
public interface ManagerLocator {

	<T> T getManager(Class<T> type);
}
