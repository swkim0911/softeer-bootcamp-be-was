package Http.builder;

import Http.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Map;

import static java.nio.charset.StandardCharsets.*;

public class HttpRequestBuilder {



    public static String getRequestMessage(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        return URLDecoder.decode(buildRequestMessage(br), UTF_8);
    }

    private String method;
    private String url;
    private String uri;
    private String queryString;
    private String version;
    private Map<String, String> headerFields;

    public HttpRequestBuilder method(String method) {
        this.method = method;
        return this;
    }

    public HttpRequestBuilder url(String url) {
        this.url = url;
        return this;
    }

    public HttpRequestBuilder uri(String uri) {
        this.uri = uri;
        return this;
    }

    public HttpRequestBuilder queryString(String queryString) {
        this.queryString = queryString;
        return this;
    }

    public HttpRequestBuilder version(String version) {
        this.version = version;
        return this;
    }

    public HttpRequestBuilder headerFields(Map<String,String> headerFields) {
        this.headerFields = headerFields;
        return this;
    }

    public HttpRequest build() {
        return new HttpRequest(method, url, uri, queryString, version, headerFields);
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
