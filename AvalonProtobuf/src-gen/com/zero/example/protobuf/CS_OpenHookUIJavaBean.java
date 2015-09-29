package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.HookPro.*;
import com.zero.example.protobuf.HookPro.CS_OpenHookUI;
import com.zero.example.protobuf.HookPro.CS_OpenHookUI.Builder;


public class CS_OpenHookUIJavaBean implements JavaProtocolTransform {

	private java.lang.Integer npcId;
	
	public java.lang.Integer getNpcId()
	{
		return 	npcId;
	}

	public void setNpcId(java.lang.Integer npcId) {
		this.npcId = npcId;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_OpenHookUI protocal = (CS_OpenHookUI) message;
		this.setNpcId(protocal.getNpcId());
	}

	@Override
	public CS_OpenHookUI javaBeanToProtocol() {
		Builder newBuilder = CS_OpenHookUI.newBuilder();
		newBuilder.setNpcId(this.getNpcId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_OpenHookUI bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_OpenHookUI.parseFrom(bytes);
	}
}