package handler;

import html.HTMLGenerator;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpResponseFactory;
import model.User;

import static http.method.HttpMethod.*;


public class HomeRequestHandler implements RequestHandler{
    @Override
    public HttpResponse handle(HttpRequest httpRequest, String findSessionId){
        String uri = httpRequest.getUri();
        if ("/".equals(uri)) {
            uri = "/index.html";
        }
		if ("/index.html".equals(uri)) {
			if (GET.equals(httpRequest.getMethod())) {
				User findUser = getUserBySession(findSessionId);
				if (findUser != null) { // 세션 ID로 User 찾은 경우
					byte[] HTML = HTMLGenerator.getLoginHomeHTML(findUser.getName());
					return HttpResponseFactory.get200Response(uri, HTML);
				}
				byte[] HTML = HTMLGenerator.getHomeHTML();
				return HttpResponseFactory.get200Response(uri, HTML); // 로그인 아니면 사용자 정보는 없고 홈 화면 동적 생성
			}
			return get405HttpResponse(GET);
		}
		return getHttpResponse(uri);
    }
}
