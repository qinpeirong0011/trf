package com.qinpr.trf.common.utils;

/**
 * Created by qinpr on 18/4/27.
 */
public class Holder<T> {

    private volatile T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
