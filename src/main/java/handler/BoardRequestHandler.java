package handler;

import exception.InvalidInputException;
import html.HTMLGenerator;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpResponseFactory;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.QueryStringParser;

import java.util.Map;

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
					//todo 게시판 db에 저장
					return HttpResponseFactory.get302Response("/index.html");
				} catch (InvalidInputException e) {
					logger.error("{}", e.getMessage());
					return get400HttpResponse();
				}

			}
			return get405HttpResponse(POST);
		}
		if ("/board/write.html".equals(uri)) { // 사용자 목록
			User findUser = getUserBySession(findSessionId);
			if (findUser != null) {
				byte[] userListHTML = HTMLGenerator.getHTML(findUser.getName(), "/board/write.html");
				return HttpResponseFactory.get200Response(uri, userListHTML);
			}
			return HttpResponseFactory.get302Response("/user/login.html");
		}
		return getHttpResponse(uri);
	}
}
