package Http;

import Http.status.HttpStatusCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

import static Http.status.HttpStatusCode.*;

public class HttpResponseSender {

    public static void send(HttpResponse httpResponse, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        HttpStatusCode statusCode = httpResponse.getStatusCode();
        if (statusCode == OK) {
            responseHeader(dos, httpResponse);
            responseBody(dos, httpResponse);
        } else if (statusCode == FOUND) {
            responseHeader(dos, httpResponse);
        } else if(statusCode == NOT_FOUND){
            responseHeader(dos, httpResponse);
            responseBody(dos, httpResponse);
        }
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
