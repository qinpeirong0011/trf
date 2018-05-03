package com.qinpr.trf.common;

import java.lang.annotation.*;

/**
 * Created by qinpr on 18/4/28.
 */
@Deprecated
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Extension {
    /**
     * @deprecated
     */
    @Deprecated
    String value() default "";
}
