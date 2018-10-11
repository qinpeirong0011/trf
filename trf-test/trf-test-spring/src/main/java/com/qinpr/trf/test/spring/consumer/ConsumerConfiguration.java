package com.qinpr.trf.test.spring.consumer;

import com.qinpr.trf.config.ApplicationConfig;
import com.qinpr.trf.config.ProtocolConfig;
import com.qinpr.trf.config.ProviderConfig;
import com.qinpr.trf.config.RegistryConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by qinpr on 2018/10/9.
 */
@ContextConfiguration(locations = { "classpath:trf-provider.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ConsumerConfiguration {

    @Autowired
    @Qualifier("test-application")
    private ApplicationConfig testApplicationConfig;

    @Autowired
    @Qualifier("test-registry")
    private RegistryConfig testRegistryConfig;

    @Autowired
    @Qualifier("test-protocol")
    private ProtocolConfig testProtocolConfig;

    @Autowired
    @Qualifier("test-provider")
    private ProviderConfig testProviderConfig;

    @Test
    public void init() {
        System.out.println(testApplicationConfig.getId());
        System.out.println(testRegistryConfig.getId());
        System.out.println(testProtocolConfig.getId());
        System.out.println(testProviderConfig.getId());
    }
}