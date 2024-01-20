package Http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final String method;
    private final String uri;
    private final String queryString; // 없는 경우 None
    private final String version;
    private final Map<String, String> headerFields;

    public HttpRequest(String method, String uri, String queryString, String version, Map<String, String> headerFields) {
        this.method = method;
        this.uri = uri;
        this.queryString = queryString;
        this.version = version;
        this.headerFields = headerFields;
    }

    public String getMethod() {
        return method;
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

    public void logHeaders(){
        StringBuilder sb = new StringBuilder();
        sb.append("\nMethod: ").append(method).append("\n");
        sb.append("Uri: ").append(uri).append("\n");
        sb.append("Query-String: ").append(queryString).append("\n");
        sb.append("Version: ").append(version).append("\n");
        for (Map.Entry<String, String> entry : headerFields.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        logger.debug("{}", sb);

//        logger.debug("Method: {}", method);
//        logger.debug("Uri: {}", uri);
//        logger.debug("Query-String: {}", queryString);
//        logger.debug("Version: {}", version);
//        logger.debug("Host: {}", headerFields.get("Host"));
//        logger.debug("User-Agent: {}", headerFields.get("User-Agent"));
//        logger.debug("Accept: {}", headerFields.get("Accept"));
    }
}