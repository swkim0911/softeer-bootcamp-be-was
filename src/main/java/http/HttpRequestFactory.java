package http;

import http.builder.HttpRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestHeaderUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpRequestFactory {

	private static final Logger logger = LoggerFactory.getLogger(HttpRequestFactory.class);
    public static HttpRequest getRequest(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String requestHeader = URLDecoder.decode(getRequestHeader(br), UTF_8);
		HttpRequestHeaderUtils httpRequestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(requestHeader);
		String requestBody = getRequestBody(httpRequestHeaderUtils, br);
		return new HttpRequestBuilder()
                .method(httpRequestHeaderUtils.getRequestMethod())
                .uri(httpRequestHeaderUtils.getRequestUri())
                .queryString(httpRequestHeaderUtils.getQueryString())
                .version(httpRequestHeaderUtils.getRequestVersion())
                .headerFields(httpRequestHeaderUtils.getRequestHeaders())
				.body(requestBody)
                .build();
    }

	private static String getRequestBody(HttpRequestHeaderUtils httpRequestHeaderUtils, BufferedReader br) throws IOException {
		Map<String, String> map = httpRequestHeaderUtils.getRequestHeaders();
		String contentLength = map.get("Content-Length");
		if (contentLength != null) {
			char[] buf = new char[Integer.parseInt(contentLength)];
			br.read(buf);
			return URLDecoder.decode(String.valueOf(buf), UTF_8);
		}
		return null;
	}

	private static String getRequestHeader(BufferedReader br) throws IOException { //헤더를 읽는 메서드로 변환
        StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		sb.append(line).append("\r\n");
		while (!line.isEmpty()) { //마지막 라인 = ""(empty)
			line = br.readLine();
			sb.append(line).append("\r\n");
		}
		return sb.toString().trim();
    }

	//todo inputstream을 이어 받아서 body를 String 변환
	private static String getRequestBody(BufferedReader br) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) { //eof 만날때까지
			sb.append(line).append("\r\n");
		}
		return sb.toString();
	}
}
