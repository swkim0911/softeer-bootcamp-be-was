package handler;

import http.HttpRequest;
import http.HttpResponse;
import util.FileUtils;
import util.HttpResponseUtils;

import java.util.Optional;


public interface RequestHandler {
    HttpResponse handle(HttpRequest httpRequest);

    default HttpResponse getHttpResponse(HttpRequest httpRequest, String uri){
        Optional<byte[]> body = FileUtils.readFile(uri);
        if (body.isPresent()) {
            return HttpResponseUtils.get200HttpResponse(httpRequest, body.get());
        }
		//body가 empty라면 404 페이지 전송
		body = FileUtils.readFile("/error/404.html1");
		//404 페이지를 찾을 수 없으면 "404 NOT FOUND 문자열 전송"
		return HttpResponseUtils.get404HttpResponse(httpRequest, body.orElse("404 NOT FOUND".getBytes()));
    }
}
