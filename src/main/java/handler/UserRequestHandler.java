package handler;

import http.HttpRequest;
import http.HttpResponse;
import db.Database;
import http.status.HttpStatusCode;
import model.User;
import session.SessionManager;
import util.HttpResponseUtils;
import util.QueryStringParser;

import java.util.Map;
import java.util.Optional;

public class UserRequestHandler implements RequestHandler{
    @Override
    public HttpResponse handle(HttpRequest httpRequest){
        String uri = httpRequest.getUri();
		String version = httpRequest.getVersion();
        if (uri.equals("/user/create")) { // 회원가입
			Map<String, String> parameters = QueryStringParser.getParameters(httpRequest.getBody());
            User user = User.create(parameters);
			SessionManager.generateSessionId(user.getUserId());
            Database.addUser(user);
			// index.html 으로 리다이렉트
			return HttpResponseUtils.get302HttpResponse("/index.html", version);
        }
		if (uri.equals("/user/login")) { // 로그인
			Map<String, String> parameters = QueryStringParser.getParameters(httpRequest.getBody());
			Optional<User> userOptional = Database.findUserById(parameters.get("userId"));
			if (userOptional.isPresent()) { //아이디가 있는 경우 아이디 비밀번호 일치하는지 확인
				User findUser = userOptional.get();
				String findUserId = findUser.getUserId();
				String findUserPassword = findUser.getPassword();
				if (findUserId.equals(parameters.get("userId")) && findUserPassword.equals(parameters.get("password"))) {
					return HttpResponseUtils.get302HttpResponse("/index.html", version);
				}
			}
			// 아이디가 없거나 아이디, 비밀번호가 불일치하는 경우
			return HttpResponseUtils.get302HttpResponse("/user/login_failed.html", version);
		}
		return getHttpResponse(version, uri);
    }
}
