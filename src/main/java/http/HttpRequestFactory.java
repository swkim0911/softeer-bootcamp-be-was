package http;

import http.builder.HttpRequestBuilder;
import util.HttpRequestHeaderUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpRequestFactory {

    public static HttpRequest getRequest(InputStream in) throws IOException {
        String requestMessage = URLDecoder.decode(getStringRequestMessage(in), UTF_8);
        HttpRequestHeaderUtils httpRequestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(requestMessage);

        return new HttpRequestBuilder()
                .method(httpRequestHeaderUtils.getRequestMethod())
                .uri(httpRequestHeaderUtils.getRequestUri())
                .queryString(httpRequestHeaderUtils.getQueryString())
                .version(httpRequestHeaderUtils.getRequestVersion())
                .headerFields(httpRequestHeaderUtils.getRequestHeaders())
                .build();
    }

    private static String getStringRequestMessage(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = br.readLine();
        sb.append(line).append("\r\n");
        while (!line.isEmpty()) { //마지막 라인 = ""(empty)
            line = br.readLine();
            sb.append(line).append("\r\n");
        }
        return sb.toString().trim();
    }
}
