package com.qinpr.trf.rpc.support;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.rpc.Invocation;

/**
 * Created by qinpr on 2018/10/22.
 */
public class RpcUtils {
    public static boolean isFutureReturnType(Invocation inv) {
        return Boolean.TRUE.toString().equals(inv.getAttachment(Constants.FUTURE_RETURNTYPE_KEY));
    }
}
