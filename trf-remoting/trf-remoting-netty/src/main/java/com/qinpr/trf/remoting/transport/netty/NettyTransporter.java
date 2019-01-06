package com.qinpr.trf.remoting.transport.netty;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.remoting.*;

/**
 * Created by qinpr on 2018/10/26.
 */
public class NettyTransporter implements Transporter {
    public Server bind(URL url, ChannelHandler handler) throws RemotingException {
        return new NettyServer(url, handler);
    }

    public Client connect(URL url, ChannelHandler handler) throws RemotingException {
        return new NettyClient(url, handler);
    }
}
