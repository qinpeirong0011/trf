package com.qinpr.trf.config.invoker;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.config.ServiceConfig;
import com.qinpr.trf.rpc.Invocation;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.Result;
import com.qinpr.trf.rpc.RpcException;

/**
 * Created by qinpr on 2018/10/23.
 */
public class DelegateProviderMetaDataInvoker<T> implements Invoker {
    protected final Invoker<T> invoker;
    private ServiceConfig metadata;

    public DelegateProviderMetaDataInvoker(Invoker<T> invoker, ServiceConfig metadata) {
        this.invoker = invoker;
        this.metadata = metadata;
    }

    public URL getUrl() {
        return invoker.getUrl();
    }

    public boolean isAvailable() {
        return invoker.isAvailable();
    }

    public void destroy() {
        invoker.destroy();
    }

    public Class getInterface() {
        return invoker.getInterface();
    }

    public Result invoke(Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }

    public ServiceConfig getMetadata() {
        return metadata;
    }
}
