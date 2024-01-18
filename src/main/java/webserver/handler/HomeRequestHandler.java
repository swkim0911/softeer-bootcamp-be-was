package webserver.handler;

import Http.HttpRequest;
import Http.HttpResponse;
import Http.builder.HttpResponseBuilder;
import Http.status.HttpStatusCode;
import util.FileUtils;

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
            return get200HttpResponse(httpRequest, uri);
        }
        return get404HttpResponse(httpRequest);
    }

    private static HttpResponse get200HttpResponse(HttpRequest httpRequest, String uri) {
        byte[] body = FileUtils.readFile(path + uri);
        return new HttpResponseBuilder()
                .version(httpRequest.getVersion())
                .status(HttpStatusCode.OK)
                .contentType("text/html;charset=utf-8")
                .contentLength(body.length)
                .body(body)
                .build();
    }

    //todo step3 진행시 수정 예정
    private static HttpResponse get404HttpResponse(HttpRequest httpRequest) {
        byte[] body = HttpStatusCode.NOT_FOUND.getReasonPhrase().getBytes();
        return new HttpResponseBuilder()
                .version(httpRequest.getVersion())
                .status(HttpStatusCode.NOT_FOUND)
                .contentType("text/html;charset=utf-8")
                .contentLength(body.length)
                .body(body)
                .build();
    }


}
