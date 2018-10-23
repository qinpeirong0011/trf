package com.qinpr.trf.config.spring.schema.handler;

import com.qinpr.trf.config.ApplicationConfig;
import com.qinpr.trf.config.spring.schema.AbstractTrfBeanParseHander;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Created by qinpr on 2018/10/11.
 */
public class ApplicationConfigParseHander extends AbstractTrfBeanParseHander<ApplicationConfig> {

    @Override
    protected <ApplicationConfig> void parse() {
        RootBeanDefinition beanDefinition = initBeanDefinition();

        String id = element.getAttribute("id");
        String name = element.getAttribute("name");

        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        beanDefinition.getPropertyValues().addPropertyValue("name", name);
    }

    public static AbstractTrfBeanParseHander instanceOf() {
        return new ApplicationConfigParseHander();
    }
}
