package com.qinpr.trf.config.spring.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by qinpr on 2018/10/9.
 */
public class TrfBeanDefinitionParser implements BeanDefinitionParser {
    private final Class<?> beanClass;
    private final boolean required;

    public static Map<Class<?>, AbstractTrfBeanParseHander> parseHolder = new ConcurrentHashMap<Class<?>, AbstractTrfBeanParseHander>(10);

    public TrfBeanDefinitionParser(Class<?> beanClass, boolean required) {
        this.beanClass = beanClass;
        this.required = required;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return parse(element, parserContext, beanClass, required);
    }

    private static BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass, boolean required) {
        AbstractTrfBeanParseHander parseHander = parseHolder.get(beanClass);
        if (parseHander != null) {
            parseHander.setParseHandler(element, parserContext, beanClass);
            parseHander.parse();
        }
        return null;
    }
}
