package com.qinpr.trf.remoting.transport;

import com.qinpr.trf.remoting.ChannelHandler;

/**
 * Created by qinpr on 2018/10/26.
 */
public interface ChannelHandlerDelegate extends ChannelHandler {
    ChannelHandler getHandler();
}
