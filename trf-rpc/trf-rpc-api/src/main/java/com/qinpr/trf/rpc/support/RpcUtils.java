package com.qinpr.trf.rpc.support;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.rpc.Invocation;

/**
 * Created by qinpr on 2018/10/22.
 */
public class RpcUtils {
    public static boolean isFutureReturnType(Invocation inv) {
        return Boolean.TRUE.toString().equals(inv.getAttachment(Constants.FUTURE_RETURNTYPE_KEY));
    }

    public static String getMethodName(Invocation invocation) {
        if (Constants.$INVOKE.equals(invocation.getMethodName())
                && invocation.getArguments() != null
                && invocation.getArguments().length > 0
                && invocation.getArguments()[0] instanceof String) {
            return (String) invocation.getArguments()[0];
        }
        return invocation.getMethodName();
    }

    public static Object[] getArguments(Invocation invocation) {
        if (Constants.$INVOKE.equals(invocation.getMethodName())
                && invocation.getArguments() != null
                && invocation.getArguments().length > 2
                && invocation.getArguments()[2] instanceof Object[]) {
            return (Object[]) invocation.getArguments()[2];
        }
        return invocation.getArguments();
    }

    public static void attachInvocationIdIfAsync(URL url, Invocation inv) {
//        if (isAttachInvocationId(url, inv) && getInvocationId(inv) == null && inv instanceof RpcInvocation) {
//            ((RpcInvocation) inv).setAttachment(Constants.ID_KEY, String.valueOf(INVOKE_ID.getAndIncrement()));
//        }
    }

    public static boolean isAsync(URL url, Invocation inv) {
        boolean isAsync;
        if (Boolean.TRUE.toString().equals(inv.getAttachment(Constants.ASYNC_KEY))) {
            isAsync = true;
        } else {
            isAsync = url.getMethodParameter(getMethodName(inv), Constants.ASYNC_KEY, false);
        }
        return isAsync;
    }

    public static boolean isGeneratedFuture(Invocation inv) {
        return Boolean.TRUE.toString().equals(inv.getAttachment(Constants.FUTURE_GENERATED_KEY));
    }

    public static boolean isOneway(URL url, Invocation inv) {
        boolean isOneway;
        if (Boolean.FALSE.toString().equals(inv.getAttachment(Constants.RETURN_KEY))) {
            isOneway = true;
        } else {
            isOneway = !url.getMethodParameter(getMethodName(inv), Constants.RETURN_KEY, true);
        }
        return isOneway;
    }
}
