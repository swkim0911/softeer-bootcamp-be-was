package Http;

import Http.status.HttpStatusCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponseSender {

    public static void send(HttpResponse httpResponse, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        if (httpResponse.getStatusCode() == HttpStatusCode.OK) {
            response200Header(dos, httpResponse);
            responseBody(dos, httpResponse);
        } else if (httpResponse.getStatusCode() == HttpStatusCode.FOUND) {
            response300Header(dos, httpResponse);
        }
        dos.flush();
    }

    private static void response300Header(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found \r\n");
        dos.writeBytes("Location: /index.html \r\n");
        dos.writeBytes("\r\n");

    }

    private static void response200Header(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + httpResponse.getBody().length + "\r\n");
        dos.writeBytes("\r\n");
    }

    private static void responseBody(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
    }
}
