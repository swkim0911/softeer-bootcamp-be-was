package webserver.handler;

import Http.HttpRequest;
import Http.HttpResponse;
import util.FileUtils;
import util.HttpResponseUtils;

import java.util.Optional;


public interface RequestHandler {
    public HttpResponse handle(HttpRequest httpRequest);

    default HttpResponse getHttpResponse(HttpRequest httpRequest, String uri) {
        Optional<byte[]> body = FileUtils.readFile(uri);
        if (body.isPresent()) {
            return HttpResponseUtils.get200HttpResponse(httpRequest, body.get());
        }
        body = FileUtils.readFile("/error/404.html");
        return HttpResponseUtils.get404HttpResponse(httpRequest, body.orElseThrow(RuntimeException::new));
    }
}
