package com.qinpr.trf.remoting.zookeeper;

/**
 * Created by qinpr on 2018/11/10.
 */
public interface StateListener {
    int DISCONNECTED = 0;

    int CONNECTED = 1;

    int RECONNECTED = 2;

    void stateChanged(int connected);
}
