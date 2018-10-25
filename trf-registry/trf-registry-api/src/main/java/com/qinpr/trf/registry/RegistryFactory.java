package com.qinpr.trf.registry;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.extension.Adaptive;
import com.qinpr.trf.common.extension.SPI;

/**
 * Created by qinpr on 2018/10/18.
 */
@SPI("trf")
public interface RegistryFactory {

    @Adaptive({"protocol"})
    Registry getRegistry(URL url);
}
