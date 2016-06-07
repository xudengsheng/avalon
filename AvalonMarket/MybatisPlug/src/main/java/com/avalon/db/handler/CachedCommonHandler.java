package com.avalon.db.handler;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSessionFactory;

import com.avalon.db.MyBatisReference;
import com.avalon.db.MybatisCommonDBHandler;
import com.avalon.db.api.ManagedObject;
import com.avalon.db.api.ManagedReference;
import com.avalon.db.status.DBState;

public class CachedCommonHandler extends MybatisCommonDBHandler {

	public CachedCommonHandler(SqlSessionFactory sqlSessionFactory, Class<?> mapper, long IdSed) {
		super(sqlSessionFactory, mapper, IdSed);
	}

	@SuppressWarnings("rawtypes")
	Map<Long, ManagedReference> trusteeshipManaged = new ConcurrentHashMap<Long, ManagedReference>();

	@SuppressWarnings("rawtypes")
	@Override
	public void setDelete(ManagedObject object) {
		if (trusteeshipManaged.containsKey(object.getId())) {
			MyBatisReference managedReference = (MyBatisReference) trusteeshipManaged.get(object.getId());
			managedReference.changeState(DBState.DB_STATE_DELETE);
		} else {
			throw new RuntimeException("not in manager" + object);
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public void mark(ManagedObject object) {
		if (trusteeshipManaged.containsKey(object.getId())) {
			MyBatisReference managedReference = (MyBatisReference) trusteeshipManaged.get(object.getId());
			if (managedReference.getfState().equals(DBState.DB_STATE_CREATE)) {
				insert(object);
				managedReference.changeState(DBState.DB_STATE_NORMAL);
			} else {
				managedReference.changeState(DBState.DB_STATE_UPDATE);
			}
		} else {
			throw new RuntimeException("not in manager" + object);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ManagedReference<T> createReference(T object) {
		if (object instanceof ManagedObject) {
			MyBatisReference<ManagedObject> myBatisReference = new MyBatisReference<ManagedObject>(
					(ManagedObject) object);
			int insert = insert((ManagedObject) object);
			if (insert != 0) {
				myBatisReference.changeState(DBState.DB_STATE_NORMAL);
				trusteeshipManaged.put(myBatisReference.getId(), myBatisReference);
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
		trusteeshipManaged.put(myBatisReference.getId(), myBatisReference);
		return myBatisReference;
	}

	@Override
	public <T> void addReference(ManagedReference<T> reference) {
		trusteeshipManaged.put(reference.getId(), reference);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ManagedReference<T> getReference(long id) {
		if (trusteeshipManaged.containsKey(id)) {
			@SuppressWarnings("rawtypes")
			MyBatisReference managedReference = (MyBatisReference) trusteeshipManaged.get(id);
			if (managedReference.getfState().equals(DBState.DB_STATE_DELETE)) {
				return null;
			} else {
				return managedReference;
			}
		}
		return getReferenceFromDB(id);
	}

	@SuppressWarnings("rawtypes")
	public final Collection<ManagedReference> getNeedUpdateObjects() {
		Collection<ManagedReference> collection = new LinkedList<ManagedReference>();
		for (Entry<Long, ManagedReference> managedObject : trusteeshipManaged.entrySet()) {
			if (((MyBatisReference) managedObject.getValue()).getfState().equals(DBState.DB_STATE_UPDATE)) {
				collection.add(managedObject.getValue());
			}
		}
		return collection;
	}

	@SuppressWarnings("rawtypes")
	public final Collection<ManagedReference> getNeedDeleteObjects() {
		Collection<ManagedReference> collection = new LinkedList<ManagedReference>();
		for (Entry<Long, ManagedReference> managedObject : trusteeshipManaged.entrySet()) {
			if (((MyBatisReference) managedObject.getValue()).getfState().equals(DBState.DB_STATE_DELETE)) {
				collection.add(managedObject.getValue());
			}
		}
		return collection;
	}

	public void removes(Collection<ManagedObject> deletes) {
		for (ManagedObject managedObject : deletes) {
			trusteeshipManaged.remove(managedObject.getId());
		}
	}

	@SuppressWarnings("rawtypes")
	public void checkExprie() {
		Collection<ManagedReference> collection = new LinkedList<ManagedReference>();
		for (Entry<Long, ManagedReference> managedObject : trusteeshipManaged.entrySet()) {
			if (((MyBatisReference) managedObject.getValue()).isExpire()
					&& ((MyBatisReference) managedObject.getValue()).equals(DBState.DB_STATE_NORMAL)) {
				collection.add(managedObject.getValue());
			}
		}
		for (ManagedReference managedReference : collection) {
			trusteeshipManaged.remove(managedReference.getId());
		}
	}

}
