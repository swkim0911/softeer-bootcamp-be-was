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

public class HomeRequestHandler implements RequestHandler{
    @Override
    public HttpResponse handle(HttpRequest httpRequest){
        String uri = httpRequest.getUri();
        if (uri.equals("/")) {
            uri = "/index.html";
        }
		if (isHTML(uri)) {
			String[] cookies = httpRequest.getCookies();
            for (String cookie : cookies) {
                if (isCookieValid(cookie)) { // SID 쿠키가 있는 경우
                    String sessionId = getSessionId(cookie);
					User findUser = getUserBySession(sessionId);
					if (findUser != null) { // 세션 ID로 User 찾은 경우
                        byte[] homeHTML = HTMLGenerator.getHomeHTML(findUser.getName());
                        return HttpResponseFactory.getHttpResponse(HttpStatusCode.OK, UriParser.getFileType(uri), homeHTML);
                    }
                }
            }
        }
		return getHttpResponse(uri);
    }

	private static User getUserBySession(String sessionId) {
		String userId = SessionManager.getUserIdBySessionId(sessionId);
        return Database.findUserById(userId);
	}
}
