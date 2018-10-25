package com.qinpr.trf.rpc.protocol.trf;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.URL;
import com.qinpr.trf.rpc.Exporter;
import com.qinpr.trf.rpc.Invoker;
import com.qinpr.trf.rpc.RpcException;
import com.qinpr.trf.rpc.protocol.AbstractProtocol;
import org.omg.PortableInterceptor.INACTIVE;


/**
 * Created by qinpr on 2018/10/18.
 */
public class TrfProtocol extends AbstractProtocol {
    public int getDefaultPort() {
        return 0;
    }

    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        URL url = invoker.getUrl();
        String key = serviceKey(url);
        TrfExporter<T> exporter = new TrfExporter<T>(invoker, key, exporterMap);
        exporterMap.put(key, exporter);
        openServer(url);
        return exporter;
    }

    private void openServer(URL url) {
        String key = url.getAddress();
        boolean isServer = url.getParameter(Constants.IS_SERVER_KEY, true);
        if (isServer) {

        }
    }

    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return null;
    }

    public void destroy() {

    }

    protected static String serviceKey(URL url) {
        int port = url.getParameter(Constants.BIND_PORT_KEY, url.getPort());
        return serviceKey(port, url.getPath(), url.getParameter(Constants.VERSION_KEY),
                url.getParameter(Constants.GROUP_KEY));
    }
}
