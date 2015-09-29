package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.UpdatePlayerPro.*;
import com.zero.example.protobuf.UpdatePlayerPro.SC_UpdateUserMasterInfo;
import com.zero.example.protobuf.UpdatePlayerPro.SC_UpdateUserMasterInfo.Builder;


public class SC_UpdateUserMasterInfoJavaBean implements JavaProtocolTransform {

	private java.lang.Long masterId;
	private java.lang.Integer masterType;
	
	public java.lang.Long getMasterId()
	{
		return 	masterId;
	}

	public void setMasterId(java.lang.Long masterId) {
		this.masterId = masterId;
	}	
	public java.lang.Integer getMasterType()
	{
		return 	masterType;
	}

	public void setMasterType(java.lang.Integer masterType) {
		this.masterType = masterType;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_UpdateUserMasterInfo protocal = (SC_UpdateUserMasterInfo) message;
		this.setMasterId(protocal.getMasterId());
		this.setMasterType(protocal.getMasterType());
	}

	@Override
	public SC_UpdateUserMasterInfo javaBeanToProtocol() {
		Builder newBuilder = SC_UpdateUserMasterInfo.newBuilder();
		newBuilder.setMasterId(this.getMasterId());
		newBuilder.setMasterType(this.getMasterType());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_UpdateUserMasterInfo bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_UpdateUserMasterInfo.parseFrom(bytes);
	}
}