package com.qinpr.trf.config.spring.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by qinpr on 2018/10/9.
 */
public class TrfBeanDefinitionParser implements BeanDefinitionParser {
    private final Class<?> beanClass;
    private final boolean required;

    public TrfBeanDefinitionParser(Class<?> beanClass, boolean required) {
        this.beanClass = beanClass;
        this.required = required;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return parse(element, parserContext, beanClass, required);
    }

    private static BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass, boolean required) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        String id = element.getAttribute("id");
//        String protocol = element.getAttribute("protocol");
//        String address = element.getAttribute("address");
//        String group = element.getAttribute("group");

        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
//        parserContext.getRegistry().registerBeanDefinition(protocol, beanDefinition);
//        parserContext.getRegistry().registerBeanDefinition(address, beanDefinition);
//        parserContext.getRegistry().registerBeanDefinition(group, beanDefinition);

        beanDefinition.getPropertyValues().addPropertyValue("id", id);
//        beanDefinition.getPropertyValues().addPropertyValue("protocol", protocol);
//        beanDefinition.getPropertyValues().addPropertyValue("address", address);
//        beanDefinition.getPropertyValues().addPropertyValue("group", group);

        return null;
    }
}
