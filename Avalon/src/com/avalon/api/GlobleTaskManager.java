package com.avalon.api;

/**
 * 全局调度任务管理器
 * 
 * @author zero
 *
 */

public interface GlobleTaskManager {
	/**
	 * 执行任务
	 * 
	 * @param runnable
	 *            可执行任务
	 */
	public CancellableTask scheduleGlobleOnceTask(Runnable runnable);

	/**
	 * 执行任务
	 * 
	 * @param delay
	 *            延时多少毫秒()
	 * @param runnable
	 *            可执行任务
	 */
	public CancellableTask scheduleGlobleOnceTask(long delay, Runnable runnable);

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
	public CancellableTask scheduleGlobleTask(long delay, long period, Runnable runnable);

}
