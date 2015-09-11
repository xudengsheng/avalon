package com.avalon.core.cluster;

import akka.actor.ActorPath;
import akka.actor.ActorSelection;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.persistence.UntypedPersistentActor;

import com.avalon.core.actor.GameEngineActor;
import com.avalon.core.command.ConnectionSessionsProtocol;
import com.avalon.core.message.ConnectionSessionSupervisorMessage;
import com.avalon.core.message.GameServerSupervisorMessage.DistributionCluserSessionMessage;

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
			ActorPath child = getContext().system().child(GameEngineActor.IDENTIFY);
			ActorSelection actorSelection = getContext().actorSelection(child);

			int clusterUid = ((ConnectionSessionsProtocol) msg).ClusterUid;
			String sessionId = ((ConnectionSessionsProtocol) msg).sessionId;
			// TransportSupervisor/59eb66d6-9463-43c0-832e-90126295b2f1
			String[] split = sessionId.split("/");
			Object origins = ((ConnectionSessionsProtocol) msg).origins;

			ConnectionSessionSupervisorMessage.CluserSessionMessage message = new ConnectionSessionSupervisorMessage.CluserSessionMessage(
					clusterUid, split[0], split[1], origins);

			DistributionCluserSessionMessage cluserSessionMessage = new DistributionCluserSessionMessage(message);
			actorSelection.tell(cluserSessionMessage, getSender());
		}
	}

	@Override
	public void onReceiveCommand(Object msg)
	{
		if (msg instanceof ConnectionSessionsProtocol)
		{
			ActorPath child = getContext().system().child(GameEngineActor.IDENTIFY);
			ActorSelection actorSelection = getContext().actorSelection(child);

			int clusterUid = ((ConnectionSessionsProtocol) msg).ClusterUid;
			String sessionId = ((ConnectionSessionsProtocol) msg).sessionId;
			// TransportSupervisor/59eb66d6-9463-43c0-832e-90126295b2f1
			String[] split = sessionId.split("/");
			Object origins = ((ConnectionSessionsProtocol) msg).origins;

			ConnectionSessionSupervisorMessage.CluserSessionMessage message = new ConnectionSessionSupervisorMessage.CluserSessionMessage(
					clusterUid, split[0], split[1], origins);
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
