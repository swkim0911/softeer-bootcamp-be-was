package webserver.handler;

import Http.HttpRequest;
import Http.HttpResponse;


public interface RequestHandler {
    public HttpResponse handle(HttpRequest httpRequest);
}
