package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Stream;

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
            HttpRequestHeaderUtils requestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(in);
            logHeaders(requestHeaderUtils);
            String requestPath = requestHeaderUtils.getRequestPath();
            DataOutputStream dos = new DataOutputStream(out);
            if (requestPath.equals("/index.html")) {
                byte[] body = readHtmlFile("src/main/resources/templates/index.html");
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
            sendDummy(dos);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    // 필요한 헤더 출력
    private static void logHeaders(HttpRequestHeaderUtils requestHeaderUtils){
        logger.debug("Method: {}", requestHeaderUtils.getRequestMethod());
        logger.debug("Request-Path: {}", requestHeaderUtils.getRequestPath());
        logger.debug("Version: {}", requestHeaderUtils.getRequestVersion());
        logger.debug("Accept: {}", requestHeaderUtils.getAccept());
        logger.debug("Accept-Language: {}", requestHeaderUtils.getAcceptLanguage());
        logger.debug("Cookie: {}", requestHeaderUtils.getCookie());
    }

    private byte[] readHtmlFile(String fileName) throws IOException {
        // index.html 파일을 읽어서 바이트 배열로 반환
        return Files.readAllBytes(new File(fileName).toPath());
    }
    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    private void sendDummy(DataOutputStream dos) {
        String bodyMessage = "hello world";
        byte[] body = bodyMessage.getBytes();
        response200Header(dos, body.length);
        responseBody(dos, body);
    }
}
