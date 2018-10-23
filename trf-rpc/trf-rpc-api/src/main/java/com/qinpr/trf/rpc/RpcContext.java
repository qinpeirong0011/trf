package com.qinpr.trf.rpc;

import com.qinpr.trf.common.threadlocal.InternalThreadLocal;

/**
 * Created by qinpr on 2018/10/22.
 */
public class RpcContext {
    private static final InternalThreadLocal<RpcContext> LOCAL = new InternalThreadLocal<RpcContext>() {
        @Override
        protected RpcContext initialValue() {
            return new RpcContext();
        }
    };

    public static RpcContext getContext() {
        return LOCAL.get();
    }
}
