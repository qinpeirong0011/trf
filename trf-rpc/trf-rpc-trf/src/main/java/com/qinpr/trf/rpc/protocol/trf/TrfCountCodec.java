package com.qinpr.trf.rpc.protocol.trf;

import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.Codec2;
import com.qinpr.trf.remoting.buffer.ChannelBuffer;

import java.io.IOException;

/**
 * Created by qinpr on 2018/11/1.
 */
public class TrfCountCodec implements Codec2 {
    public void encode(Channel channel, ChannelBuffer buffer, Object message) throws IOException {

    }

    public Object decode(Channel channel, ChannelBuffer buffer) throws IOException {
        return null;
    }
}
