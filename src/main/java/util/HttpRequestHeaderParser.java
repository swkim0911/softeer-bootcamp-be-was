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
        String method = fields[0];
        String uri = fields[1];
        String version = fields[2];
        requestHeaderMap.put("Method", method);
        requestHeaderMap.put("Version", version);
        putUriField(uri, requestHeaderMap);
    }

    private static void putUriField(String uri, Map<String, String> requestHeaderMap) {
        String requestUri;
        String queryString = "None";
        if (uri.contains("?")) { // 쿼리 스트링이 있는 경우
            int index = uri.indexOf("?"); // 쿼리 스트링에 사용자가 ?을 입력했을 수도 있으니까
            requestUri = uri.substring(0, index);
            queryString = uri.substring(index + 1);
        }else{
            requestUri = uri;
        }
        requestHeaderMap.put("RequestUri", requestUri);
        requestHeaderMap.put("QueryString", queryString);
    }
}
