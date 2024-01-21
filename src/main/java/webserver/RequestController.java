package webserver;

import java.io.*;
import java.net.Socket;

import Http.HttpRequest;
import Http.HttpRequestFactory;
import Http.HttpResponse;
import Http.HttpResponseSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.RequestHandler;
import webserver.handler.HomeRequestHandler;
import webserver.handler.UserRequestHandler;

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
            HttpRequest httpRequest = HttpRequestFactory.getRequestMessage(in);
            httpRequest.logHeaders();
            handleRequest(httpRequest, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    public void handleRequest(HttpRequest httpRequest, OutputStream out) throws IOException {
        String uri = httpRequest.getUri();
        RequestHandler requestHandler;
        if (uri.startsWith("/user")) {
            requestHandler = new UserRequestHandler();
        }else{
            requestHandler = new HomeRequestHandler();
        }
        HttpResponse httpResponse = requestHandler.handle(httpRequest);
        HttpResponseSender.send(httpResponse, out);
    }
}
