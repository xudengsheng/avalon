package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.SectPro.*;
import com.zero.example.protobuf.SectPro.SC_GetMaster;
import com.zero.example.protobuf.SectPro.SC_GetMaster.Builder;


public class SC_GetMasterJavaBean implements JavaProtocolTransform {

	private java.lang.Boolean canGetMaster;
	private java.lang.Integer errorMsgId;
	
	public java.lang.Boolean getCanGetMaster()
	{
		return 	canGetMaster;
	}

	public void setCanGetMaster(java.lang.Boolean canGetMaster) {
		this.canGetMaster = canGetMaster;
	}	
	public java.lang.Integer getErrorMsgId()
	{
		return 	errorMsgId;
	}

	public void setErrorMsgId(java.lang.Integer errorMsgId) {
		this.errorMsgId = errorMsgId;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_GetMaster protocal = (SC_GetMaster) message;
		this.setCanGetMaster(protocal.getCanGetMaster());
		this.setErrorMsgId(protocal.getErrorMsgId());
	}

	@Override
	public SC_GetMaster javaBeanToProtocol() {
		Builder newBuilder = SC_GetMaster.newBuilder();
		newBuilder.setCanGetMaster(this.getCanGetMaster());
		newBuilder.setErrorMsgId(this.getErrorMsgId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_GetMaster bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_GetMaster.parseFrom(bytes);
	}
}