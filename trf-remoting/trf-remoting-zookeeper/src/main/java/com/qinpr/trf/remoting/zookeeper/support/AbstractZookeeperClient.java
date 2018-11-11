package com.qinpr.trf.remoting.zookeeper.support;

import com.qinpr.trf.common.URL;
import com.qinpr.trf.remoting.zookeeper.StateListener;
import com.qinpr.trf.remoting.zookeeper.ZookeeperClient;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by qinpr on 2018/11/10.
 */
public abstract class AbstractZookeeperClient<TargetChildListener> implements ZookeeperClient {

    private final URL url;

    private final Set<StateListener> stateListeners = new CopyOnWriteArraySet<StateListener>();

    public AbstractZookeeperClient(URL url) {
        this.url = url;
    }

    public void addStateListener(StateListener listener) {
        stateListeners.add(listener);
    }

    public Set<StateListener> getSessionListeners() {
        return stateListeners;
    }

    public void create(String path, boolean ephemeral) {
        if (!ephemeral) {
            if (checkExists(path)) {
                return;
            }
        }
        int i = path.lastIndexOf('/');
        if (i > 0) {
            create(path.substring(0, i), false);
        }
        if (ephemeral) {
            createEphemeral(path);
        } else {
            createPersistent(path);
        }
    }

    public URL getUrl() {
        return url;
    }

    protected abstract void createPersistent(String path);

    protected abstract void createEphemeral(String path);

    protected abstract boolean checkExists(String path);
}
