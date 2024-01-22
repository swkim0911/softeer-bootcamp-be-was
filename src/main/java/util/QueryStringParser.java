package util;

import java.util.HashMap;
import java.util.Map;

public class QueryStringParser {

    public static Map<String, String> getParameters(String queryString) {
        return parseQueryString(queryString);
    }
    private static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> keyValueMap = new HashMap<>();
        String[] parameters = queryString.split("&");
        for (String parameter : parameters) {
            String[] keyValue = parameter.split("=");
            String key = keyValue[0];
            String value = keyValue[1];
            keyValueMap.put(key, value);
        }
        return keyValueMap;
    }
}
