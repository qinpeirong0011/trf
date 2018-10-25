package com.qinpr.trf.registry.zookeeper;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.registry.support.FailbackRegistry;
import com.qinpr.trf.remoting.zookeeper.ZookeeperTransporter;

import java.util.List;

/**
 * Created by qinpr on 2018/10/25.
 */
public class ZookeeperRegistry extends FailbackRegistry {


    public ZookeeperRegistry(URL url, ZookeeperTransporter zookeeperTransporter) {

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
