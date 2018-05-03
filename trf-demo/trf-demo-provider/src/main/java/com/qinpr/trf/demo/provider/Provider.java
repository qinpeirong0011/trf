package com.qinpr.trf.demo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;

/**
 * Created by qinpr on 18/4/26.
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/trf-demo-provider.xml"});
        context.start();
        System.in.read();
//        File file = new File("/Users/qinpr/Documents/space/tech/trf/trf-demo/trf-demo-provider/src/main/resources/META-INF.spring/trf-demo-provider.xml");
//        System.out.println(file.exists());
    }
}
