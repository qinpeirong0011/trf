package com.qinpr.trf.remoting.exchange;

/**
 * Created by qinpr on 2018/10/25.
 */
public interface ResponseCallback {

    void done(Object response);

    void caught(Throwable exception);
}
