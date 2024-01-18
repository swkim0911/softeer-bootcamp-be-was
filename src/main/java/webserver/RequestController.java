package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import Http.HttpRequest;
import Http.HttpRequestFactory;
import Http.builder.HttpRequestBuilder;
import Http.builder.HttpResponseBuilder;
import db.Database;
import dto.UserDto;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestHeaderUtils;
import util.UserEntityConverter;
import webserver.handler.ResourseRequestHandler;

public class RequestController implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
    private Map<String, Object> handlers = new HashMap<>();
    private final Socket connection;

    public RequestController(Socket connectionSocket){
        this.connection = connectionSocket;
    }

    // handler 추가
    public void initHandler() {
        ResourseRequestHandler resourseRequestHandler = new ResourseRequestHandler();
        handlers.put("/", resourseRequestHandler);
    }
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        initHandler();
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestFactory.getRequestMessage(in);
            httpRequest.logHeaders();
//            sendResponse(out, httpRequestHeaderUtils);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendResponse(OutputStream out, HttpRequestHeaderUtils httpRequestHeaderUtils) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        String requestUri = httpRequestHeaderUtils.getRequestUri();
        if (requestUri.equals("/") || requestUri.equals("/index.html")) {
            byte[] body = readHtmlFile("src/main/resources/templates/index.html");
            HttpResponseBuilder.buildResponseMessage(dos, body);
            dos.flush();
        } else if (requestUri.equals("/user/form.html")) {
            byte[] body = readHtmlFile("src/main/resources/templates/user/form.html");
            HttpResponseBuilder.buildResponseMessage(dos, body);
            dos.flush();
        }else if(requestUri.equals("/user/create")){
            UserDto userDto = UserDto.fromQueryString(httpRequestHeaderUtils.getQueryString());
            User user = UserEntityConverter.toEntity(userDto);
            Database.addUser(user);
        }
        HttpResponseBuilder.buildResponseMessage(dos, makeDummyBody());
        dos.flush();
    }
    // 필요한 헤더 출력
    private byte[] readHtmlFile(String fileName) throws IOException {
        // index.html 파일을 읽어서 바이트 배열로 반환
        return Files.readAllBytes(new File(fileName).toPath());
    }

    private byte[] makeDummyBody() {
        String bodyMessage = "hello world";
        return bodyMessage.getBytes();
    }
}
