package com.qinpr.trf.config.spring.schema.handler;

import com.qinpr.trf.config.spring.ServiceBean;
import com.qinpr.trf.config.spring.schema.AbstractTrfBeanParseHander;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Created by qinpr on 2018/10/11.
 */
public class ServiceBeanParseHandler extends AbstractTrfBeanParseHander<ServiceBean> {
    protected <ServiceBean> void parse() {
        RootBeanDefinition beanDefinition = initBeanDefinition();

        String id = element.getAttribute("id");
        String interfaceName = element.getAttribute("interface");
        String ref = element.getAttribute("ref");
        String version = element.getAttribute("version");
        String group = element.getAttribute("group");

        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        beanDefinition.getPropertyValues().addPropertyValue("interface", interfaceName);
        beanDefinition.getPropertyValues().addPropertyValue("ref", new RuntimeBeanReference(ref));
        beanDefinition.getPropertyValues().addPropertyValue("version", version);
        beanDefinition.getPropertyValues().addPropertyValue("group", group);
    }

    public static AbstractTrfBeanParseHander instanceOf() {
        return new ServiceBeanParseHandler();
    }
}
