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

    public RemotingException(Channel channel, String msg) {
        this(channel == null ? null : channel.getLocalAddress(), channel == null ? null : channel.getRemoteAddress(),
                msg);
    }

    public RemotingException(InetSocketAddress localAddress, InetSocketAddress remoteAddress, String message) {
        super(message);

        this.localAddress = localAddress;
        this.remoteAddress = remoteAddress;
    }

    public RemotingException(Channel channel, String message, Throwable cause) {
        this(channel == null ? null : channel.getLocalAddress(), channel == null ? null : channel.getRemoteAddress(),
                message, cause);
    }


    public RemotingException(InetSocketAddress localAddress, InetSocketAddress remoteAddress, String message,
                             Throwable cause) {
        super(message, cause);

        this.localAddress = localAddress;
        this.remoteAddress = remoteAddress;
    }
}
