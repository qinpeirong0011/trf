package com.qinpr.trf.common;


/**
 * Created by qinpr on 18/4/27.
 */
public interface Node {

    URL getUrl();

    boolean isAvailable();

    void destroy();
}
