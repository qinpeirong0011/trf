package com.qinpr.trf.rpc;

import com.qinpr.trf.common.Node;

/**
 * Created by qinpr on 18/4/27.
 */
public interface Invoker<T> extends Node {

    Class<T> getInterface();

    Result invoke(Invocation invocation) throws RpcException;
}
