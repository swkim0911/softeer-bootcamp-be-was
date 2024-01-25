package handler;

import db.Database;
import html.HTMLGenerator;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpResponseFactory;
import http.status.HttpStatusCode;
import model.User;
import session.SessionManager;
import util.UriParser;

import java.util.Map;
import java.util.Optional;


public class HomeRequestHandler implements RequestHandler{
    @Override
    public HttpResponse handle(HttpRequest httpRequest){
        String uri = httpRequest.getUri();
        if (uri.equals("/")) {
            uri = "/index.html";
        }
		if (uri.equals("/index.html")) {
			String[] cookies = httpRequest.getCookies();
			for (String cookie : cookies) {
				if (isCookieValid(cookie)) { // SID 쿠키가 있는 경우 동적 html 전송
					String sessionId = getSessionId(cookie);
					String userId = SessionManager.getUserIdBySessionId(sessionId);
					Optional<User> optionalUser = Database.findUserById(userId);
					if (optionalUser.isPresent()) {
						User findUser = optionalUser.get();
						byte[] homeHTML = HTMLGenerator.getHomeHTML(uri, findUser.getName());
						return HttpResponseFactory.getHttpResponse(HttpStatusCode.OK, UriParser.getFileType(uri), homeHTML);
					}
				}
			}
		}
		return getHttpResponse(uri);
    }
}
