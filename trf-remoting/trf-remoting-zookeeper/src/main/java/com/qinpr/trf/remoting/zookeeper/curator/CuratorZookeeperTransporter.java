package com.qinpr.trf.remoting.zookeeper.curator;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.remoting.zookeeper.ZookeeperClient;
import com.qinpr.trf.remoting.zookeeper.ZookeeperTransporter;

/**
 * Created by qinpr on 2018/11/10.
 */
public class CuratorZookeeperTransporter implements ZookeeperTransporter {
    public ZookeeperClient connect(URL url) {
        return new CuratorZookeeperClient(url);
    }
}
