package util;

import Http.HttpRequest;
import Http.HttpResponse;
import Http.builder.HttpResponseBuilder;
import Http.status.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseUtils {
    public static HttpResponse get200HttpResponse(HttpRequest httpRequest, byte[] body) {
        Map<String, String> headerFields = new HashMap<>();
        headerFields.put("Content-Type", "text/html;charset=utf-8");
        headerFields.put("Content-Length", String.valueOf(body.length));
        return new HttpResponseBuilder()
                .version(httpRequest.getVersion())
                .status(HttpStatusCode.OK)
                .headerFields(headerFields)
                .body(body)
                .build();
    }
    public static HttpResponse get302HttpResponse(HttpRequest httpRequest) {
        Map<String, String> headerFields = new HashMap<>();
        headerFields.put("Content-Type", "text/html;charset=utf-8");
        headerFields.put("Location", "/index.html");
        return new HttpResponseBuilder()
                .version(httpRequest.getVersion())
                .status(HttpStatusCode.FOUND)
                .headerFields(headerFields)
                .build();
    }

    public static HttpResponse get404HttpResponse(HttpRequest httpRequest, byte[] body) {
        Map<String, String> headerFields = new HashMap<>();
        headerFields.put("Content-Type", "text/html;charset=utf-8");
        headerFields.put("Content-Length", String.valueOf(body.length));
        return new HttpResponseBuilder()
                .version(httpRequest.getVersion())
                .status(HttpStatusCode.NOT_FOUND)
                .headerFields(headerFields)
                .body(body)
                .build();
    }
}
