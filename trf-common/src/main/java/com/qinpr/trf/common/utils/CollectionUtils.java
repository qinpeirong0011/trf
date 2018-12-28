package com.qinpr.trf.common.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinpr on 18/5/3.
 */
public class CollectionUtils {
    public static Map<String, String> toStringMap(String... pairs) {
        Map<String, String> parameters = new HashMap<String, String>();
        if (pairs.length > 0) {
            if (pairs.length % 2 != 0) {
                throw new IllegalArgumentException("pairs must be even.");
            }
            for (int i = 0; i < pairs.length; i = i + 2) {
                parameters.put(pairs[i], pairs[i + 1]);
            }
        }
        return parameters;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }
}
