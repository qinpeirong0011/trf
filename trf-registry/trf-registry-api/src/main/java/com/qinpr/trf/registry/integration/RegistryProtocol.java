package com.qinpr.trf.registry.integration;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.registry.RegistryFactory;
import com.qinpr.trf.rpc.*;


/**
 * Created by qinpr on 2018/10/18.
 */
public class RegistryProtocol implements Protocol {

    private Protocol protocol;
    private RegistryFactory registryFactory;

    public int getDefaultPort() {
        return 0;
    }

    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        return null;
    }

    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return null;
    }

    public void destroy() {

    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public void setRegistryFactory(RegistryFactory registryFactory) {
        this.registryFactory = registryFactory;
    }
}
