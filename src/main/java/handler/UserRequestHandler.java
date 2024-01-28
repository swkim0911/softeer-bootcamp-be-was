package handler;

import html.HTMLGenerator;
import http.HttpRequest;
import http.HttpResponse;
import db.Database;
import http.status.HttpStatusCode;
import model.User;
import session.SessionManager;
import http.HttpResponseFactory;
import util.QueryStringParser;
import util.UriParser;

import java.util.Map;

public class UserRequestHandler implements RequestHandler{
    @Override
    public HttpResponse handle(HttpRequest httpRequest, String findSessionId){
        String uri = httpRequest.getUri();
        if (uri.equals("/user/create")) { // 회원가입
			Map<String, String> queryParameters = QueryStringParser.getParameters(httpRequest.getBody());
            User user = User.create(queryParameters);
            Database.addUser(user);
			// index.html 으로 리다이렉트
			return HttpResponseFactory.get302HttpResponse("/index.html");
        }

		if (uri.equals("/user/login")) { // 로그인
			Map<String, String> queryParameters = QueryStringParser.getParameters(httpRequest.getBody());
			User findUser = Database.findUserById(queryParameters.get("userId"));

			if (findUser != null) { //아이디가 있는 경우 비밀번호 일치하는지 확인
				String findUserId = findUser.getUserId();
				String findUserPassword = findUser.getPassword();

				if (verifyPassword(findUserPassword, queryParameters.get("password"))) {
					String sessionId = SessionManager.generateSessionId(findUserId);
					return HttpResponseFactory.get302HttpResponse("/index.html", sessionId);
				}
			}
			// 아이디가 없는 경우
			return HttpResponseFactory.get302HttpResponse("/user/login_failed.html");
		}

		if (uri.equals("/user/list.html")) { // 사용자 목록
			User findUser = getUserBySession(findSessionId);
			if (findUser != null) {
				byte[] userListHTML = HTMLGenerator.getUserListHTML();
				return HttpResponseFactory.getHttpResponse(HttpStatusCode.OK, UriParser.getFileType(uri), userListHTML);
			}
			return HttpResponseFactory.get302HttpResponse("/user/login.html");
		}
		return getHttpResponse(uri);
    }

	private static boolean verifyPassword(String findUserPassword, String password) {
		return findUserPassword.equals(password);
	}
}
