package com.qinpr.trf.rpc;

import java.util.Map;

/**
 * Created by qinpr on 18/4/27.
 */
public interface Result {

    Object getValue();

    Throwable getException();

    boolean hasException();

    Object recreate() throws Throwable;

    Object getResult();

    Map<String, String> getAttachments();

    String getAttachment(String key);

    String getAttachment(String key, String defaultValue);
}
