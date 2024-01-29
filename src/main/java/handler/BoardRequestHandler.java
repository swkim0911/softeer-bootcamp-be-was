package handler;

import html.HTMLGenerator;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpResponseFactory;
import model.User;


public class BoardRequestHandler implements RequestHandler{

	@Override
	public HttpResponse handle(HttpRequest httpRequest, String findSessionId) {
		String uri = httpRequest.getUri();
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
