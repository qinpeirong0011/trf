package com.qinpr.trf.rpc.cluster.directory;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.rpc.Invocation;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.RpcException;
import com.qinpr.trf.rpc.cluster.Directory;
import com.qinpr.trf.rpc.cluster.Router;
import com.qinpr.trf.rpc.cluster.router.MockInvokersSelector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by qinpr on 2018/11/21.
 */
public abstract class AbstractDirectory<T> implements Directory<T> {

    private volatile boolean destroyed = false;

    private final URL url;

    private volatile URL consumerUrl;

    private volatile List<Router> routers;

    public AbstractDirectory(URL url) {
        this(url, null);
    }

    public AbstractDirectory(URL url, List<Router> routers) {
        this(url, url, routers);
    }

    public AbstractDirectory(URL url, URL consumerUrl, List<Router> routers) {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }
        this.url = url;
        this.consumerUrl = consumerUrl;
        setRouters(routers);
    }

    protected void setRouters(List<Router> routers) {
        // copy list
        routers = routers == null ? new ArrayList<Router>() : new ArrayList<Router>(routers);
        // append url router
        String routerKey = url.getParameter(Constants.ROUTER_KEY);
        if (routerKey != null && routerKey.length() > 0) {
//            RouterFactory routerFactory = ExtensionLoader.getExtensionLoader(RouterFactory.class).getExtension(routerKey);
//            routers.add(routerFactory.getRouter(url));
        }
        // append mock invoker selector
        routers.add(new MockInvokersSelector());
        Collections.sort(routers);
        this.routers = routers;
    }

    public void setConsumerUrl(URL consumerUrl) {
        this.consumerUrl = consumerUrl;
    }

    @Override
    public List<Invoker<T>> list(Invocation invocation) throws RpcException {
        if (destroyed) {
            throw new RpcException("Directory already destroyed .url: " + getUrl());
        }
        List<Invoker<T>> invokers = doList(invocation);
        List<Router> localRouters = this.routers;
        if (localRouters != null && !localRouters.isEmpty()) {
            for (Router router : localRouters) {
                try {
                    if (router.getUrl() == null || router.getUrl().getParameter(Constants.RUNTIME_KEY, false)) {
                        invokers = router.route(invokers, getConsumerUrl(), invocation);
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
        return invokers;
    }

    public List<Router> getRouters() {
        return routers;
    }

    public URL getConsumerUrl() {
        return consumerUrl;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void destroy() {
        destroyed = true;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    protected abstract List<Invoker<T>> doList(Invocation invocation) throws RpcException;
}
