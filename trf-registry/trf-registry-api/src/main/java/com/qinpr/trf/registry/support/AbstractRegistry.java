package com.qinpr.trf.registry.support;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.utils.ConcurrentHashSet;
import com.qinpr.trf.registry.Registry;

import java.util.Set;

/**
 * Created by qinpr on 2018/10/25.
 */
public abstract class AbstractRegistry implements Registry {
    private URL registryUrl;

    private final Set<URL> registered = new ConcurrentHashSet<URL>();

    public AbstractRegistry(URL url) {
        setUrl(url);
    }

    protected void setUrl(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("registry url == null");
        }
        this.registryUrl = url;
    }

    public URL getUrl() {
        return registryUrl;
    }

    public void register(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("register url == null");
        }
        registered.add(url);
    }
}
