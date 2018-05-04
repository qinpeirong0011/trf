import com.qinpr.trf.common.extension.ExtensionLoader;
import com.qinpr.trf.test.constants.SpiConstants;
import com.qinpr.trf.test.spi2.Dir2Extension;

/**
 * Created by qinpr on 18/5/4.
 */
public class Dir2ExtensionTest {
    public static void main(String[] args) {
        ExtensionLoader<Dir2Extension> extensionLoader = ExtensionLoader.getExtensionLoader(Dir2Extension.class);
        Dir2Extension dir2Extension = extensionLoader.getAdaptiveExtension();

        String type = null;
        type = SpiConstants.DIR2_DEFAULT;
//        type = SpiConstants.DIR2_FIRST;

        System.out.println(dir2Extension.sayHello("qinppr", type));
    }
}
