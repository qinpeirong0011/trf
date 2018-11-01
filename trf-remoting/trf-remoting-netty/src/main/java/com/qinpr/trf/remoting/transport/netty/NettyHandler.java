package com.qinpr.trf.remoting.transport.netty;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.ChannelHandler;
import org.jboss.netty.channel.SimpleChannelHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by qinpr on 2018/11/1.
 */
public class NettyHandler extends SimpleChannelHandler {
    private final Map<String, Channel> channels = new ConcurrentHashMap<String, Channel>(); // <ip:port, channel>

    private final URL url;

    private final ChannelHandler handler;

    public NettyHandler(URL url, ChannelHandler handler) {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }
        if (handler == null) {
            throw new IllegalArgumentException("handler == null");
        }
        this.url = url;
        this.handler = handler;
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }
}
