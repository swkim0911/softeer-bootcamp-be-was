package handler;

import Http.HttpRequest;
import Http.HttpResponse;
import util.FileUtils;
import util.HttpResponseUtils;

import java.util.Optional;


public interface RequestHandler {
    HttpResponse handle(HttpRequest httpRequest);

    default HttpResponse getHttpResponse(HttpRequest httpRequest, String uri) {
        Optional<byte[]> body = FileUtils.readFile(uri);
        if (body.isPresent()) {
            return HttpResponseUtils.get200HttpResponse(httpRequest, body.get());
        }
         body = FileUtils.readFile("/error/404.html"); //body가 empty라면 404 페이지 전송
        // 404 페이지를 못찾아서 body가 empty라면 이때는 언체크 예외로 RuntimeException
        return HttpResponseUtils.get404HttpResponse(httpRequest, body.orElseThrow(RuntimeException::new));
    }
}
