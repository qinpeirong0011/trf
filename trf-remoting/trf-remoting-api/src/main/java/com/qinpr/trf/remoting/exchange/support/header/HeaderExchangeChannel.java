package com.qinpr.trf.remoting.exchange.support.header;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.Version;
import com.qinpr.trf.remoting.*;
import com.qinpr.trf.remoting.exchange.ExchangeChannel;
import com.qinpr.trf.remoting.exchange.ExchangeHandler;
import com.qinpr.trf.remoting.exchange.ResponseFuture;
import com.qinpr.trf.remoting.exchange.support.DefaultFuture;

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
        return channel.getRemoteAddress();
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
        send(message, false);
    }

    @Override
    public void send(Object message, boolean sent) throws RemotingException {
        if (closed) {
            throw new RemotingException(this.getLocalAddress(), null, "Failed to send message " + message + ", cause: The channel " + this + " is closed!");
        }
        if (message instanceof Request
                || message instanceof Response
                || message instanceof String) {
            channel.send(message, sent);
        } else {
            Request request = new Request();
            request.setVersion(Version.getProtocolVersion());
            request.setTwoWay(false);
            request.setData(message);
            channel.send(request, sent);
        }
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
        if (closed) {
            throw new RemotingException(this.getLocalAddress(), null, "Failed to send request " + request + ", cause: The channel " + this + " is closed!");
        }
        // create request.
        Request req = new Request();
        req.setVersion(Version.getProtocolVersion());
        req.setTwoWay(true);
        req.setData(request);
        DefaultFuture future = DefaultFuture.newFuture(channel, req, timeout);
        try {
            channel.send(req);
        } catch (RemotingException e) {
            future.cancel();
            throw e;
        }
        return future;
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
