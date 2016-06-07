package com.avalon.db;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avalon.db.api.IMybatisCommonHandler;
import com.avalon.db.api.IMybatisCommonMapper;
import com.avalon.db.api.ManagedObject;
import com.avalon.db.api.ManagedReference;

public abstract class MybatisCommonDBHandler implements IMybatisCommonHandler {

	Logger logger = LoggerFactory.getLogger(getClass());

	// // 缓存对象
	final SqlSessionFactory sqlSessionFactory;

	// Mybatis查询辅助类
	final Class<?> mapper;

	final AtomicLong Idsed;

	public synchronized long getNextId() {
		return Idsed.getAndIncrement();
	}

	/**
	 * 
	 * @param sqlSessionFactory
	 *            数据库session
	 * @param mapper
	 *            持久化
	 * @param IdSed
	 *            初始化Id因子
	 */
	public MybatisCommonDBHandler(SqlSessionFactory sqlSessionFactory, Class<?> mapper, long IdSed) {
		super();
		this.sqlSessionFactory = sqlSessionFactory;
		this.mapper = mapper;
		this.Idsed = new AtomicLong(IdSed);
		if (logger.isDebugEnabled()) {
			logger.debug(mapper.getSimpleName() + " 初始化Id seed " + IdSed);
		}
	}

	public int delete(ManagedObject object) {
		SqlSession openSession = null;
		int deleteByPrimaryKey = 0;
		try {
			openSession = sqlSessionFactory.openSession();
			IMybatisCommonMapper sessiomMapper = (IMybatisCommonMapper) openSession.getMapper(mapper);
			deleteByPrimaryKey = sessiomMapper.deleteByPrimaryKey(object.getId());
			openSession.commit();
		} catch (Exception e) {
			logger.error(object.toString(), e);
		} finally {
			if (openSession != null) {
				openSession.close();
			}
		}
		return deleteByPrimaryKey;
	}

	/**
	 * 直接更新写入数据库
	 */
	public int insert(ManagedObject object) {
		SqlSession openSession = null;

		int result = 0;

		try {
			openSession = sqlSessionFactory.openSession();
			if (object.getId() == 0) {
				object.setPid(getNextId());
			}
			IMybatisCommonMapper sessiomMapper = (IMybatisCommonMapper) openSession.getMapper(mapper);
			result = sessiomMapper.insert(object);

			openSession.commit();
		} catch (Exception e) {
			logger.error(object.toString(), e);
		} finally {
			if (openSession != null) {
				openSession.close();
			}
		}
		return result;
	}

	public int batchUpdate(Collection<ManagedObject> objects) {
		SqlSession openSession = null;

		int result = 0;

		try {
			openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
			for (ManagedObject managedObject : objects) {
				IMybatisCommonMapper sessiomMapper = (IMybatisCommonMapper) openSession.getMapper(mapper);
				result += sessiomMapper.updateByPrimaryKeySelective(managedObject);
			}
			openSession.commit();
		} catch (Exception e) {
			logger.error("批处理出现问题", e);
		} finally {
			if (openSession != null) {
				openSession.close();
			}
		}
		return result;
	}

	public int update(ManagedObject object) {
		SqlSession openSession = null;

		int result = 0;

		try {
			openSession = sqlSessionFactory.openSession();
			IMybatisCommonMapper sessiomMapper = (IMybatisCommonMapper) openSession.getMapper(mapper);
			result = sessiomMapper.updateByPrimaryKeySelective(object);
			openSession.commit();
		} catch (Exception e) {
			logger.error(object.toString(), e);
		} finally {
			if (openSession != null) {
				openSession.close();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> ManagedReference<T> getReferenceFromDB(long id) {
		SqlSession openSession = null;
		@SuppressWarnings("rawtypes")
		ManagedReference createReference = null;
		try {
			openSession = sqlSessionFactory.openSession();

			Object sessiomMapper = openSession.getMapper(mapper);

			if (sessiomMapper instanceof IMybatisCommonMapper) {
				Object selectByPrimaryKey = ((IMybatisCommonMapper) sessiomMapper).selectByPrimaryKey(id);
				createReference = queryCreateReference(selectByPrimaryKey);
				addReference(createReference);
			}
		} catch (Exception e) {
			logger.error("getReference error", e);
		} finally {
			if (openSession != null) {
				openSession.close();
			}
		}
		return createReference;

	}

	public int batchDelete(Collection<ManagedObject> objects) {
		SqlSession openSession = null;

		int result = 0;

		try {
			openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
			for (ManagedObject managedObject : objects) {
				managedObject.setPid(getNextId());
				IMybatisCommonMapper sessiomMapper = (IMybatisCommonMapper) openSession.getMapper(mapper);
				result += sessiomMapper.deleteByPrimaryKey(managedObject.getId());
			}
			openSession.commit();
		} catch (Exception e) {
			logger.error("批处理出现问题", e);
		} finally {
			if (openSession != null) {
				openSession.close();
			}
		}
		return result;
	}

	public abstract void setDelete(ManagedObject object);

	public abstract void mark(ManagedObject object);

	/**
	 * 创建新的持久对象
	 */
	public abstract <T> ManagedReference<T> createReference(T object);

	/**
	 * 创建一个封装类
	 */
	public abstract <T> ManagedReference<T> queryCreateReference(T object);

	/**
	 * 添加一个封装类
	 * 
	 * @param reference
	 */
	public <T> void addReference(ManagedReference<T> reference) {
		
	};
}
