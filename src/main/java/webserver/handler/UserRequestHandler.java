package webserver.handler;

import Http.HttpRequest;
import Http.HttpResponse;
import db.Database;
import dto.UserDto;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtils;
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
            // home으로 리다이렉트
            return HttpResponseUtils.get302HttpResponse(httpRequest);
        }
        String fileType = FileUtils.getFileType(uri);
        if (fileType.equals("html")) {
            byte[] body = FileUtils.readFile(path + uri);
            return HttpResponseUtils.get200HttpResponse(httpRequest, body);
        }
        return HttpResponseUtils.get404HttpResponse(httpRequest);
    }
}
