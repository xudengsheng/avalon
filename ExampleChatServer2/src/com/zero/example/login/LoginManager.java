package com.zero.example.login;

import java.util.Map;
import java.util.TreeMap;

import akka.actor.UntypedActor;

import com.avalon.api.internal.IoMessagePackage;
import com.avalon.game.AvalonWorld;
import com.avalon.io.MessagePackImpl;
import com.example.protocol.MessageKey;
import com.example.protocol.javabean.SC_Globle_messageJavaBean;
import com.zero.example.message.LoginManagerMessage.LoginMessage;
import com.zero.example.message.LoginManagerMessage.LoginRegeditMessage;
import com.zero.example.message.ObjectManagerMesage;
import com.zero.example.message.SessionLisenterMessage.SessionSendIoMessage;

public class LoginManager extends UntypedActor {

	private Map<String, String> cache = new TreeMap<String, String>();

	@Override
	public void onReceive(Object arg0) throws Exception {

		if (arg0 instanceof LoginRegeditMessage) {
			String name = ((LoginRegeditMessage) arg0).javaBean.getName();
			String password = ((LoginRegeditMessage) arg0).javaBean.getPassword();
			if (cache.containsKey(name)) {
				SC_Globle_messageJavaBean bean = new SC_Globle_messageJavaBean();
				bean.setKey(2);
				bean.setContext("注册失败");
				bean.setStats(0);
				IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.SC_Globle_message,
						bean.getByteArray());
				SessionSendIoMessage ioMessage = new SessionSendIoMessage(ioMessagePackage);
				((LoginRegeditMessage) arg0).lisenterProcess.receivedActorMessage(getSelf(), ioMessage);
			} else {
				SC_Globle_messageJavaBean bean = new SC_Globle_messageJavaBean();
				bean.setKey(2);
				bean.setContext("注册成功");
				bean.setStats(1);
				IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.SC_Globle_message,
						bean.getByteArray());
				SessionSendIoMessage ioMessage = new SessionSendIoMessage(ioMessagePackage);
				((LoginRegeditMessage) arg0).lisenterProcess.receivedActorMessage(getSelf(), ioMessage);
				cache.put(name, password);
			}
		} else if (arg0 instanceof LoginMessage) {
			String name = ((LoginMessage) arg0).bean.getName();
			String password = ((LoginMessage) arg0).bean.getPassword();
			if (cache.containsKey(name)) {
				if (cache.get(name).equals(password)) {
					ObjectManagerMesage mesage = new ObjectManagerMesage.UserLogin(name,
							((LoginMessage) arg0).lisenterProcess);
					AvalonWorld.objectRef.tell(mesage, getSelf());
				} else {
					SC_Globle_messageJavaBean bean = new SC_Globle_messageJavaBean();
					bean.setKey(4);
					bean.setContext("登入");
					bean.setStats(2);
					IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.SC_Globle_message,
							bean.getByteArray());
					((LoginMessage) arg0).lisenterProcess.receivedActorMessage(getSelf(), ioMessagePackage);
				}
			} else {
				SC_Globle_messageJavaBean bean = new SC_Globle_messageJavaBean();
				bean.setKey(4);
				bean.setContext("登入");
				bean.setStats(0);
				IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.SC_Globle_message,
						bean.getByteArray());
				((LoginMessage) arg0).lisenterProcess.receivedActorMessage(getSelf(), ioMessagePackage);
			}
		}
	}

}
