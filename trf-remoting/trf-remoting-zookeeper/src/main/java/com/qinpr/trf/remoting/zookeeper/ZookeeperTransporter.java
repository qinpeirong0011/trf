package com.qinpr.trf.remoting.zookeeper;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.extension.Adaptive;
import com.qinpr.trf.common.extension.SPI;

/**
 * Created by qinpr on 2018/10/25.
 */
@SPI("curator")
public interface ZookeeperTransporter {

    @Adaptive({Constants.CLIENT_KEY, Constants.TRANSPORTER_KEY})
    ZookeeperClient connect(URL url);
}
