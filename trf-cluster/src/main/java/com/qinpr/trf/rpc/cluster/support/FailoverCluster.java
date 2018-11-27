package com.qinpr.trf.rpc.cluster.support;

import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.RpcException;
import com.qinpr.trf.rpc.cluster.Cluster;
import com.qinpr.trf.rpc.cluster.Directory;

/**
 * Created by qinpr on 2018/11/21.
 */
public class FailoverCluster implements Cluster {

    public final static String NAME = "failover";


    public <T> Invoker<T> join(Directory<T> directory) throws RpcException {
        return null;
    }
}
