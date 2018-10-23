package com.qinpr.trf.config;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.Version;
import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.common.utils.NamedThreadFactory;
import com.qinpr.trf.config.annotation.Service;
import com.qinpr.trf.config.support.Parameter;
import com.qinpr.trf.rpc.Protocol;
import com.qinpr.trf.rpc.ProxyFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by qinpr on 18/4/27.
 */
public class ServiceConfig<T> extends AbstractServiceConfig {
    private static final long serialVersionUID = 4587486937575492897L;

    private static final Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

    private static final ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    private static final ScheduledExecutorService delayExportExecutor = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("trfServiceDelayExporter", true));

    private String interfaceName;

    private T ref;

    private ProviderConfig provider;

    private transient volatile boolean exported;

    private transient volatile boolean unexported;

    public ServiceConfig() {
    }

    public ServiceConfig(Service service) {
        appendAnnotation(Service.class, service);
    }

    public String getInterface() {
        return interfaceName;
    }

    public void setInterface(String interfaceName) {
        this.interfaceName = interfaceName;
        if (id == null || id.length() == 0) {
            id = interfaceName;
        }
    }

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public ProviderConfig getProvider() {
        return provider;
    }

    public void setProvider(ProviderConfig provider) {
        this.provider = provider;
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
       if (provider != null) {
           if (export == null) {
               export = provider.getExport();
           }
           if (delay == null) {
               delay = provider.getDelay();
           }
       }

       if (delay != null && delay > 0) {
           delayExportExecutor.schedule(new Runnable() {
               public void run() {
                   doExport();
               }
           }, delay, TimeUnit.MILLISECONDS);
       } else {
           doExport();
       }
    }

    protected synchronized void doExport() {
        doExportUrls();
    }

    private void doExportUrls() {
        List<URL> registryURLs = loadRegistries(true);
        for (ProtocolConfig config : protocols) {
            doExportUrlsFor1Protocol(config, registryURLs);
        }
    }

    private void doExportUrlsFor1Protocol(ProtocolConfig protocolConfig, List<URL> registryURLs) {
        String name = protocolConfig.getName();
        if (name == null || name.length() == 0) {
            name = "dubbo";
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put(Constants.SIDE_KEY, Constants.PROVIDER_SIDE);
        map.put(Constants.DUBBO_VERSION_KEY, Version.getProtocolVersion());
        map.put(Constants.TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));

    }
}
