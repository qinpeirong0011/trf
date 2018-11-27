package com.qinpr.trf.registry.support;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.utils.ConcurrentHashSet;
import com.qinpr.trf.common.utils.NamedThreadFactory;
import com.qinpr.trf.registry.NotifyListener;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by qinpr on 2018/10/25.
 */
public abstract class FailbackRegistry extends AbstractRegistry {
    private final ScheduledExecutorService retryExecutor = Executors.newScheduledThreadPool(1, new NamedThreadFactory("DubboRegistryFailedRetryTimer", true));
    private final ScheduledFuture<?> retryFuture;
    private final int retryPeriod;

    private final Set<URL> failedRegistered = new ConcurrentHashSet<URL>();

    private final Set<URL> failedUnregistered = new ConcurrentHashSet<URL>();

    private final ConcurrentMap<URL, Set<NotifyListener>> failedSubscribed = new ConcurrentHashMap<URL, Set<NotifyListener>>();

    private final ConcurrentMap<URL, Set<NotifyListener>> failedUnsubscribed = new ConcurrentHashMap<URL, Set<NotifyListener>>();

    private final ConcurrentMap<URL, Map<NotifyListener, List<URL>>> failedNotified = new ConcurrentHashMap<URL, Map<NotifyListener, List<URL>>>();

    public FailbackRegistry(URL url) {
        super(url);
        this.retryPeriod = url.getParameter(Constants.REGISTRY_RETRY_PERIOD_KEY, Constants.DEFAULT_REGISTRY_RETRY_PERIOD);
        this.retryFuture = retryExecutor.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                // Check and connect to the registry
                try {
//                    retry();
                } catch (Throwable t) { // Defensive fault tolerance
                }
            }
        }, retryPeriod, retryPeriod, TimeUnit.MILLISECONDS);
    }

    public void register(URL url) {
        super.register(url);
        failedRegistered.remove(url);
        failedUnregistered.remove(url);
        try {
            doRegister(url);
        } catch (Exception e) {
            failedRegistered.add(url);
        }
    }

    protected abstract void doRegister(URL url);

    @Override
    public void subscribe(URL url, NotifyListener listener) {
       super.subscribe(url, listener);
       removeFailedSubscribed(url, listener);
       try {
           doSubscribe(url, listener);
       } catch (Exception e) {

       }
    }

    private void removeFailedSubscribed(URL url, NotifyListener listener) {
        Set<NotifyListener> listeners = failedSubscribed.get(url);
        if (listeners != null) {
            listeners.remove(listener);
        }
        listeners = failedUnsubscribed.get(url);
        if (listeners != null) {
            listeners.remove(listener);
        }
        Map<NotifyListener, List<URL>> notified = failedNotified.get(url);
        if (notified != null) {
            notified.remove(listener);
        }
    }

    protected abstract void doSubscribe(URL url, NotifyListener listener);
}
