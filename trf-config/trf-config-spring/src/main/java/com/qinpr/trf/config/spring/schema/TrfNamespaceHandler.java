package com.qinpr.trf.config.spring.schema;

import com.qinpr.trf.config.RegistryConfig;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by qinpr on 2018/10/8.
 */
public class TrfNamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        registerBeanDefinitionParser("registry", new TrfBeanDefinitionParser());
    }
}
