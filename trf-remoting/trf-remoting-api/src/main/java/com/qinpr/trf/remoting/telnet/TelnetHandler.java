package com.qinpr.trf.remoting.telnet;

import com.qinpr.trf.common.extension.SPI;
import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.RemotingException;

/**
 * Created by qinpr on 2018/10/26.
 */
@SPI
public interface TelnetHandler {

    String telnet(Channel channel, String message) throws RemotingException;
}
