package com.qinpr.trf.registry.support;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.registry.Registry;
import com.qinpr.trf.registry.RegistryFactory;

/**
 * Created by qinpr on 2018/10/24.
 */
public abstract class AbstractRegistryFactory implements RegistryFactory {

    public Registry getRegistry(URL url) {
        return null;
    }

    protected abstract Registry createRegistry(URL url);
}
