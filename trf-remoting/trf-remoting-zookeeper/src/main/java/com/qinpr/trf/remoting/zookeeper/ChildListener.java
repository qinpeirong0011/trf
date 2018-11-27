package com.qinpr.trf.remoting.zookeeper;

import java.util.List;

/**
 * Created by qinpr on 2018/11/22.
 */
public interface ChildListener {
    void childChanged(String path, List<String> children);
}
