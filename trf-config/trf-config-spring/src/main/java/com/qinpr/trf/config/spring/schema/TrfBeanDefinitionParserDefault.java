package com.qinpr.trf.config.spring.schema;

import com.qinpr.trf.config.RegistryConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Created by qinpr on 2018/10/8.
 */
public class TrfBeanDefinitionParserDefault extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return RegistryConfig.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String id = element.getAttribute("id");
        String protocol = element.getAttribute("protocol");
        String address = element.getAttribute("address");
        String group = element.getAttribute("group");

        if(StringUtils.hasText(id))
            builder.addPropertyValue("id", id);

        if(StringUtils.hasText(protocol))
            builder.addPropertyValue("protocol", protocol);

        if(StringUtils.hasText(address))
            builder.addPropertyValue("address", address);

        if(StringUtils.hasText(group))
            builder.addPropertyValue("group", group);
    }
}
