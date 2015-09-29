package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.SectPro.*;
import com.zero.example.protobuf.SectPro.CS_RevoltMaster;
import com.zero.example.protobuf.SectPro.CS_RevoltMaster.Builder;


public class CS_RevoltMasterJavaBean implements JavaProtocolTransform {

	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_RevoltMaster protocal = (CS_RevoltMaster) message;
	}

	@Override
	public CS_RevoltMaster javaBeanToProtocol() {
		Builder newBuilder = CS_RevoltMaster.newBuilder();
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_RevoltMaster bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_RevoltMaster.parseFrom(bytes);
	}
}