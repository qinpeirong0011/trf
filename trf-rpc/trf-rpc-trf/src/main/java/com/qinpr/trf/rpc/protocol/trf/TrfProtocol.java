package com.qinpr.trf.rpc.protocol.trf;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.common.utils.ConcurrentHashSet;
import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.exchange.ExchangeHandler;
import com.qinpr.trf.remoting.exchange.ExchangeServer;
import com.qinpr.trf.remoting.exchange.Exchangers;
import com.qinpr.trf.remoting.exchange.support.ExchangeHandlerAdapter;
import com.qinpr.trf.rpc.Exporter;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.RpcException;
import com.qinpr.trf.rpc.protocol.AbstractProtocol;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by qinpr on 2018/10/18.
 */
public class TrfProtocol extends AbstractProtocol {

    private final Map<String, ExchangeServer> serverMap = new ConcurrentHashMap<String, ExchangeServer>();

    protected final Set<Invoker<?>> invokers = new ConcurrentHashSet<Invoker<?>>();

    private ExchangeHandler requestHandler = new ExchangeHandlerAdapter() {
        @Override
        public CompletableFuture<Object> reply(ExchangeHandler channel, Object request) throws RemotingException {
            return super.reply(channel, request);
        }
    };

    public int getDefaultPort() {
        return 0;
    }

    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        URL url = invoker.getUrl();
        String key = serviceKey(url);
        TrfExporter<T> exporter = new TrfExporter<T>(invoker, key, exporterMap);
        exporterMap.put(key, exporter);
        openServer(url);
        return exporter;
    }

    private void openServer(URL url) {
        String key = url.getAddress();
        boolean isServer = url.getParameter(Constants.IS_SERVER_KEY, true);
        if (isServer) {
            ExchangeServer server = serverMap.get(key);
            if (server == null) {
                synchronized (this) {
                    server = serverMap.get(key);
                    if (server == null) {
                        serverMap.put(key, createServer(url));
                    }
                }
            } else {
                server.reset(url);
            }
        }
    }

    private ExchangeServer createServer(URL url) {
        url = url.addParameterIfAbsent(Constants.CHANNEL_READONLYEVENT_SENT_KEY, Boolean.TRUE.toString());
        url = url.addParameterIfAbsent(Constants.HEARTBEAT_KEY, String.valueOf(Constants.DEFAULT_HEARTBEAT));
        url = url.addParameter(Constants.CODEC_KEY, "trf");
        ExchangeServer server;
        try {
            server = Exchangers.bind(url, requestHandler);
        } catch (RemotingException e) {
            throw new RpcException("Fail to start server(url: " + url + ") " + e.getMessage(), e);
        }
        return server;
    }

    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return null;
    }

    public void destroy() {

    }

    protected static String serviceKey(URL url) {
        int port = url.getParameter(Constants.BIND_PORT_KEY, url.getPort());
        return serviceKey(port, url.getPath(), url.getParameter(Constants.VERSION_KEY),
                url.getParameter(Constants.GROUP_KEY));
    }
}
