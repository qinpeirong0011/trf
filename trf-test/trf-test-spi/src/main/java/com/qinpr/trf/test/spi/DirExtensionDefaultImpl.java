package com.qinpr.trf.test.spi;


import com.qinpr.trf.common.URL;

/**
 * Created by qinpr on 18/5/2.
 */
public class DirExtensionDefaultImpl implements DirExtension {

    public String sayHello(URL url, String name) {
        return name + "-default";
    }
}
