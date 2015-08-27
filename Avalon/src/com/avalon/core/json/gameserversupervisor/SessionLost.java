package com.avalon.core.json.gameserversupervisor;

import com.alibaba.fastjson.JSON;
import com.avalon.api.message.JsonMessagePacket;
import com.avalon.core.supervision.GameServerSupervisor;

public class SessionLost extends JsonMessagePacket{

	private static final long serialVersionUID = 1170518041231519545L;
	
	public final static int CODE_ID = 2;

	public SessionLost(int serverUid) {
		super();
		this.serverUid = serverUid;
	}

	public SessionLost() {
		super();
	}

	public int serverUid;

	@Override
	public int getCodeId() {
		return SessionLost.CODE_ID;
	}

	public static void process(GameServerSupervisor gameServerSupervisor, String json) {
		SessionLost parseObject = JSON.parseObject(json, SessionLost.class);
		gameServerSupervisor.subNewSessionJoin(parseObject.serverUid);
	}

	
}
