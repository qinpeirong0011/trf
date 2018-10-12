package com.qinpr.trf.config.spring.schema;

import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by qinpr on 2018/10/11.
 */
public abstract class AbstractTrfBeanParseHander<T> {

    protected Element element;

    protected ParserContext parserContext;

    protected Class<T> beanClass;

    protected abstract <T> void parse();

    protected RootBeanDefinition initBeanDefinition() {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        return beanDefinition;
    }

    public void setParseHandler(Element element, ParserContext parserContext, Class<T> beanClass) {
        this.element = element;
        this.parserContext = parserContext;
        this.beanClass = beanClass;
    }

}
