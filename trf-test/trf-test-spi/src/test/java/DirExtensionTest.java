import com.qinpr.trf.common.URL;
import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.test.spi.DirExtension;

import java.util.HashMap;

/**
 * Created by qinpr on 18/5/3.
 */
public class DirExtensionTest {
    public static void main(String[] args) {
        ExtensionLoader<DirExtension> extensionLoader = ExtensionLoader.getExtensionLoader(DirExtension.class);
        DirExtension dirExtension = extensionLoader.getAdaptiveExtension();

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("dirDefault", "dirFirst");

        URL url = new URL("dubbo", "127.0.0.1", 8089, parameters);
        System.out.println(dirExtension.sayHello(url, "qinpr"));
    }
}
