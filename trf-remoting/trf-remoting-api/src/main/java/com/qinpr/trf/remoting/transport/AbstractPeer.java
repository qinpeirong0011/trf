package com.qinpr.trf.remoting.transport;

import com.qinpr.trf.common.URL;
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

    public URL getUrl() {
        return url;
    }

    public ChannelHandler getChannelHandler() {
        return null;
    }

    public InetSocketAddress getLocalAddress() {
        return null;
    }

    public void send(Object message) throws RemotingException {

    }

    public void send(Object message, boolean sent) throws RemotingException {

    }

    public void close() {

    }

    public void close(int timeout) {

    }

    public void startClose() {

    }

    public boolean isClosed() {
        return false;
    }
}
