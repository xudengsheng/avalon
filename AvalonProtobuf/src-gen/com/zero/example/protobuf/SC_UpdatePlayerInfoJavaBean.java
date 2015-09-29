package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.UpdatePlayerPro.*;
import com.zero.example.protobuf.UpdatePlayerPro.SC_UpdatePlayerInfo;
import com.zero.example.protobuf.UpdatePlayerPro.SC_UpdatePlayerInfo.Builder;
import com.zero.example.protobuf.StructurePro.*;


public class SC_UpdatePlayerInfoJavaBean implements JavaProtocolTransform {

	private PlayerInfoProtocolJavaBean playerInfo;
	
	
	public PlayerInfoProtocolJavaBean getPlayerInfo()
	{
		return 	playerInfo;
	}

	public void setPlayerInfo(PlayerInfoProtocolJavaBean playerInfo) {
		this.playerInfo = playerInfo;
	}	
	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_UpdatePlayerInfo protocal = (SC_UpdatePlayerInfo) message;
		{
			PlayerInfoProtocolJavaBean protocol = new PlayerInfoProtocolJavaBean();
			protocol.protocolToJavaBean(protocal.getPlayerInfo());
			this.setPlayerInfo(protocol);
		}
	}

	@Override
	public SC_UpdatePlayerInfo javaBeanToProtocol() {
		Builder newBuilder = SC_UpdatePlayerInfo.newBuilder();
		{
			PlayerInfoProtocol protocol = this.getPlayerInfo().javaBeanToProtocol();
			newBuilder.setPlayerInfo(protocol);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_UpdatePlayerInfo bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_UpdatePlayerInfo.parseFrom(bytes);
	}
}