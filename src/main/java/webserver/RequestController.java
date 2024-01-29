package webserver;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import db.Database;
import dto.BoardDto;
import handler.BoardRequestHandler;
import http.HttpRequest;
import http.HttpRequestFactory;
import http.HttpResponse;
import http.HttpResponseSender;
import model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import handler.RequestHandler;
import handler.HomeRequestHandler;
import handler.UserRequestHandler;
import util.QueryStringParser;
import util.SessionCookieUtils;

public class RequestController implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
    private final Socket connection;

	static{
		initDummyDB();
	}
	public RequestController(Socket connectionSocket){
        this.connection = connectionSocket;
    }
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			HttpRequest httpRequest = HttpRequestFactory.getRequest(in);
			logger.debug("{}", httpRequest);
			String findSessionId = SessionCookieUtils.getSessionId(httpRequest.getCookies());
			HttpResponse httpResponse = handleRequest(httpRequest, findSessionId);
			HttpResponseSender.send(httpResponse, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    public HttpResponse handleRequest(HttpRequest httpRequest, String findSessionId) throws IOException {
        String uri = httpRequest.getUri();
        RequestHandler requestHandler;
        if (uri.startsWith("/user")) {
            requestHandler = new UserRequestHandler();
			return requestHandler.handle(httpRequest, findSessionId);
        }
		if (uri.startsWith("/board")) {
			requestHandler = new BoardRequestHandler();
			return requestHandler.handle(httpRequest, findSessionId);
		}
		requestHandler = new HomeRequestHandler();
		return requestHandler.handle(httpRequest, findSessionId);
    }

	private static void initDummyDB() {
		Map<String, String> queryParameters = new HashMap<>();
		queryParameters.put("writer", "lee");
		queryParameters.put("title", "hello world");
		queryParameters.put("contents", "good bye");
		Board board = Board.create(new BoardDto(queryParameters, LocalDate.now()));
		Database.addBoard(board);
	}
}
