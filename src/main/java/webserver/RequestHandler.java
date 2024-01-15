package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String ROOT = "/Users/user/Documents/softeer/softeer-bootcamp-be-was/";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            // 요청 헤더 읽기;
            String requestPath = readRequestPath(in);
            logger.debug("requestPath = {}", requestPath);
            DataOutputStream dos = new DataOutputStream(out);
            if (requestPath.equals("/index.html")) {
                byte[] body = readHtmlFile("src/main/resources/templates/index.html");
                response200Header(dos, body.length, "html");
                responseBody(dos, body);
            } else if (requestPath.equals("/css/bootstrap.min.css")) {
                byte[] body = readHtmlFile("src/main/resources/static/css/bootstrap.min.css");
                response200Header(dos, body.length, "css");
                responseBody(dos, body);
            } else if (requestPath.equals("/css/styles.css")) {
                byte[] body = readHtmlFile("src/main/resources/static/css/styles.css");
                response200Header(dos, body.length, "css");
                responseBody(dos, body);
            }else if (requestPath.equals("/js/jquery-2.2.0.min.js")) {
                byte[] body = readHtmlFile("src/main/resources/static/js/jquery-2.2.0.min.js");
                response200Header(dos, body.length, "js");
                responseBody(dos, body);
            }else if (requestPath.equals("/js/bootstrap.min.js")) {
                byte[] body = readHtmlFile("src/main/resources/static/js/bootstrap.min.js");
                response200Header(dos, body.length, "js");
                responseBody(dos, body);
            }else if (requestPath.equals("/js/scripts.js")) {
                byte[] body = readHtmlFile("src/main/resources/static/js/scripts.js");
                response200Header(dos, body.length, "js");
                responseBody(dos, body);
            }else if (requestPath.equals("/favicon.ico")) {
                byte[] body = readHtmlFile("src/main/resources/static/favicon.ico");
                response200Header(dos, body.length, "ico");
                responseBody(dos, body);
            }

            String bodyMessage = "hello world"; // 이렇게 하면 css
            byte[] body = bodyMessage.getBytes();
            response200Header(dos, body.length, "html");
            responseBody(dos, body);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static String readRequestPath(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line = br.readLine();
        logger.debug("request line : {}", line);
        String requestPath = line.split(" ")[1];
        while (!line.isEmpty()) { //마지막 라인 = ""(empty)
            line = br.readLine();
            logger.debug("header : {}", line);
        }
        return requestPath;
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String type) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            if (type.equals("html")) {
                dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            } else if (type.equals("css")) {
                dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            } else if (type.equals("js")) {
                dos.writeBytes("Content-Type: application/javascript;charset=utf-8\r\n");
            }else if (type.equals("ico")) {
                dos.writeBytes("Content-Type: image/x-icon;charset=utf-8\r\n");
            }
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
    private byte[] readHtmlFile(String fileName) throws IOException {
        // index.html 파일을 읽어서 문자열로 반환
        return Files.readAllBytes(new File(fileName).toPath());
    }
}
