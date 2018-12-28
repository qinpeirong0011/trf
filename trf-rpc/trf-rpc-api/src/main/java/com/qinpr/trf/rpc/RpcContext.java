package com.qinpr.trf.rpc;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.threadlocal.InternalThreadLocal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinpr on 2018/10/22.
 */
public class RpcContext {

    @Deprecated
    private List<Invoker<?>> invokers;

    private List<URL> urls;

    private final Map<String, String> attachments = new HashMap<String, String>();

    private static final InternalThreadLocal<RpcContext> LOCAL = new InternalThreadLocal<RpcContext>() {
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
}
