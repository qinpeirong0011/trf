package com.qinpr.trf.common.threadPool;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.extension.Adaptive;
import com.qinpr.trf.common.extension.SPI;

import java.util.concurrent.Executor;

/**
 * Created by qinpr on 2019/1/5.
 */
@SPI("fixed")
public interface ThreadPool {

    @Adaptive({Constants.THREADPOOL_KEY})
    Executor getExecutor(URL url);
}
