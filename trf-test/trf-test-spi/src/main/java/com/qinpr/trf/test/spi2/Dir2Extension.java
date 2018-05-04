package com.qinpr.trf.test.spi2;


import com.qinpr.trf.common.extension.SPI;

/**
 * Created by qinpr on 18/5/2.
 */
@SPI("dir2Default")
public interface Dir2Extension {

    String sayHello(String name, String type);
}
