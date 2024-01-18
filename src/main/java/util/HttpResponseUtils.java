package util;

import Http.HttpRequest;
import Http.HttpResponse;
import Http.builder.HttpResponseBuilder;
import Http.status.HttpStatusCode;

public class HttpResponseUtils {

    public static HttpResponse get200HttpResponse(HttpRequest httpRequest, byte[] body) {
        return new HttpResponseBuilder()
                .version(httpRequest.getVersion())
                .status(HttpStatusCode.OK)
                .contentType("text/html;charset=utf-8")
                .contentLength(body.length)
                .body(body)
                .build();
    }

    public static HttpResponse get302HttpResponse(HttpRequest httpRequest) {
        return new HttpResponseBuilder()
                .version(httpRequest.getVersion())
                .status(HttpStatusCode.FOUND)
                .build();
    }

    //todo: step3 진행시 수정 예정
    public static HttpResponse get404HttpResponse(HttpRequest httpRequest) {
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
