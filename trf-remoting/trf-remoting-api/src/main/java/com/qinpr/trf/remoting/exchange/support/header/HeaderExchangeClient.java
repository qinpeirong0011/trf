package com.qinpr.trf.remoting.exchange.support.header;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.utils.NamedThreadFactory;
import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.Client;
import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.exchange.ExchangeChannel;
import com.qinpr.trf.remoting.exchange.ExchangeClient;
import com.qinpr.trf.remoting.exchange.ExchangeHandler;
import com.qinpr.trf.remoting.exchange.ResponseFuture;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by qinpr on 2019/1/3.
 */
public class HeaderExchangeClient implements ExchangeClient {
    private static final ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(2, new NamedThreadFactory("dubbo-remoting-client-heartbeat", true));
    private final Client client;
    private final ExchangeChannel channel;
    // heartbeat timer
    private ScheduledFuture<?> heartbeatTimer;
    // heartbeat(ms), default value is 0 , won't execute a heartbeat.
    private int heartbeat;
    private int heartbeatTimeout;

    public HeaderExchangeClient(Client client, boolean needHeartbeat) {
        if (client == null) {
            throw new IllegalArgumentException("client == null");
        }
        this.client = client;
        this.channel = new HeaderExchangeChannel(client);
        String dubbo = client.getUrl().getParameter(Constants.TRF_VERSION_KEY);
        this.heartbeat = client.getUrl().getParameter(Constants.HEARTBEAT_KEY, dubbo != null && dubbo.startsWith("1.0.") ? Constants.DEFAULT_HEARTBEAT : 0);
        this.heartbeatTimeout = client.getUrl().getParameter(Constants.HEARTBEAT_TIMEOUT_KEY, heartbeat * 3);
        if (heartbeatTimeout < heartbeat * 2) {
            throw new IllegalStateException("heartbeatTimeout < heartbeatInterval * 2");
        }
        if (needHeartbeat) {
            startHeartbeatTimer();
        }
    }

    private void startHeartbeatTimer() {
        stopHeartbeatTimer();
        if (heartbeat > 0) {
            heartbeatTimer = scheduled.scheduleWithFixedDelay(
                    new HeartBeatTask(new HeartBeatTask.ChannelProvider() {
                        @Override
                        public Collection<Channel> getChannels() {
                            return Collections.<Channel>singletonList(HeaderExchangeClient.this);
                        }
                    }, heartbeat, heartbeatTimeout),
                    heartbeat, heartbeat, TimeUnit.MILLISECONDS);
        }
    }

    private void stopHeartbeatTimer() {
        if (heartbeatTimer != null && !heartbeatTimer.isCancelled()) {
            try {
                heartbeatTimer.cancel(true);
                scheduled.purge();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        heartbeatTimer = null;
    }


    @Override
    public void reset(URL url) {

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
    public void reconnect() throws RemotingException {

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
    public ResponseFuture request(Object request) throws RemotingException {
        return null;
    }

    @Override
    public ResponseFuture request(Object request, int timeout) throws RemotingException {
        return channel.request(request, timeout);
    }

    @Override
    public ExchangeHandler getExchangeHandler() {
        return null;
    }
}
