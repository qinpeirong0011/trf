package com.qinpr.trf.remoting;

import com.qinpr.trf.common.Resetable;

/**
 * Created by qinpr on 2018/10/26.
 */
public interface Client extends Endpoint, Channel, Resetable {
    void reconnect() throws RemotingException;
}
