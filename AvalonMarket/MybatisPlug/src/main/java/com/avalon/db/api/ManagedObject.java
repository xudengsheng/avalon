package com.avalon.db.api;

import java.io.Serializable;

public interface ManagedObject extends Serializable {
	/**
	 * 数据对象的唯一Id
	 * 
	 * @return
	 */
	public long getId();

	/**
	 * 数据对象的唯一Id
	 * 
	 * @return
	 */
	void setPid(long pid);
}
