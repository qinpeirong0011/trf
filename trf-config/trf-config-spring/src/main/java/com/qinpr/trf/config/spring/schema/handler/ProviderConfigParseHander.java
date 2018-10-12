package com.qinpr.trf.config.spring.schema.handler;

import com.qinpr.trf.config.ProviderConfig;
import com.qinpr.trf.config.spring.schema.AbstractTrfBeanParseHander;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Created by qinpr on 2018/10/11.
 */
public class ProviderConfigParseHander extends AbstractTrfBeanParseHander<ProviderConfig> {

    @Override
    protected <ProviderConfig> void parse() {
        RootBeanDefinition beanDefinition = initBeanDefinition();

        String id = element.getAttribute("id");
        String executes = element.getAttribute("executes");
        String threads = element.getAttribute("threads");
        String token = element.getAttribute("token");
        String timeout = element.getAttribute("timeout");

        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        parserContext.getRegistry().registerBeanDefinition(executes, beanDefinition);
        parserContext.getRegistry().registerBeanDefinition(threads, beanDefinition);
        parserContext.getRegistry().registerBeanDefinition(token, beanDefinition);
        parserContext.getRegistry().registerBeanDefinition(timeout, beanDefinition);

        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        beanDefinition.getPropertyValues().addPropertyValue("executes", executes);
        beanDefinition.getPropertyValues().addPropertyValue("threads", threads);
        beanDefinition.getPropertyValues().addPropertyValue("token", token);
        beanDefinition.getPropertyValues().addPropertyValue("timeout", timeout);
    }

    public static AbstractTrfBeanParseHander instanceOf() {
        return new ProviderConfigParseHander();
    }
}
