package com.qinpr.trf.rpc.protocol.trf;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.exchange.ExchangeClient;
import com.qinpr.trf.remoting.exchange.ExchangeHandler;
import com.qinpr.trf.remoting.exchange.ResponseFuture;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by qinpr on 2019/1/3.
 */
public class ReferenceCountExchangeClient implements ExchangeClient {
    private final URL url;
    private final AtomicInteger refenceCount = new AtomicInteger(0);

    //    private final ExchangeHandler handler;
    private final ConcurrentMap<String, LazyConnectExchangeClient> ghostClientMap;
    private ExchangeClient client;

    public ReferenceCountExchangeClient(ExchangeClient client, ConcurrentMap<String, LazyConnectExchangeClient> ghostClientMap) {
        this.client = client;
        refenceCount.incrementAndGet();
        this.url = client.getUrl();
        if (ghostClientMap == null) {
            throw new IllegalStateException("ghostClientMap can not be null, url: " + url);
        }
        this.ghostClientMap = ghostClientMap;
    }

    @Override
    public void reset(URL url) {
        client.reset(url);
    }

    @Override
    public ResponseFuture request(Object request) throws RemotingException {
        return client.request(request);
    }

    @Override
    public URL getUrl() {
        return client.getUrl();
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return client.getRemoteAddress();
    }

    @Override
    public ChannelHandler getChannelHandler() {
        return client.getChannelHandler();
    }

    @Override
    public ResponseFuture request(Object request, int timeout) throws RemotingException {
        return client.request(request, timeout);
    }

    @Override
    public boolean isConnected() {
        return client.isConnected();
    }

    @Override
    public void reconnect() throws RemotingException {
        client.reconnect();
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return client.getLocalAddress();
    }

    @Override
    public boolean hasAttribute(String key) {
        return client.hasAttribute(key);
    }

    @Override
    public void send(Object message) throws RemotingException {
        client.send(message);
    }

    @Override
    public ExchangeHandler getExchangeHandler() {
        return client.getExchangeHandler();
    }

    @Override
    public Object getAttribute(String key) {
        return client.getAttribute(key);
    }

    @Override
    public void send(Object message, boolean sent) throws RemotingException {
        client.send(message, sent);
    }

    @Override
    public void setAttribute(String key, Object value) {
        client.setAttribute(key, value);
    }

    @Override
    public void removeAttribute(String key) {
        client.removeAttribute(key);
    }

    /**
     * close() is not idempotent any longer
     */
    @Override
    public void close() {
        close(0);
    }

    @Override
    public void close(int timeout) {
        if (refenceCount.decrementAndGet() <= 0) {
            if (timeout == 0) {
                client.close();
            } else {
                client.close(timeout);
            }
            client = replaceWithLazyClient();
        }
    }

    @Override
    public void startClose() {
        client.startClose();
    }

    private LazyConnectExchangeClient replaceWithLazyClient() {
        // this is a defensive operation to avoid client is closed by accident, the initial state of the client is false
        URL lazyUrl = url.addParameter(Constants.LAZY_CONNECT_INITIAL_STATE_KEY, Boolean.FALSE)
                .addParameter(Constants.RECONNECT_KEY, Boolean.FALSE)
                .addParameter(Constants.SEND_RECONNECT_KEY, Boolean.TRUE.toString())
                .addParameter("warning", Boolean.TRUE.toString())
                .addParameter(LazyConnectExchangeClient.REQUEST_WITH_WARNING_KEY, true)
                .addParameter("_client_memo", "referencecounthandler.replacewithlazyclient");

        String key = url.getAddress();
        // in worst case there's only one ghost connection.
        LazyConnectExchangeClient gclient = ghostClientMap.get(key);
        if (gclient == null || gclient.isClosed()) {
            gclient = new LazyConnectExchangeClient(lazyUrl, client.getExchangeHandler());
            ghostClientMap.put(key, gclient);
        }
        return gclient;
    }

    @Override
    public boolean isClosed() {
        return client.isClosed();
    }

    public void incrementAndGetCount() {
        refenceCount.incrementAndGet();
    }
}
