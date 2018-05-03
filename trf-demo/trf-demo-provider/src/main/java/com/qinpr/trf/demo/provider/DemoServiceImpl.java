package com.qinpr.trf.demo.provider;

import com.qinpr.trf.demo.DemoService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qinpr on 18/4/26.
 */
public class DemoServiceImpl implements DemoService {
    public String remoteCall(String call) {
//        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + call + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
//        return "Hello " + call + ", response from provider: " + RpcContext.getContext().getLocalAddress();
        return null;
    }
}
