package com.qinpr.trf.rpc.cluster;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.extension.Adaptive;
import com.qinpr.trf.common.extension.SPI;

/**
 * Created by qinpr on 2018/11/23.
 */
@SPI
public interface RouterFactory {
    @Adaptive("protocol")
    Router getRouter(URL url);
}
