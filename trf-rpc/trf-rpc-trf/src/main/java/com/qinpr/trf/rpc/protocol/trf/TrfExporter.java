package com.qinpr.trf.rpc.protocol.trf;

import com.qinpr.trf.rpc.Exporter;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.protocol.AbstractExporter;

import java.util.Map;

/**
 * Created by qinpr on 2018/10/25.
 */
public class TrfExporter<T> extends AbstractExporter<T> {

    private final String key;

    private final Map<String, Exporter<?>> exporterMap;

    public TrfExporter(Invoker<T> invoker, String key, Map<String, Exporter<?>> exporterMap) {
        super(invoker);
        this.key = key;
        this.exporterMap = exporterMap;
    }

    @Override
    public void unexport() {
        super.unexport();
        exporterMap.remove(key);
    }
}
