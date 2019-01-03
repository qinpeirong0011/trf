package com.qinpr.trf.rpc;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.threadlocal.InternalThreadLocal;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by qinpr on 2018/10/22.
 */
public class RpcContext {

    private final Map<String, String> attachments = new HashMap<String, String>();
    private final Map<String, Object> values = new HashMap<String, Object>();
    private Future<?> future;

    private List<URL> urls;

    private URL url;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] arguments;

    private InetSocketAddress localAddress;

    private InetSocketAddress remoteAddress;
    @Deprecated
    private List<Invoker<?>> invokers;
    @Deprecated
    private Invoker<?> invoker;
    @Deprecated
    private Invocation invocation;

    // now we don't use the 'values' map to hold these objects
    // we want these objects to be as generic as possible
    private Object request;
    private Object response;
    private AsyncContext asyncContext;

    private static final InternalThreadLocal<RpcContext> LOCAL = new InternalThreadLocal<RpcContext>() {
        @Override
        protected RpcContext initialValue() {
            return new RpcContext();
        }
    };
    private static final InternalThreadLocal<RpcContext> SERVER_LOCAL = new InternalThreadLocal<RpcContext>() {
        @Override
        protected RpcContext initialValue() {
            return new RpcContext();
        }
    };

    public static RpcContext getContext() {
        return LOCAL.get();
    }

    public RpcContext setInvokers(List<Invoker<?>> invokers) {
        this.invokers = invokers;
        if (invokers != null && !invokers.isEmpty()) {
            List<URL> urls = new ArrayList<URL>(invokers.size());
            for (Invoker<?> invoker : invokers) {
                urls.add(invoker.getUrl());
            }
            setUrls(urls);
        }
        return this;
    }

    public void setUrls(List<URL> urls) {
        this.urls = urls;
    }

    public Map<String, String> getAttachments() {
        return attachments;
    }

    public void setFuture(Future<?> future) {
        this.future = future;
    }

    public RpcContext copyOf() {
        RpcContext copy = new RpcContext();
        copy.attachments.putAll(this.attachments);
        copy.values.putAll(this.values);
        copy.future = this.future;
        copy.urls = this.urls;
        copy.url = this.url;
        copy.methodName = this.methodName;
        copy.parameterTypes = this.parameterTypes;
        copy.arguments = this.arguments;
        copy.localAddress = this.localAddress;
        copy.remoteAddress = this.remoteAddress;
        copy.invokers = this.invokers;
        copy.invoker = this.invoker;
        copy.invocation = this.invocation;

        copy.request = this.request;
        copy.response = this.response;
        copy.asyncContext = this.asyncContext;

        return copy;
    }

    public static RpcContext getServerContext() {
        return SERVER_LOCAL.get();
    }
}
