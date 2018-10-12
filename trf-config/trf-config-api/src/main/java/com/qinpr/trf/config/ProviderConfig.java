package com.qinpr.trf.config;

/**
 * Created by qinpr on 18/5/4.
 */
public class ProviderConfig extends AbstractServiceConfig {

    // thread pool size (fixed size)
    private Integer threads;

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }
}
