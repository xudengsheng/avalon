package com.avalon.util;

import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * IObuffer工具类
 * @author ZERO
 *
 */
public class IobufferUtil {

    /**
     * 默认1024个字节（1k）
     */
    private static final int DEFAUL_SIZE = 1024;

    private static final int DEFAUL_HEAD_SIZE = 4;

    /**
     * 获取一个4个字节起始的IoBuffer
     * 
     * @return
     */
    public static IoBuffer getDefIoBuffer() {
        IoBuffer buffer = IoBuffer.allocate(DEFAUL_HEAD_SIZE);
        buffer.setAutoExpand(true);
        buffer.setAutoShrink(true);
        return buffer;
    }

    /**
     * 获取一个封装好指令的IoBuffer
     * 
     * @param Command
     *            指令号
     * @return
     */
    public static IoBuffer getIoBufferWithCommandId(int Command) {
        return getDefIoBuffer().putInt(Command);
    }

    public static byte[] getByte(IoBuffer buffer) {
        byte[] b = new byte[buffer.limit()-4];
        buffer.get(b);
        return b;
    }

    /**
     * 舍弃一个int位
     * 
     * @param buffer
     * @return
     */
    public static byte[] getByte(ByteBuffer buffer) {
        byte[] b = new byte[buffer.limit() - 4];
        buffer.get(b);
        return b;
    }

    public static byte[] getByte(int message) {
        IoBuffer buffer = getDefIoBuffer();
        buffer.putInt(message);
        buffer.flip();
        byte[] b = new byte[buffer.limit()];
        buffer.get(b);
        return b;
    }


    public static byte[] getBytes(IoBuffer buffer) {
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return bytes;
    }

}
