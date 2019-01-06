package com.qinpr.trf.remoting.transport;

import com.qinpr.trf.remoting.ChannelHandler;

/**
 * Created by qinpr on 2019/1/5.
 */
public class MultiMessageHandler extends AbstractChannelHandlerDelegate {
    public MultiMessageHandler(ChannelHandler handler) {
        super(handler);
    }
}
