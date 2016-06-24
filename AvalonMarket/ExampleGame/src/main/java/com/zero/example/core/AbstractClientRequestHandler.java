package com.zero.example.core;

import com.avalon.extensions.request.IClientRequestHandler;
import com.avalon.protocol.JavaProtocolTransform;
import com.google.protobuf.InvalidProtocolBufferException;

public abstract class AbstractClientRequestHandler implements IClientRequestHandler {

	@Override
	public void handleClientRequest(Object listener, Object message) {
		try {
			JavaProtocolTransform decode = decode(message);

			boolean pass = verifyParams(decode);

			if (pass) {
				handleClientRequest(listener, decode);
			} else {

			}
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解码验证过得参数
	 * 
	 * @param listener
	 * @param message
	 */
	public abstract void handleClientRequest(Object listener, JavaProtocolTransform message);

	/**
	 * 解码
	 * 
	 * @param meObject
	 * @throws InvalidProtocolBufferException
	 */
	public abstract JavaProtocolTransform decode(Object meObject) throws InvalidProtocolBufferException;

	/**
	 * 参数的校对 子类有需求就叫对
	 */
	public boolean verifyParams(JavaProtocolTransform javaBean) {
		return true;
	}
}
