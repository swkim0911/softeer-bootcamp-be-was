package handler;

import db.Database;
import http.HttpRequest;
import http.HttpResponse;
import http.builder.HttpRequestBuilder;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserRequestHandlerTest {

	@Test
	@DisplayName("회원가입 요청시 사용자 정보 db 저장")
	void 회원가입시_db_저장() {
		//given
		RequestHandler requestHandler = new UserRequestHandler();
		HttpRequest httpRequest = new HttpRequestBuilder()
			.method("GET")
			.uri("/user/create")
			.queryString("userId=hello&password=sdd&name=swk&email=test@naver.com")
			.version("HTTP/1.1")
			.build();

		//when
		HttpResponse httpResponse = requestHandler.handle(httpRequest);
		User user = Database.findUserById("hello");

		//then
		assertThat(user).isNotNull();
		assertThat(user.getPassword()).isEqualTo("sdd");
		assertThat(user.getName()).isEqualTo("swk");
		assertThat(user.getEmail()).isEqualTo("test@naver.com");
	}
}
