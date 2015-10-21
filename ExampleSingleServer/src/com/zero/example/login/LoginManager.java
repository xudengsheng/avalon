package com.zero.example.login;

import java.util.Map;
import java.util.TreeMap;

import akka.actor.UntypedActor;

import com.avalon.api.AppContext;
import com.avalon.game.AvalonWorld;
import com.zero.example.login.message.LoginBackMessage;
import com.zero.example.login.message.LoginMessage;
import com.zero.example.login.message.LoginRegeditBackMessage;
import com.zero.example.login.message.LoginRegeditMessage;
import com.zero.example.message.ObjectManagerMesage;

public class LoginManager extends UntypedActor {

	private Map<String, String> cache = new TreeMap<String, String>();

	@Override
	public void onReceive(Object arg0) throws Exception {

		if (arg0 instanceof LoginRegeditMessage) {
			String name = ((LoginRegeditMessage) arg0).javaBean.getName();
			String password = ((LoginRegeditMessage) arg0).javaBean.getPassword();
			if (cache.containsKey(name)) {
				LoginRegeditBackMessage ioMessage = new LoginRegeditBackMessage(false);
				((LoginRegeditMessage) arg0).lisenterProcess.receivedActorMessage(getSelf(), ioMessage);
			} else {
				LoginRegeditBackMessage ioMessage = new LoginRegeditBackMessage(true);
				((LoginRegeditMessage) arg0).lisenterProcess.receivedActorMessage(getSelf(), ioMessage);
				cache.put(name, password);

			}
		} else if (arg0 instanceof LoginMessage) {
			String name = ((LoginMessage) arg0).bean.getName();
			String password = ((LoginMessage) arg0).bean.getPassword();
			if (cache.containsKey(name)) {
				if (cache.get(name).equals(password)) {
					ObjectManagerMesage mesage = new ObjectManagerMesage.UserLogin(name,((LoginMessage) arg0).lisenterProcess);
					AvalonWorld.objectRef.tell(mesage, getSelf());
					
//					LoginBackMessage ioMessage = new LoginBackMessage(1);
//					((LoginMessage) arg0).lisenterProcess.receivedActorMessage(getSelf(), ioMessage);
				} else {
					LoginBackMessage ioMessage = new LoginBackMessage(2);
					((LoginMessage) arg0).lisenterProcess.receivedActorMessage(getSelf(), ioMessage);
				}
			} else {
				LoginBackMessage ioMessage = new LoginBackMessage(0);
				((LoginMessage) arg0).lisenterProcess.receivedActorMessage(getSelf(), ioMessage);
			}
		}
	}

}
