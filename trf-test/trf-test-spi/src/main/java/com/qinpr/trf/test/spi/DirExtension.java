package com.alibaba.dubbo.rpc.spi;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;
import com.alibaba.dubbo.rpc.constants.SpiConstants;

/**
 * Created by qinpr on 18/5/2.
 */
@SPI("dirDefault")
public interface DirExtension {

    @Adaptive({SpiConstants.DIR_DEFAULT, SpiConstants.DIR_FIRST})
    String sayHello(URL url, String name);
}
