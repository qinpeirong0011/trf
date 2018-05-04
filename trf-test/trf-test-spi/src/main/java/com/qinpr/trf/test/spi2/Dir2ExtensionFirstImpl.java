package com.qinpr.trf.test.spi2;

/**
 * Created by qinpr on 18/5/2.
 */
public class Dir2ExtensionFirstImpl implements Dir2Extension {

    public String sayHello(String name, String type) {
        return "dir2Extension-" + name + "-first";
    }
}
