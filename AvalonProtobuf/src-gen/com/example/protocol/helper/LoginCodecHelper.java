package com.example.protocol.helper;

import com.example.protocol.LoginPro.CS_Login;
import com.example.protocol.LoginPro.CS_LoginNewName;
import com.example.protocol.LoginPro.CS_Regedit;
import com.example.protocol.LoginPro.SC_Globle_message;
import com.example.protocol.LoginPro.SC_LoginInfo;
import com.example.protocol.javabean.CS_LoginJavaBean;
import com.example.protocol.javabean.CS_LoginNewNameJavaBean;
import com.example.protocol.javabean.CS_RegeditJavaBean;
import com.example.protocol.javabean.SC_Globle_messageJavaBean;
import com.example.protocol.javabean.SC_LoginInfoJavaBean;
import com.google.protobuf.InvalidProtocolBufferException;

public class LoginCodecHelper {

	public static CS_LoginJavaBean decodeCS_LoginJavaBean(byte[] message) throws InvalidProtocolBufferException
	{
		CS_LoginJavaBean bean = new  CS_LoginJavaBean();
		CS_Login bytesToProtocol = bean.bytesToProtocol(message);
		bean.protocolToJavaBean(bytesToProtocol);
		return bean;
	}

	public static CS_Login ecodeCS_LoginJavaBean( CS_LoginJavaBean bean)
	{
		CS_Login bytesToProtocol = bean.javaBeanToProtocol();
		return bytesToProtocol;
	}
	public static CS_RegeditJavaBean decodeCS_RegeditJavaBean(byte[] message) throws InvalidProtocolBufferException
	{
		CS_RegeditJavaBean bean = new  CS_RegeditJavaBean();
		CS_Regedit bytesToProtocol = bean.bytesToProtocol(message);
		bean.protocolToJavaBean(bytesToProtocol);
		return bean;
	}

	public static CS_Regedit ecodeCS_RegeditJavaBean( CS_RegeditJavaBean bean)
	{
		CS_Regedit bytesToProtocol = bean.javaBeanToProtocol();
		return bytesToProtocol;
	}
	public static SC_Globle_messageJavaBean decodeSC_Globle_messageJavaBean(byte[] message) throws InvalidProtocolBufferException
	{
		SC_Globle_messageJavaBean bean = new  SC_Globle_messageJavaBean();
		SC_Globle_message bytesToProtocol = bean.bytesToProtocol(message);
		bean.protocolToJavaBean(bytesToProtocol);
		return bean;
	}

	public static SC_Globle_message ecodeSC_Globle_messageJavaBean( SC_Globle_messageJavaBean bean)
	{
		SC_Globle_message bytesToProtocol = bean.javaBeanToProtocol();
		return bytesToProtocol;
	}
	public static CS_LoginNewNameJavaBean decodeCS_LoginNewNameJavaBean(byte[] message) throws InvalidProtocolBufferException
	{
		CS_LoginNewNameJavaBean bean = new  CS_LoginNewNameJavaBean();
		CS_LoginNewName bytesToProtocol = bean.bytesToProtocol(message);
		bean.protocolToJavaBean(bytesToProtocol);
		return bean;
	}

	public static CS_LoginNewName ecodeCS_LoginNewNameJavaBean( CS_LoginNewNameJavaBean bean)
	{
		CS_LoginNewName bytesToProtocol = bean.javaBeanToProtocol();
		return bytesToProtocol;
	}
	public static SC_LoginInfoJavaBean decodeSC_LoginInfoJavaBean(byte[] message) throws InvalidProtocolBufferException
	{
		SC_LoginInfoJavaBean bean = new  SC_LoginInfoJavaBean();
		SC_LoginInfo bytesToProtocol = bean.bytesToProtocol(message);
		bean.protocolToJavaBean(bytesToProtocol);
		return bean;
	}

	public static SC_LoginInfo ecodeSC_LoginInfoJavaBean( SC_LoginInfoJavaBean bean)
	{
		SC_LoginInfo bytesToProtocol = bean.javaBeanToProtocol();
		return bytesToProtocol;
	}
}
