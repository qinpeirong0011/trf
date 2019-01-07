package com.qinpr.trf.remoting.exchange;

import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.telnet.TelnetHandler;

import java.util.concurrent.CompletableFuture;


/**
 * Created by qinpr on 2018/10/26.
 */
public interface ExchangeHandler extends ChannelHandler, TelnetHandler {

    CompletableFuture<Object> reply(ExchangeChannel channel, Object request) throws RemotingException;
}
