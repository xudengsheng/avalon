package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.SystemPro.*;
import com.zero.example.protobuf.SystemPro.SC_GlobleError;
import com.zero.example.protobuf.SystemPro.SC_GlobleError.Builder;


public class SC_GlobleErrorJavaBean implements JavaProtocolTransform {

	private java.lang.Integer errorMessageKey;
	private java.lang.Integer level;
	private java.lang.String desc;
	private java.lang.String errorType;
	
	public java.lang.Integer getErrorMessageKey()
	{
		return 	errorMessageKey;
	}

	public void setErrorMessageKey(java.lang.Integer errorMessageKey) {
		this.errorMessageKey = errorMessageKey;
	}	
	public java.lang.Integer getLevel()
	{
		return 	level;
	}

	public void setLevel(java.lang.Integer level) {
		this.level = level;
	}	
	public java.lang.String getDesc()
	{
		return 	desc;
	}

	public void setDesc(java.lang.String desc) {
		this.desc = desc;
	}	
	public java.lang.String getErrorType()
	{
		return 	errorType;
	}

	public void setErrorType(java.lang.String errorType) {
		this.errorType = errorType;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_GlobleError protocal = (SC_GlobleError) message;
		this.setErrorMessageKey(protocal.getErrorMessageKey());
		this.setLevel(protocal.getLevel());
		this.setDesc(protocal.getDesc());
		this.setErrorType(protocal.getErrorType());
	}

	@Override
	public SC_GlobleError javaBeanToProtocol() {
		Builder newBuilder = SC_GlobleError.newBuilder();
		newBuilder.setErrorMessageKey(this.getErrorMessageKey());
		newBuilder.setLevel(this.getLevel());
		newBuilder.setDesc(this.getDesc());
		newBuilder.setErrorType(this.getErrorType());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_GlobleError bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_GlobleError.parseFrom(bytes);
	}
}