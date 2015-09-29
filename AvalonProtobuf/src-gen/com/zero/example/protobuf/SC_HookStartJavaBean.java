package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.HookPro.*;
import com.zero.example.protobuf.HookPro.SC_HookStart;
import com.zero.example.protobuf.HookPro.SC_HookStart.Builder;


public class SC_HookStartJavaBean implements JavaProtocolTransform {

	private java.lang.Long currentTimestamp;
	private java.lang.Long duration_s;
	
	public java.lang.Long getCurrentTimestamp()
	{
		return 	currentTimestamp;
	}

	public void setCurrentTimestamp(java.lang.Long currentTimestamp) {
		this.currentTimestamp = currentTimestamp;
	}	
	public java.lang.Long getDuration_s()
	{
		return 	duration_s;
	}

	public void setDuration_s(java.lang.Long duration_s) {
		this.duration_s = duration_s;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_HookStart protocal = (SC_HookStart) message;
		this.setCurrentTimestamp(protocal.getCurrentTimestamp());
		this.setDuration_s(protocal.getDurationS());
	}

	@Override
	public SC_HookStart javaBeanToProtocol() {
		Builder newBuilder = SC_HookStart.newBuilder();
		newBuilder.setCurrentTimestamp(this.getCurrentTimestamp());
		newBuilder.setDurationS(this.getDuration_s());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_HookStart bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_HookStart.parseFrom(bytes);
	}
}