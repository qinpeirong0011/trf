package com.qinpr.trf.config.spring;

import com.qinpr.trf.config.*;
import com.qinpr.trf.config.annotation.Service;
import com.sun.org.apache.bcel.internal.generic.I2F;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qinpr on 2018/10/11.
 */
public class ServiceBean<T> extends ServiceConfig<T> implements InitializingBean, DisposableBean, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent>, BeanNameAware {

    private static final long serialVersionUID = -1714153265033793215L;

    private final transient Service service;

    private transient ApplicationContext applicationContext;

    private transient String beanName;

    private transient boolean supportedApplicationListener;

    public ServiceBean() {
        super();
        this.service = null;
    }

    public ServiceBean(Service service) {
        super(service);
        this.service = service;
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    public void destroy() throws Exception {

    }

    public void afterPropertiesSet() throws Exception {
        if (getProvider() == null) {
            Map<String, ProviderConfig> providerConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ProviderConfig.class, false, false);
            if (providerConfigMap != null && providerConfigMap.size() > 0) {
                Map<String, ProtocolConfig> protocolConfigMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ProtocolConfig.class, false, false);
                if (protocolConfigMap != null && protocolConfigMap.size() > 0) {
                    for (ProviderConfig providerConfig : providerConfigMap.values()) {
                        if (providerConfig != null) {
                            setProvider(providerConfig);
                            break;
                        }
                    }
                }
            }
        }

        if (getApplication() == null) {
            Map<String, ApplicationConfig> applicationConfigMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ApplicationConfig.class, false, false);
            if (applicationConfigMap != null && applicationConfigMap.size() >0) {
                for (ApplicationConfig config : applicationConfigMap.values()) {
                    if (config != null) {
                        setApplication(config);
                        break;
                    }
                }
            }
        }

        if (getRegistries() == null || getRegistries().isEmpty()) {
            Map<String, RegistryConfig> registryConfigMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, RegistryConfig.class, false, false);
            if (registryConfigMap != null && registryConfigMap.size() >0) {
                List<RegistryConfig> registries = new ArrayList<RegistryConfig>();
                for (RegistryConfig config : registryConfigMap.values()) {
                    if (config != null) {
                        registries.add(config);
                    }
                }
                if (!registries.isEmpty()) {
                    setRegistries(registries);
                }
            }
        }

        if (getProtocols() == null || getProtocols().isEmpty()) {
            Map<String, ProtocolConfig> protocolConfigMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ProtocolConfig.class, false, false);
            if (protocolConfigMap != null && protocolConfigMap.size() > 0) {
                List<ProtocolConfig> protocols = new ArrayList<ProtocolConfig>();
                for (ProtocolConfig config : protocolConfigMap.values()) {
                    if (config != null) {
                        protocols.add(config);
                    }
                }
                if (!protocols.isEmpty()) {
                    setProtocols(protocols);
                }
            }
        }

        if (!isDelay()) {
            export();
        }

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
         if (isDelay() && !isExported() && !isUnexported()) {
             export();
         }
    }

    private boolean isDelay() {
        Integer delay = getDelay();
        ProviderConfig provider = getProvider();
        if (delay == null && provider!= null) {
            delay = provider.getDelay();
        }

        return delay == null || delay == -1;
    }
}
