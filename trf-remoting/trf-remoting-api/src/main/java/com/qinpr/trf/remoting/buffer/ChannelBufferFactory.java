package com.qinpr.trf.remoting.buffer;

import java.nio.ByteBuffer;

/**
 * Created by qinpr on 2018/11/1.
 */
public interface ChannelBufferFactory {
    ChannelBuffer getBuffer(int capacity);

    ChannelBuffer getBuffer(byte[] array, int offset, int length);

    ChannelBuffer getBuffer(ByteBuffer nioBuffer);
}
