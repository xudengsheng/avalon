package com.avalon.db.handler;

import java.util.Collection;
import java.util.LinkedList;

import com.avalon.db.MyBatisReference;
import com.avalon.db.api.ManagedObject;
import com.avalon.db.api.ManagedReference;
import com.avalon.db.status.DBState;

public class ProcessingCacheToDBHandler implements Runnable {

	private final Collection<CachedCommonHandler> managedObjectPools;

	public ProcessingCacheToDBHandler(Collection<CachedCommonHandler> managedObjectPools) {
		super();
		this.managedObjectPools = managedObjectPools;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		for (CachedCommonHandler managedObjectPool : managedObjectPools) {
			try {
				{
					Collection<ManagedReference> objects = managedObjectPool.getNeedUpdateObjects();
					Collection<ManagedObject> updates = new LinkedList<ManagedObject>();
					for (ManagedReference managedReference : objects) {
						updates.add((ManagedObject) managedReference.get());
					}
					if (updates.size() > 0) {
						managedObjectPool.batchUpdate(updates);
						for (ManagedReference managedReference : objects) {
							((MyBatisReference) managedReference).changeState(DBState.DB_STATE_NORMAL);
						}
					}
				}
				{
					Collection<ManagedReference> objects = managedObjectPool.getNeedDeleteObjects();
					Collection<ManagedObject> deletes = new LinkedList<ManagedObject>();
					for (ManagedReference managedReference : objects) {
						deletes.add((ManagedObject) managedReference.get());
					}
					if (deletes.size() > 0) {
						managedObjectPool.batchDelete(deletes);
						managedObjectPool.removes(deletes);
					}
				}
				{
					managedObjectPool.checkExprie();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
