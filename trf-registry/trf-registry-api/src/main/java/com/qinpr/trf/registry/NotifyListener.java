package com.qinpr.trf.registry;

import com.qinpr.trf.common.URL;

import java.util.List;

/**
 * Created by qinpr on 2018/11/21.
 */
public interface NotifyListener {

    void notify(List<URL> urls);
}
