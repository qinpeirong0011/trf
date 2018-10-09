package com.qinpr.trf.config.spring.context;

import com.qinpr.trf.config.RegistryConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by qinpr on 2018/9/29.
 */

@ContextConfiguration(locations = { "classpath:trf-provider.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ConsumerConfiguration {

    @Autowired
    @Qualifier("test")
    private RegistryConfig test;

    @Test
    public void init() {
        System.out.println(test.getAddress());
        System.out.println(test.getProtocol());
        System.out.println(test.getGroup());
    }
}
