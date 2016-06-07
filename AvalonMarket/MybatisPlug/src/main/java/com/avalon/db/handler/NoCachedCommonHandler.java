package com.avalon.db.handler;

import org.apache.ibatis.session.SqlSessionFactory;

import com.avalon.db.MyBatisReference;
import com.avalon.db.MybatisCommonDBHandler;
import com.avalon.db.api.ManagedObject;
import com.avalon.db.api.ManagedReference;
import com.avalon.db.status.DBState;

public class NoCachedCommonHandler extends MybatisCommonDBHandler {

	public NoCachedCommonHandler(SqlSessionFactory sqlSessionFactory, Class<?> mapper, long IdSed) {
		super(sqlSessionFactory, mapper, IdSed);
	}

	@Override
	public void setDelete(ManagedObject object) {
		delete(object);
	}

	@Override
	public void mark(ManagedObject object) {
		update(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ManagedReference<T> createReference(T object) {
		if (object instanceof ManagedObject) {
			MyBatisReference<ManagedObject> myBatisReference = new MyBatisReference<ManagedObject>((ManagedObject) object);
			int insert = insert((ManagedObject) object);
			if (insert != 0) {
				return (ManagedReference<T>) myBatisReference;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> ManagedReference<T> queryCreateReference(T object) {
		MyBatisReference myBatisReference = new MyBatisReference<T>(object);
		myBatisReference.changeState(DBState.DB_STATE_NORMAL);
		return myBatisReference;
	}

	@Override
	public <T> ManagedReference<T> getReference(long id) {
		return getReferenceFromDB(id);
	}

}
