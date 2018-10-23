package com.qinpr.trf.config.spring.schema.handler;

import com.qinpr.trf.config.ProtocolConfig;
import com.qinpr.trf.config.RegistryConfig;
import com.qinpr.trf.config.spring.schema.AbstractTrfBeanParseHander;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Created by qinpr on 2018/10/11.
 */
public class ProtocolConfigParseHander extends AbstractTrfBeanParseHander<ProtocolConfig> {

    @Override
    protected <ProtocolConfig> void parse() {
        RootBeanDefinition beanDefinition = initBeanDefinition();

        String id = element.getAttribute("id");
        String name = element.getAttribute("name");
        String port = element.getAttribute("port");

        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        beanDefinition.getPropertyValues().addPropertyValue("name", name);
        beanDefinition.getPropertyValues().addPropertyValue("port", port);
    }

    public static AbstractTrfBeanParseHander instanceOf() {
        return new ProtocolConfigParseHander();
    }
}
