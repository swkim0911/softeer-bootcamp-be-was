package util;

import exception.InvalidInputException;

import java.util.HashMap;
import java.util.Map;

public class QueryStringParser {

    public static Map<String, String> getParameters(String queryString) throws InvalidInputException {
        return parseQueryString(queryString);
    }
    private static Map<String, String> parseQueryString(String queryString) throws InvalidInputException {
        Map<String, String> keyValueMap = new HashMap<>();
        String[] parameters = queryString.split("&");
        for (String parameter : parameters) {
            String[] keyValue = parameter.split("=");
			if(keyValue.length != 2){
				throw new InvalidInputException("잘못된 입력입니다.");
			}
            String key = keyValue[0];
            String value = keyValue[1];
            keyValueMap.put(key, value);
        }
        return keyValueMap;
    }
}
