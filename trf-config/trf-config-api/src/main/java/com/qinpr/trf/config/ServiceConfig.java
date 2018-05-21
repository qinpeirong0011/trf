package com.qinpr.trf.config;

import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.config.annotation.Service;
import com.qinpr.trf.config.support.Parameter;
import com.qinpr.trf.rpc.Protocol;

/**
 * Created by qinpr on 18/4/27.
 */
public class ServiceConfig<T> extends AbstractServiceConfig {
    private static final long serialVersionUID = 4587486937575492897L;

    private static final Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

    private ProviderConfig provider;

    private transient volatile boolean exported;

    private transient volatile boolean unexported;

    public ServiceConfig() {
    }

    public ServiceConfig(Service service) {
        appendAnnotation(Service.class, service);
    }

    public ProviderConfig getProvider() {
        return provider;
    }

    @Parameter(excluded = true)
    public boolean isExported() {
        return exported;
    }

    @Parameter(excluded = true)
    public boolean isUnexported() {
        return unexported;
    }

    public synchronized void export() {

    }
}
