package com.qinpr.trf.remoting;

import java.net.InetSocketAddress;

/**
 * Created by qinpr on 2018/10/25.
 */
public class RemotingException extends Exception {

    private InetSocketAddress localAddress;

    private InetSocketAddress remoteAddress;

    public RemotingException() {
    }

    public RemotingException(InetSocketAddress localAddress, InetSocketAddress remoteAddress, String message,
                             Throwable cause) {
        super(message, cause);

        this.localAddress = localAddress;
        this.remoteAddress = remoteAddress;
    }
}
