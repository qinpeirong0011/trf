package com.qinpr.trf.config.support;

import java.lang.annotation.*;

/**
 * Created by qinpr on 18/4/27.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Parameter {
    String key() default "";

    boolean required() default false;

    boolean excluded() default false;

    boolean escaped() default false;

    boolean attribute() default false;

    boolean append() default false;
}
