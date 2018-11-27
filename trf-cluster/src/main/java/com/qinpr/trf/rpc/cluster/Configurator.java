package com.qinpr.trf.rpc.cluster;

import com.qinpr.trf.common.URL;

/**
 * Created by qinpr on 2018/11/23.
 */
public interface Configurator extends Comparable<Configurator> {
    URL getUrl();
    URL configure(URL url);
}
