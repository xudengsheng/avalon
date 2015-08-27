package com.avalon.core.json.gameserversupervisor;

import com.alibaba.fastjson.JSON;
import com.avalon.api.message.JsonMessagePacket;
import com.avalon.core.supervision.GameServerSupervisor;

public class SessionJoin extends JsonMessagePacket{

	private static final long serialVersionUID = 1170518041231519545L;
	
	public final static int CODE_ID = 1;

	public SessionJoin(int serverUid) {
		super();
		this.serverUid = serverUid;
	}

	public SessionJoin() {
		super();
	}

	public int serverUid;

	@Override
	public int getCodeId() {
		return SessionJoin.CODE_ID;
	}

	public static void process(GameServerSupervisor gameServerSupervisor, String json) {
		SessionJoin parseObject = JSON.parseObject(json, SessionJoin.class);
		gameServerSupervisor.addNewSessionJoin(parseObject.serverUid);
	}

	
}
