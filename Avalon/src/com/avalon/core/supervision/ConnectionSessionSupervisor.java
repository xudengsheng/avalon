package com.avalon.core.supervision;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.avalon.core.actor.ConnectionSession;
import com.avalon.core.message.ConnectionSessionMessage;
import com.avalon.core.message.ConnectionSessionMessage.DirectSessionMessage;
import com.avalon.core.message.ConnectionSessionMessage.HasSenderPathMessage;
import com.avalon.core.message.ConnectionSessionSupervisorMessage.CluserSessionMessage;
import com.avalon.core.message.GameServerSupervisorMessage.LocalSessionMessage;
import com.avalon.core.message.TopicMessage.ConnectionSessionSupervisorTopicMessage;
import com.avalon.core.subscribe.ConnectionSessionSupervisorTopic;

/**
 * 连接会话监听 消息会给游戏逻辑使用
 * 可以理解成游戏逻辑管理的主节点
 * @author ZERO
 *
 */
public class ConnectionSessionSupervisor extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	int sessionNum = 0;

	public static final String IDENTIFY = "ConnectionSessionSupervisor";

	private Map<String, ActorRef> keyConnectionSession = new HashMap<String, ActorRef>();

	
	
	@Override
	public void preStart() throws Exception
	{
		super.preStart();
		ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
		mediator.tell(new DistributedPubSubMediator.Subscribe(ConnectionSessionSupervisorTopic.shardName, getSelf()), getSelf());

	}



	@Override
	public void onReceive(Object msg) throws Exception
	{
		if (msg instanceof CluserSessionMessage)
		{
			log.debug("Geting CluserSessionMessage");
			// TransportSupervisor
			String supervisorName = ((CluserSessionMessage) msg).supervisorName;
			// 59eb66d6-9463-43c0-832e-90126295b2f1
			String actorUId = ((CluserSessionMessage) msg).actorId;
			// akka.tcp://AVALON@192.168.199.200:2551/user/TransportSupervisor/59eb66d6-9463-43c0-832e-90126295b2f1
			String Path = getSender().path().parent().toString() + "/" + supervisorName + "/" + actorUId;

			if (keyConnectionSession.containsKey(actorUId))
			{
				log.debug("keyConnectionSession has key="+actorUId);
				ActorRef actorRef = keyConnectionSession.get(keyConnectionSession);
				ConnectionSessionMessage.DirectSessionMessage directSessionMessage = new DirectSessionMessage(((LocalSessionMessage) msg).messagePackage);
				actorRef.tell(directSessionMessage, getSender());
			} else
			{		
				log.debug("keyConnectionSession has not key="+actorUId);
				ActorRef actorOf = getContext().actorOf(Props.create(ConnectionSession.class), actorUId);
				getContext().watch(actorOf);

				int uid = ((CluserSessionMessage) msg).uid;
				Object origins = ((CluserSessionMessage) msg).origins;
				HasSenderPathMessage message = new HasSenderPathMessage(uid, Path, origins);

				actorOf.tell(message, getSelf());
				sessionNum += 1;
			}
			return;
		}
		// 单服的消息策略
		else if (msg instanceof LocalSessionMessage)
		{
			log.debug("Geting LocalSessionMessage");
			// 放置延时的策略
			// be645988-0ff5-4e7a-bcd0-566ec1789cb7
			String name = getSender().path().name();
			if (keyConnectionSession.containsKey(name))
			{
				log.debug("keyConnectionSession has name="+name);
				ActorRef actorRef = keyConnectionSession.get(keyConnectionSession);
				ConnectionSessionMessage.DirectSessionMessage directSessionMessage = new DirectSessionMessage(((LocalSessionMessage) msg).messagePackage);
				actorRef.tell(directSessionMessage, getSender());
			} else
			{
				log.debug("keyConnectionSession has not name="+name);
				
				String Path = getSender().path().toString();
				ActorRef actorOf = getContext().actorOf(Props.create(ConnectionSession.class), name);
				getContext().watch(actorOf);

				HasSenderPathMessage message = new HasSenderPathMessage(0, Path, ((LocalSessionMessage) msg).messagePackage);
				actorOf.tell(message, getSelf());

				sessionNum += 1;
				keyConnectionSession.put(name, actorOf);
			}
			return;
		} else if (msg instanceof ConnectionSessionSupervisorTopicMessage)
		{
			// TODO
		} else if (msg instanceof Terminated)
		{
			// 一个被监听Actor销毁掉了
			sessionNum -= 1;
			return;
		} else
		{
			unhandled(msg);
		}

	}

}
