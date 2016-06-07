package com.avalon.db.api;

import java.util.Collection;
import java.util.List;

import com.avalon.db.MybatisQureyHelper;

public interface MyBatisDataManager extends DataManager {
	/**
	 * 扩展查询
	 * 
	 * @param qureyHelper
	 * @return
	 */
	public <T> List<T> executeQuery(MybatisQureyHelper<T> qureyHelper);

	/**
	 * 根据查询获得对应的持久化对象的映射集合
	 * 
	 * @param object
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public <T> Collection<ManagedReference<T>> getReferences(Class<T> object, MybatisQureyHelper query);
}
