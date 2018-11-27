package com.qinpr.trf.rpc.cluster;

import com.qinpr.trf.common.extension.Adaptive;
import com.qinpr.trf.common.extension.SPI;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.RpcException;
import com.qinpr.trf.rpc.cluster.support.FailoverCluster;



/**
 * Created by qinpr on 2018/11/21.
 */
@SPI(FailoverCluster.NAME)
public interface Cluster {

    @Adaptive
    <T> Invoker<T> join(Directory<T> directory) throws RpcException;
}
