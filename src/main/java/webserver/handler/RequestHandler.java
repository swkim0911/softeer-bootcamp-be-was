package webserver.handler;

import Http.HttpRequest;
import Http.HttpResponse;

import java.io.OutputStream;

public interface RequestHandler {

    public HttpResponse handle(HttpRequest httpRequest);
}
