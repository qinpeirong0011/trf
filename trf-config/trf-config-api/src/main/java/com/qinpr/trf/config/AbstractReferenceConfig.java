package com.qinpr.trf.config;

/**
 * Created by qinpr on 2018/11/20.
 */
public abstract class AbstractReferenceConfig extends AbstractInterfaceConfig {

    private static final long serialVersionUID = -6439551709314891968L;

    protected Boolean check;

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
