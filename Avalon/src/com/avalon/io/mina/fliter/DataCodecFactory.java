package com.avalon.io.mina.fliter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class DataCodecFactory implements ProtocolCodecFactory {

	// 编码器
	private final DataCodecEncoder encoder;
	// 解码器（通讯消息的完整）
	private final DataCodecDecoder decoder;

	public DataCodecFactory() {
		super();
		this.encoder = new DataCodecEncoder();
		this.decoder = new DataCodecDecoder();
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

}
