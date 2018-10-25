package com.qinpr.trf.remoting;

import com.qinpr.trf.common.Resetable;

import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * Created by qinpr on 2018/10/25.
 */
public interface Server extends Endpoint, Resetable {
    /**
     * is bound.
     *
     * @return bound
     */
    boolean isBound();

    /**
     * get channels.
     *
     * @return channels
     */
    Collection<Channel> getChannels();

    /**
     * get channel.
     *
     * @param remoteAddress
     * @return channel
     */
    Channel getChannel(InetSocketAddress remoteAddress);

}
