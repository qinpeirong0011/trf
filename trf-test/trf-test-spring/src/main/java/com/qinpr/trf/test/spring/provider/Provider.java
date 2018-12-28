package com.qinpr.trf.test.spring.provider;

import com.qinpr.trf.config.ApplicationConfig;
import com.qinpr.trf.config.ProtocolConfig;
import com.qinpr.trf.config.ProviderConfig;
import com.qinpr.trf.config.RegistryConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by qinpr on 2018/10/9.
 */
//@ContextConfiguration(locations = { "classpath:trf-provider.xml" })
//@RunWith(SpringJUnit4ClassRunner.class)
public class Provider {

//    @Autowired
//    @Qualifier("test-application")
//    private ApplicationConfig testApplicationConfig;
//
//    @Autowired
//    @Qualifier("test-registry")
//    private RegistryConfig testRegistryConfig;
//
//    @Autowired
//    @Qualifier("test-protocol")
//    private ProtocolConfig testProtocolConfig;
//
//    @Autowired
//    @Qualifier("test-provider")
//    private ProviderConfig testProviderConfig;
//
//
//    @Test
//    public void init() throws IOException {
//        System.out.println(testApplicationConfig.getName());
//        System.out.println(testRegistryConfig.getAddress());
//        System.out.println(testProtocolConfig.getName());
//        System.out.println(testProviderConfig.getThreads());
//        System.in.read();
//    }

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"trf-provider.xml"});
        context.start();
        System.in.read();
    }
}