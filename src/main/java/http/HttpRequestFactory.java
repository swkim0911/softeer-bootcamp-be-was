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
		String requestHeader = URLDecoder.decode(getRequestHeader(in), UTF_8);
//		String requestBody = getRequestBody(in);
		HttpRequestHeaderUtils httpRequestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(requestHeader);

        return new HttpRequestBuilder()
                .method(httpRequestHeaderUtils.getRequestMethod())
                .uri(httpRequestHeaderUtils.getRequestUri())
                .queryString(httpRequestHeaderUtils.getQueryString())
                .version(httpRequestHeaderUtils.getRequestVersion())
                .headerFields(httpRequestHeaderUtils.getRequestHeaders())
                .build();
    }

    private static String getRequestHeader(InputStream in) throws IOException { //헤더를 읽는 메서드로 변환
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		while (!(line = br.readLine()).isEmpty()) {
			sb.append(line).append("\r\n");
		}
        return sb.toString().trim();
    }

	//todo inputstream을 이어 받아서 body를 String 변환
}
