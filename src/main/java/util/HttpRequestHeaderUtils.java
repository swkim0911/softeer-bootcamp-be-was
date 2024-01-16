package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class HttpRequestHeaderUtils {
    private Map<String, String> requestHeaderMap;
    private HttpRequestHeaderUtils() {
    }

    public static HttpRequestHeaderUtils createHeaderUtils(InputStream in) throws IOException {
        HttpRequestHeaderUtils httpRequestHeaderUtils = new HttpRequestHeaderUtils();
        httpRequestHeaderUtils.requestHeaderMap = HttpRequestHeaderParser.parse(in);
        return httpRequestHeaderUtils;
    }

    public String getRequestMethod() {
        return requestHeaderMap.get("Method");
    }
    public String getRequestUri() {
        return requestHeaderMap.get("RequestUri");
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
