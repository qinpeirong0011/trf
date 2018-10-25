package com.qinpr.trf.rpc.protocol;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.rpc.Invocation;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.Result;
import com.qinpr.trf.rpc.RpcException;

/**
 * Created by qinpr on 2018/10/25.
 */
public class InvokerWrapper<T> implements Invoker<T> {

    private final Invoker<T> invoker;

    private final URL url;

    public InvokerWrapper(Invoker<T> invoker, URL url) {
        this.invoker = invoker;
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public boolean isAvailable() {
        return invoker.isAvailable();
    }

    public void destroy() {
        invoker.destroy();
    }

    public Class<T> getInterface() {
        return invoker.getInterface();
    }

    public Result invoke(Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }
}
