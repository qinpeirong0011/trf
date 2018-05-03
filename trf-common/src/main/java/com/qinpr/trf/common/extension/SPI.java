package com.qinpr.trf.common.extension;

import java.lang.annotation.*;

/**
 * Created by qinpr on 18/4/27.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {
    /**
     * default extension name
     */
    String value() default "";
}
