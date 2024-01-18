package webserver.handler;

import Http.HttpRequest;
import Http.HttpResponse;
import Http.builder.HttpResponseBuilder;
import Http.status.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HomeRequestHandler implements RequestHandler{

    private static final Logger logger = LoggerFactory.getLogger(HomeRequestHandler.class);
    private static final String path = "src/main/resources/templates/";

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String uri = httpRequest.getUri();
        if (uri.equals("/")) {
            uri = "/index.html";
        }
        byte[] body = readHtmlFile( path + uri);
        return new HttpResponseBuilder()
                .version(httpRequest.getVersion())
                .status(HttpStatusCode.OK)
                .contentType("text/html;charset=utf-8")
                .contentLength(body.length)
                .body(body)
                .build();
    }

    private byte[] readHtmlFile(String fileName) {
        // index.html 파일을 읽어서 바이트 배열로 반환
        try {
            return Files.readAllBytes(new File(fileName).toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
            return HttpStatusCode.BAD_REQUEST.getReasonPhrase().getBytes(); //todo
        }
    }
}
