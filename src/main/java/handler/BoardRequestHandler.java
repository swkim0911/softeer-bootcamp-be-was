package handler;

import db.Database;
import dto.BoardDto;
import exception.InvalidInputException;
import html.HTMLGenerator;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpResponseFactory;
import model.Board;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.QueryStringParser;

import java.time.LocalDate;
import java.util.Map;

import static http.method.HttpMethod.GET;
import static http.method.HttpMethod.POST;


public class BoardRequestHandler implements RequestHandler{
	private static final Logger logger = LoggerFactory.getLogger(BoardRequestHandler.class);
	@Override
	public HttpResponse handle(HttpRequest httpRequest, String findSessionId) {
		String uri = httpRequest.getUri();
		if ("/board/create".equals(uri)) {
			if(POST.equals(httpRequest.getMethod())){
				try {
					Map<String, String> queryParameters = QueryStringParser.getParameters(httpRequest.getBody());
					BoardDto boardDto = new BoardDto(queryParameters, LocalDate.now());
					Board board = Board.create(boardDto);
					Database.addBoard(board);
					return HttpResponseFactory.get302Response("/index.html");
				} catch (InvalidInputException e) {
					logger.error("{}", e.getMessage());
					return get400HttpResponse();
				}
			}
			return get405HttpResponse(POST);
		}

		if ("/board/write.html".equals(uri)) { // 사용자 목록
			if (GET.equals(httpRequest.getMethod())) {
				User findUser = getUserBySession(findSessionId);
				if (findUser != null) {
					byte[] HTML = HTMLGenerator.getHTML(findUser.getName(), "/board/write.html");
					return HttpResponseFactory.get200Response(uri, HTML);
				}
				return HttpResponseFactory.get302Response("/user/login.html");
			}
			return get405HttpResponse(GET);
		}

		if ("/board/show.html".equals(uri)) {
			if (GET.equals(httpRequest.getMethod())) {
				User findUser = getUserBySession(findSessionId);
				if (findUser != null) {
					String queryString = httpRequest.getQueryString();
					Long boardId = Long.parseLong(queryString.split("=")[1]);
					byte[] HTML = HTMLGenerator.getBoardHTML(findUser.getName(), boardId);
					return HttpResponseFactory.get200Response(uri, HTML);
				}
				return HttpResponseFactory.get302Response("/user/login.html"); // 로그인하지 않은 경우
			}
			return get405HttpResponse(GET);
		}

		return getHttpResponse(uri);
	}
}
