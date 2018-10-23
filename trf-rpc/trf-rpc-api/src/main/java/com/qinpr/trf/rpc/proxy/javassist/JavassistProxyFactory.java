package com.qinpr.trf.rpc.proxy.javassist;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.bytecode.Wrapper;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.RpcException;
import com.qinpr.trf.rpc.proxy.AbstractProxyFactory;
import com.qinpr.trf.rpc.proxy.AbstractProxyInvoker;

/**
 * Created by qinpr on 2018/10/19.
 */
public class JavassistProxyFactory extends AbstractProxyFactory {
    @Override
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] types) {
        return null;
    }

    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) throws RpcException {
        final Wrapper wrapper = Wrapper.getWrapper(proxy.getClass().getName().indexOf('$') < 0 ? proxy.getClass() : type);
        return new AbstractProxyInvoker<T>(proxy, type, url) {
            @Override
            protected Object doInvoke(T proxy, String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Throwable {
                return wrapper.invokeMethod(proxy, methodName, parameterTypes, arguments);
            }
        };
    }
}
