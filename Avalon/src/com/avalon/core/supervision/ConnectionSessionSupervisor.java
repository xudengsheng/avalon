package com.avalon.core.supervision;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.avalon.core.actor.ConnectionSession;
import com.avalon.core.message.ConnectionSessionMessage;
import com.avalon.core.message.ConnectionSessionMessage.DirectSessionMessage;
import com.avalon.core.message.ConnectionSessionMessage.HasSenderPathMessage;
import com.avalon.core.message.ConnectionSessionSupervisorMessage.CluserSessionMessage;
import com.avalon.core.message.GameServerSupervisorMessage.LocalSessionMessage;
import com.avalon.core.message.TopicMessage.ConnectionSessionSupervisorTopicMessage;

/**
 * 连接会话监听 消息会给游戏逻辑使用
 * 
 * @author ZERO
 *
 */
public class ConnectionSessionSupervisor extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	int sessionNum = 0;

	public static final String IDENTIFY = "ConnectionSessionSupervisor";

	private Map<String, ActorRef> keyConnectionSession = new HashMap<String, ActorRef>();

	@Override
	public void onReceive(Object msg) throws Exception
	{
		if (msg instanceof CluserSessionMessage)
		{

			String remoteAddress = ((CluserSessionMessage) msg).remoteAddress;
			String Path = getSender().path().parent().toString() + "/" + remoteAddress;

			ActorRef actorOf = getContext().actorOf(Props.create(ConnectionSession.class));
			getContext().watch(actorOf);

			int uid = ((CluserSessionMessage) msg).uid;
			Object origins = ((CluserSessionMessage) msg).origins;
			HasSenderPathMessage message = new HasSenderPathMessage(uid, Path, origins);

			actorOf.tell(message, getSelf());
			sessionNum += 1;

		} else if (msg instanceof LocalSessionMessage)
		{
			//放置延时的策略
			String name = getSender().path().name();
			if (keyConnectionSession.containsKey(name))
			{
				ActorRef actorRef = keyConnectionSession.get(keyConnectionSession);
				ConnectionSessionMessage.DirectSessionMessage directSessionMessage=new DirectSessionMessage(((LocalSessionMessage) msg).messagePackage);
				actorRef.tell(directSessionMessage, getSender());
			} else
			{
				String Path = getSender().path().toString();
				ActorRef actorOf = getContext().actorOf(Props.create(ConnectionSession.class), name);
				getContext().watch(actorOf);

				HasSenderPathMessage message = new HasSenderPathMessage(0, Path, ((LocalSessionMessage) msg).messagePackage);
				actorOf.tell(message, getSelf());

				sessionNum += 1;
				keyConnectionSession.put(name, actorOf);
			}

		} else if (msg instanceof ConnectionSessionSupervisorTopicMessage)
		{
			// TODO
		} else if (msg instanceof Terminated)
		{
			// 一个被监听Actor销毁掉了
			sessionNum -= 1;
		} else
		{
			unhandled(msg);
		}

	}

}
