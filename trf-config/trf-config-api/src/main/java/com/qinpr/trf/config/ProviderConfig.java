package com.qinpr.trf.config;

/**
 * Created by qinpr on 18/5/4.
 */
public class ProviderConfig extends AbstractServiceConfig {

    // thread pool size (fixed size)
    private Integer threads;

    private String host;

    private Integer port;

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
