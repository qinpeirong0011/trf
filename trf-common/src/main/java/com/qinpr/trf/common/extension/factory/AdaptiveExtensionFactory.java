package com.qinpr.trf.common.extension.factory;

import com.qinpr.trf.common.extension.Adaptive;
import com.qinpr.trf.common.extension.ExtensionFactory;
import com.qinpr.trf.common.extension.ExtensionLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by qinpr on 18/5/3.
 */
@Adaptive
public class AdaptiveExtensionFactory implements ExtensionFactory {
    private final List<ExtensionFactory> factories;

    public AdaptiveExtensionFactory() {
        ExtensionLoader<ExtensionFactory> loader = ExtensionLoader.getExtensionLoader(ExtensionFactory.class);
        List<ExtensionFactory> list = new ArrayList<ExtensionFactory>();

        for (String name : loader.getSupportedExtensions()) {
            list.add(loader.getExtension(name));
        }

        factories = Collections.unmodifiableList(list);
    }

    public <T> T getExtension(Class<T> type, String name) {
        for (ExtensionFactory factory : factories) {
            T extension = factory.getExtension(type, name);
            if (extension != null) {
                return extension;
            }
        }
        return null;
    }
}
