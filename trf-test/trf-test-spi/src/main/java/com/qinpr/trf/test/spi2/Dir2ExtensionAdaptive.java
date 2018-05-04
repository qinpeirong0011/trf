package com.qinpr.trf.test.spi2;


import com.qinpr.trf.common.extension.Adaptive;
import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.test.constants.SpiConstants;

/**
 * Created by qinpr on 18/5/2.
 */
@Adaptive
public class Dir2ExtensionAdaptive implements Dir2Extension {

    public String sayHello(String name, String type) {
        ExtensionLoader<Dir2Extension> extensionLoader = ExtensionLoader.getExtensionLoader(Dir2Extension.class);
        Dir2Extension dir2Extension = extensionLoader.getDefaultExtension();

        if (type != null && SpiConstants.DIR2_FIRST.equalsIgnoreCase(type.trim())) {
            dir2Extension = extensionLoader.getExtension(SpiConstants.DIR2_FIRST);
        }

        return dir2Extension.sayHello(name, type);
    }
}
