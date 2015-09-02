package com.avalon.core.command;

import java.io.Serializable;

/**
 * 协议发送到 ClusterConnectionSessions 可能会在网络服务器之间传输
 * 
 * @author ZERO
 *
 */
public class ConnectionSessionsProtocol implements Serializable{

	private static final long serialVersionUID = -8818237764034275011L;
	// 远程地址，用于消息返回使用
	public final String remoteAddress;
	// 会话ID
	public final String sessionId;
	//集群网管唯一ID
	public final int ClusterUid;
	// 获取内容数据源 IoMessagePackage
	public final Object origins;
	
	public ConnectionSessionsProtocol(String remoteAddress, String sessionId,int uid, Object origins) {
		super();
		this.remoteAddress = remoteAddress;
		this.sessionId = sessionId;
		this.ClusterUid=uid;
		this.origins = origins;
	}



}
