package com.avalon.io.mina.fliter;

import java.util.Arrays;

import org.apache.mina.core.buffer.BufferDataException;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.avalon.api.internal.IoMessagePackage;
import com.avalon.io.MessagePackImpl;
import com.avalon.util.IobufferUtil;


public class DataCodecDecoder extends ProtocolDecoderAdapter {

	public DataCodecDecoder()
	{
		super();
	}

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception
	{
		try
		{
			decodeData(session, in, out);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Flash的跨域请求
	public final static String FLEX_POLICY = "<policy-file-request/>\0";

	// Flash的跨域请求回复（全域，全端口）
	public final static String FLEX_RESP = "<?xml version='1.0'?>" + "<cross-domain-policy>"
			+ "<allow-access-from domain='*' to-ports='*'/>" + "</cross-domain-policy>\0";

	public final static byte[] FLEX_POLICY_BYTE = FLEX_POLICY.getBytes();

	public final static byte[] FlEX_RESP_BYTE = FLEX_RESP.getBytes();

	// 通讯的缓存
	public final static String SOCKET_CACHE_DATA = "SOCKET_CACHE_DATA";

	// 消息头长度的字节数
	public final static int PREFIXED_LENGTH = 4;

	private void decodeData(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception
	{
		// 获得上次剩余的并把这次的放进去
		IoBuffer buffer = getLastCache(session, in);
		// 取得完整的数据
		if (prefixedDataAvailable(buffer, PREFIXED_LENGTH, Integer.MAX_VALUE))
		{
			int size = buffer.getInt();
			int opcode=buffer.getInt();
			int remaining = buffer.remaining();
			byte[] rew = new byte[remaining];
			buffer.get(rew, 0, remaining);

			IoMessagePackage messagePack=new MessagePackImpl(opcode, rew);
//			IoBuffer ioBuffer = IobufferUtil.getDefIoBuffer();
//			ioBuffer.put(rew);
//			ioBuffer.flip();

			// 完整的数据回写
			out.write(messagePack);
			// 处理剩下的缓存
			resetDate(buffer, size);
		}
	}

	public boolean prefixedDataAvailable(IoBuffer buffer, int prefixLength, int maxDataLength)
	{
		if (buffer.remaining() < prefixLength)
		{
			return false;
		}

		int dataLength;
		switch (prefixLength)
		{
		case 1:
			dataLength = buffer.getUnsigned(buffer.position());
			break;
		case 2:
			dataLength = buffer.getUnsignedShort(buffer.position());
			break;
		case 4:
			dataLength = buffer.getInt(buffer.position());
			break;
		default:
			throw new IllegalArgumentException("prefixLength: " + prefixLength);
		}

		if (dataLength < 0 || dataLength > maxDataLength)
		{
			throw new BufferDataException("dataLength: " + dataLength);
		}

		return buffer.remaining() - prefixLength >= dataLength;
	}

	/**
	 * 处理剩余的缓存
	 * 
	 * @param session
	 * @param size
	 */
	private void resetDate(IoBuffer buffer, int size)
	{
		// 没有缓存
		if (buffer.remaining() == 0)
		{
			buffer.sweep();
			return;
		}
		// 读出剩余数据
		byte[] remin = new byte[buffer.remaining()];
		buffer.get(remin);
		buffer.sweep();
		// 回写入缓存
		buffer.put(remin);
	}

	/**
	 * 获取上次的缓存并把这次的写入
	 * 
	 * @param session
	 * @param in
	 * @return
	 */
	private IoBuffer getLastCache(IoSession session, IoBuffer in)
	{

		IoBuffer cache = (IoBuffer) session.getAttribute(SOCKET_CACHE_DATA);
		if (cache == null)
		{
			cache = IobufferUtil.getDefIoBuffer();
			session.setAttribute(SOCKET_CACHE_DATA, cache);
			byte[] mes = new byte[in.limit()];
			in.get(mes);
			// 如果是Flash跨域请求，回复跨域信息 其他情况则填充数据
			if (Arrays.equals(mes, FLEX_POLICY_BYTE))
			{
				// String dumpBytesAsJavaByteArray = ByteUtils
				// .hexDumpForBytes(mes);
				// logger.info(dumpBytesAsJavaByteArray);
				session.write(FlEX_RESP_BYTE);
				cache.flip();
			} else
			{
				// String dumpBytesAsJavaByteArray =
				// ByteUtils.hexDumpForBytes(mes);
				// logger.info(dumpBytesAsJavaByteArray);
				cache.put(mes);
				cache.flip();
			}
			return cache;
		}
		cache.put(in);
		cache.flip();
		return cache;
	}

}
