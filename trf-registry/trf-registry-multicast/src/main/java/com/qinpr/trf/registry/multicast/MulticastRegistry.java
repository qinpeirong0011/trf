package com.qinpr.trf.registry.multicast;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.registry.support.FailbackRegistry;

import java.util.List;

/**
 * Created by qinpr on 2018/10/25.
 */
public class MulticastRegistry extends FailbackRegistry {


    public MulticastRegistry(URL url) {
    }

    public URL getUrl() {
        return null;
    }

    public boolean isAvailable() {
        return false;
    }

    public void destroy() {

    }

    public void register(URL url) {

    }

    public void unregister(URL url) {

    }

    public List<URL> lookup(URL url) {
        return null;
    }
}
