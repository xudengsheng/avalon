package com.avalon.gameengine.interfaces;


import akka.actor.ActorRef;
/**
 * 创建Avalon对象
 * @author zero
 *
 */
public interface ICreateANObeject {
	/**
	 * 创建Avalon actor对象
	 * @param anObject 具体的类对象继承ANObject
	 * @param id	   分配的唯一Id
	 * @param arg      附带的创建参数
	 * @return
	 */
	public ActorRef createANObject(Class<?> anObject,String id,Object... arg);
	/**
	 * 创建Avalon actor对象
	 * @param anObject 具体的类对象继承ANObject
	 * @param id	   分配的唯一Id
	 * @return
	 */
	public ActorRef createANObject(Class<?> anObject,String id);
	
}
