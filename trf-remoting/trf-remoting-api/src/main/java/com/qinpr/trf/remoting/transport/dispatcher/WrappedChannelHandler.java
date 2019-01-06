package com.qinpr.trf.remoting.transport.dispatcher;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.common.threadPool.ThreadPool;
import com.qinpr.trf.common.utils.NamedThreadFactory;
import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.transport.ChannelHandlerDelegate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qinpr on 2019/1/5.
 */
public class WrappedChannelHandler implements ChannelHandlerDelegate {

    protected static final ExecutorService SHARED_EXECUTOR = Executors.newCachedThreadPool(new NamedThreadFactory("DubboSharedHandler", true));

    protected final ExecutorService executor;

    protected final ChannelHandler handler;

    protected final URL url;

    public WrappedChannelHandler(ChannelHandler handler, URL url) {
        this.handler = handler;
        this.url = url;
        executor = (ExecutorService) ExtensionLoader.getExtensionLoader(ThreadPool.class).getAdaptiveExtension().getExecutor(url);

        String componentKey = Constants.EXECUTOR_SERVICE_COMPONENT_KEY;
        if (Constants.CONSUMER_SIDE.equalsIgnoreCase(url.getParameter(Constants.SIDE_KEY))) {
            componentKey = Constants.CONSUMER_SIDE;
        }
//        DataStore dataStore = ExtensionLoader.getExtensionLoader(DataStore.class).getDefaultExtension();
//        dataStore.put(componentKey, Integer.toString(url.getPort()), executor);
    }


    @Override
    public ChannelHandler getHandler() {
        return null;
    }

    @Override
    public void connected(Channel channel) throws RemotingException {

    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {

    }

    @Override
    public void sent(Channel channel, Object message) throws RemotingException {

    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {

    }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException {

    }
}
