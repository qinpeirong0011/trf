package com.qinpr.trf.common.threadlocal;

import com.qinpr.trf.common.utils.NamedThreadFactory;

/**
 * Created by qinpr on 2019/1/5.
 */
public class NamedInternalThreadFactory extends NamedThreadFactory {

    public NamedInternalThreadFactory() {
        super();
    }

    public NamedInternalThreadFactory(String prefix) {
        super(prefix, false);
    }

    public NamedInternalThreadFactory(String prefix, boolean daemon) {
        super(prefix, daemon);
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String name = mPrefix + mThreadNum.getAndIncrement();
        InternalThread ret = new InternalThread(mGroup, runnable, name, 0);
        ret.setDaemon(mDaemon);
        return ret;
    }
}
