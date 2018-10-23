package com.qinpr.trf.rpc.proxy;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.utils.ReflectUtils;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.ProxyFactory;
import com.qinpr.trf.rpc.RpcException;
import com.qinpr.trf.rpc.service.EchoService;

/**
 * Created by qinpr on 2018/10/19.
 */
public abstract class AbstractProxyFactory implements ProxyFactory {

    public abstract <T> T getProxy(Invoker<T> invoker, Class<?>[] types);

    public <T> T getProxy(Invoker<T> invoker) throws RpcException {
        return getProxy(invoker, false);
    }

    @Override
    public <T> T getProxy(Invoker<T> invoker, boolean generic) throws RpcException {
        Class<?>[] interfaces = null;
        String config = invoker.getUrl().getParameter(Constants.INTERFACES);
        if (config != null && config.length() > 0) {
            String[] types = Constants.COMMA_SPLIT_PATTERN.split(config);
            if (types != null && types.length > 0) {
                interfaces = new Class<?>[types.length + 2];
                interfaces[0] = invoker.getInterface();
                interfaces[1] = EchoService.class;
                for (int i=0 ; i < types.length; i++) {
                    interfaces[i+2] = ReflectUtils.forName(types[i]);
                }
            }
        }

        if (interfaces == null) {
            interfaces = new Class<?>[]{invoker.getInterface(), EchoService.class};
        }
        return getProxy(invoker, interfaces);
    }
}
