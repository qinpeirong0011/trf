package com.qinpr.trf.rpc.protocol.trf;

import com.qinpr.trf.remoting.exchange.ResponseCallback;
import com.qinpr.trf.remoting.exchange.ResponseFuture;
import com.qinpr.trf.rpc.Result;
import com.qinpr.trf.rpc.RpcException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by qinpr on 2019/1/3.
 */
public class FutureAdapter<V> extends CompletableFuture<V> {
    private final ResponseFuture future;
    private CompletableFuture<Result> resultFuture;

    public FutureAdapter(ResponseFuture future) {
        this.future = future;
        this.resultFuture = new CompletableFuture<Result>();
        future.setCallback(new ResponseCallback() {
            @Override
            public void done(Object response) {
                Result result = (Result) response;
                FutureAdapter.this.resultFuture.complete(result);
                V value = null;
                try {
                    value = (V) result.recreate();
                } catch (Throwable t) {
                    FutureAdapter.this.completeExceptionally(t);
                }
                FutureAdapter.this.complete(value);
            }

            @Override
            public void caught(Throwable exception) {
                FutureAdapter.this.completeExceptionally(exception);
            }
        });
    }

    public ResponseFuture getFuture() {
        return future;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return super.isDone();
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get() throws InterruptedException, ExecutionException {
        try {
            return super.get();
        }  catch (Throwable e) {
            throw new RpcException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        try {
            return super.get(timeout, unit);
        }  catch (Throwable e) {
            throw new RpcException(e);
        }
    }

    /**
     * FIXME
     * This method has no need open to the the end user.
     * Mostly user use RpcContext.getFuture() to refer the instance of this class, so the user will get a CompletableFuture, this method will rarely be noticed.
     *
     * @return
     */
    public CompletableFuture<Result> getResultFuture() {
        return resultFuture;
    }
}
