package com.qinpr.trf.test;

import com.qinpr.trf.common.Extension;
import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.rpc.Protocol;
import com.qinpr.trf.rpc.ProxyFactory;

/**
 * Created by qinpr on 2018/10/18.
 */
public class Provider {
    public static void main(String[] args) {
//        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
//        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
        Protocol adaptiveExtension = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
    }
}
