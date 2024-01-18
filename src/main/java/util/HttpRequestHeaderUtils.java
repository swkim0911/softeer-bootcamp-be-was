package util;

import java.io.IOException;
import java.util.Map;

public class HttpRequestHeaderUtils {
    private Map<String, String> requestHeaderMap;

    private Map<String, String> requestLineMap;
    private HttpRequestHeaderUtils() {
    }

    public static HttpRequestHeaderUtils createHeaderUtils(String requestMessage) throws IOException {
        HttpRequestHeaderUtils httpRequestHeaderUtils = new HttpRequestHeaderUtils();
        httpRequestHeaderUtils.requestLineMap = HttpRequestHeaderParser.parseRequestLine(requestMessage);
        httpRequestHeaderUtils.requestHeaderMap = HttpRequestHeaderParser.parseHeaders(requestMessage);
        return httpRequestHeaderUtils;
    }

    public String getRequestMethod() {
        return requestLineMap.get("Method");
    }
    public String getRequestUri() {
        return requestLineMap.get("RequestUri");
    }
    public String getQueryString() {
        return  requestLineMap.get("QueryString");
    }
    public String getRequestVersion() {
        return requestLineMap.get("Version");
    }


    public Map<String,String> getRequestHeaders() {
        return requestHeaderMap;
    }

//    public String getRequestUserAgent() {
//        return requestHeaderMap.get("User-Agent");
//    }
//    public String getHost() {
//        return requestHeaderMap.get("Host");
//    }
//    public String getCookie() {
//        return requestHeaderMap.get("Cookie");
//    }
//    public String getAccept() {
//        return requestHeaderMap.get("Accept");
//    }
}
