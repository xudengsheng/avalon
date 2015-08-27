package com.avalon.api.internal;
/**
 * 系统级组件服务
 * @author ZERO
 *
 */
public interface IService {
	/**
	 * 初始化
	 * @param obj
	 */
	public void init(Object obj);
	/**
	 * 销毁
	 * @param obj
	 */
	public void destroy(Object obj);
	/**
	 * 处理消息
	 * @param obj
	 */
	public void handleMessage(Object obj);
	/**
	 * 获得服务名称
	 * @return
	 */
	public String getName();
	/**
	 * 设置服务名称
	 * @param name
	 */
	public void setName(String name);
}
