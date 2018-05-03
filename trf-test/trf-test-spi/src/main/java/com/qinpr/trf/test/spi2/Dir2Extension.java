package com.alibaba.dubbo.rpc.spi2;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.SPI;

/**
 * Created by qinpr on 18/5/2.
 */
@SPI("dir2Default")
public interface Dir2Extension {

    String sayHello(String name, String type);
}
