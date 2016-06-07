package com.avalon.db.api;

public interface ManagedReference<T> extends Comparable<ManagedObject> {
	/**
	 * 获取元数据不锁定
	 * 
	 * @return
	 */
	public T get();

	/**
	 * 获取元数据并修改
	 * 
	 * @return
	 */
	public void modify();

	/**
	 * 数据超期可以从内存中删除
	 */
	public void expire();

	/**
	 * 获取唯一Id
	 * 
	 * @return
	 */
	public long getId();

	/**
	 * 是否是同一个对象
	 * 
	 * @param object
	 * @return
	 */
	public boolean equals(Object object);

	/**
	 * Hash码
	 * 
	 * @return
	 */
	public int hashCode();

}
