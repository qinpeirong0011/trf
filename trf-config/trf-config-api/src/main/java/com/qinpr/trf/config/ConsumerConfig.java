package com.qinpr.trf.config;

/**
 * Created by qinpr on 2018/11/21.
 */
public class ConsumerConfig extends AbstractReferenceConfig {

    private static final long serialVersionUID = 3650945074917795055L;

    // is default or not
    private Boolean isDefault;

    // networking framework client uses: netty, mina, etc.
    private String client;

    // consumer thread pool type: cached, fixed, limit, eager
    private String threadpool;

    // consumer threadpool core thread size
    private Integer corethreads;

    // consumer threadpool thread size
    private Integer threads;

    // consumer threadpool queue size
    private Integer queues;

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getThreadpool() {
        return threadpool;
    }

    public void setThreadpool(String threadpool) {
        this.threadpool = threadpool;
    }

    public Integer getCorethreads() {
        return corethreads;
    }

    public void setCorethreads(Integer corethreads) {
        this.corethreads = corethreads;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Integer getQueues() {
        return queues;
    }

    public void setQueues(Integer queues) {
        this.queues = queues;
    }
}
