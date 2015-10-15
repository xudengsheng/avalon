package com.tang.logic.${java_outer_classname?uncap_first?replace("Pro","")};

import com.zero.engine.extensions.IClientRequestHandler;
import com.google.protobuf.InvalidProtocolBufferException;
import com.tang.protocol.${java_outer_classname}.${protoName};
import com.zero.engine.core.akka.model.player.UntypePlayer;
import com.tang.logic.player.GamePlayerProxy;
import com.tang.logic.${java_outer_classname?uncap_first?replace("Pro","")}.${java_name}Decorate;
public class ${java_name}Handler implements IClientRequestHandler {

	@Override
	public void handleClientRequest(Object player, Object message) throws InvalidProtocolBufferException
	{
		UntypePlayer gamePlayer = (UntypePlayer) player;
		${protoName} parseFrom = ${protoName}.parseFrom((byte[]) message);
		//如果有需求可以在这里加
		${java_name}Decorate.handleClientRequest((GamePlayerProxy)gamePlayer, parseFrom);
	}

}
