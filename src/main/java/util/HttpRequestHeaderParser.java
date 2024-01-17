package util;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeaderParser {

    public static Map<String, String> parse(String requestMessage){
        Map<String, String> requestHeaderMap = new HashMap<>();
        String[] lines = requestMessage.split("\r\n");
        parseRequestLine(lines[0], requestHeaderMap);
        for (int i = 1; i < lines.length; i++) {
            parseHeader(lines[i], requestHeaderMap);
        }
        return requestHeaderMap;
    }
    private static void parseHeader(String requestHeader, Map<String, String> requestHeaderMap){
        String[] header = requestHeader.split(":", 2);
        if (header.length > 1) {
            String field = header[0];
            String value = header[1].stripLeading();
            requestHeaderMap.put(field, value);
        }
    }
    private static void parseRequestLine(String requestLine, Map<String, String> requestHeaderMap){
        String[] fields = requestLine.split(" ");
        requestHeaderMap.put("Method", fields[0]);
        requestHeaderMap.put("RequestUri", fields[1]);
        requestHeaderMap.put("Version", fields[2]);
    }
}
