package com.qinpr.trf.remoting.exchange.support.header;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.Server;
import com.qinpr.trf.remoting.exchange.ExchangeServer;

import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * Created by qinpr on 2018/10/26.
 */
public class HeaderExchangeServer implements ExchangeServer {

    private final Server server;

    public HeaderExchangeServer(Server server) {
        this.server = server;
    }

    public void reset(URL url) {

    }

    public URL getUrl() {
        return null;
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

    public boolean isBound() {
        return false;
    }

    public Collection<Channel> getChannels() {
        return null;
    }

    public Channel getChannel(InetSocketAddress remoteAddress) {
        return null;
    }
}
