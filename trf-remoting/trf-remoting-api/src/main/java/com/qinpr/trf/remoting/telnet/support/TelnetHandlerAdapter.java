package com.qinpr.trf.remoting.telnet.support;

import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.telnet.TelnetHandler;
import com.qinpr.trf.remoting.transport.ChannelHandlerAdapter;

/**
 * Created by qinpr on 2018/10/26.
 */
public class TelnetHandlerAdapter extends ChannelHandlerAdapter implements TelnetHandler {
    public String telnet(Channel channel, String message) throws RemotingException {
        return null;
    }
}
