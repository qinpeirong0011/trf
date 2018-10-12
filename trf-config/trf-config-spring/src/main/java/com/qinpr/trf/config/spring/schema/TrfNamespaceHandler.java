package com.qinpr.trf.config.spring.schema;

import com.qinpr.trf.config.ApplicationConfig;
import com.qinpr.trf.config.ProtocolConfig;
import com.qinpr.trf.config.ProviderConfig;
import com.qinpr.trf.config.RegistryConfig;
import com.qinpr.trf.config.spring.ServiceBean;
import com.qinpr.trf.config.spring.schema.handler.*;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by qinpr on 2018/10/8.
 */
public class TrfNamespaceHandler extends NamespaceHandlerSupport {

    static {
        TrfBeanDefinitionParser.parseHolder.put(ApplicationConfig.class, ApplicationConfigParseHander.instanceOf());
        TrfBeanDefinitionParser.parseHolder.put(RegistryConfig.class, RegistryConfigParseHander.instanceOf());
        TrfBeanDefinitionParser.parseHolder.put(ProtocolConfig.class, ProtocolConfigParseHander.instanceOf());
        TrfBeanDefinitionParser.parseHolder.put(ProviderConfig.class, ProviderConfigParseHander.instanceOf());
        TrfBeanDefinitionParser.parseHolder.put(ServiceBean.class, ServiceBeanParseHandler.instanceOf());
    }

    public void init() {
        registerBeanDefinitionParser("application", new TrfBeanDefinitionParser(ApplicationConfig.class, true));
        registerBeanDefinitionParser("registry", new TrfBeanDefinitionParser(RegistryConfig.class, true));
        registerBeanDefinitionParser("protocol", new TrfBeanDefinitionParser(ProtocolConfig.class, true));
        registerBeanDefinitionParser("provider", new TrfBeanDefinitionParser(ProviderConfig.class, true));
        registerBeanDefinitionParser("service", new TrfBeanDefinitionParser(ServiceBean.class, true));
    }
}
