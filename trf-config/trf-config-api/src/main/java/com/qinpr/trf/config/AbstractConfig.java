package com.qinpr.trf.config;

import com.qinpr.trf.config.support.Parameter;

import java.io.Serializable;

/**
 * Created by qinpr on 18/4/27.
 */
public abstract class AbstractConfig implements Serializable {
    private static final long serialVersionUID = -5137411157146193785L;

    protected String id;

    @Parameter(excluded = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
