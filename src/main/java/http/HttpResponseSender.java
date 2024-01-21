package http;

import http.status.HttpStatusCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

import static http.status.HttpStatusCode.*;

public class HttpResponseSender {

    public static void send(HttpResponse httpResponse, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        HttpStatusCode statusCode = httpResponse.getStatusCode();
        responseHeader(dos, httpResponse);
        if (statusCode == OK || statusCode == NOT_FOUND) { // 상태 코드가 200, 404인 경우 body가 포함된다.
            responseBody(dos, httpResponse);
        }
        // 상태 코드가 302인 경우 body는 포함되지 않는다.
        dos.flush();
    }
    private static void responseHeader(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.writeBytes(httpResponse.getResponseHeader());
    }


    private static void responseBody(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        Optional<byte[]> body = httpResponse.getBody();
        if (body.isPresent()) {
            dos.write(body.get(), 0, body.get().length);
        }
    }
}
