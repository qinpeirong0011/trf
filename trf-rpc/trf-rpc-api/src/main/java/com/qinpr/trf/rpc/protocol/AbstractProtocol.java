package com.qinpr.trf.rpc.protocol;

import com.qinpr.trf.rpc.Exporter;
import com.qinpr.trf.rpc.Protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by qinpr on 2018/10/18.
 */
public abstract class AbstractProtocol implements Protocol {
    protected final Map<String, Exporter<?>> exporterMap = new ConcurrentHashMap<String, Exporter<?>>();
}
