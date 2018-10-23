package com.qinpr.trf.rpc.protocol;

import com.qinpr.trf.rpc.Exporter;
import com.qinpr.trf.rpc.Invoker;

/**
 * Created by qinpr on 2018/10/23.
 */
public abstract class AbstractExporter<T> implements Exporter {

    private final Invoker<T> invoker;

    public AbstractExporter(Invoker<T> invoker) {
        if (invoker == null)
            throw new IllegalStateException("service invoker == null");
        if (invoker.getInterface() == null)
            throw new IllegalStateException("service type == null");
        if (invoker.getUrl() == null)
            throw new IllegalStateException("service url == null");
        this.invoker = invoker;
    }
}
