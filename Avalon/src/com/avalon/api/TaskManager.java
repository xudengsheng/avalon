package com.avalon.api;

import akka.actor.Cancellable;

/**
 * 单独的任务管理器
 * 在单独的节点的任务
 * @author zero
 *
 */
public interface TaskManager {
	/**
	 * 执行任务
	 * 
	 * @param runnable
	 *            可执行任务
	 */
	public Cancellable scheduleOnceTask(Runnable runnable);

	/**
	 * 执行任务
	 * 
	 * @param delay
	 *            延时多少毫秒()
	 * @param runnable
	 *            可执行任务
	 */
	public Cancellable scheduleOnceTask(long delay, Runnable runnable);

	/**
	 * 执行周期任务
	 * 
	 * @param delay
	 *            延时多少毫秒
	 * @param period
	 *            多少毫秒为一个周期
	 * @param runnable
	 *            可执行任务
	 */
	public Cancellable scheduleTask(long delay, long period, Runnable runnable);
}
