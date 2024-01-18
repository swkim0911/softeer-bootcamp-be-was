package Http;

import java.util.Map;

public class HttpRequest {

    private String method;
    private String url;
    private String uri;
    private String queryString;
    private String version;
    private Map<String, String> headerFields;

    public HttpRequest(String method, String url, String uri, String queryString, String version, Map<String, String> headerFields) {
        this.method = method;
        this.url = url;
        this.uri = uri;
        this.queryString = queryString;
        this.version = version;
        this.headerFields = headerFields;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getUri() {
        return uri;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeaderFields() {
        return headerFields;
    }
}
