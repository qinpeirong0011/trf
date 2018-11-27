package com.qinpr.trf.remoting.zookeeper;

import com.qinpr.trf.common.URL;

import java.util.List;

/**
 * Created by qinpr on 2018/10/25.
 */
public interface ZookeeperClient {
    void create(String path, boolean ephemeral);

    void addStateListener(StateListener listener);

    boolean isConnected();

    List<String> addChildListener(String path, ChildListener listener);

    URL getUrl();
}
