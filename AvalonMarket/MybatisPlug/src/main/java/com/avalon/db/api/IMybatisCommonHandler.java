package com.avalon.db.api;

public interface IMybatisCommonHandler {
	
	void setDelete(ManagedObject object);

	void mark(ManagedObject object);

	<T> ManagedReference<T> getReference(long id);

	<T> ManagedReference<T> createReference(T object);

	<T> ManagedReference<T> queryCreateReference(T object);

}
