package com.qinpr.trf.rpc;

/**
 * Created by qinpr on 18/4/27.
 */
public interface Exporter<T> {

    Invoker<T> getInvoker();

    void unexport();

}
