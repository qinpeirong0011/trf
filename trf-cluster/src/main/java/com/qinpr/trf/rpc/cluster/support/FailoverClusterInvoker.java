package com.qinpr.trf.rpc.cluster.support;

import com.qinpr.trf.rpc.cluster.Directory;

/**
 * Created by qinpr on 2018/11/27.
 */
public class FailoverClusterInvoker<T> extends AbstractClusterInvoker<T> {
    public FailoverClusterInvoker(Directory<T> directory) {
        super(directory);
    }
}
