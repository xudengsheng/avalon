package com.avalon.core.cluster;

import akka.actor.ActorPath;
import akka.actor.ActorSelection;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.persistence.UntypedPersistentActor;

import com.avalon.core.command.ConnectionSessionsProtocol;
import com.avalon.core.message.ConnectionSessionSupervisorMessage;
import com.avalon.core.message.GameServerSupervisorMessage.DistributionCluserSessionMessage;
import com.avalon.core.supervision.GameServerSupervisor;

/**
 * 集群网络会话，处理分布信息
 * 
 * @author ZERO
 *
 */
public class ClusterConnectionSessions extends UntypedPersistentActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	public final static String shardName = "ClusterConnectionSessions";

	@Override
	public void onReceiveRecover(Object msg)
	{
		if (msg instanceof ConnectionSessionsProtocol)
		{
			ActorPath child = getContext().system().child(GameServerSupervisor.IDENTIFY);
			ActorSelection actorSelection = getContext().actorSelection(child);

			int clusterUid = ((ConnectionSessionsProtocol) msg).ClusterUid;
			String sessionId = ((ConnectionSessionsProtocol) msg).sessionId;
			Object origins = ((ConnectionSessionsProtocol) msg).origins;

			ConnectionSessionSupervisorMessage.CluserSessionMessage message = 
					new ConnectionSessionSupervisorMessage.CluserSessionMessage(clusterUid, sessionId, origins);
			
			DistributionCluserSessionMessage cluserSessionMessage = new DistributionCluserSessionMessage(message);
			actorSelection.tell(cluserSessionMessage, getSender());
		}
	}

	@Override
	public void onReceiveCommand(Object msg)
	{
		if (msg instanceof ConnectionSessionsProtocol)
		{
			ActorPath child = getContext().system().child(GameServerSupervisor.IDENTIFY);
			ActorSelection actorSelection = getContext().actorSelection(child);

			int clusterUid = ((ConnectionSessionsProtocol) msg).ClusterUid;
			String sessionId = ((ConnectionSessionsProtocol) msg).sessionId;
			Object origins = ((ConnectionSessionsProtocol) msg).origins;

			ConnectionSessionSupervisorMessage.CluserSessionMessage message = 
					new ConnectionSessionSupervisorMessage.CluserSessionMessage(clusterUid, sessionId, origins);
			DistributionCluserSessionMessage cluserSessionMessage = new DistributionCluserSessionMessage(message);
			actorSelection.tell(cluserSessionMessage, getSender());
		}
	}

	@Override
	public String persistenceId()
	{
		return shardName;
	}
}
