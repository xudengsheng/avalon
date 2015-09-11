package com.avalon.core.cluster;

import akka.actor.ActorPath;
import akka.actor.ActorSelection;
import akka.actor.Address;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.cluster.Member;
import akka.cluster.UniqueAddress;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.avalon.core.actor.GameEngineActor;
import com.avalon.core.message.GameEngineMessage.AddNodeInfo;
import com.avalon.core.message.GameEngineMessage.CheckNodeInfo;
import com.avalon.core.message.GameServerSupervisorMessage;
import com.avalon.setting.AvalonServerMode;

/**
 * 集群监听 (理论上说只是监听)
 * 
 * @author ZERO
 *
 */
public class ClusterListener extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	Cluster cluster = Cluster.get(getContext().system());

	// 订阅集群改变
	@Override
	public void preStart()
	{
		cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), MemberEvent.class, UnreachableMember.class);
	}

	// 自己或者集群关闭
	@Override
	public void postStop()
	{
		cluster.unsubscribe(getSelf());
	}

	@Override
	public void onReceive(Object message)
	{
		if (message instanceof MemberUp)
		{
			MemberUp mUp = (MemberUp) message;
			Member member = mUp.member();
			boolean hasRole = member.hasRole(AvalonServerMode.SERVER_TYPE_GAME.modeName);
			UniqueAddress uniqueAddress = member.uniqueAddress();
			int uid = uniqueAddress.uid();

			Address address = member.address();
			{
			//通知对方节点，并带有自己的唯一UID
			String string = member.address().toString();
			CheckNodeInfo nodeInfo = new CheckNodeInfo(member);
			ActorSelection actorSelection = getContext().actorSelection(string + "/user/" + GameEngineActor.IDENTIFY);
			actorSelection.tell(nodeInfo, getSelf());
			}
			//添加到自己的管理
			{
				AddNodeInfo nodeInfo = new AddNodeInfo(member);
				ActorSelection actorSelection =  getContext().actorSelection(getContext().system().child(GameEngineActor.IDENTIFY));
				actorSelection.tell(nodeInfo, getSelf());
			}
			log.info("Member is Up: {}", member);
		} else if (message instanceof UnreachableMember)
		{
			UnreachableMember mUnreachable = (UnreachableMember) message;
			Member member = mUnreachable.member();
			log.info("Member detected as unreachable: {}", member);
		} else if (message instanceof MemberRemoved)
		{
			MemberRemoved mRemoved = (MemberRemoved) message;
			Member member = mRemoved.member();
			log.info("Member is Removed: {}", member);
		} else if (message instanceof MemberEvent)
		{
			// 非法的消息
		} else
		{
			unhandled(message);
		}

	}
}