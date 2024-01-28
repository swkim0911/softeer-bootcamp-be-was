package handler;

import html.HTMLGenerator;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpResponseFactory;
import http.status.HttpStatusCode;
import model.User;
import util.UriParser;

public class HomeRequestHandler implements RequestHandler{
    @Override
    public HttpResponse handle(HttpRequest httpRequest, String findSessionId){
        String uri = httpRequest.getUri();
        if (uri.equals("/")) {
            uri = "/index.html";
        }

		if (isHTML(uri)) {
			User findUser = getUserBySession(findSessionId);
			if (findUser != null) { // 세션 ID로 User 찾은 경우
				byte[] HTML = HTMLGenerator.getHTML(findUser.getName(), uri);
				return HttpResponseFactory.getHttpResponse(HttpStatusCode.OK, UriParser.getFileType(uri), HTML);
			}
        }
		return getHttpResponse(uri);
    }
}
