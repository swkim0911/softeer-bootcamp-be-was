package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final String method;
    private final String uri;
    private final String queryString; // 없는 경우 None
    private final String version;
    private final Map<String, String> headerFields;
	private final Optional<String> body;

    public HttpRequest(String method, String uri, String queryString, String version, Map<String, String> headerFields, Optional<String> body) {
        this.method = method;
        this.uri = uri;
        this.queryString = queryString;
        this.version = version;
        this.headerFields = headerFields;
        this.body = body;
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

	public Optional<String> getBody() {
		return body;
	}

	public void logHeaders(){ // 바디도 같이 찍기
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Method: ").append(method).append("\n");
        sb.append("Uri: ").append(uri).append("\n");
        sb.append("Query-String: ").append(queryString).append("\n");
        sb.append("Version: ").append(version).append("\n");
        for (Map.Entry<String, String> entry : headerFields.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
		sb.append("Body: ").append(body.orElse(""));
		logger.debug("{}", sb.toString().trim());

	}
}
