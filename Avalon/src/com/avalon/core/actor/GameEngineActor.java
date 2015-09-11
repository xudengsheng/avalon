package com.avalon.core.actor;

import java.util.List;
import java.util.UUID;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.cluster.Member;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.avalon.core.ContextResolver;
import com.avalon.core.message.ConnectionSessionSupervisorMessage.CluserSessionMessage;
import com.avalon.core.message.GameEngineMessage.AddNodeInfo;
import com.avalon.core.message.GameEngineMessage.CheckNodeInfo;
import com.avalon.core.message.GameEngineMessage.SysNodeInfo;
import com.avalon.core.message.GameServerSupervisorMessage.DistributionCluserSessionMessage;
import com.avalon.core.message.TaskManagerMessage;
import com.avalon.core.model.EngineNodeInfo;
import com.avalon.core.supervision.ConnectionSessionSupervisor;
import com.avalon.core.task.GlobleTaskManagerActor;
import com.avalon.setting.AvalonServerMode;
import com.google.common.collect.Lists;

/**
 * 游戏逻辑的主管理
 * 
 * @author zero
 *
 */
public class GameEngineActor extends UntypedActor {
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	public static String GEUID = UUID.randomUUID().toString();

	public static final String IDENTIFY = "GameEngineActor";

	private EngineNodeInfo selfEngine;

	private List<EngineNodeInfo> otherNodes = Lists.newArrayList();

	@Override
	public void onReceive(Object arg0) throws Exception
	{
		// 检测这个节点是不是自己的当前JVM
		if (arg0 instanceof CheckNodeInfo)
		{
			log.debug("get CheckNodeInfo");
			if (((CheckNodeInfo) arg0).GEUID.equals(GEUID))
			{
				log.debug("get Self Node");
				selfEngine = new EngineNodeInfo();
				selfEngine.member = ((CheckNodeInfo) arg0).member;
				selfEngine.engineUUID = GEUID;
				selfEngine.serverMode = ContextResolver.getServerMode();
				checkSameNode();
				sysNodeInfoNotice();
			}
		}
		// 添加节点信息
		else if (arg0 instanceof AddNodeInfo)
		{
			log.debug("AddNodeInfo");
			EngineNodeInfo engineNodeInfo = new EngineNodeInfo();
			engineNodeInfo.member = ((AddNodeInfo) arg0).member;
			otherNodes.add(engineNodeInfo);
			checkSameNode();
			sysNodeInfoNotice();
		}
		// 获得别的节点信息
		else if (arg0 instanceof SysNodeInfo)
		{
			log.debug("SysNodeInfo");
			for (EngineNodeInfo engineNodeInfo : otherNodes)
			{
				if (engineNodeInfo.member.uniqueAddress().uid() == ((SysNodeInfo) arg0).memberUID)
				{
					log.debug("sys node info ");
					engineNodeInfo.serverMode = ((SysNodeInfo) arg0).serverMode;
					engineNodeInfo.engineUUID = ((SysNodeInfo) arg0).serverUUID;
				}
			}
		}

		// 发布分布式信息，把当前的连接分发出去
		else if (arg0 instanceof DistributionCluserSessionMessage)
		{
			Member serverNodeMember = getGameServer();
			if (serverNodeMember != null)
			{
				// akka.tcp://AVALON@192.168.199.200:2552/user/ConnectionSessionSupervisor
				String path = serverNodeMember.address().toString() + "/user/" + ConnectionSessionSupervisor.IDENTIFY;

				ActorSelection actorSelection = getContext().system().actorSelection(path);

				CluserSessionMessage message2 = ((DistributionCluserSessionMessage) arg0).message;
				actorSelection.tell(message2, getSelf());

//				SessionJoin packet = new SessionJoin(serverNodeMember.uid);
//				TopicMessage topicMessage = new TopicMessage.GameServerSupervisorTopicMessage(packet);
//
//				mediator.tell(new DistributedPubSubMediator.Publish(GameServerSupervisorTopic.shardName, topicMessage), getSelf());
			}
		}
		
		//处理任务的
		else if(arg0 instanceof TaskManagerMessage.createTaskMessage){
			List<Member> gameServers = getGameServers();
			for (Member member : gameServers)
			{
				String path = member.address().toString() + "/user/" + GlobleTaskManagerActor.IDENTIFY;

				ActorSelection actorSelection = getContext().system().actorSelection(path);

				CluserSessionMessage message2 = ((DistributionCluserSessionMessage) arg0).message;
				actorSelection.tell(message2, getSelf());
			}
		}
	}

	private List<Member> getGameServers()
	{
		List<Member> members = Lists.newArrayList();
		for (EngineNodeInfo member : otherNodes)
		{
			if (member.serverMode.equals(AvalonServerMode.SERVER_TYPE_GAME))
			{
				members.add(member.member);
			}
		}
		return members;
	}
	
	private Member getGameServer()
	{
		List<Member> members = Lists.newArrayList();
		for (EngineNodeInfo member : otherNodes)
		{
			if (member.serverMode.equals(AvalonServerMode.SERVER_TYPE_GAME))
			{
				members.add(member.member);
			}
		}
		return members.get(0);
	}

	private void checkSameNode()
	{
		if (selfEngine == null)
		{
			log.debug("self in Null No Check");
			return;
		}
		for (EngineNodeInfo engineNodeInfo : otherNodes)
		{
			if (engineNodeInfo.member.uniqueAddress().uid() == selfEngine.member.uniqueAddress().uid())
			{
				log.debug("find same node remove it in other");
				otherNodes.remove(engineNodeInfo);
				return;
			}
		}
	}

	private void sysNodeInfoNotice()
	{
		if (selfEngine == null)
		{
			log.debug("self in Null No Check");
			return;
		}
		for (EngineNodeInfo engineNodeInfo : otherNodes)
		{
			SysNodeInfo nodeInfo = new SysNodeInfo(selfEngine.member.uniqueAddress().uid(), selfEngine.serverMode, selfEngine.engineUUID);
			String path = engineNodeInfo.member.address().toString() + "/user/" + IDENTIFY;
			ActorSelection actorSelection = getContext().system().actorSelection(path);
			actorSelection.tell(nodeInfo, getSelf());
		}
	}

}
