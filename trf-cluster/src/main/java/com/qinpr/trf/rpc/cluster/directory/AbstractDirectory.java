package com.qinpr.trf.rpc.cluster.directory;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.rpc.cluster.Directory;
import com.qinpr.trf.rpc.cluster.Router;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by qinpr on 2018/11/21.
 */
public abstract class AbstractDirectory<T> implements Directory<T> {

    private final URL url;

    private volatile URL consumerUrl;

    private volatile List<Router> routers;

    public AbstractDirectory(URL url) {
        this(url, null);
    }

    public AbstractDirectory(URL url, List<Router> routers) {
        this(url, url, routers);
    }

    public AbstractDirectory(URL url, URL consumerUrl, List<Router> routers) {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }
        this.url = url;
        this.consumerUrl = consumerUrl;
        setRouters(routers);
    }

    protected void setRouters(List<Router> routers) {
        // copy list
        routers = routers == null ? new ArrayList<Router>() : new ArrayList<Router>(routers);
        // append url router
        String routerKey = url.getParameter(Constants.ROUTER_KEY);
        if (routerKey != null && routerKey.length() > 0) {
//            RouterFactory routerFactory = ExtensionLoader.getExtensionLoader(RouterFactory.class).getExtension(routerKey);
//            routers.add(routerFactory.getRouter(url));
        }
        // append mock invoker selector
//        routers.add(new MockInvokersSelector());
        Collections.sort(routers);
        this.routers = routers;
    }

    public void setConsumerUrl(URL consumerUrl) {
        this.consumerUrl = consumerUrl;
    }
}
