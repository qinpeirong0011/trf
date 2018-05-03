package com.alibaba.dubbo.rpc.spi2;

import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.constants.SpiConstants;

/**
 * Created by qinpr on 18/5/2.
 */
@Adaptive
public class Dir2ExtensionAdaptive implements Dir2Extension {
    @Override
    public String sayHello(String name, String type) {
        ExtensionLoader<Dir2Extension> extensionLoader = ExtensionLoader.getExtensionLoader(Dir2Extension.class);
        Dir2Extension dir2Extension = extensionLoader.getDefaultExtension();

        if (type != null && SpiConstants.DIR2_FIRST.equalsIgnoreCase(type.trim())) {
            dir2Extension = extensionLoader.getExtension(SpiConstants.DIR2_FIRST);
        }

        return dir2Extension.sayHello(name, type);
    }
}
