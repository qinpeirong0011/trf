package com.qinpr.trf.config.spring.schema.handler;

import com.qinpr.trf.config.spring.ServiceBean;
import com.qinpr.trf.config.spring.schema.AbstractTrfBeanParseHander;

/**
 * Created by qinpr on 2018/10/11.
 */
public class ServiceBeanParseHandler extends AbstractTrfBeanParseHander<ServiceBean> {
    protected <T> void parse() {

    }

    public static AbstractTrfBeanParseHander instanceOf() {
        return new ServiceBeanParseHandler();
    }
}
