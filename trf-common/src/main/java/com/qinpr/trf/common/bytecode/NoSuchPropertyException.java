package com.qinpr.trf.common.bytecode;

/**
 * Created by qinpr on 2018/10/19.
 */
public class NoSuchPropertyException extends RuntimeException {
    private static final long serialVersionUID = 3008409544548986235L;

    public NoSuchPropertyException() {
    }

    public NoSuchPropertyException(String message) {
        super(message);
    }
}
