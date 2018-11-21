package com.qinpr.trf.config.spring.schema.handler;

import com.qinpr.trf.config.spring.ReferenceBean;
import com.qinpr.trf.config.spring.schema.AbstractTrfBeanParseHander;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Created by qinpr on 2018/11/20.
 */
public class ReferenceBeanParseHandler extends AbstractTrfBeanParseHander<ReferenceBean> {
    @Override
    protected <T> void parse() {
        RootBeanDefinition beanDefinition = initBeanDefinition();

        String id = element.getAttribute("id");
        String interfaceName = element.getAttribute("interface");
        String check = element.getAttribute("check");

        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        beanDefinition.getPropertyValues().addPropertyValue("interfaceName", interfaceName);
        beanDefinition.getPropertyValues().addPropertyValue("check", check);

        try {
            Class<?> interfaceClass = Class.forName(interfaceName);
            beanDefinition.getPropertyValues().addPropertyValue("interfaceClass", interfaceClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static AbstractTrfBeanParseHander instanceOf() {
        return new ReferenceBeanParseHandler();
    }
}
