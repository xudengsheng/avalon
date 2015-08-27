package com.avalon.api.internal;


/**
 * 网络封包 获取操作码及数据内容
 * 
 * @author ZERO
 *
 */
public interface IoMessagePackage extends IoMessage {
	/**
	 * 获取操作码
	 * @return
	 */
	public int getOpCode();
	/***
	 * 获得元数据
	 * @return
	 */
	public byte[] getRawData();

}
