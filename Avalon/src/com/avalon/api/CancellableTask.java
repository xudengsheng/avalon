package com.avalon.api;

import java.io.Serializable;

/**
 * 取消任务
 * @author zero
 *
 */
public interface CancellableTask extends Serializable{
	/**
	 * 取消任务
	 */
	void cancel();

}
