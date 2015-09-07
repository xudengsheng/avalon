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

import com.avalon.core.message.GameServerSupervisorMessage;
import com.avalon.core.supervision.GameServerSupervisor;
import com.avalon.setting.AvalonServerMode;

/**
 * 集群监听 (理论上说只是监听)
 * 
 * @author ZERO
 *
 */
public class AvalonClusterListener extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	Cluster cluster = Cluster.get(getContext().system());

	// 订阅集群改变
	@Override
	public void preStart() {
		cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), MemberEvent.class, UnreachableMember.class);
	}

	// 自己或者集群关闭
	@Override
	public void postStop() {
		cluster.unsubscribe(getSelf());
	}

	@Override
	public void onReceive(Object message) {
		if (message instanceof MemberUp) {
			MemberUp mUp = (MemberUp) message;
			Member member = mUp.member();
//			System.out.println(this.cluster.selfUniqueAddress().uid());
//			System.out.println(member.uniqueAddress().uid());
			boolean hasRole = member.hasRole(AvalonServerMode.SERVER_TYPE_GAME.modeName);
			if (hasRole) {
				ActorPath child = getContext().system().child(GameServerSupervisor.IDENTIFY);
				ActorSelection actorSelection = getContext().actorSelection(child);

				UniqueAddress uniqueAddress = member.uniqueAddress();
				int uid = uniqueAddress.uid();
				Address address = member.address();

				GameServerSupervisorMessage.AddGameServerMember serverMember = new GameServerSupervisorMessage.AddGameServerMember(uid,address);
				actorSelection.tell(serverMember, getSelf());
			}
			log.info("Member is Up: {}", member);
		}
		else if (message instanceof UnreachableMember) {
			UnreachableMember mUnreachable = (UnreachableMember) message;
			Member member = mUnreachable.member();
			log.info("Member detected as unreachable: {}", member);
		}
		else if (message instanceof MemberRemoved) {
			MemberRemoved mRemoved = (MemberRemoved) message;
			Member member = mRemoved.member();
			log.info("Member is Removed: {}", member);
		}
		else if (message instanceof MemberEvent) {
			// 非法的消息
		}
		else {
			unhandled(message);
		}

	}
}