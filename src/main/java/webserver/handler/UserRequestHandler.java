package webserver.handler;

import Http.HttpRequest;
import Http.HttpResponse;
import db.Database;
import dto.UserDto;
import model.User;
import util.HttpResponseUtils;
import util.UserEntityConverter;


public class UserRequestHandler implements RequestHandler{
    private static final String path = "src/main/resources/templates";
    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String uri = httpRequest.getUri();
        if (uri.equals("/user/create")) {
            UserDto userDto = UserDto.fromQueryString(httpRequest.getQueryString());
            User user = UserEntityConverter.toEntity(userDto);
            Database.addUser(user);
            // index.html 으로 리다이렉트
            return HttpResponseUtils.get302HttpResponse(httpRequest);
        }
        return getHttpResponse(httpRequest, uri);
    }

}
