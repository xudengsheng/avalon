package com.avalon.db.api;


/**
 * 数据管理
 * 
 * @author mac
 * 
 */
public interface DataManager {

	/**
	 * 创建一个持久化对象 object需要实现{@code ManagedObject}
	 * 
	 * @param object
	 * @return
	 */
	public <T> ManagedReference<T> createReference(T object);

	/**
	 * 根据唯一Id获得对应的持久化对象的映射
	 * 
	 * @param object
	 * @param id
	 * @return
	 */
	public <T> ManagedReference<T> getReference(Class<T> object, long id);



	/**
	 * 根据唯一Id删除对应的持久化对象的映射
	 * 
	 * @param object
	 * @param id
	 * @return
	 */
	public void removeObject(Object object);

	/**
	 * 标记映射对象的持久状态为需要更新
	 * 
	 * @param object
	 * @return
	 */
	public void markForUpdate(Object object);

	/**
	 * 获得绑定的对应对象
	 * 
	 * @param name
	 * @return
	 */
	public <T> T getCacheBindding(String name);

	/**
	 * 把某一个对象绑定到全局的key上
	 * 
	 * @param name
	 * @param object
	 */
	public void setCacheBinding(String name, Object object);

	/**
	 * 解除一个绑定
	 * 
	 * @param name
	 */
	public void removeCacheBinding(String name);

	
}
