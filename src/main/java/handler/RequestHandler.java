package handler;

import http.HttpRequest;
import http.HttpResponse;
import http.status.HttpStatusCode;
import session.SessionManager;
import util.FileUtils;
import http.HttpResponseFactory;
import util.UriParser;

import java.util.Optional;


public interface RequestHandler {
    HttpResponse handle(HttpRequest httpRequest);

    default HttpResponse getHttpResponse(String uri){
        Optional<byte[]> body = FileUtils.readFile(uri);
        if (body.isPresent()) {
			return HttpResponseFactory.getHttpResponse(HttpStatusCode.OK, UriParser.getFileType(uri), body.get());
        }
		//body가 empty라면 404 페이지 전송
		body = FileUtils.readFile("/error/404.html");
		//404 페이지를 찾을 수 없으면 "404 NOT FOUND" 문자열 전송
		return HttpResponseFactory.getHttpResponse(HttpStatusCode.NOT_FOUND, UriParser.getFileType(uri), body.orElse("404 NOT FOUND".getBytes()));
    }

	default boolean isHTML(String uri) {
		String fileType = UriParser.getFileType(uri);
		return fileType.equals("html");
	}

	default boolean isCookieValid(String cookie) {
		String targetKey = "SID";
		System.out.println("cookie = " + cookie);
		String[] keyValue = cookie.trim().split("=");
		String key = keyValue[0];
		String value = keyValue[1];
		return key.equals(targetKey) && SessionManager.containsSession(value);
	}

	default String getSessionId(String cookie) {
		String[] keyValue = cookie.trim().split("=");
		return keyValue[1];
	}
}
