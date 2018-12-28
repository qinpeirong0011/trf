package com.qinpr.trf.rpc.proxy;

import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.RpcInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by qinpr on 2018/11/27.
 */
public class InvokerInvocationHandler implements InvocationHandler {

    private final Invoker<?> invoker;

    public InvokerInvocationHandler(Invoker<?> handler) {
        this.invoker = handler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        RpcInvocation invocation = new RpcInvocation(method, args);
        return invoker.invoke(invocation).recreate();
    }
}
