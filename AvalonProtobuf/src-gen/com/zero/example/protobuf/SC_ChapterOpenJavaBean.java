package com.zero.example.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import com.zero.example.protobuf.MissionPro.*;
import com.zero.example.protobuf.MissionPro.SC_ChapterOpen;
import com.zero.example.protobuf.MissionPro.SC_ChapterOpen.Builder;


public class SC_ChapterOpenJavaBean implements JavaProtocolTransform {

	private java.lang.Integer chapterId;
	
	public java.lang.Integer getChapterId()
	{
		return 	chapterId;
	}

	public void setChapterId(java.lang.Integer chapterId) {
		this.chapterId = chapterId;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_ChapterOpen protocal = (SC_ChapterOpen) message;
		this.setChapterId(protocal.getChapterId());
	}

	@Override
	public SC_ChapterOpen javaBeanToProtocol() {
		Builder newBuilder = SC_ChapterOpen.newBuilder();
		newBuilder.setChapterId(this.getChapterId());
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_ChapterOpen bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_ChapterOpen.parseFrom(bytes);
	}
}