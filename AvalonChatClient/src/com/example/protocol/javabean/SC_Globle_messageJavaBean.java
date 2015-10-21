package com.example.protocol.javabean;

import com.avalon.protobuff.JavaProtocolTransform;
import com.example.protocol.LoginPro.SC_Globle_message;
import com.example.protocol.LoginPro.SC_Globle_message.Builder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;


public class SC_Globle_messageJavaBean implements JavaProtocolTransform {

	private java.lang.Integer key;
	private java.lang.Integer stats;
	private java.lang.String context;
	
	public java.lang.Integer getKey()
	{
		return 	key;
	}

	public void setKey(java.lang.Integer key) {
		this.key = key;
	}	
	public java.lang.Integer getStats()
	{
		return 	stats;
	}

	public void setStats(java.lang.Integer stats) {
		this.stats = stats;
	}	
	public java.lang.String getContext()
	{
		return 	context;
	}

	public void setContext(java.lang.String context) {
		this.context = context;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_Globle_message protocal = (SC_Globle_message) message;
		this.setKey(protocal.getKey());
		this.setStats(protocal.getStats());
		this.setContext(protocal.getContext());
	}

	@Override
	public SC_Globle_message javaBeanToProtocol() {
		Builder newBuilder = SC_Globle_message.newBuilder();
		newBuilder.setKey(this.getKey());
		newBuilder.setStats(this.getStats());
		newBuilder.setContext(this.getContext());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_Globle_message bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_Globle_message.parseFrom(bytes);
	}
}