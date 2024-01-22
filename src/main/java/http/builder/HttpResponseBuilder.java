package http.builder;

import http.HttpResponse;
import http.status.HttpStatusCode;

import java.util.Map;
import java.util.Optional;

public class HttpResponseBuilder {
    private String version;
    private HttpStatusCode statusCode;
    private Map<String, String> headerFields;
    private Optional<byte[]> body;

    public HttpResponseBuilder version(String version) {
        this.version = version;
        return this;
    }
    public HttpResponseBuilder status(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
        return this;
    }
    public HttpResponseBuilder headerFields(Map<String, String> headerFields) {
        this.headerFields = headerFields;
        return this;
    }

    public HttpResponseBuilder body(byte[] body) {
        this.body = Optional.of(body);
        return this;
    }

    public HttpResponse build() {
        return new HttpResponse(version, statusCode, headerFields, body);
    }
}
