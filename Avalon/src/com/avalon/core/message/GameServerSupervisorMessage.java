package com.avalon.core.message;

import java.io.Serializable;

import akka.actor.Address;

import com.avalon.api.internal.IoMessagePackage;
import com.avalon.core.message.ConnectionSessionSupervisorMessage.CluserSessionMessage;

/**
 * 集群事件
 * 
 * @author ZERO
 *
 */
public interface GameServerSupervisorMessage extends Serializable {

	public class AddGameServerMember implements GameServerSupervisorMessage {

		private static final long serialVersionUID = 3596913193246469014L;

		public int uid;

		public Address address;

		public AddGameServerMember(int uid, Address address) {
			super();
			this.uid = uid;
			this.address = address;
		}

	}

	public class BlockGameServerMember implements GameServerSupervisorMessage {

		private static final long serialVersionUID = 8294984165358324668L;
		public int uid;

		public BlockGameServerMember(int uid) {
			super();
			this.uid = uid;
		}

	}

	public class LostGameServerMember implements GameServerSupervisorMessage {

		private static final long serialVersionUID = -3747289567829245653L;
		public final int uid;

		public LostGameServerMember(int uid) {
			super();
			this.uid = uid;
		}

	}

	public class DistributionCluserSessionMessage implements GameServerSupervisorMessage {

		private static final long serialVersionUID = -650248906569656268L;
		public final CluserSessionMessage message;

		public DistributionCluserSessionMessage(CluserSessionMessage message) {
			super();
			this.message = message;
		}

	}
	
	
	public class LocalSessionMessage implements GameServerSupervisorMessage {

		private static final long serialVersionUID = -4085109230935593066L;

		public final IoMessagePackage messagePackage;

		public LocalSessionMessage(IoMessagePackage messagePackage) {
			super();
			this.messagePackage = messagePackage;
		}

	}
}
