package util;

import Http.HttpRequest;
import Http.HttpResponse;
import builder.HttpResponseBuilder;
import Http.status.HttpStatusCode;
import util.mapper.ContentTypeMapper;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseUtils {
    public static HttpResponse get200HttpResponse(HttpRequest httpRequest, byte[] body) {
        Map<String, String> headerFields = setHeaderFields200And400Code(httpRequest, body);
        return new HttpResponseBuilder()
                .version(httpRequest.getVersion())
                .status(HttpStatusCode.OK)
                .headerFields(headerFields)
                .body(body)
                .build();
    }

    public static HttpResponse get302HttpResponse(HttpRequest httpRequest) {
        Map<String, String> headerFields = setHeaderFields300Code();
        return new HttpResponseBuilder()
                .version(httpRequest.getVersion())
                .status(HttpStatusCode.FOUND)
                .headerFields(headerFields)
                .build();
    }
    public static HttpResponse get404HttpResponse(HttpRequest httpRequest, byte[] body) {
        Map<String, String> headerFields = setHeaderFields200And400Code(httpRequest, body);
        return new HttpResponseBuilder()
                .version(httpRequest.getVersion())
                .status(HttpStatusCode.NOT_FOUND)
                .headerFields(headerFields)
                .body(body)
                .build();
    }

    private static Map<String, String> setHeaderFields200And400Code(HttpRequest httpRequest, byte[] body) {
        Map<String, String> headerFields = new HashMap<>();
        String fileType = UriParser.getFileType(httpRequest.getUri());
        String contentType = ContentTypeMapper.contentTypeMap.get(fileType);
        headerFields.put("Content-Type", contentType+";charset=utf-8");
        headerFields.put("Content-Length", String.valueOf(body.length));
        return headerFields;
    }

    private static Map<String, String> setHeaderFields300Code() {
        Map<String, String> headerFields = new HashMap<>();
        headerFields.put("Content-Type", "text/html;charset=utf-8");
        headerFields.put("Location", "/index.html");
        return headerFields;
    }
}
