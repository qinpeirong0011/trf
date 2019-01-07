package com.qinpr.trf.remoting.transport.netty;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.utils.NetUtils;
import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.transport.AbstractClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by qinpr on 2019/1/3.
 */
public class NettyClient extends AbstractClient {

    private static final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(Constants.DEFAULT_IO_THREADS, new DefaultThreadFactory("NettyClientWorker", true));

    private Bootstrap bootstrap;

    private volatile Channel channel;


    @Override
    protected void doOpen() throws Throwable {
        final NettyClientHandler nettyClientHandler = new NettyClientHandler(getUrl(), this);
        bootstrap = new Bootstrap();
        bootstrap.group(nioEventLoopGroup)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                //.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, getTimeout())
                .channel(NioSocketChannel.class);

        if (getConnectTimeout() < 3000) {
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
        } else {
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, getConnectTimeout());
        }

        bootstrap.handler(new ChannelInitializer() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                NettyCodecAdapter adapter = new NettyCodecAdapter(getCodec(), getUrl(), NettyClient.this);
                ch.pipeline()//.addLast("logging",new LoggingHandler(LogLevel.INFO))//for debug
                        .addLast("decoder", adapter.getDecoder())
                        .addLast("encoder", adapter.getEncoder())
                        .addLast("handler", nettyClientHandler);
            }
        });

    }

    @Override
    protected void doConnect() throws Throwable {
        long start = System.currentTimeMillis();
        ChannelFuture future = bootstrap.connect(getConnectAddress());
        try {
            boolean ret = future.awaitUninterruptibly(getConnectTimeout(), TimeUnit.MILLISECONDS);

            if (ret && future.isSuccess()) {
                Channel newChannel = future.channel();
                try {
                    // Close old channel
                    Channel oldChannel = NettyClient.this.channel; // copy reference
                    if (oldChannel != null) {
                        try {
//                            if (logger.isInfoEnabled()) {
//                                logger.info("Close old netty channel " + oldChannel + " on create new netty channel " + newChannel);
//                            }
                            oldChannel.close();
                        } finally {
                            NettyChannel.removeChannelIfDisconnected(oldChannel);
                        }
                    }
                } finally {
                    if (NettyClient.this.isClosed()) {
                        try {
//                            if (logger.isInfoEnabled()) {
//                                logger.info("Close new netty channel " + newChannel + ", because the client closed.");
//                            }
                            newChannel.close();
                        } finally {
                            NettyClient.this.channel = null;
                            NettyChannel.removeChannelIfDisconnected(newChannel);
                        }
                    } else {
                        NettyClient.this.channel = newChannel;
                    }
                }
            } else if (future.cause() != null) {
                throw new RemotingException(this, "client(url: " + getUrl() + ") failed to connect to server "
                        + getRemoteAddress() + ", error message is:" + future.cause().getMessage(), future.cause());
            } else {
                throw new RemotingException(this, "client(url: " + getUrl() + ") failed to connect to server "
                        + getRemoteAddress() + " client-side timeout "
                        + getConnectTimeout() + "ms (elapsed: " + (System.currentTimeMillis() - start) + "ms) from netty client "
                        + NetUtils.getLocalHost() + " using dubbo version " );
            }
        } finally {
            if (!isConnected()) {
                //future.cancel(true);
            }
        }

    }

    public NettyClient(final URL url, final ChannelHandler handler) throws RemotingException {
        super(url, wrapChannelHandler(url, handler));
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }




    @Override
    public boolean hasAttribute(String key) {
        return false;
    }

    @Override
    public Object getAttribute(String key) {
        return null;
    }

    @Override
    public void setAttribute(String key, Object value) {

    }

    @Override
    public void removeAttribute(String key) {

    }

    @Override
    public void reconnect() throws RemotingException {

    }

    @Override
    protected com.qinpr.trf.remoting.Channel getChannel() {
        Channel c = channel;
        if (c == null || !c.isActive())
            return null;
        return NettyChannel.getOrAddChannel(c, getUrl(), this);
    }
}
