package handler;

import http.HttpRequest;
import http.HttpResponse;
import db.Database;
import http.status.HttpStatusCode;
import model.User;
import util.HttpResponseUtils;
import util.QueryStringParser;

import java.util.Map;

public class UserRequestHandler implements RequestHandler{
    @Override
    public HttpResponse handle(HttpRequest httpRequest){
        String uri = httpRequest.getUri();
		String version = httpRequest.getVersion();
        if (uri.equals("/user/create")) {
			Map<String, String> parameters = QueryStringParser.getParameters(httpRequest.getBody());
            User user = User.create(parameters);
            Database.addUser(user);
			// index.html 으로 리다이렉트
			return HttpResponseUtils.get302HttpResponse(HttpStatusCode.FOUND, version);
        }
		if (uri.equals("/user/login")) {
			Map<String, String> parameters = QueryStringParser.getParameters(httpRequest.getBody());
			//todo 사용자 아이디 비교해서 로그인 가능 여부 확인
		}

		return getHttpResponse(version, uri);
    }
}
