package com.qinpr.trf.remoting.transport;

import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.RemotingException;

/**
 * Created by qinpr on 2018/10/26.
 */
public abstract class AbstractChannelHandlerDelegate implements ChannelHandlerDelegate {
    protected ChannelHandler handler;

    protected AbstractChannelHandlerDelegate(ChannelHandler handler) {
        this.handler = handler;
    }

    public ChannelHandler getHandler() {
        return null;
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
