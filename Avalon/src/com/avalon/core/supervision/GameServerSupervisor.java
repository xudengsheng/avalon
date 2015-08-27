package com.avalon.core.supervision;

import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.avalon.api.message.JsonMessagePacket;
import com.avalon.api.message.Packet;
import com.avalon.core.json.gameserversupervisor.SessionJoin;
import com.avalon.core.json.gameserversupervisor.SessionLost;
import com.avalon.core.message.ConnectionSessionSupervisorMessage.CluserSessionMessage;
import com.avalon.core.message.GameServerSupervisorMessage;
import com.avalon.core.message.GameServerSupervisorMessage.AddGameServerMember;
import com.avalon.core.message.GameServerSupervisorMessage.BlockGameServerMember;
import com.avalon.core.message.GameServerSupervisorMessage.DistributionCluserSessionMessage;
import com.avalon.core.message.TopicMessage;
import com.avalon.core.model.ServerNodeMember;
import com.avalon.core.status.GameNodeNetWorkStatus;
import com.avalon.core.subscribe.GameServerSupervisorTopic;
import com.google.common.collect.Lists;

/**
 * 游戏逻辑服务器监听 （动态分配）
 * 
 * @author ZERO
 *
 */
public class GameServerSupervisor extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	public static final String IDENTIFY = "GameServerSupervisor";

	private List<ServerNodeMember> serverNodeMembers = Lists.newArrayList();
	ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();

	public void addNewSessionJoin(int Uid)
	{
		for (ServerNodeMember serverNodeMember : serverNodeMembers)
		{
			if (serverNodeMember.uid == Uid)
			{
				serverNodeMember.setSessionNum(serverNodeMember.getSessionNum() + 1);
			}
		}
	}

	public void subNewSessionJoin(int Uid)
	{
		for (ServerNodeMember serverNodeMember : serverNodeMembers)
		{
			if (serverNodeMember.uid == Uid)
			{
				serverNodeMember.setSessionNum(serverNodeMember.getSessionNum() - 1);
			}
		}
	}

	@Override
	public void onReceive(Object message) throws Exception
	{

		if (message instanceof GameServerSupervisorMessage.AddGameServerMember)
		{
			AddGameServerMember addGameServerMember = (GameServerSupervisorMessage.AddGameServerMember) message;
			for (ServerNodeMember serverNodeMember : serverNodeMembers)
			{
				if (serverNodeMember.uid == addGameServerMember.uid)
				{
					serverNodeMember.setState(GameNodeNetWorkStatus.UNICOM);
					return;
				}
			}
			ServerNodeMember gameServerMember = new ServerNodeMember(addGameServerMember.uid, addGameServerMember.address);
			serverNodeMembers.add(gameServerMember);
		} else if (message instanceof GameServerSupervisorMessage.BlockGameServerMember)
		{
			for (ServerNodeMember serverNodeMember : serverNodeMembers)
			{
				if (serverNodeMember.uid == ((BlockGameServerMember) message).uid)
				{
					serverNodeMember.setState(GameNodeNetWorkStatus.BLOCK);
					return;
				}
			}
		} else if (message instanceof GameServerSupervisorMessage.LostGameServerMember)
		{
			for (ServerNodeMember serverNodeMember : serverNodeMembers)
			{
				if (serverNodeMember.uid == ((BlockGameServerMember) message).uid)
				{
					serverNodeMembers.remove(serverNodeMember);
					return;
				}
			}
		} else if (message instanceof DistributionCluserSessionMessage)
		{
			if (serverNodeMembers.size() > 0)
			{
				List<ServerNodeMember> sortedCopy = ServerNodeMember.bySessionOrdering.sortedCopy(serverNodeMembers);
				for (ServerNodeMember serverNodeMember : sortedCopy)
				{

					String path = serverNodeMember.address.toString() + "/user/" + ConnectionSessionSupervisor.IDENTIFY;
					ActorSelection actorSelection = getContext().system().actorSelection(path);

					CluserSessionMessage message2 = ((DistributionCluserSessionMessage) message).message;
					actorSelection.tell(message2, getSelf());

					SessionJoin packet = new SessionJoin(serverNodeMember.uid);
					TopicMessage topicMessage = new TopicMessage.GameServerSupervisorTopicMessage(packet);

					mediator.tell(new DistributedPubSubMediator.Publish(GameServerSupervisorTopic.shardName, topicMessage), getSelf());
					return;
				}

			}
		} else if (message instanceof Packet)
		{
			Byte packetType = ((Packet) message).getPacketType();
			if (packetType == Packet.Customize)
			{

			} else if (packetType == Packet.JSON_TYPE)
			{
				JsonMessagePacket messagePacket = (JsonMessagePacket) message;
				processJsonMessage(messagePacket);
			}
		} else
		{
			unhandled(message);
		}

	}

	private void processJsonMessage(JsonMessagePacket messagePacket)
	{
		switch (messagePacket.getCodeId())
		{
		case SessionJoin.CODE_ID:
			SessionJoin.process(this, messagePacket.toJsonMessagePacket());
			break;
		case SessionLost.CODE_ID:
			SessionLost.process(this, messagePacket.toJsonMessagePacket());
			break;
		default:
			break;
		}

	}

}
