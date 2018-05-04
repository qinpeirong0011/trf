package com.qinpr.trf.common.compiler;

import com.qinpr.trf.common.extension.SPI;

/**
 * Created by qinpr on 18/5/3.
 */
@SPI("javassist")
public interface Compiler {

    Class<?> compile(String code, ClassLoader classLoader);
}
