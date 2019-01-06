package com.qinpr.trf.remoting.buffer;

import java.nio.ByteBuffer;

/**
 * Created by qinpr on 2018/11/1.
 */
public interface ChannelBuffer extends Comparable<ChannelBuffer> {

    int capacity();

    void clear();

    ChannelBuffer copy();

    ChannelBuffer copy(int index, int length);

    void discardReadBytes();

    void ensureWritableBytes(int writableBytes);

    ChannelBufferFactory factory();

    byte getByte(int index);

    void getBytes(int index, byte[] dst);

    void getBytes(int index, byte[] dst, int dstIndex, int length);

    void getBytes(int index, ByteBuffer dst);

    void getBytes(int index, ChannelBuffer dst);

    int readerIndex();

    void readerIndex(int readerIndex);

    boolean readable();
}
