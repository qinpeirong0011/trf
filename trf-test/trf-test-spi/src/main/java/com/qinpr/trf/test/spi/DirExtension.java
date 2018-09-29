package com.qinpr.trf.test.spi;


import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.extension.Adaptive;
import com.qinpr.trf.common.extension.SPI;
import com.qinpr.trf.test.constants.SpiConstants;

/**
 * Created by qinpr on 18/5/2.
 */
@SPI("dirDefault")
public interface DirExtension {

    @Adaptive("dirDefault")
    String sayHello(URL url, String name);
}
