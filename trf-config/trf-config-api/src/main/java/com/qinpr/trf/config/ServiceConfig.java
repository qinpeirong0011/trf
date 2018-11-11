package com.qinpr.trf.config;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.Version;
import com.qinpr.trf.common.bytecode.Wrapper;
import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.common.utils.ConfigUtils;
import com.qinpr.trf.common.utils.NamedThreadFactory;
import com.qinpr.trf.common.utils.StringUtils;
import com.qinpr.trf.config.annotation.Service;
import com.qinpr.trf.config.invoker.DelegateProviderMetaDataInvoker;
import com.qinpr.trf.config.support.Parameter;
import com.qinpr.trf.rpc.Exporter;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.Protocol;
import com.qinpr.trf.rpc.ProxyFactory;

import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.qinpr.trf.common.utils.NetUtils.LOCALHOST;

/**
 * Created by qinpr on 18/4/27.
 */
public class ServiceConfig<T> extends AbstractServiceConfig {
    private static final long serialVersionUID = 4587486937575492897L;

    private static final Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

    private static final ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    private static final ScheduledExecutorService delayExportExecutor = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("trfServiceDelayExporter", true));

    private final List<Exporter<?>> exporters = new ArrayList<Exporter<?>>();

    private String interfaceName;

    private Class<?> interfaceClass;

    private T ref;

    private ProviderConfig provider;

    private String path;

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
            name = "trf";
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put(Constants.SIDE_KEY, Constants.PROVIDER_SIDE);
        map.put(Constants.TRF_VERSION_KEY, Version.getProtocolVersion());
        map.put(Constants.TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));

        appendParameters(map, application);
        appendParameters(map, module);
        appendParameters(map, provider, Constants.DEFAULT_KEY);
        appendParameters(map, protocolConfig);
        appendParameters(map, this);

        String[] methods = Wrapper.getWrapper(interfaceClass).getMethodNames();
        if (methods.length == 0) {
            map.put(Constants.METHODS_KEY, Constants.ANY_VALUE);
        } else {
            map.put(Constants.METHODS_KEY, StringUtils.join(new HashSet<String>(Arrays.asList(methods)), ","));
        }

        if (!ConfigUtils.isEmpty(token)) {
            if (ConfigUtils.isDefault(token)) {
                map.put(Constants.TOKEN_KEY, UUID.randomUUID().toString());
            } else {
                map.put(Constants.TOKEN_KEY, token);
            }
        }

        String host = this.findConfigedHosts(protocolConfig, registryURLs, map);
        Integer port = this.findConfigedPorts(protocolConfig, name, map);
        URL url = new URL(name, host, port, path, map);
        String scope = url.getParameter(Constants.SCOPE_KEY);

        if (!Constants.SCOPE_REMOTE.equalsIgnoreCase(scope)) {
            exportLocal(url);
        }

        if (!Constants.SCOPE_LOCAL.equalsIgnoreCase(scope)) {
            if (registryURLs != null && !registryURLs.isEmpty()) {
                for (URL registryURL : registryURLs) {
                    url = url.addParameterIfAbsent(Constants.DYNAMIC_KEY, registryURL.getParameter(Constants.DYNAMIC_KEY));
                    Invoker<?> invoker = proxyFactory.getInvoker(ref, (Class) interfaceClass, registryURL.addParameterAndEncoded(Constants.EXPORT_KEY, url.toFullString()));
                    DelegateProviderMetaDataInvoker wrapperInvoker = new DelegateProviderMetaDataInvoker(invoker, this);
                    Exporter<?> export = protocol.export(wrapperInvoker);
                    exporters.add(export);
                }
            }
        }

    }

    private void exportLocal(URL url) {
        URL local = URL.valueOf(url.toFullString())
                .setProtocol(Constants.LOCAL_PROTOCOL)
                .setHost(LOCALHOST)
                .setPort(0);
        Invoker invoker = proxyFactory.getInvoker(ref, (Class) interfaceClass, local);
        Exporter<?> export = protocol.export(invoker);
        exporters.add(export);
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    private String findConfigedHosts(ProtocolConfig protocolConfig, List<URL> registryURLs, Map<String, String> map) {
        boolean anyhost = false;

        String hostToBind = protocolConfig.getHost();
        if (provider != null && (hostToBind == null || hostToBind.length() == 0)) {
            hostToBind = provider.getHost();
        }
        if (isInvalidLocalHost(hostToBind)) {
            anyhost = true;
            try {
                hostToBind = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        map.put(Constants.BIND_IP_KEY, hostToBind);
        map.put(Constants.ANYHOST_KEY, String.valueOf(anyhost));

        return hostToBind;
    }

    private Integer findConfigedPorts(ProtocolConfig protocolConfig, String name, Map<String, String> map) {
        Integer portToBind = protocolConfig.getPort();

        if (provider != null && (portToBind == null || portToBind == 0)) {
            portToBind = provider.getPort();
        }
        if (portToBind == null || portToBind == 0) {
            final int defaultPort = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(name).getDefaultPort();
            portToBind = defaultPort;
        }

        // save bind port, used as url's key later
        map.put(Constants.BIND_PORT_KEY, String.valueOf(portToBind));

        return portToBind;
    }


    private static final Pattern LOCAL_IP_PATTERN = Pattern.compile("127(\\.\\d{1,3}){3}$");
    public static boolean isInvalidLocalHost(String host) {
        return host == null
                || host.length() == 0
                || host.equalsIgnoreCase("localhost")
                || host.equals("0.0.0.0")
                || (LOCAL_IP_PATTERN.matcher(host).matches());
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
