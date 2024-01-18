package Http.builder;

import java.io.DataOutputStream;
import java.io.IOException;

//builder 패턴을 적용할 수 있지 않을까?
public class HttpResponseBuilder {

    public static DataOutputStream buildResponseMessage(DataOutputStream dos, byte[] body) throws IOException {
        response200Header(dos, body.length);
        responseBody(dos, body);
        return dos;
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
