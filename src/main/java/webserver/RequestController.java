package webserver;

import java.io.*;
import java.net.Socket;

import http.HttpRequest;
import http.HttpRequestFactory;
import http.HttpResponse;
import http.HttpResponseSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import handler.RequestHandler;
import handler.HomeRequestHandler;
import handler.UserRequestHandler;

public class RequestController implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
    private final Socket connection;
    public RequestController(Socket connectionSocket){
        this.connection = connectionSocket;
    }
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			HttpRequest httpRequest = HttpRequestFactory.getRequest(in);
			httpRequest.logHeaders();
			HttpResponse httpResponse = handleRequest(httpRequest);
			HttpResponseSender.send(httpResponse, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    public HttpResponse handleRequest(HttpRequest httpRequest) throws IOException {
        String uri = httpRequest.getUri();
        RequestHandler requestHandler;
        if (uri.startsWith("/user")) {
            requestHandler = new UserRequestHandler();
        }else{
            requestHandler = new HomeRequestHandler();
        }
        return requestHandler.handle(httpRequest);
    }
}
