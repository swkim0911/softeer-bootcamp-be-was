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
    private static final String ROOT = "src/main/resources";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            // 요청 헤더 읽기
            String requestPath = readRequestPath(in);
            String fileType = getFileType(requestPath);
            DataOutputStream dos = new DataOutputStream(out);
            if (fileType.equals("html")) {
                byte[] body = readHtmlFile("/templates/index.html");
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
            String bodyMessage = "hello world";
            byte[] body = bodyMessage.getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static String getFileType(String requestPath) {
        int idx = requestPath.lastIndexOf(".");
        return requestPath.substring(idx + 1);
    }

    private static String readRequestPath(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line = br.readLine();
        logger.debug("request line: {}", line);
        String requestPath = line.split(" ")[1];
        while (!line.isEmpty()) { //마지막 라인 = ""(empty)
            line = br.readLine();
            logger.debug("header: {}", line);
        }
        return requestPath;
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
    private byte[] readHtmlFile(String fileName) throws IOException {
        // index.html 파일을 읽어서 바이트 배열로 반환
        return Files.readAllBytes(new File(ROOT+fileName).toPath());
    }
}
