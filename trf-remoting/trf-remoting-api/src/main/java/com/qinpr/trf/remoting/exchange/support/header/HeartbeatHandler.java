package com.qinpr.trf.remoting.exchange.support.header;

import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.transport.AbstractChannelHandlerDelegate;

/**
 * Created by qinpr on 2019/1/3.
 */
public class HeartbeatHandler extends AbstractChannelHandlerDelegate {
    public static String KEY_READ_TIMESTAMP = "READ_TIMESTAMP";

    public static String KEY_WRITE_TIMESTAMP = "WRITE_TIMESTAMP";


    public HeartbeatHandler(ChannelHandler handler) {
        super(handler);
    }
}
