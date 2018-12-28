package com.qinpr.trf.rpc.cluster;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.extension.Adaptive;
import com.qinpr.trf.common.extension.SPI;
import com.qinpr.trf.rpc.Invocation;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.RpcException;
import com.qinpr.trf.rpc.cluster.loadbalance.RandomLoadBalance;

import java.util.List;

/**
 * Created by qinpr on 2018/12/28.
 */
@SPI(RandomLoadBalance.NAME)
public interface LoadBalance {
    @Adaptive("loadbalance")
    <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException;
}
