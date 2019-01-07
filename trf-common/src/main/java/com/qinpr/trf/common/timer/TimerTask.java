package com.qinpr.trf.common.timer;

/**
 * Created by qinpr on 2019/1/6.
 */
public interface TimerTask {

    void run(Timeout timeout) throws Exception;
}
