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
package com.qinpr.trf.common.utils;


import com.qinpr.trf.common.io.UnsafeStringWriter;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtils
 */

public final class StringUtils {

    private static final Pattern KVP_PATTERN = Pattern.compile("([_.a-zA-Z0-9][-_.a-zA-Z0-9]*)[=](.*)"); //key value pair pattern.


    /**
     * @param e
     * @return string
     */
    public static String toString(Throwable e) {
        UnsafeStringWriter w = new UnsafeStringWriter();
        PrintWriter p = new PrintWriter(w);
        p.print(e.getClass().getName());
        if (e.getMessage() != null) {
            p.print(": " + e.getMessage());
        }
        p.println();
        try {
            e.printStackTrace(p);
            return w.toString();
        } finally {
            p.close();
        }
    }


    /**
     * parse query string to Parameters.
     *
     * @param qs query string.
     * @return Parameters instance.
     */
    public static Map<String, String> parseQueryString(String qs) {
        if (qs == null || qs.length() == 0)
            return new HashMap<String, String>();
        return parseKeyValuePair(qs, "\\&");
    }

    /**
     * parse key-value pair.
     *
     * @param str           string.
     * @param itemSeparator item separator.
     * @return key-value map;
     */
    private static Map<String, String> parseKeyValuePair(String str, String itemSeparator) {
        String[] tmp = str.split(itemSeparator);
        Map<String, String> map = new HashMap<String, String>(tmp.length);
        for (int i = 0; i < tmp.length; i++) {
            Matcher matcher = KVP_PATTERN.matcher(tmp[i]);
            if (matcher.matches() == false)
                continue;
            map.put(matcher.group(1), matcher.group(2));
        }
        return map;
    }




}