package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.SectPro.*;
import com.zero.example.protobuf.SectPro.CS_GetMaster;
import com.zero.example.protobuf.SectPro.CS_GetMaster.Builder;


public class CS_GetMasterJavaBean implements JavaProtocolTransform {

	private java.lang.Integer masterId;
	private java.lang.Integer masterType;
	private java.lang.Integer sectId;
	
	public java.lang.Integer getMasterId()
	{
		return 	masterId;
	}

	public void setMasterId(java.lang.Integer masterId) {
		this.masterId = masterId;
	}	
	public java.lang.Integer getMasterType()
	{
		return 	masterType;
	}

	public void setMasterType(java.lang.Integer masterType) {
		this.masterType = masterType;
	}	
	public java.lang.Integer getSectId()
	{
		return 	sectId;
	}

	public void setSectId(java.lang.Integer sectId) {
		this.sectId = sectId;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		CS_GetMaster protocal = (CS_GetMaster) message;
		this.setMasterId(protocal.getMasterId());
		this.setMasterType(protocal.getMasterType());
		this.setSectId(protocal.getSectId());
	}

	@Override
	public CS_GetMaster javaBeanToProtocol() {
		Builder newBuilder = CS_GetMaster.newBuilder();
		newBuilder.setMasterId(this.getMasterId());
		newBuilder.setMasterType(this.getMasterType());
		newBuilder.setSectId(this.getSectId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public CS_GetMaster bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return CS_GetMaster.parseFrom(bytes);
	}
}