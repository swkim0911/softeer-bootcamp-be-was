package Http.builder;

import Http.HttpResponse;
import Http.status.HttpStatusCode;

import java.io.DataOutputStream;
import java.io.IOException;

//builder 패턴을 적용할 수 있지 않을까?
public class HttpResponseBuilder {

    private String version;
    private HttpStatusCode statusCode;
    private String contentType;
    private int contentLength;
    private byte[] body;

    public HttpResponseBuilder version(String version) {
        this.version = version;
        return this;
    }
    public HttpResponseBuilder status(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
        return this;
    }
    public HttpResponseBuilder contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }
    public HttpResponseBuilder contentLength(int contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public HttpResponseBuilder body(byte[] body) {
        this.body = body;
        return this;
    }

    public HttpResponse build() {
        return new HttpResponse(version, statusCode, contentType, contentLength, body);
    }
}
