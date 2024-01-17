package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class HttpRequestHeaderUtils {
    private Map<String, String> requestHeaderMap;
    private HttpRequestHeaderUtils() {
    }

    public static HttpRequestHeaderUtils createHeaderUtils(String requestMessage) throws IOException {
        HttpRequestHeaderUtils httpRequestHeaderUtils = new HttpRequestHeaderUtils();
        httpRequestHeaderUtils.requestHeaderMap = HttpRequestHeaderParser.parse(requestMessage);
        return httpRequestHeaderUtils;
    }

    public String getRequestMethod() {
        return requestHeaderMap.get("Method");
    }
    public String getRequestUri() {
        return requestHeaderMap.get("RequestUri");
    }

    public String getQueryString() {
        return requestHeaderMap.get("QueryString");
    }
    public String getRequestVersion() {
        return requestHeaderMap.get("Version");
    }
    public String getRequestUserAgent() {
        return requestHeaderMap.get("User-Agent");
    }
    public String getHost() {
        return requestHeaderMap.get("Host");
    }
    public String getCookie() {
        return requestHeaderMap.get("Cookie");
    }
    public String getAccept() {
        return requestHeaderMap.get("Accept");
    }
}
