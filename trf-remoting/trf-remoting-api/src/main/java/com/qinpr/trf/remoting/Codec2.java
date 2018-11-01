package com.qinpr.trf.remoting;

import com.qinpr.trf.common.Constants;
import com.qinpr.trf.common.extension.Adaptive;
import com.qinpr.trf.common.extension.SPI;
import com.qinpr.trf.remoting.buffer.ChannelBuffer;

import java.io.IOException;

/**
 * Created by qinpr on 2018/11/1.
 */
@SPI
public interface Codec2 {
    @Adaptive({Constants.CODEC_KEY})
    void encode(Channel channel, ChannelBuffer buffer, Object message) throws IOException;

    @Adaptive({Constants.CODEC_KEY})
    Object decode(Channel channel, ChannelBuffer buffer) throws IOException;

    enum DecodeResult {
        NEED_MORE_INPUT, SKIP_SOME_INPUT
    }
}
