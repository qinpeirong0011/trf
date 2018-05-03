package com.alibaba.dubbo.rpc.spi;

import com.alibaba.dubbo.common.URL;

/**
 * Created by qinpr on 18/5/2.
 */
public class DirExtensionDefaultImpl implements DirExtension {
    @Override
    public String sayHello(URL url, String name) {
        return name + "-default";
    }
}
