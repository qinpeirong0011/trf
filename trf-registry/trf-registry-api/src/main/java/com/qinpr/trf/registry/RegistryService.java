package com.qinpr.trf.registry;

import com.qinpr.trf.common.URL;

import java.util.List;

/**
 * Created by qinpr on 2018/10/18.
 */
public interface RegistryService {

    void register(URL url);

    void subscribe(URL url, NotifyListener listener);

    void unregister(URL url);

    List<URL> lookup(URL url);
}
