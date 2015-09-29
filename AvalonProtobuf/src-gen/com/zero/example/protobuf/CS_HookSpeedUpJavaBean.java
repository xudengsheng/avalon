package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.HookPro.*;
import com.zero.example.protobuf.HookPro.CS_HookSpeedUp;
import com.zero.example.protobuf.HookPro.CS_HookSpeedUp.Builder;


public class CS_HookSpeedUpJavaBean implements JavaProtocolTransform {

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
		CS_HookSpeedUp protocal = (CS_HookSpeedUp) message;
		this.setHookId(protocal.getHookId());
	}

	@Override
	public CS_HookSpeedUp javaBeanToProtocol() {
		Builder newBuilder = CS_HookSpeedUp.newBuilder();
		newBuilder.setHookId(this.getHookId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_HookSpeedUp bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_HookSpeedUp.parseFrom(bytes);
	}
}