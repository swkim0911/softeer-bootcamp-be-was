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
			//todo 쿠키가 있는지 확인하기 -> index.html 파일에 동적 html
			Map<String, String> headerFields = httpRequest.getHeaderFields();
			String cookieValues = headerFields.getOrDefault("Cookie", ""); // 쿠키 필드가 없는 경우 빈 문자열
			String targetKey = "SID";
			String[] cookies = cookieValues.split(";");
			for (String cookie : cookies) {
				String[] keyValue = cookie.trim().split("=");
				String sessionId = keyValue[1];
				if (keyValue[0].equals(targetKey) && SessionManager.containsSession(sessionId)) { // 쿠키가 있는 경우 동적 html
					String userId = SessionManager.getUserIdBySessionId(sessionId);
					Optional<User> optionalUser = Database.findUserById(userId);
					if (optionalUser.isPresent()) {
						User findUser = optionalUser.get();
						byte[] homeHTML = HTMLGenerator.getHomeHTML(findUser.getName());
						return HttpResponseFactory.getHttpResponse(HttpStatusCode.OK, UriParser.getFileType(uri), homeHTML);
					}
				}
			}
		}
		return getHttpResponse(uri);
    }
}
