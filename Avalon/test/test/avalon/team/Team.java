package test.avalon.team;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Team extends UntypedActor{

	List<ActorRef> list=new ArrayList<>();
	
	int MAX=4;
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		if (arg0.equals("addTeam")) {
			if (list.size()>MAX) {
				//不能加入
			}else{
				//加入队伍
			}
		}else if(arg0.equals("brocast"))
		{
			for (ActorRef player : list) {
				player.tell("hrllo", ActorRef.noSender());
			}
		}
	}

}
class Player extends UntypedActor{

	@Override
	public void onReceive(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}