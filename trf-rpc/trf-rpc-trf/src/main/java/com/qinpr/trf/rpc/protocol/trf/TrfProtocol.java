package com.qinpr.trf.rpc.protocol.trf;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.common.utils.ConcurrentHashSet;
import com.qinpr.trf.common.utils.NetUtils;
import com.qinpr.trf.common.utils.StringUtils;
import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.Transporter;
import com.qinpr.trf.remoting.exchange.*;
import com.qinpr.trf.remoting.exchange.support.ExchangeHandlerAdapter;
import com.qinpr.trf.rpc.*;
import com.qinpr.trf.rpc.protocol.AbstractProtocol;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * Created by qinpr on 2018/10/18.
 */
public class TrfProtocol extends AbstractProtocol {

    public static final int DEFAULT_PORT = 20880;
    private static final String IS_CALLBACK_SERVICE_INVOKE = "_isCallBackServiceInvoke";

    private final Map<String, ExchangeServer> serverMap = new ConcurrentHashMap<String, ExchangeServer>();

    private final Map<String, ReferenceCountExchangeClient> referenceClientMap = new ConcurrentHashMap<String, ReferenceCountExchangeClient>(); // <host:port,Exchanger>

    private final ConcurrentMap<String, LazyConnectExchangeClient> ghostClientMap = new ConcurrentHashMap<String, LazyConnectExchangeClient>();

    private final ConcurrentMap<String, Object> locks = new ConcurrentHashMap<String, Object>();

    protected final Set<Invoker<?>> invokers = new ConcurrentHashSet<Invoker<?>>();

    private ExchangeHandler requestHandler = new ExchangeHandlerAdapter() {

        @Override
        public CompletableFuture<Object> reply(ExchangeChannel channel, Object message) throws RemotingException {
            if (message instanceof Invocation) {
                Invocation inv = (Invocation) message;
                Invoker<?> invoker = getInvoker(channel, inv);
                // need to consider backward-compatibility if it's a callback
                if (Boolean.TRUE.toString().equals(inv.getAttachments().get(IS_CALLBACK_SERVICE_INVOKE))) {
                    String methodsStr = invoker.getUrl().getParameters().get("methods");
                    boolean hasMethod = false;
                    if (methodsStr == null || !methodsStr.contains(",")) {
                        hasMethod = inv.getMethodName().equals(methodsStr);
                    } else {
                        String[] methods = methodsStr.split(",");
                        for (String method : methods) {
                            if (inv.getMethodName().equals(method)) {
                                hasMethod = true;
                                break;
                            }
                        }
                    }
                    if (!hasMethod) {
//                        logger.warn(new IllegalStateException("The methodName " + inv.getMethodName()
//                                + " not found in callback service interface ,invoke will be ignored."
//                                + " please update the api interface. url is:"
//                                + invoker.getUrl()) + " ,invocation is :" + inv);
                        return null;
                    }
                    RpcContext rpcContext = RpcContext.getContext();
//                    boolean supportServerAsync = invoker.getUrl().getMethodParameter(inv.getMethodName(), Constants.ASYNC_KEY, false);
//                    if (supportServerAsync) {
//                        CompletableFuture<Object> future = new CompletableFuture<>();
//                        rpcContext.setAsyncContext(new AsyncContextImpl(future));
//                    }
//                    rpcContext.setRemoteAddress(channel.getRemoteAddress());
//                    Result result = invoker.invoke(inv);
//
//                    if (result instanceof AsyncRpcResult) {
//                        return ((AsyncRpcResult) result).getResultFuture().thenApply(r -> (Object) r);
//                    } else {
//                        return CompletableFuture.completedFuture(result);
//                    }
                }

            }
            throw new RemotingException(channel, "Unsupported request: "
                    + (message == null ? null : (message.getClass().getName() + ": " + message))
                    + ", channel: consumer: " + channel.getRemoteAddress() + " --> provider: " + channel.getLocalAddress());
        }

        @Override
        public void connected(Channel channel) throws RemotingException {
            super.connected(channel);
        }

        @Override
        public void disconnected(Channel channel) throws RemotingException {
            super.disconnected(channel);
        }

        @Override
        public void received(Channel channel, Object message) throws RemotingException {
            super.received(channel, message);
        }
    };

    Invoker<?> getInvoker(Channel channel, Invocation inv) throws RemotingException {
        boolean isCallBackServiceInvoke = false;
        boolean isStubServiceInvoke = false;
        int port = channel.getLocalAddress().getPort();
        String path = inv.getAttachments().get(Constants.PATH_KEY);
        // if it's callback service on client side
        isStubServiceInvoke = Boolean.TRUE.toString().equals(inv.getAttachments().get(Constants.STUB_EVENT_KEY));
        if (isStubServiceInvoke) {
            port = channel.getRemoteAddress().getPort();
        }
        //callback
        isCallBackServiceInvoke = isClientSide(channel) && !isStubServiceInvoke;
        if (isCallBackServiceInvoke) {
            path = inv.getAttachments().get(Constants.PATH_KEY) + "." + inv.getAttachments().get(Constants.CALLBACK_SERVICE_KEY);
            inv.getAttachments().put(IS_CALLBACK_SERVICE_INVOKE, Boolean.TRUE.toString());
        }
        String serviceKey = serviceKey(port, path, inv.getAttachments().get(Constants.VERSION_KEY), inv.getAttachments().get(Constants.GROUP_KEY));

        TrfExporter<?> exporter = (TrfExporter<?>) exporterMap.get(serviceKey);

        if (exporter == null)
            throw new RemotingException(channel, "Not found exported service: " + serviceKey + " in " + exporterMap.keySet() + ", may be version or group mismatch " + ", channel: consumer: " + channel.getRemoteAddress() + " --> provider: " + channel.getLocalAddress() + ", message:" + inv);

        return exporter.getInvoker();
    }

    private boolean isClientSide(Channel channel) {
        InetSocketAddress address = channel.getRemoteAddress();
        URL url = channel.getUrl();
        return url.getPort() == address.getPort() &&
                NetUtils.filterLocalHost(channel.getUrl().getIp())
                        .equals(NetUtils.filterLocalHost(address.getAddress().getHostAddress()));
    }

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
        TrfInvoker<T> invoker = new TrfInvoker<T>(type, url, getClients(url), invokers);
        invokers.add(invoker);
        return invoker;
    }

    private ExchangeClient[] getClients(URL url) {
        // whether to share connection
        boolean service_share_connect = false;
        int connections = url.getParameter(Constants.CONNECTIONS_KEY, 0);
        // if not configured, connection is shared, otherwise, one connection for one service
        if (connections == 0) {
            service_share_connect = true;
            connections = 1;
        }

        ExchangeClient[] clients = new ExchangeClient[connections];
        for (int i = 0; i < clients.length; i++) {
            if (service_share_connect) {
                clients[i] = getSharedClient(url);
            } else {
                clients[i] = initClient(url);
            }
        }
        return clients;
    }

    private ExchangeClient getSharedClient(URL url) {
        String key = url.getAddress();
        ReferenceCountExchangeClient client = referenceClientMap.get(key);
        if (client != null) {
            if (!client.isClosed()) {
                client.incrementAndGetCount();
                return client;
            } else {
                referenceClientMap.remove(key);
            }
        }

        locks.putIfAbsent(key, new Object());
        synchronized (locks.get(key)) {
            if (referenceClientMap.containsKey(key)) {
                return referenceClientMap.get(key);
            }

            ExchangeClient exchangeClient = initClient(url);
            client = new ReferenceCountExchangeClient(exchangeClient, ghostClientMap);
            referenceClientMap.put(key, client);
            ghostClientMap.remove(key);
            locks.remove(key);
            return client;
        }
    }

    /**
     * Create new connection
     */
    private ExchangeClient initClient(URL url) {

        // client type setting.
        String str = url.getParameter(Constants.CLIENT_KEY, url.getParameter(Constants.SERVER_KEY, Constants.DEFAULT_REMOTING_CLIENT));

        url = url.addParameter(Constants.CODEC_KEY, "trf");
        // enable heartbeat by default
        url = url.addParameterIfAbsent(Constants.HEARTBEAT_KEY, String.valueOf(Constants.DEFAULT_HEARTBEAT));

        // BIO is not allowed since it has severe performance issue.
        if (str != null && str.length() > 0 && !ExtensionLoader.getExtensionLoader(Transporter.class).hasExtension(str)) {
            throw new RpcException("Unsupported client type: " + str + "," +
                    " supported client type is " + StringUtils.join(ExtensionLoader.getExtensionLoader(Transporter.class).getSupportedExtensions(), " "));
        }

        ExchangeClient client;
        try {
            // connection should be lazy
            if (url.getParameter(Constants.LAZY_CONNECT_KEY, false)) {
                client = new LazyConnectExchangeClient(url, requestHandler);
            } else {
                client = Exchangers.connect(url, requestHandler);
            }
        } catch (RemotingException e) {
            throw new RpcException("Fail to create remoting client for service(" + url + "): " + e.getMessage(), e);
        }
        return client;
    }

    public void destroy() {

    }

    protected static String serviceKey(URL url) {
        int port = url.getParameter(Constants.BIND_PORT_KEY, url.getPort());
        return serviceKey(port, url.getPath(), url.getParameter(Constants.VERSION_KEY),
                url.getParameter(Constants.GROUP_KEY));
    }

}
