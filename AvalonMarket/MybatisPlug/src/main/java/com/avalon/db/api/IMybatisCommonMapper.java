package com.avalon.db.api;

/**
 * Mybatis数据库操作接口
 * 
 * @author zero
 *
 */
public interface IMybatisCommonMapper {
	/**
	 * 插入数据库
	 * 
	 * @param record
	 * @return
	 */
	int insert(Object record);

	/**
	 * 主键删除
	 * 
	 * @param itempid
	 * @return
	 */
	int deleteByPrimaryKey(Long itempid);

	/**
	 * 主键查询
	 * 
	 * @param itempid
	 * @return
	 */
	Object selectByPrimaryKey(Long itempid);

	/**
	 * 主键更新对象
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(Object record);
}
