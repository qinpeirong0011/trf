package com.qinpr.trf.remoting.transport.dispatcher.all;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.Dispatcher;

/**
 * Created by qinpr on 2019/1/5.
 */
public class AllDispatcher implements Dispatcher {

    public static final String NAME = "all";

    @Override
    public ChannelHandler dispatch(ChannelHandler handler, URL url) {
        return new AllChannelHandler(handler, url);
    }
}
