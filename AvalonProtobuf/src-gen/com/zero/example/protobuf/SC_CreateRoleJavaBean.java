package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.LoginPro.*;
import com.zero.example.protobuf.LoginPro.SC_CreateRole;
import com.zero.example.protobuf.LoginPro.SC_CreateRole.Builder;


public class SC_CreateRoleJavaBean implements JavaProtocolTransform {

	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_CreateRole protocal = (SC_CreateRole) message;
	}

	@Override
	public SC_CreateRole javaBeanToProtocol() {
		Builder newBuilder = SC_CreateRole.newBuilder();
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_CreateRole bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_CreateRole.parseFrom(bytes);
	}
}