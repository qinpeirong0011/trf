package com.qinpr.trf.remoting.transport;

import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.RemotingException;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by qinpr on 2018/10/26.
 */
public class ChannelHandlerDispatcher implements ChannelHandler {


    private final Collection<ChannelHandler> channelHandlers = new CopyOnWriteArraySet<ChannelHandler>();

    public ChannelHandlerDispatcher(ChannelHandler... handlers) {
        this(handlers == null ? null : Arrays.asList(handlers));
    }

    public ChannelHandlerDispatcher(Collection<ChannelHandler> handlers) {
        if (handlers != null && !handlers.isEmpty()) {
            this.channelHandlers.addAll(handlers);
        }
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
