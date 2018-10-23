package com.qinpr.trf.registry.integration;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.rpc.Exporter;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.Protocol;
import com.qinpr.trf.rpc.RpcException;


/**
 * Created by qinpr on 2018/10/18.
 */
public class RegistryProtocol implements Protocol {
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
}
