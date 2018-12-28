package com.qinpr.trf.rpc;

import java.util.Map;

/**
 * Created by qinpr on 2018/10/22.
 */
public class RpcResult extends AbstractResult {

    public RpcResult(Object result) {
        this.result = result;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Throwable getException() {
        return null;
    }

    @Override
    public boolean hasException() {
        return false;
    }

    @Override
    public Object recreate() throws Throwable {
        return result;
    }

    @Override
    public Object getResult() {
        return result;
    }

    @Override
    public Map<String, String> getAttachments() {
        return null;
    }

    @Override
    public String getAttachment(String key) {
        return null;
    }

    @Override
    public String getAttachment(String key, String defaultValue) {
        return null;
    }
}
