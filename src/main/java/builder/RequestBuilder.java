package builder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.*;

public class RequestBuilder {

    public static String getRequestMessage(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        return URLDecoder.decode(buildRequestMessage(br), UTF_8);
    }

    private static String buildRequestMessage(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        sb.append(line).append("\r\n");
        while (!line.isEmpty()) { //마지막 라인 = ""(empty)
            line = br.readLine();
            sb.append(line).append("\r\n");
        }
        return sb.toString().trim();
    }
}
