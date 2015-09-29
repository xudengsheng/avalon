package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.UpdatePlayerPro.*;
import com.zero.example.protobuf.UpdatePlayerPro.SC_UpdatePlayerYuanBao;
import com.zero.example.protobuf.UpdatePlayerPro.SC_UpdatePlayerYuanBao.Builder;


public class SC_UpdatePlayerYuanBaoJavaBean implements JavaProtocolTransform {

	private java.lang.Integer yuanBao;
	
	public java.lang.Integer getYuanBao()
	{
		return 	yuanBao;
	}

	public void setYuanBao(java.lang.Integer yuanBao) {
		this.yuanBao = yuanBao;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_UpdatePlayerYuanBao protocal = (SC_UpdatePlayerYuanBao) message;
		this.setYuanBao(protocal.getYuanBao());
	}

	@Override
	public SC_UpdatePlayerYuanBao javaBeanToProtocol() {
		Builder newBuilder = SC_UpdatePlayerYuanBao.newBuilder();
		newBuilder.setYuanBao(this.getYuanBao());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_UpdatePlayerYuanBao bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_UpdatePlayerYuanBao.parseFrom(bytes);
	}
}