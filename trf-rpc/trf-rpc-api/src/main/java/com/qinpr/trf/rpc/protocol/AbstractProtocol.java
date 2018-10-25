package com.qinpr.trf.rpc.protocol;

import com.qinpr.trf.rpc.Exporter;
import com.qinpr.trf.rpc.Protocol;
import com.qinpr.trf.rpc.support.ProtocolUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by qinpr on 2018/10/18.
 */
public abstract class AbstractProtocol implements Protocol {
    protected final Map<String, Exporter<?>> exporterMap = new ConcurrentHashMap<String, Exporter<?>>();

    protected static String serviceKey(int port, String serviceName, String serviceVersion, String serviceGroup) {
        return ProtocolUtils.serviceKey(port, serviceName, serviceVersion, serviceGroup);
    }
}
