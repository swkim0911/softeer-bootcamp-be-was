package Http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponseSender {

    public static void send(HttpResponse httpResponse, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        responseHeader(dos, httpResponse);
        responseBody(dos, httpResponse);
        dos.flush();
    }

    private static void responseHeader(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(httpResponse.getVersion()).append(" ").
            append(httpResponse.getStatusCode().getCode()).append(" ").
                append(httpResponse.getStatusCode().getReasonPhrase()).append("\r\n").
                append("Content-Type: ").append(httpResponse.getContentType()).append("\r\n").
                append("Content-Length: ").append(httpResponse.getBody().length).append("\r\n").
                append("\r\n");
        dos.writeBytes(sb.toString());
    }

    private static void responseBody(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
    }
}
