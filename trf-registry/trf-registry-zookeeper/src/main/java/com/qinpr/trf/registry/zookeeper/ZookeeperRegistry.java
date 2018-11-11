package com.qinpr.trf.registry.zookeeper;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.registry.support.FailbackRegistry;
import com.qinpr.trf.remoting.zookeeper.StateListener;
import com.qinpr.trf.remoting.zookeeper.ZookeeperClient;
import com.qinpr.trf.remoting.zookeeper.ZookeeperTransporter;
import com.qinpr.trf.rpc.RpcException;

import java.util.List;

/**
 * Created by qinpr on 2018/10/25.
 */
public class ZookeeperRegistry extends FailbackRegistry {
    private final String root;
    private final static String DEFAULT_ROOT = "trf";

    private final ZookeeperClient zkClient;

    public ZookeeperRegistry(URL url, ZookeeperTransporter zookeeperTransporter) {
        super(url);
        if (url.isAnyHost()) {
            throw new IllegalStateException("registry address == null");
        }
        String group = url.getParameter(Constants.GROUP_KEY, DEFAULT_ROOT);
        if (!group.startsWith(Constants.PATH_SEPARATOR)) {
            group = Constants.PATH_SEPARATOR + group;
        }
        this.root = group;
        zkClient = zookeeperTransporter.connect(url);
        zkClient.addStateListener(new StateListener() {
            public void stateChanged(int state) {
                if (state == RECONNECTED) {
                    try {
//                        recover();
                    } catch (Exception e) {
                    }
                }
            }
        });
    }


    public boolean isAvailable() {
        return zkClient.isConnected();
    }

    public void destroy() {

    }


    protected void doRegister(URL url) {
        try {
            zkClient.create(toUrlPath(url), url.getParameter(Constants.DYNAMIC_KEY, true));
        } catch (Throwable e) {
            throw new RpcException("Failed to register " + url + " to zookeeper " + getUrl() + ", cause: " + e.getMessage(), e);
        }
    }

    public void unregister(URL url) {

    }

    public List<URL> lookup(URL url) {
        return null;
    }

    private String toUrlPath(URL url) {
        return toCategoryPath(url) + Constants.PATH_SEPARATOR + URL.encode(url.toFullString());
    }

    private String toCategoryPath(URL url) {
        return toServicePath(url) + Constants.PATH_SEPARATOR + url.getParameter(Constants.CATEGORY_KEY, Constants.DEFAULT_CATEGORY);
    }

    private String toServicePath(URL url) {
        String name = url.getServiceInterface();
        if (Constants.ANY_VALUE.equals(name)) {
            return toRootPath();
        }
        return toRootDir() + URL.encode(name);
    }

    private String toRootPath() {
        return root;
    }

    private String toRootDir() {
        if (root.equals(Constants.PATH_SEPARATOR)) {
            return root;
        }
        return root + Constants.PATH_SEPARATOR;
    }
}
