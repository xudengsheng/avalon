package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.HookPro.*;
import com.zero.example.protobuf.HookPro.SC_HookStop;
import com.zero.example.protobuf.HookPro.SC_HookStop.Builder;
import com.zero.example.protobuf.StructurePro.*;


public class SC_HookStopJavaBean implements JavaProtocolTransform {

	private HookInfoProtocolJavaBean hookInfo;
	
	
	public HookInfoProtocolJavaBean getHookInfo()
	{
		return 	hookInfo;
	}

	public void setHookInfo(HookInfoProtocolJavaBean hookInfo) {
		this.hookInfo = hookInfo;
	}	
	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_HookStop protocal = (SC_HookStop) message;
		{
			HookInfoProtocolJavaBean protocol = new HookInfoProtocolJavaBean();
			protocol.protocolToJavaBean(protocal.getHookInfo());
			this.setHookInfo(protocol);
		}
	}

	@Override
	public SC_HookStop javaBeanToProtocol() {
		Builder newBuilder = SC_HookStop.newBuilder();
		{
			HookInfoProtocol protocol = this.getHookInfo().javaBeanToProtocol();
			newBuilder.setHookInfo(protocol);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_HookStop bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_HookStop.parseFrom(bytes);
	}
}