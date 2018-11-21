package com.qinpr.trf.test.spring.consumer;

import com.qinpr.trf.config.ApplicationConfig;
import com.qinpr.trf.config.RegistryConfig;
import com.qinpr.trf.config.spring.ReferenceBean;
import com.qinpr.trf.demo.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by qinpr on 2018/11/20.
 */
//@ContextConfiguration(locations = { "classpath:trf-consumer.xml" })
//@RunWith(SpringJUnit4ClassRunner.class)
public class Consumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"trf-consumer.xml"});
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService"); // get remote service proxy
        while (true) {
            try {
                Thread.sleep(1000);
                String hello = demoService.remoteCall("world"); // call remote method
                System.out.println(hello); // get result
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

//    @Autowired
//    @Qualifier("test-application-c")
//    private ApplicationConfig testApplicationConfig;
//
//    @Autowired
//    @Qualifier("test-registry-c")
//    private RegistryConfig testRegistryConfig;
//
//    @Autowired
//    @Qualifier("demoService")
//    private ReferenceBean demoService;
//
//    @Test
//    public void init() {
//        System.out.println(demoService.getId());
//    }
}
