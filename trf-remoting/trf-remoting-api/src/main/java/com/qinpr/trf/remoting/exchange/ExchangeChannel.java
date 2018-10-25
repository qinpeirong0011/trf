package com.qinpr.trf.remoting.exchange;

import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.RemotingException;

/**
 * Created by qinpr on 2018/10/25.
 */
public interface ExchangeChannel extends Channel {

    ResponseFuture request(Object request) throws RemotingException;

    ResponseFuture request(Object request, int timeout) throws RemotingException;

    void close(int timeout);
}
