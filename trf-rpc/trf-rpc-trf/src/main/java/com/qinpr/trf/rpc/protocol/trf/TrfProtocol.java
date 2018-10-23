package com.qinpr.trf.rpc.protocol.trf;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.rpc.Exporter;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.RpcException;
import com.qinpr.trf.rpc.protocol.AbstractProtocol;


/**
 * Created by qinpr on 2018/10/18.
 */
public class TrfProtocol extends AbstractProtocol {
    public int getDefaultPort() {
        return 0;
    }

    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        System.out.println("trf-protocol-test");
        return null;
    }

    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return null;
    }

    public void destroy() {

    }
}
