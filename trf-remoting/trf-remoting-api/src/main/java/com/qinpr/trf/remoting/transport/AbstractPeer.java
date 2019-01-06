package com.qinpr.trf.remoting.transport;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.Endpoint;
import com.qinpr.trf.remoting.RemotingException;

import java.net.InetSocketAddress;

/**
 * Created by qinpr on 2018/11/1.
 */
public abstract class AbstractPeer implements Endpoint, ChannelHandler {

    private final ChannelHandler handler;

    private volatile URL url;

    public AbstractPeer(URL url, ChannelHandler handler) {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }
        if (handler == null) {
            throw new IllegalArgumentException("handler == null");
        }
        this.url = url;
        this.handler = handler;
    }

    @Override
    public URL getUrl() {
        return url;
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
    public void connected(Channel channel) throws RemotingException {

    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {

    }

    @Override
    public void sent(Channel channel, Object message) throws RemotingException {

    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {

    }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException {

    }
}
