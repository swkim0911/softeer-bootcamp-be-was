package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Stream;

import builder.RequestBuilder;
import builder.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestHeaderUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;

    public RequestHandler(Socket connectionSocket){
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            String requestMessage = RequestBuilder.getRequestMessage(in);
            HttpRequestHeaderUtils httpRequestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(requestMessage);
            logHeaders(httpRequestHeaderUtils);
            String requestUri = httpRequestHeaderUtils.getRequestUri();
            sendResponse(out, requestUri);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendResponse(OutputStream out, String requestUri) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        if (requestUri.equals("/") || requestUri.equals("/index.html")) {
            byte[] body = readHtmlFile("src/main/resources/templates/index.html");
            ResponseBuilder.buildResponseMessage(dos, body);
            dos.flush();
        } else if (requestUri.equals("/user/form.html")) {
            byte[] body = readHtmlFile("src/main/resources/templates/user/form.html");
            ResponseBuilder.buildResponseMessage(dos, body);
            dos.flush();
        }else if(requestUri.equals("/user/create")){

        }

        ResponseBuilder.buildResponseMessage(dos, makeDummyBody());
        dos.flush();
    }
    // 필요한 헤더 출력
    private static void logHeaders(HttpRequestHeaderUtils requestHeaderUtils){
        logger.debug("Method: {}", requestHeaderUtils.getRequestMethod());
        logger.debug("Request-Path: {}", requestHeaderUtils.getRequestUri());
        logger.debug("Query-String: {}", requestHeaderUtils.getQueryString());
        logger.debug("Version: {}", requestHeaderUtils.getRequestVersion());
        logger.debug("User-Agent: {}", requestHeaderUtils.getRequestUserAgent());
        logger.debug("Host: {}", requestHeaderUtils.getHost());
        logger.debug("Accept: {}", requestHeaderUtils.getAccept());
        logger.debug("Cookie: {}", requestHeaderUtils.getCookie());
    }

    private byte[] readHtmlFile(String fileName) throws IOException {
        // index.html 파일을 읽어서 바이트 배열로 반환
        return Files.readAllBytes(new File(fileName).toPath());
    }

    private byte[] makeDummyBody() {
        String bodyMessage = "hello world";
        return bodyMessage.getBytes();
    }
}
