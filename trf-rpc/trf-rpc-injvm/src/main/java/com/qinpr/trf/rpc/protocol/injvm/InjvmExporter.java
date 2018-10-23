package com.qinpr.trf.rpc.protocol.injvm;

import com.qinpr.trf.rpc.Exporter;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.protocol.AbstractExporter;

import java.util.Map;

/**
 * Created by qinpr on 2018/10/23.
 */
public class InjvmExporter<T> extends AbstractExporter<T> {

    private final String key;

    private final Map<String, Exporter<?>> exporterMap;

    public InjvmExporter(Invoker<T> invoker, String key, Map<String, Exporter<?>> exporterMap) {
        super(invoker);
        this.key = key;
        this.exporterMap = exporterMap;
        exporterMap.put(key, this);
    }

    public Invoker getInvoker() {
        return null;
    }

    public void unexport() {
        exporterMap.remove(key);
    }
}
