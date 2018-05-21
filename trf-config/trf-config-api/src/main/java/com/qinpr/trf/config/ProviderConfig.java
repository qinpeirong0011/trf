package com.qinpr.trf.config;

/**
 * Created by qinpr on 18/5/4.
 */
public class ProviderConfig extends AbstractServiceConfig {

    // delay service exporting
    protected Integer delay;

    @Override
    public Integer getDelay() {
        return delay;
    }

    @Override
    public void setDelay(Integer delay) {
        this.delay = delay;
    }
}
