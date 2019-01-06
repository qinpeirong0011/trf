package com.qinpr.trf.remoting.exchange.support.header;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.exchange.ExchangeChannel;
import com.qinpr.trf.remoting.exchange.ExchangeHandler;
import com.qinpr.trf.remoting.exchange.ResponseFuture;

import java.net.InetSocketAddress;

/**
 * Created by qinpr on 2019/1/3.
 */
public class HeaderExchangeChannel implements ExchangeChannel {
    private static final String CHANNEL_KEY = HeaderExchangeChannel.class.getName() + ".CHANNEL";

    private final Channel channel;

    private volatile boolean closed = false;

    HeaderExchangeChannel(Channel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("channel == null");
        }
        this.channel = channel;
    }


    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public boolean hasAttribute(String key) {
        return false;
    }

    @Override
    public Object getAttribute(String key) {
        return null;
    }

    @Override
    public void setAttribute(String key, Object value) {

    }

    @Override
    public void removeAttribute(String key) {

    }

    @Override
    public URL getUrl() {
        return null;
    }

    @Override
    public ChannelHandler getChannelHandler() {
        return null;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public void send(Object message) throws RemotingException {

    }

    @Override
    public void send(Object message, boolean sent) throws RemotingException {

    }

    @Override
    public void close() {

    }

    @Override
    public ResponseFuture request(Object request) throws RemotingException {
        return null;
    }

    @Override
    public ResponseFuture request(Object request, int timeout) throws RemotingException {
        return null;
    }

    @Override
    public void close(int timeout) {

    }

    @Override
    public void startClose() {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public ExchangeHandler getExchangeHandler() {
        return null;
    }
}
