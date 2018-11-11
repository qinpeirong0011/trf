package com.qinpr.trf.registry.support;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.registry.Registry;
import com.qinpr.trf.registry.RegistryFactory;
import com.qinpr.trf.registry.RegistryService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by qinpr on 2018/10/24.
 */
public abstract class AbstractRegistryFactory implements RegistryFactory {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final Map<String, Registry> REGISTRIES = new ConcurrentHashMap<String, Registry>();

    public Registry getRegistry(URL url) {
        url = url.setPath(RegistryService.class.getName())
                .addParameter(Constants.INTERFACE_KEY, RegistryService.class.getName())
                .removeParameters(Constants.EXPORT_KEY, Constants.REFER_KEY);
        String key = url.toServiceStringWithoutResolving();
        LOCK.lock();
        try {
            Registry registry = REGISTRIES.get(key);
            if (registry != null) {
                return registry;
            }
            registry = createRegistry(url);
            if (registry == null) {
                throw new IllegalStateException("Can not create registry " + url);
            }
            REGISTRIES.put(key, registry);
            return registry;
        } finally {
            // Release the lock
            LOCK.unlock();
        }
    }

    protected abstract Registry createRegistry(URL url);
}
