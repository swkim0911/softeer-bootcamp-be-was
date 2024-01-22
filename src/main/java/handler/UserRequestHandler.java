package handler;

import http.HttpRequest;
import http.HttpResponse;
import db.Database;
import model.User;
import util.HttpResponseUtils;
import util.QueryStringParser;

import java.util.Map;

public class UserRequestHandler implements RequestHandler{
    @Override
    public HttpResponse handle(HttpRequest httpRequest){
        String uri = httpRequest.getUri();
        if (uri.equals("/user/create")) {
			Map<String, String> parameters = QueryStringParser.getParameters(httpRequest.getBody());
            User user = User.create(parameters);
            Database.addUser(user);
			// index.html 으로 리다이렉트
            return HttpResponseUtils.get302HttpResponse(httpRequest);
        }
        return getHttpResponse(httpRequest, uri);
    }
}
