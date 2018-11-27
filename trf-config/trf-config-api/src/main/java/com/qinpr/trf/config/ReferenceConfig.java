package com.qinpr.trf.config;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.Version;
import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.common.utils.ConfigUtils;
import com.qinpr.trf.common.utils.NetUtils;
import com.qinpr.trf.common.utils.StringUtils;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.Protocol;
import com.qinpr.trf.rpc.ProxyFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinpr on 2018/11/20.
 */
public class ReferenceConfig<T> extends AbstractReferenceConfig {

    private static final Protocol refprotocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

    private static final ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    private String interfaceName;
    private Class<?> interfaceClass;

    private ConsumerConfig consumer;

    private transient volatile T ref;
    private transient volatile Invoker<?> invoker;
    private transient volatile boolean initialized;
    private transient volatile boolean destroyed;

    private final List<URL> urls = new ArrayList<URL>();

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public synchronized T get() {
        if (destroyed) {
            throw new IllegalStateException("Already destroyed!");
        }
        if (ref == null) {
            init();
        }
        return ref;
    }

    private void init() {
        if (initialized) {
            return;
        }
        initialized = true;
        if (interfaceName == null || interfaceName.length() == 0) {
            throw new IllegalStateException("<dubbo:reference interface=\"\" /> interface not allow null!");
        }
        checkDefault();
        Map<String, String> map = new HashMap<String, String>();
        resolveAsyncInterface(interfaceClass, map);
        map.put(Constants.SIDE_KEY, Constants.CONSUMER_SIDE);
        map.put(Constants.TRF_VERSION_KEY, Version.getProtocolVersion());
        map.put(Constants.TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
        map.put(Constants.INTERFACE_KEY, interfaceName);
        appendParameters(map, application);
        appendParameters(map, module);
        appendParameters(map, consumer, Constants.DEFAULT_KEY);
        appendParameters(map, this);

        String hostToRegistry = ConfigUtils.getSystemProperty(Constants.TRF_IP_TO_REGISTRY);
        if (hostToRegistry == null || hostToRegistry.length() == 0) {
            hostToRegistry = NetUtils.getLocalHost();
        } else if (NetUtils.isInvalidLocalHost(hostToRegistry)) {
            throw new IllegalArgumentException("Specified invalid registry ip from property:" + Constants.TRF_IP_TO_REGISTRY + ", value:" + hostToRegistry);
        }
        map.put(Constants.REGISTER_IP_KEY, hostToRegistry);
        ref = createProxy(map);
    }

    public ConsumerConfig getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerConfig consumer) {
        this.consumer = consumer;
    }

    private void checkDefault() {
        if (consumer == null) {
            consumer = new ConsumerConfig();
        }
        appendProperties(consumer);
    }

    protected static void appendProperties(AbstractConfig config) {

    }

    private void resolveAsyncInterface(Class<?> interfaceClass, Map<String, String> map) {
        map.put(Constants.INTERFACES, interfaceClass.getName());
    }

    private T createProxy(Map<String, String> map) {
        List<URL> us = loadRegistries(false);
        if (us != null && !us.isEmpty()) {
            for (URL u : us) {
                urls.add(u.addParameterAndEncoded(Constants.REFER_KEY, StringUtils.toQueryString(map)));
            }
        }
        if (urls.isEmpty()) {
            throw new IllegalStateException("No such any registry to reference " + interfaceName + " on the consumer " + NetUtils.getLocalHost() + " use trf version , please config <trf:registry address=\"...\" /> to your spring config.");
        }
        if (urls.size() == 1) {
            invoker = refprotocol.refer(interfaceClass, urls.get(0));
        } else {
            //todo sth
        }
        return (T) proxyFactory.getProxy(invoker);
    }


}
