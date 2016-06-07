package com.avalon.core.message;

import java.io.Serializable;

import akka.actor.ActorRef;
import akka.cluster.Member;

public interface ServerSupervisorMessage extends Serializable {
	/**
	 * 检测是否是自己这个节点
	 * 
	 * @author zero
	 *
	 */
	public class ServerIsTheSame implements ServerSupervisorMessage {

		private static final long serialVersionUID = 999191529606219571L;

		public final String UUID;

		public final int type;

		public final Member member;

		public ServerIsTheSame(String uUID, int type, Member member) {
			super();
			UUID = uUID;
			this.type = type;
			this.member = member;
		}

	}

	/**
	 * 通知其他节点，有新节点上线
	 * 
	 * @author zero
	 *
	 */
	public class ServerOnline implements ServerSupervisorMessage {

		private static final long serialVersionUID = 999191529606219571L;

		public final String UUID;

		public final int type;

		public final String addressPath;
		
		public final int addressUid;

		public final int serverId;

		public final boolean noBack;

		public ServerOnline(String uUID, int type,String addressPath, int addressUid,int serverId) {
			super();
			UUID = uUID;
			this.type = type;
			this.addressPath = addressPath;
			this.addressUid=addressUid;
			this.serverId = serverId;
			this.noBack = false;
		}

		public ServerOnline(String uUID, int type,String addressPath, int addressUid, int serverId, boolean nobak) {
			super();
			this.UUID = uUID;
			this.type = type;
			this.addressPath = addressPath;
			this.addressUid=addressUid;
			this.serverId = serverId;
			this.noBack = nobak;
		}

	}

	/**
	 * 节点断开连接
	 * 
	 * @author zero
	 *
	 */
	public class ServerLost implements ServerSupervisorMessage {

		private static final long serialVersionUID = -8106187315194918345L;

		public final int memberUid;

		public ServerLost(int memberUid) {
			super();
			this.memberUid = memberUid;
		}

	}

	/**
	 * 重定向消息
	 * 
	 * @author zero
	 *
	 */
	public class SendRedirectMessage implements ServerSupervisorMessage {

		private static final long serialVersionUID = 4569736332037112007L;

		public final ActorRef sender;

		public final String path;

		public final Object message;

		public SendRedirectMessage(ActorRef sender, String path, Object message) {
			super();
			this.sender = sender;
			this.path = path;
			this.message = message;
		}

	}

	/**
	 * 重定向消息
	 * 
	 * @author zero
	 *
	 */
	public class ReciveRedirectMessage implements ServerSupervisorMessage {

		private static final long serialVersionUID = 4569736332037112007L;

		public final ActorRef sender;

		public final String path;

		public final Object message;

		public ReciveRedirectMessage(ActorRef sender, String path, Object message) {
			super();
			this.sender = sender;
			this.path = path;
			this.message = message;
		}

	}
	/**
	 * 分发TCP的Transport Actor到集群中随机或者绑定的节点
	 * @author zero
	 *
	 */
	public class DistributionConnectionSessionsProtocol implements ServerSupervisorMessage{

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -8818237764034275011L;
		// 远程地址，用于消息返回使用
		/** The remote address. */
		public final ActorRef sender;
		//网关绑定服务Id（-1为随机分配）
		public final int serverid;
		// 获取内容数据源 IoMessagePackage
		/** The origins. */
		public final Object origins;
		
		/**
		 * Instantiates a new connection sessions protocol.
		 *
		 * @param remoteAddress the remote address
		 * @param sessionId the session id
		 * @param uid the uid
		 * @param origins the origins
		 */
		public DistributionConnectionSessionsProtocol(ActorRef sender, Object origins,int serverid) {
			super();
			this.sender = sender;
			this.serverid=serverid;
			this.origins = origins;
		}
	}

	public class Ping implements ServerSupervisorMessage{
		
		private static final long serialVersionUID = -5077511031331253358L;

		public final String UUID;

		public final int addressUid;
		
		public final int type;

		public final int serverId;

		public Ping(String uUID, int addressUid, int type, int serverId) {
			super();
			UUID = uUID;
			this.addressUid = addressUid;
			this.type = type;
			this.serverId = serverId;
		}


		
	}
}
