package com.qinpr.trf.remoting.exchange.support;

import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.exchange.ExchangeChannel;
import com.qinpr.trf.remoting.exchange.ExchangeHandler;
import com.qinpr.trf.remoting.telnet.support.TelnetHandlerAdapter;

import java.util.concurrent.CompletableFuture;


/**
 * Created by qinpr on 2018/10/26.
 */
public abstract class ExchangeHandlerAdapter extends TelnetHandlerAdapter implements ExchangeHandler {

    @Override
    public CompletableFuture<Object> reply(ExchangeChannel channel, Object request) throws RemotingException {
        return null;
    }
}
