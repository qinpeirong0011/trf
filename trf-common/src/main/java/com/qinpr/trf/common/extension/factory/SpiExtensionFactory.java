package com.qinpr.trf.common.extension.factory;

import com.qinpr.trf.common.extension.ExtensionFactory;
import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.common.extension.SPI;

/**com.qinpr.trf.common.extension.factory
 * Created by qinpr on 18/5/3.
 */
public class SpiExtensionFactory implements ExtensionFactory {
    public <T> T getExtension(Class<T> type, String name) {
        if (type.isInterface() && type.isAnnotationPresent(SPI.class)) {
            ExtensionLoader<T> loader = ExtensionLoader.getExtensionLoader(type);
            if (!loader.getSupportedExtensions().isEmpty()) {
                return loader.getAdaptiveExtension();
            }
        }

        return null;
    }
}
