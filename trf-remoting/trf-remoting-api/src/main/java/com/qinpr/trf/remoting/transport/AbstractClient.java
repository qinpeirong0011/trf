package com.qinpr.trf.remoting.transport;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.common.utils.ExecutorUtil;
import com.qinpr.trf.common.utils.NamedThreadFactory;
import com.qinpr.trf.common.utils.NetUtils;
import com.qinpr.trf.remoting.ChannelHandler;
import com.qinpr.trf.remoting.Client;
import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.transport.dispatcher.ChannelHandlers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by qinpr on 2019/1/3.
 */
public abstract class AbstractClient extends AbstractEndpoint implements Client {

    protected static final String CLIENT_THREAD_POOL_NAME = "TrfClientHandler";

    private final boolean send_reconnect;

    private final long shutdown_timeout;

    private final int reconnect_warning_period;

    private final AtomicInteger reconnect_count = new AtomicInteger(0);

    private final AtomicBoolean reconnect_error_log_flag = new AtomicBoolean(false);

    private volatile ScheduledFuture<?> reconnectExecutorFuture = null;

    private static final ScheduledThreadPoolExecutor reconnectExecutorService = new ScheduledThreadPoolExecutor(2, new NamedThreadFactory("TrfClientReconnectTimer", true));

    protected volatile ExecutorService executor;

    private final Lock connectLock = new ReentrantLock();

    private long lastConnectedTime = System.currentTimeMillis();

    protected abstract void doOpen() throws Throwable;

    protected abstract void doConnect() throws Throwable;

    public AbstractClient(URL url, ChannelHandler handler) throws RemotingException {
        super(url, handler);

        send_reconnect = url.getParameter(Constants.SEND_RECONNECT_KEY, false);

        shutdown_timeout = url.getParameter(Constants.SHUTDOWN_TIMEOUT_KEY, Constants.DEFAULT_SHUTDOWN_TIMEOUT);

        // The default reconnection interval is 2s, 1800 means warning interval is 1 hour.
        reconnect_warning_period = url.getParameter("reconnect.waring.period", 1800);

        try {
            doOpen();
        } catch (Throwable t) {
            close();
            throw new RemotingException(url.toInetSocketAddress(), null,
                    "Failed to start " + getClass().getSimpleName() + " " + NetUtils.getLocalAddress()
                            + " connect to the server " + getRemoteAddress() + ", cause: " + t.getMessage(), t);
        }
        try {
            // connect.
            connect();
//            if (logger.isInfoEnabled()) {
//                logger.info("Start " + getClass().getSimpleName() + " " + NetUtils.getLocalAddress() + " connect to the server " + getRemoteAddress());
//            }
        } catch (RemotingException t) {
            if (url.getParameter(Constants.CHECK_KEY, true)) {
                close();
                throw t;
            } else {
//                logger.warn("Failed to start " + getClass().getSimpleName() + " " + NetUtils.getLocalAddress()
//                        + " connect to the server " + getRemoteAddress() + " (check == false, ignore and retry later!), cause: " + t.getMessage(), t);
            }
        } catch (Throwable t) {
            close();
            throw new RemotingException(url.toInetSocketAddress(), null,
                    "Failed to start " + getClass().getSimpleName() + " " + NetUtils.getLocalAddress()
                            + " connect to the server " + getRemoteAddress() + ", cause: " + t.getMessage(), t);
        }

//        executor = (ExecutorService) ExtensionLoader.getExtensionLoader(DataStore.class)
//                .getDefaultExtension().get(Constants.CONSUMER_SIDE, Integer.toString(url.getPort()));
//        ExtensionLoader.getExtensionLoader(DataStore.class)
//                .getDefaultExtension().remove(Constants.CONSUMER_SIDE, Integer.toString(url.getPort()));
    }

    protected void connect() throws RemotingException {
        connectLock.lock();
        try {
            if (isConnected()) {
                return;
            }
            initConnectStatusCheckCommand();
            doConnect();
            if (!isConnected()) {
                throw new RemotingException(this, "Failed connect to server " + getRemoteAddress() + " from " + getClass().getSimpleName() + " "
                        + NetUtils.getLocalHost() + " using dubbo version "
                        + ", cause: Connect wait timeout: " + getConnectTimeout() + "ms.");
            } else {
//                if (logger.isInfoEnabled()) {
//                    logger.info("Successed connect to server " + getRemoteAddress() + " from " + getClass().getSimpleName() + " "
//                            + NetUtils.getLocalHost() + " using dubbo version "
//                            + ", channel is " + this.getChannel());
//                }
            }
            reconnect_count.set(0);
            reconnect_error_log_flag.set(false);
        } catch (RemotingException e) {
            throw e;
        } catch (Throwable e) {
            e.printStackTrace();
//            throw new RemotingException(this, "Failed connect to server " + getRemoteAddress() + " from " + getClass().getSimpleName() + " "
//                    + NetUtils.getLocalHost() + " using dubbo version , cause: "
//                    + e.getMessage(), e);
        } finally {
            connectLock.unlock();
        }
    }

    private synchronized void initConnectStatusCheckCommand() {
        //reconnect=false to close reconnect
        int reconnect = getReconnectParam(getUrl());
        if (reconnect > 0 && (reconnectExecutorFuture == null || reconnectExecutorFuture.isCancelled())) {
            Runnable connectStatusCheckCommand = new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!isConnected()) {
                            connect();
                        } else {
                            lastConnectedTime = System.currentTimeMillis();
                        }
                    } catch (Throwable t) {
                        String errorMsg = "client reconnect to " + getUrl().getAddress() + " find error . url: " + getUrl();
                        // wait registry sync provider list
                        if (System.currentTimeMillis() - lastConnectedTime > shutdown_timeout) {
                            if (!reconnect_error_log_flag.get()) {
                                reconnect_error_log_flag.set(true);
//                                logger.error(errorMsg, t);
                                return;
                            }
                        }
                        if (reconnect_count.getAndIncrement() % reconnect_warning_period == 0) {
//                            logger.warn(errorMsg, t);
                        }
                    }
                }
            };
            reconnectExecutorFuture = reconnectExecutorService.scheduleWithFixedDelay(connectStatusCheckCommand, reconnect, reconnect, TimeUnit.MILLISECONDS);
        }
    }

    private static int getReconnectParam(URL url) {
        int reconnect;
        String param = url.getParameter(Constants.RECONNECT_KEY);
        if (param == null || param.length() == 0 || "true".equalsIgnoreCase(param)) {
            reconnect = Constants.DEFAULT_RECONNECT_PERIOD;
        } else if ("false".equalsIgnoreCase(param)) {
            reconnect = 0;
        } else {
            try {
                reconnect = Integer.parseInt(param);
            } catch (Exception e) {
                throw new IllegalArgumentException("reconnect param must be nonnegative integer or false/true. input is:" + param);
            }
            if (reconnect < 0) {
                throw new IllegalArgumentException("reconnect param must be nonnegative integer or false/true. input is:" + param);
            }
        }
        return reconnect;
    }

    protected static ChannelHandler wrapChannelHandler(URL url, ChannelHandler handler) {
        url = ExecutorUtil.setThreadName(url, CLIENT_THREAD_POOL_NAME);
        url = url.addParameterIfAbsent(Constants.THREADPOOL_KEY, Constants.DEFAULT_CLIENT_THREADPOOL);
        return ChannelHandlers.wrap(handler, url);
    }


}
