package com.qinpr.trf.rpc.cluster;

import com.qinpr.trf.common.Node;
import com.qinpr.trf.rpc.Invocation;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.RpcException;

import java.util.List;

/**
 * Created by qinpr on 2018/11/21.
 */
public interface Directory<T> extends Node {
    /**
     * get service type.
     *
     * @return service type.
     */
    Class<T> getInterface();

    /**
     * list invokers.
     *
     * @return invokers
     */
    List<Invoker<T>> list(Invocation invocation) throws RpcException;
}
