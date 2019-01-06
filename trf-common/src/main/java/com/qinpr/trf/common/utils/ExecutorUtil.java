package com.qinpr.trf.common.utils;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;

/**
 * Created by qinpr on 2019/1/3.
 */
public class ExecutorUtil {
    public static URL setThreadName(URL url, String defaultName) {
        String name = url.getParameter(Constants.THREAD_NAME_KEY, defaultName);
        name = name + "-" + url.getAddress();
        url = url.addParameter(Constants.THREAD_NAME_KEY, name);
        return url;
    }
}
