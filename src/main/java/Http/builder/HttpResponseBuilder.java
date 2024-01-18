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

    public static void buildResponseMessage(DataOutputStream dos, byte[] body) {
        try {
            response200Header(dos, body.length);
            responseBody(dos, body);
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void response200Header(DataOutputStream dos, int lengthOfBodyContent) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        dos.writeBytes("\r\n");
    }

    private static void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
    }
}
