package com.qinpr.trf.remoting.exchange;

import com.qinpr.trf.remoting.RemotingException;

/**
 * Created by qinpr on 2018/10/25.
 */
public interface ResponseFuture {

    Object get() throws RemotingException;

    Object get(int timeoutInMillis) throws RemotingException;

    void setCallback(ResponseCallback callback);

    boolean isDone();
}
