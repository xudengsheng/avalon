package com.zero.example.core;

import com.example.protocol.MessageKey;
import com.example.protocol.handler.Hall.CS_HallMessageHandler;
import com.example.protocol.handler.Hall.CS_JoinHallHandler;
import com.example.protocol.handler.Login.CS_LoginHandler;
import com.example.protocol.handler.Login.CS_LoginNewNameHandler;
import com.example.protocol.handler.Login.CS_RegeditHandler;

public class HandlerRegisterCenter {
	public static void registerClientRequestHandler(ExampleClientExtension clientExtension) {
		clientExtension.addRequestHandler(MessageKey.CS_Regedit, CS_RegeditHandler.class);
		clientExtension.addRequestHandler(MessageKey.CS_Login, CS_LoginHandler.class);
		clientExtension.addRequestHandler(MessageKey.CS_LoginNewName, CS_LoginNewNameHandler.class);
		clientExtension.addRequestHandler(MessageKey.CS_JoinHall, CS_JoinHallHandler.class);
		clientExtension.addRequestHandler(MessageKey.CS_HallMessage, CS_HallMessageHandler.class);
	}
}
