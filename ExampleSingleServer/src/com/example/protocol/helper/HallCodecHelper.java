package com.example.protocol.helper;

import com.example.protocol.HallPro.CS_HallMessage;
import com.example.protocol.HallPro.CS_JoinHall;
import com.example.protocol.HallPro.SC_HallInfo;
import com.example.protocol.HallPro.SC_HallMessage;
import com.example.protocol.HallPro.SC_JoinHall;
import com.example.protocol.HallPro.SC_LeaveHall;
import com.example.protocol.javabean.CS_HallMessageJavaBean;
import com.example.protocol.javabean.CS_JoinHallJavaBean;
import com.example.protocol.javabean.SC_HallInfoJavaBean;
import com.example.protocol.javabean.SC_HallMessageJavaBean;
import com.example.protocol.javabean.SC_JoinHallJavaBean;
import com.example.protocol.javabean.SC_LeaveHallJavaBean;
import com.google.protobuf.InvalidProtocolBufferException;

public class HallCodecHelper {

	public static CS_JoinHallJavaBean decodeCS_JoinHallJavaBean(byte[] message) throws InvalidProtocolBufferException
	{
		CS_JoinHallJavaBean bean = new  CS_JoinHallJavaBean();
		CS_JoinHall bytesToProtocol = bean.bytesToProtocol(message);
		bean.protocolToJavaBean(bytesToProtocol);
		return bean;
	}

	public static CS_JoinHall ecodeCS_JoinHallJavaBean( CS_JoinHallJavaBean bean)
	{
		CS_JoinHall bytesToProtocol = bean.javaBeanToProtocol();
		return bytesToProtocol;
	}
	public static SC_JoinHallJavaBean decodeSC_JoinHallJavaBean(byte[] message) throws InvalidProtocolBufferException
	{
		SC_JoinHallJavaBean bean = new  SC_JoinHallJavaBean();
		SC_JoinHall bytesToProtocol = bean.bytesToProtocol(message);
		bean.protocolToJavaBean(bytesToProtocol);
		return bean;
	}

	public static SC_JoinHall ecodeSC_JoinHallJavaBean( SC_JoinHallJavaBean bean)
	{
		SC_JoinHall bytesToProtocol = bean.javaBeanToProtocol();
		return bytesToProtocol;
	}
	public static SC_LeaveHallJavaBean decodeSC_LeaveHallJavaBean(byte[] message) throws InvalidProtocolBufferException
	{
		SC_LeaveHallJavaBean bean = new  SC_LeaveHallJavaBean();
		SC_LeaveHall bytesToProtocol = bean.bytesToProtocol(message);
		bean.protocolToJavaBean(bytesToProtocol);
		return bean;
	}

	public static SC_LeaveHall ecodeSC_LeaveHallJavaBean( SC_LeaveHallJavaBean bean)
	{
		SC_LeaveHall bytesToProtocol = bean.javaBeanToProtocol();
		return bytesToProtocol;
	}
	public static SC_HallInfoJavaBean decodeSC_HallInfoJavaBean(byte[] message) throws InvalidProtocolBufferException
	{
		SC_HallInfoJavaBean bean = new  SC_HallInfoJavaBean();
		SC_HallInfo bytesToProtocol = bean.bytesToProtocol(message);
		bean.protocolToJavaBean(bytesToProtocol);
		return bean;
	}

	public static SC_HallInfo ecodeSC_HallInfoJavaBean( SC_HallInfoJavaBean bean)
	{
		SC_HallInfo bytesToProtocol = bean.javaBeanToProtocol();
		return bytesToProtocol;
	}
	public static CS_HallMessageJavaBean decodeCS_HallMessageJavaBean(byte[] message) throws InvalidProtocolBufferException
	{
		CS_HallMessageJavaBean bean = new  CS_HallMessageJavaBean();
		CS_HallMessage bytesToProtocol = bean.bytesToProtocol(message);
		bean.protocolToJavaBean(bytesToProtocol);
		return bean;
	}

	public static CS_HallMessage ecodeCS_HallMessageJavaBean( CS_HallMessageJavaBean bean)
	{
		CS_HallMessage bytesToProtocol = bean.javaBeanToProtocol();
		return bytesToProtocol;
	}
	public static SC_HallMessageJavaBean decodeSC_HallMessageJavaBean(byte[] message) throws InvalidProtocolBufferException
	{
		SC_HallMessageJavaBean bean = new  SC_HallMessageJavaBean();
		SC_HallMessage bytesToProtocol = bean.bytesToProtocol(message);
		bean.protocolToJavaBean(bytesToProtocol);
		return bean;
	}

	public static SC_HallMessage ecodeSC_HallMessageJavaBean( SC_HallMessageJavaBean bean)
	{
		SC_HallMessage bytesToProtocol = bean.javaBeanToProtocol();
		return bytesToProtocol;
	}
}
