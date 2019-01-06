/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qinpr.trf.remoting.exchange.support.header;


import com.qinpr.trf.common.Version;
import com.qinpr.trf.remoting.Channel;
import com.qinpr.trf.remoting.Client;
import com.qinpr.trf.remoting.Request;

import java.util.Collection;

final class HeartBeatTask implements Runnable {

    private ChannelProvider channelProvider;

    private int heartbeat;

    private int heartbeatTimeout;

    HeartBeatTask(ChannelProvider provider, int heartbeat, int heartbeatTimeout) {
        this.channelProvider = provider;
        this.heartbeat = heartbeat;
        this.heartbeatTimeout = heartbeatTimeout;
    }

    @Override
    public void run() {
        try {
            long now = System.currentTimeMillis();
            for (Channel channel : channelProvider.getChannels()) {
                if (channel.isClosed()) {
                    continue;
                }
                try {
                    Long lastRead = (Long) channel.getAttribute(
                            HeaderExchangeHandler.KEY_READ_TIMESTAMP);
                    Long lastWrite = (Long) channel.getAttribute(
                            HeaderExchangeHandler.KEY_WRITE_TIMESTAMP);
                    if ((lastRead != null && now - lastRead > heartbeat)
                            || (lastWrite != null && now - lastWrite > heartbeat)) {
                        Request req = new Request();
                        req.setVersion(Version.getProtocolVersion());
                        req.setTwoWay(true);
                        req.setEvent(Request.HEARTBEAT_EVENT);
                        channel.send(req);

                    }
                    if (lastRead != null && now - lastRead > heartbeatTimeout) {
//                        logger.warn("Close channel " + channel
//                                + ", because heartbeat read idle time out: " + heartbeatTimeout + "ms");
                        if (channel instanceof Client) {
                            try {
                                ((Client) channel).reconnect();
                            } catch (Exception e) {
                                //do nothing
                            }
                        } else {
                            channel.close();
                        }
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    interface ChannelProvider {
        Collection<Channel> getChannels();
    }

}

