package com.qinpr.trf.rpc.protocol.trf;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.utils.AtomicPositiveInteger;
import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.TimeoutException;
import com.qinpr.trf.remoting.exchange.ExchangeClient;
import com.qinpr.trf.remoting.exchange.ResponseFuture;
import com.qinpr.trf.rpc.*;
import com.qinpr.trf.rpc.protocol.AbstractInvoker;
import com.qinpr.trf.rpc.support.RpcUtils;

import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by qinpr on 2019/1/3.
 */
public class TrfInvoker<T> extends AbstractInvoker<T> {


    private final ExchangeClient[] clients;

    private final AtomicPositiveInteger index = new AtomicPositiveInteger();

    private final String version;

    private final ReentrantLock destroyLock = new ReentrantLock();

    private final Set<Invoker<?>> invokers;

    public TrfInvoker(Class<T> serviceType, URL url, ExchangeClient[] clients, Set<Invoker<?>> invokers) {
        super(serviceType, url, new String[]{Constants.INTERFACE_KEY, Constants.GROUP_KEY, Constants.TOKEN_KEY, Constants.TIMEOUT_KEY});
        this.clients = clients;
        // get version.
        this.version = url.getParameter(Constants.VERSION_KEY, "0.0.0");
        this.invokers = invokers;
    }

    @Override
    protected Result doInvoke(Invocation invocation) throws Throwable {
        RpcInvocation inv = (RpcInvocation) invocation;
        final String methodName = RpcUtils.getMethodName(invocation);
        inv.setAttachment(Constants.PATH_KEY, getUrl().getPath());
        inv.setAttachment(Constants.VERSION_KEY, version);

        ExchangeClient currentClient;
        if (clients.length == 1) {
            currentClient = clients[0];
        } else {
            currentClient = clients[index.getAndIncrement() % clients.length];
        }
        try {
            boolean isAsync = RpcUtils.isAsync(getUrl(), invocation);
            boolean isAsyncFuture = RpcUtils.isGeneratedFuture(inv) || RpcUtils.isFutureReturnType(inv);
            boolean isOneway = RpcUtils.isOneway(getUrl(), invocation);
            int timeout = getUrl().getMethodParameter(methodName, Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT);
            if (isOneway) {
                boolean isSent = getUrl().getMethodParameter(methodName, Constants.SENT_KEY, false);
                currentClient.send(inv, isSent);
                RpcContext.getContext().setFuture(null);
                return new RpcResult();
            } else if (isAsync) {
                ResponseFuture future = currentClient.request(inv, timeout);
                // For compatibility
                FutureAdapter<Object> futureAdapter = new FutureAdapter<Object>(future);
                RpcContext.getContext().setFuture(futureAdapter);

                Result result;
                if (isAsyncFuture) {
                    // register resultCallback, sometimes we need the asyn result being processed by the filter chain.
                    result = new AsyncRpcResult(futureAdapter, futureAdapter.getResultFuture(), false);
                } else {
                    result = new SimpleAsyncRpcResult(futureAdapter, futureAdapter.getResultFuture(), false);
                }
                return result;
            } else {
                RpcContext.getContext().setFuture(null);
                return (Result) currentClient.request(inv, timeout).get();
            }
        } catch (TimeoutException e) {
            throw new RpcException(RpcException.TIMEOUT_EXCEPTION, "Invoke remote method timeout. method: " + invocation.getMethodName() + ", provider: " + getUrl() + ", cause: " + e.getMessage(), e);
        } catch (RemotingException e) {
            throw new RpcException(RpcException.NETWORK_EXCEPTION, "Failed to invoke remote method: " + invocation.getMethodName() + ", provider: " + getUrl() + ", cause: " + e.getMessage(), e);
        }
    }
}
