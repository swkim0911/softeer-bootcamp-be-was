package Http;

import Http.status.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private String version;
    private HttpStatusCode statusCode;
    private String contentType;
    private int contentLength;
    private byte[] body;

    public HttpResponse(String version, HttpStatusCode statusCode, String contentType, int contentLength, byte[] body) {
        this.version = version;
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.body = body;
    }

    public String getVersion() {
        return version;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public String getContentType() {
        return contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public byte[] getBody() {
        return body;
    }
}
