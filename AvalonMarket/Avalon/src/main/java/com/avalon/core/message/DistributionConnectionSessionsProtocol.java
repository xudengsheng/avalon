package com.avalon.core.message;

import akka.actor.ActorRef;

public class DistributionConnectionSessionsProtocol extends AvaloneMessage
{
	// 远程地址，用于消息返回使用
	/** The remote address. */
	public final ActorRef sender;
	// 网关绑定服务Id（-1为随机分配）
	public final int serverid;
	// 获取内容数据源 IoMessagePackage
	/** The origins. */
	public final Object origins;
	public DistributionConnectionSessionsProtocol(ActorRef sender, int serverid, Object origins)
	{
		super(MessageType.DistributionConnectionSessionsProtocol);
		this.sender = sender;
		this.serverid = serverid;
		this.origins = origins;
	}

}
