package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.ScenePro.*;
import com.zero.example.protobuf.ScenePro.SC_MoveError;
import com.zero.example.protobuf.ScenePro.SC_MoveError.Builder;


public class SC_MoveErrorJavaBean implements JavaProtocolTransform {

	private java.lang.Integer resutlCode;
	
	public java.lang.Integer getResutlCode()
	{
		return 	resutlCode;
	}

	public void setResutlCode(java.lang.Integer resutlCode) {
		this.resutlCode = resutlCode;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_MoveError protocal = (SC_MoveError) message;
		this.setResutlCode(protocal.getResutlCode());
	}

	@Override
	public SC_MoveError javaBeanToProtocol() {
		Builder newBuilder = SC_MoveError.newBuilder();
		newBuilder.setResutlCode(this.getResutlCode());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_MoveError bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_MoveError.parseFrom(bytes);
	}
}