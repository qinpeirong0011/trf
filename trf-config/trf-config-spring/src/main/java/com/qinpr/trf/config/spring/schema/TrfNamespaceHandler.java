package com.qinpr.trf.config.spring.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by qinpr on 18/4/26.
 */
public class TrfNamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        System.out.println("init ..... TrfNamespaceHandler");
    }
}
