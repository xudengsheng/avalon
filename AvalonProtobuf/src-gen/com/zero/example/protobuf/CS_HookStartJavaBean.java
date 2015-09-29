package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.HookPro.*;
import com.zero.example.protobuf.HookPro.CS_HookStart;
import com.zero.example.protobuf.HookPro.CS_HookStart.Builder;


public class CS_HookStartJavaBean implements JavaProtocolTransform {

	private java.lang.Integer hookId;
	
	public java.lang.Integer getHookId()
	{
		return 	hookId;
	}

	public void setHookId(java.lang.Integer hookId) {
		this.hookId = hookId;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_HookStart protocal = (CS_HookStart) message;
		this.setHookId(protocal.getHookId());
	}

	@Override
	public CS_HookStart javaBeanToProtocol() {
		Builder newBuilder = CS_HookStart.newBuilder();
		newBuilder.setHookId(this.getHookId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_HookStart bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_HookStart.parseFrom(bytes);
	}
}