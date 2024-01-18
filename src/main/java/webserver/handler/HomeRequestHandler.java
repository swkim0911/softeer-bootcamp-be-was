package webserver.handler;

import Http.HttpRequest;
import Http.HttpResponse;
import Http.builder.HttpResponseBuilder;
import Http.status.HttpStatusCode;
import util.FileUtils;
import util.HttpResponseUtils;

public class HomeRequestHandler implements RequestHandler{
    private static final String path = "src/main/resources/templates";
    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String uri = httpRequest.getUri();
        if (uri.equals("/")) {
            uri = "/index.html";
        }
        // 파일 확장자가 html이면
        String fileType = FileUtils.getFileType(uri);
        if (fileType.equals("html")) {
            byte[] body = FileUtils.readFile(path + uri);
            return HttpResponseUtils.get200HttpResponse(httpRequest, body);
        }
        return HttpResponseUtils.get404HttpResponse(httpRequest);
    }
}
