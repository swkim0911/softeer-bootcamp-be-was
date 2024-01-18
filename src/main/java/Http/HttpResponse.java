package Http;

import Http.status.HttpStatusCode;

import java.util.Map;
import java.util.Optional;

public class HttpResponse {
    private final String version;
    private final HttpStatusCode statusCode;
    private final Map<String, String> headerFields;
    private final Optional<byte[]> body;

    public HttpResponse(String version, HttpStatusCode statusCode, Map<String, String> headerFields, Optional<byte[]> body) {
        this.version = version;
        this.statusCode = statusCode;
        this.headerFields = headerFields;
        this.body = body;
    }

    public String getVersion() {
        return version;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaderFields() {
        return headerFields;
    }

    public Optional<byte[]> getBody() {
        return body;
    }
}
