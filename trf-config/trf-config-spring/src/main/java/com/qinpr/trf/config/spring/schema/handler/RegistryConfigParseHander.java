package com.qinpr.trf.config.spring.schema.handler;

import com.qinpr.trf.config.RegistryConfig;
import com.qinpr.trf.config.spring.schema.AbstractTrfBeanParseHander;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by qinpr on 2018/10/11.
 */
public class RegistryConfigParseHander extends AbstractTrfBeanParseHander<RegistryConfig> {

    @Override
    protected <RegistryConfig> void parse() {
        RootBeanDefinition beanDefinition = initBeanDefinition();

        String id = element.getAttribute("id");
        String protocol = element.getAttribute("protocol");
        String address = element.getAttribute("address");
        String group = element.getAttribute("group");

        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        parserContext.getRegistry().registerBeanDefinition(protocol, beanDefinition);
        parserContext.getRegistry().registerBeanDefinition(address, beanDefinition);
        parserContext.getRegistry().registerBeanDefinition(group, beanDefinition);

        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        beanDefinition.getPropertyValues().addPropertyValue("protocol", protocol);
        beanDefinition.getPropertyValues().addPropertyValue("address", address);
        beanDefinition.getPropertyValues().addPropertyValue("group", group);
    }

    public static AbstractTrfBeanParseHander instanceOf() {
        return new RegistryConfigParseHander();
    }
}
