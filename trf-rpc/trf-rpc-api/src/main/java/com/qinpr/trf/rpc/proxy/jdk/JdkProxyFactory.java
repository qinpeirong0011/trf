package com.qinpr.trf.rpc.proxy.jdk;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.RpcException;
import com.qinpr.trf.rpc.proxy.AbstractProxyFactory;
import com.qinpr.trf.rpc.proxy.AbstractProxyInvoker;

import java.lang.reflect.Method;

/**
 * Created by qinpr on 2018/10/19.
 */
public class JdkProxyFactory extends AbstractProxyFactory {
    @Override
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] types) {
        return null;
    }

    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) throws RpcException {
        return new AbstractProxyInvoker<T>(proxy, type, url) {
            @Override
            protected Object doInvoke(T proxy, String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Throwable {
                Method method = proxy.getClass().getMethod(methodName, parameterTypes);
                return method.invoke(proxy, arguments);
            }
        };
    }
}
