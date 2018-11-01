package com.qinpr.trf.remoting.transport.netty;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.remoting.Codec2;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * Created by qinpr on 2018/11/1.
 */
public class NettyCodecAdapter {

    private final ChannelHandler encoder = new InternalEncoder();

    private final ChannelHandler decoder = new InternalDecoder();

    private final Codec2 codec;

    private final URL url;

    private final int bufferSize;

    private final com.qinpr.trf.remoting.ChannelHandler handler;

    public NettyCodecAdapter(Codec2 codec, URL url, com.qinpr.trf.remoting.ChannelHandler handler) {
       this.codec = codec;
       this.url = url;
       this.handler = handler;
       int b = url.getPositiveParameter(Constants.BUFFER_KEY, Constants.DEFAULT_BUFFER_SIZE);
       this.bufferSize = b >= Constants.MIN_BUFFER_SIZE && b <= Constants.MAX_BUFFER_SIZE ? b : Constants.DEFAULT_BUFFER_SIZE;
    }

    public ChannelHandler getEncoder() {
        return encoder;
    }

    public ChannelHandler getDecoder() {
        return decoder;
    }

    private class InternalEncoder extends OneToOneEncoder {
        @Override
        protected Object encode(ChannelHandlerContext channelHandlerContext, Channel channel, Object o) throws Exception {
            return null;
        }
    }

    private class InternalDecoder extends SimpleChannelUpstreamHandler {
        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            super.messageReceived(ctx, e);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
            super.exceptionCaught(ctx, e);
        }
    }
}
