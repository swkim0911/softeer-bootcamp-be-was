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
import java.util.Optional;

public class UserRequestHandler implements RequestHandler{
    @Override
    public HttpResponse handle(HttpRequest httpRequest){
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
			Optional<User> userOptional = Database.findUserById(queryParameters.get("userId"));

			if (userOptional.isPresent()) { //아이디가 있는 경우 비밀번호 일치하는지 확인
				User findUser = userOptional.get();
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
		if (uri.equals("/user/list.html")) {
			String[] cookies = httpRequest.getCookies();
			for (String cookie : cookies) {
				if (isCookieValid(cookie)) { // SID 쿠키가 있는 경우 동적 html
					String sessionId = getSessionId(cookie);
					String userId = SessionManager.getUserIdBySessionId(sessionId);
					Optional<User> optionalUser = Database.findUserById(userId);
					if (optionalUser.isPresent()) {
						//todo 로그인 한 사람이면 사용라 리스트를 포함한 동적 html 생성하기
//						byte[] userListHTML = HTMLGenerator.getUserListHTML();
//						return HttpResponseFactory.getHttpResponse(HttpStatusCode.OK, UriParser.getFileType(uri), userListHTML);
					}
				}
			}
			//todo 로그인 하지 않은 경우 /user/login.html 리다이렉트
			HttpResponseFactory.get302HttpResponse("/user/login.html");
		}
		return getHttpResponse(uri);
    }

	private static boolean verifyPassword(String findUserPassword, String password) {
		return findUserPassword.equals(password);
	}
}
