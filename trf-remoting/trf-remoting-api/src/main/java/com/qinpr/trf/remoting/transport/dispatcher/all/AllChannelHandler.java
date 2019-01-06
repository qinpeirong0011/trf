package com.qinpr.trf.remoting.transport.dispatcher.all;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.transport.dispatcher.WrappedChannelHandler;

/**
 * Created by qinpr on 2019/1/5.
 */
public class AllChannelHandler extends WrappedChannelHandler {

    public AllChannelHandler(ChannelHandler handler, URL url) {
        super(handler, url);
    }
}
