package com.example.protocol.helper;
import com.example.protocol.javabean.CS_LoginJavaBean;
import com.example.protocol.LoginPro.CS_Login;
import com.example.protocol.javabean.SC_LoginJavaBean;
import com.example.protocol.LoginPro.SC_Login;
import com.google.protobuf.InvalidProtocolBufferException;

public class LoginCodecHelper {

	public CS_LoginJavaBean decodeCS_LoginJavaBean(byte[] message) throws InvalidProtocolBufferException
	{
		CS_LoginJavaBean bean = new  CS_LoginJavaBean();
		CS_Login bytesToProtocol = bean.bytesToProtocol(message);
		bean.protocolToJavaBean(bytesToProtocol);
		return bean;
	}

	public CS_Login ecodeCS_LoginJavaBean( CS_LoginJavaBean bean)
	{
		CS_Login bytesToProtocol = bean.javaBeanToProtocol();
		return bytesToProtocol;
	}
	public SC_LoginJavaBean decodeSC_LoginJavaBean(byte[] message) throws InvalidProtocolBufferException
	{
		SC_LoginJavaBean bean = new  SC_LoginJavaBean();
		SC_Login bytesToProtocol = bean.bytesToProtocol(message);
		bean.protocolToJavaBean(bytesToProtocol);
		return bean;
	}

	public SC_Login ecodeSC_LoginJavaBean( SC_LoginJavaBean bean)
	{
		SC_Login bytesToProtocol = bean.javaBeanToProtocol();
		return bytesToProtocol;
	}
}
