package com.example.protocol.javabean;

import java.util.ArrayList;
import java.util.List;

import com.avalon.protocol.JavaProtocolTransform;
import com.example.protocol.HallPro.SC_HallInfo;
import com.example.protocol.HallPro.SC_HallInfo.Builder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;


public class SC_HallInfoJavaBean implements JavaProtocolTransform {

	private java.lang.Integer hallId;
	private List<java.lang.String> usersName;
	
	public java.lang.Integer getHallId()
	{
		return 	hallId;
	}

	public void setHallId(java.lang.Integer hallId) {
		this.hallId = hallId;
	}	
	public List<java.lang.String> getUsersName()
	{
		return 	usersName;
	}

	public void setUsersName(List<java.lang.String> usersName) {
		this.usersName = usersName;
	}	
	
	@Override
	public void protocolToJavaBean(Message message) {
		SC_HallInfo protocal = (SC_HallInfo) message;
		this.setHallId(protocal.getHallId());
		{	
		List<java.lang.String> list = new ArrayList<java.lang.String>();
		for (java.lang.String JavaBean : protocal.getUsersNameList()) {
			list.add(JavaBean);
		}
		this.setUsersName(list);
		}
	}

	@Override
	public SC_HallInfo javaBeanToProtocol() {
		Builder newBuilder = SC_HallInfo.newBuilder();
		newBuilder.setHallId(this.getHallId());
		{
			List<java.lang.String> list = new ArrayList<java.lang.String>();
			for (java.lang.String JavaBean : this.getUsersName()) {
				list.add(JavaBean);
			}
			newBuilder.addAllUsersName(list);
		}
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public SC_HallInfo bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return SC_HallInfo.parseFrom(bytes);
	}
}