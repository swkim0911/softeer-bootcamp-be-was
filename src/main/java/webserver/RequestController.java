package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import Http.HttpRequest;
import Http.HttpRequestFactory;
import Http.HttpResponse;
import Http.HttpResponseSender;
import Http.builder.HttpResponseBuilder;
import Http.status.HttpStatusCode;
import db.Database;
import dto.UserDto;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestHeaderUtils;
import util.UserEntityConverter;
import webserver.handler.RequestHandler;
import webserver.handler.HomeRequestHandler;
import webserver.handler.UserRequestHandler;

public class RequestController implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
    private final Map<String, RequestHandler> handlers = new HashMap<>();
    private final Socket connection;

    public RequestController(Socket connectionSocket){
        this.connection = connectionSocket;
    }
    // handler 추가
    public void initHandler() {
        RequestHandler resourseRequestHandler = new HomeRequestHandler();
        RequestHandler userRequestHandler = new UserRequestHandler();
        handlers.put("/", resourseRequestHandler);
        handlers.put("/index.html", resourseRequestHandler);
        handlers.put("/user", userRequestHandler);
    }
    public void run() {
        initHandler();
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
        RequestHandler handler = handlers.get(httpRequest.getUri());
        HttpResponse httpResponse;
        if (handler != null) {
            httpResponse = handler.handle(httpRequest);
            HttpResponseSender.send(httpResponse, out);
            return;
        }
        //  잘못된 요청 - 404 보내기
        logger.debug("not found:{}", httpRequest.getUri());

        httpResponse = new HttpResponseBuilder()
                .version(httpRequest.getVersion())
                .status(HttpStatusCode.NOT_FOUND)
                .contentType("text/html;charset=utf-8")
                .contentLength(HttpStatusCode.NOT_FOUND.getReasonPhrase().length())
                .body(HttpStatusCode.NOT_FOUND.getReasonPhrase().getBytes())
                .build();

        HttpResponseSender.send(httpResponse, out);

    }


    //todo 핸들러로 보내고 sender 클래스 만들자
//    private void sendResponse(OutputStream out, HttpRequestHeaderUtils httpRequestHeaderUtils) throws IOException {
//        DataOutputStream dos = new DataOutputStream(out);
//        String requestUri = httpRequestHeaderUtils.getRequestUri();
//        if (requestUri.equals("/") || requestUri.equals("/index.html")) {
//            byte[] body = readHtmlFile("src/main/resources/templates/index.html");
//            HttpResponseBuilder.buildResponseMessage(dos, body);
//            dos.flush();
//        } else if (requestUri.equals("/user/form.html")) {
//            byte[] body = readHtmlFile("src/main/resources/templates/user/form.html");
//            HttpResponseBuilder.buildResponseMessage(dos, body);
//            dos.flush();
//        }else if(requestUri.equals("/user/create")){
//            UserDto userDto = UserDto.fromQueryString(httpRequestHeaderUtils.getQueryString());
//            User user = UserEntityConverter.toEntity(userDto);
//            Database.addUser(user);
//        }
//        HttpResponseBuilder.buildResponseMessage(dos, makeDummyBody());
//        dos.flush();
//    }
}
