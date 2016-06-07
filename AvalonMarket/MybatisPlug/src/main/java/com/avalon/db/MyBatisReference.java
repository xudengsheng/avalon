package com.avalon.db;

import com.avalon.db.api.ManagedObject;
import com.avalon.db.api.ManagedReference;
import com.avalon.db.status.DBState;

public class MyBatisReference<T> implements ManagedReference<T> {

	private DBState fState = DBState.DB_STATE_NORMAL;

	boolean expire = false;

	public void changeState(DBState state) {
		this.fState = state;
	}

	public MyBatisReference(T object) {
		super();
		this.entity = object;
	}

	private T entity;

	public void remove() {
		this.fState = DBState.DB_STATE_DELETE;
	}

	@Override
	public T get() {
		return entity;
	}

	@Override
	public long getId() {
		if (entity == null) {
			return -1;
		}
		return ((ManagedObject) entity).getId();
	}

	@Override
	public boolean equals(Object obj) {
		boolean extracted = extracted(obj);
		return extracted;
	}

	private boolean extracted(Object obj) {
		try {
			return ((ManagedObject) entity).getId() == ((ManagedObject) obj).getId();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void modify() {
		if (!this.fState.equals(DBState.DB_STATE_CREATE)) {
			this.fState = DBState.DB_STATE_UPDATE;
		}
	}

	public DBState getfState() {
		return fState;
	}

	@Override
	public int compareTo(ManagedObject o) {
		return this.getId() - o.getId() > 0 ? 1 : -1;
	}

	@Override
	public void expire() {
		this.expire = true;
	}

	public boolean isExpire() {
		return expire;
	}

}
