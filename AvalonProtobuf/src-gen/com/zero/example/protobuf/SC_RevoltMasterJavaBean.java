package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.SectPro.*;
import com.zero.example.protobuf.SectPro.SC_RevoltMaster;
import com.zero.example.protobuf.SectPro.SC_RevoltMaster.Builder;


public class SC_RevoltMasterJavaBean implements JavaProtocolTransform {

	private java.lang.Boolean canRevoltMaster;
	
	public java.lang.Boolean getCanRevoltMaster()
	{
		return 	canRevoltMaster;
	}

	public void setCanRevoltMaster(java.lang.Boolean canRevoltMaster) {
		this.canRevoltMaster = canRevoltMaster;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_RevoltMaster protocal = (SC_RevoltMaster) message;
		this.setCanRevoltMaster(protocal.getCanRevoltMaster());
	}

	@Override
	public SC_RevoltMaster javaBeanToProtocol() {
		Builder newBuilder = SC_RevoltMaster.newBuilder();
		newBuilder.setCanRevoltMaster(this.getCanRevoltMaster());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_RevoltMaster bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_RevoltMaster.parseFrom(bytes);
	}
}