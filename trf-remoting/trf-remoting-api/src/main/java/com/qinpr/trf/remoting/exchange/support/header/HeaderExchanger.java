package com.qinpr.trf.remoting.exchange.support.header;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.remoting.RemotingException;
import com.qinpr.trf.remoting.Transporters;
import com.qinpr.trf.remoting.exchange.ExchangeClient;
import com.qinpr.trf.remoting.exchange.ExchangeHandler;
import com.qinpr.trf.remoting.exchange.ExchangeServer;
import com.qinpr.trf.remoting.exchange.Exchanger;
import com.qinpr.trf.remoting.transport.DecodeHandler;

/**
 * Created by qinpr on 2018/10/26.
 */
public class HeaderExchanger implements Exchanger {
    public ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException {
        return new HeaderExchangeServer(Transporters.bind(url, new DecodeHandler(new HeaderExchangeHandler(handler))));
    }

    public ExchangeClient connect(URL url, ExchangeHandler handler) throws RemotingException {
        return new HeaderExchangeClient(Transporters.connect(url, new DecodeHandler(new HeaderExchangeHandler(handler))), true);
    }
}
