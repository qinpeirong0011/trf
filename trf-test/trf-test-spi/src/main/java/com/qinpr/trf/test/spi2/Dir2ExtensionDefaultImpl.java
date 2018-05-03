package com.alibaba.dubbo.rpc.spi2;

/**
 * Created by qinpr on 18/5/2.
 */
public class Dir2ExtensionDefaultImpl implements Dir2Extension {

    @Override
    public String sayHello(String name, String type) {
        return "dir2Extension-" + name + "-default";
    }
}
