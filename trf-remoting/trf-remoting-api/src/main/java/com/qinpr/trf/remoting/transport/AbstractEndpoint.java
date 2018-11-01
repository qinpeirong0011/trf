package com.qinpr.trf.remoting.transport;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.Resetable;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.Codec2;
import com.qinpr.trf.remoting.RemotingException;

import java.net.InetSocketAddress;

/**
 * Created by qinpr on 2018/11/1.
 */
public class AbstractEndpoint extends AbstractPeer implements Resetable {

    private Codec2 codec;

    private int timeout;

    private int connectTimeout;

    public AbstractEndpoint(URL url, ChannelHandler handler) {
        super(url, handler);
        this.codec = getChannelCodec(url);
        this.timeout = url.getPositiveParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT);
        this.connectTimeout = url.getPositiveParameter(Constants.CONNECT_TIMEOUT_KEY, Constants.DEFAULT_CONNECT_TIMEOUT);
    }

    protected static Codec2 getChannelCodec(URL url) {
        String codecName = url.getParameter(Constants.CODEC_KEY, "telnet");
        return ExtensionLoader.getExtensionLoader(Codec2.class).getExtension(codecName);
    }

    public void reset(URL url) {

    }

    public ChannelHandler getChannelHandler() {
        return null;
    }

    public InetSocketAddress getLocalAddress() {
        return null;
    }

    public void send(Object message) throws RemotingException {

    }

    public void send(Object message, boolean sent) throws RemotingException {

    }

    public void close() {

    }

    public void close(int timeout) {

    }

    public void startClose() {

    }

    public boolean isClosed() {
        return false;
    }

    public void connected(Channel channel) throws RemotingException {

    }

    public void disconnected(Channel channel) throws RemotingException {

    }

    public void sent(Channel channel, Object message) throws RemotingException {

    }

    public void received(Channel channel, Object message) throws RemotingException {

    }

    public void caught(Channel channel, Throwable exception) throws RemotingException {

    }

    public Codec2 getCodec() {
        return codec;
    }
}
