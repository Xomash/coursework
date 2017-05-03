package com.khomandiak.courseWork;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by o.khomandiak on 03.05.2017.
 */
public class MyMapDecoder implements MapDecoder {
    /**
     * Decodes the given String into a Map. The String format is a URL parameter string (key=value&key=value&...).
     * Empty keys and values are allowed.
     *
     * @param s the String to decode
     * @return a    Map representing the given String. If the given String is empty, an empty Map is returned. If the given
     * String is null, null is returned.
     * @throws IllegalArgumentException if the given String has an invalid format
     */
    public Map<String, String> decode(String s) {
        Map<String, String> map = new HashMap<String, String>();
        if (s == null)
            return null;
        if (!s.equals("")) {
            if (!s.contains("=")) {
                throw new IllegalArgumentException("No '=' sign!");
            }
            if (s.endsWith("&")) {
                throw new IllegalArgumentException("Illegal end sign!");
            }
            String[] splitted = s.split("&");
            for (int i = 0; i < splitted.length; i++) {
                if (!splitted[i].equals("")) {
                    if (!splitted[i].contains("=")) {
                        throw new IllegalArgumentException("No '=' sign where it was expected!");
                    }
                    String[] splittedValue = splitted[i].split("=");
                    String key = splittedValue[0];
                    String value;
                    if (splittedValue.length > 1)
                        value = splittedValue[splittedValue.length - 1];
                    else
                        value = "";
                    map.put(key, value);
                }
            }
        }
        return map;
    }

    public static void main(String[] args) {
        String sample = "one=";
        MapDecoder mapDecoder = new MyMapDecoder();
        System.out.println(mapDecoder.decode(sample));
    }
}
