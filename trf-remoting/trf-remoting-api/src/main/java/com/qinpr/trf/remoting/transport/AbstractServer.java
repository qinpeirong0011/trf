package com.qinpr.trf.remoting.transport;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.Server;

import java.net.InetSocketAddress;

/**
 * Created by qinpr on 2018/11/1.
 */
public abstract class AbstractServer extends AbstractEndpoint implements Server {

    private InetSocketAddress localAddress;
    private InetSocketAddress bindAddress;
    private int accepts;
    private int idleTimeout = 600; //600 seconds

    public AbstractServer(URL url, ChannelHandler handler) throws RemotingException {
        super(url, handler);
        localAddress = getUrl().toInetSocketAddress();
        String bindIp = getUrl().getParameter(Constants.BIND_IP_KEY, getUrl().getHost());
        int bindPort = getUrl().getParameter(Constants.BIND_PORT_KEY, getUrl().getPort());
        bindAddress = new InetSocketAddress(bindIp, bindPort);
        this.accepts = url.getParameter(Constants.ACCEPTS_KEY, Constants.DEFAULT_ACCEPTS);
        this.idleTimeout = url.getParameter(Constants.IDLE_TIMEOUT_KEY, Constants.DEFAULT_IDLE_TIMEOUT);
        try {
            doOpen();
        } catch (Throwable t) {
            throw new RemotingException(url.toInetSocketAddress(), null, "Failed to bind " + getClass().getSimpleName()
                    + " on " + getLocalAddress() + ", cause: " + t.getMessage(), t);
        }
    }

    protected abstract void doOpen() throws Throwable;

    protected abstract void doClose() throws Throwable;

    public InetSocketAddress getBindAddress() {
        return bindAddress;
    }

    public void reset(URL url) {

    }

    public void connected(Channel channel) throws RemotingException {

    }

    public void disconnected(Channel channel) throws RemotingException {

    }

    public void sent(Channel channel, Object message) throws RemotingException {

    }

    public void received(Channel channel, Object message) throws RemotingException {

    }

    public void caught(Channel channel, Throwable exception) throws RemotingException {

    }
}
