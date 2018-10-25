package com.qinpr.trf.registry.zookeeper;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.registry.Registry;
import com.qinpr.trf.registry.support.AbstractRegistryFactory;
import com.qinpr.trf.remoting.zookeeper.ZookeeperTransporter;

/**
 * Created by qinpr on 2018/10/24.
 */
public class ZookeeperRegistryFactory extends AbstractRegistryFactory {

    private ZookeeperTransporter zookeeperTransporter;

    public void setZookeeperTransporter(ZookeeperTransporter zookeeperTransporter) {
        this.zookeeperTransporter = zookeeperTransporter;
    }

    protected Registry createRegistry(URL url) {
        return new ZookeeperRegistry(url, zookeeperTransporter);
    }
}
