package com.qinpr.trf.registry.multicast;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.registry.Registry;
import com.qinpr.trf.registry.support.AbstractRegistryFactory;

/**
 * Created by qinpr on 2018/10/25.
 */
public class MulticastRegistryFactory extends AbstractRegistryFactory {
    protected Registry createRegistry(URL url) {
        return new MulticastRegistry(url);
    }
}
