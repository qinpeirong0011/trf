package com.qinpr.trf.rpc.cluster.directory;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.rpc.Invocation;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.RpcException;
import com.qinpr.trf.rpc.cluster.Router;

import java.util.List;

/**
 * Created by qinpr on 2018/12/27.
 */
public class StaticDirectory<T> extends AbstractDirectory<T> {

    private final List<Invoker<T>> invokers;

    public StaticDirectory(List<Invoker<T>> invokers) {
        this(null, invokers, null);
    }

    public StaticDirectory(List<Invoker<T>> invokers, List<Router> routers) {
        this(null, invokers, routers);
    }

    public StaticDirectory(URL url, List<Invoker<T>> invokers) {
        this(url, invokers, null);
    }

    public StaticDirectory(URL url, List<Invoker<T>> invokers, List<Router> routers) {
        super(url == null && invokers != null && !invokers.isEmpty() ? invokers.get(0).getUrl() : url, routers);
        if (invokers == null || invokers.isEmpty()) {
            throw new IllegalArgumentException("invokers == null");
        }
        this.invokers = invokers;
    }


    @Override
    public boolean isAvailable() {
        if (isDestroyed()) {
            return false;
        }
        for (Invoker<T> invoker : invokers) {
            if (invoker.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Class<T> getInterface() {
        return invokers.get(0).getInterface();
    }

    @Override
    public void destroy() {
        if (isDestroyed()) {
            return;
        }
        super.destroy();
        for (Invoker<T> invoker : invokers) {
            invoker.destroy();
        }
        invokers.clear();
    }

    @Override
    protected List<Invoker<T>> doList(Invocation invocation) throws RpcException {
        return invokers;
    }
}
