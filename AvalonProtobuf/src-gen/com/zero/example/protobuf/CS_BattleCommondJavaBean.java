package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.BattlePro.*;
import com.zero.example.protobuf.BattlePro.CS_BattleCommond;
import com.zero.example.protobuf.BattlePro.CS_BattleCommond.Builder;


public class CS_BattleCommondJavaBean implements JavaProtocolTransform {

	private java.lang.Long commondId;
	
	public java.lang.Long getCommondId()
	{
		return 	commondId;
	}

	public void setCommondId(java.lang.Long commondId) {
		this.commondId = commondId;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_BattleCommond protocal = (CS_BattleCommond) message;
		this.setCommondId(protocal.getCommondId());
	}

	@Override
	public CS_BattleCommond javaBeanToProtocol() {
		Builder newBuilder = CS_BattleCommond.newBuilder();
		newBuilder.setCommondId(this.getCommondId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_BattleCommond bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_BattleCommond.parseFrom(bytes);
	}
}