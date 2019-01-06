package com.qinpr.trf.remoting.transport.netty;

import com.qinpr.trf.remoting.buffer.ChannelBuffer;
import com.qinpr.trf.remoting.buffer.ChannelBufferFactory;
import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

/**
 * Created by qinpr on 2019/1/6.
 */
public class NettyBackedChannelBuffer implements ChannelBuffer {

    private ByteBuf buffer;

    public NettyBackedChannelBuffer(ByteBuf buffer) {
        this.buffer = buffer;
    }

    @Override
    public int capacity() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public ChannelBuffer copy() {
        return null;
    }

    @Override
    public ChannelBuffer copy(int index, int length) {
        return null;
    }

    @Override
    public void discardReadBytes() {

    }

    @Override
    public void ensureWritableBytes(int writableBytes) {

    }

    @Override
    public ChannelBufferFactory factory() {
        return null;
    }

    @Override
    public byte getByte(int index) {
        return 0;
    }

    @Override
    public void getBytes(int index, byte[] dst) {

    }

    @Override
    public void getBytes(int index, byte[] dst, int dstIndex, int length) {

    }

    @Override
    public void getBytes(int index, ByteBuffer dst) {

    }

    @Override
    public void getBytes(int index, ChannelBuffer dst) {

    }

    @Override
    public int readerIndex() {
        return buffer.readerIndex();
    }

    @Override
    public void readerIndex(int readerIndex) {
        buffer.readerIndex(readerIndex);
    }

    @Override
    public boolean readable() {
        return buffer.isReadable();
    }

    @Override
    public int compareTo(ChannelBuffer o) {
        return 0;
    }
}
