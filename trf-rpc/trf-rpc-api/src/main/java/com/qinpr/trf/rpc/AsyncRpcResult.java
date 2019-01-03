package com.qinpr.trf.rpc;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Created by qinpr on 2019/1/3.
 */
public class AsyncRpcResult extends AbstractResult {

    private RpcContext storedContext;
    private RpcContext storedServerContext;

    protected CompletableFuture<Object> valueFuture;

    protected CompletableFuture<Result> resultFuture;


    public AsyncRpcResult(CompletableFuture<Object> future, final CompletableFuture<Result> rFuture, boolean registerCallback) {
        if (rFuture == null) {
            throw new IllegalArgumentException();
        }
        resultFuture = rFuture;
        if (registerCallback) {
            /**
             * We do not know whether future already completed or not, it's a future exposed or even created by end user.
             * 1. future complete before whenComplete. whenComplete fn (resultFuture.complete) will be executed in thread subscribing, in our case, it's Dubbo thread.
             * 2. future complete after whenComplete. whenComplete fn (resultFuture.complete) will be executed in thread calling complete, normally its User thread.
             */
            future.whenComplete((v,t)->{
                RpcResult rpcResult;
                if (t!=null) {
                    if (t instanceof CompletionException) {
                        rpcResult = new RpcResult(t.getCause());
                    } else {
                        rpcResult = new RpcResult(t);
                    }
                } else {
                    rpcResult = new RpcResult(v);
                }
                rFuture.complete(rpcResult);
            });
        }
        this.valueFuture = future;
        // employ copy of context avoid the other call may modify the context content
        this.storedContext = RpcContext.getContext().copyOf();
        this.storedServerContext = RpcContext.getServerContext().copyOf();
    }

    @Override
    public Object getValue() {
        return getRpcResult().getValue();
    }

    @Override
    public Throwable getException() {
        return getRpcResult().getException();
    }

    @Override
    public boolean hasException() {
        return getRpcResult().hasException();
    }

    @Override
    public Object getResult() {
        return getRpcResult().getResult();
    }

    public CompletableFuture getValueFuture() {
        return valueFuture;
    }

    public CompletableFuture<Result> getResultFuture() {
        return resultFuture;
    }

    public void setResultFuture(CompletableFuture<Result> resultFuture) {
        this.resultFuture = resultFuture;
    }

    public Result getRpcResult() {
        Result result;
        try {
            result = resultFuture.get();
        } catch (Exception e) {
            // This should never happen;
            e.printStackTrace();
            result = new RpcResult();
        }
        return result;
    }

    @Override
    public Object recreate() throws Throwable {
        return valueFuture;
    }

    @Override
    public Map<String, String> getAttachments() {
        return getRpcResult().getAttachments();
    }

    @Override
    public String getAttachment(String key) {
        return getRpcResult().getAttachment(key);
    }

    @Override
    public String getAttachment(String key, String defaultValue) {
        return getRpcResult().getAttachment(key, defaultValue);
    }


}
