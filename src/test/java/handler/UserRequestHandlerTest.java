package handler;

import db.Database;
import http.HttpRequest;
import http.HttpResponse;
import http.builder.HttpRequestBuilder;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class UserRequestHandlerTest {

	@Test
	@DisplayName("회원가입 요청시 사용자 정보 db 저장 성공 테스트")
	void 회원가입시_db_저장_O() {
		//given
		RequestHandler requestHandler = new UserRequestHandler();
		String body = "userId=hello&password=sdd&name=swk&email=test@naver.com";
		HttpRequest httpRequest = new HttpRequestBuilder()
			.method("POST")
			.uri("/user/create")
			.version("HTTP/1.1")
			.body(body)
			.build();

		//when
		HttpResponse httpResponse = requestHandler.handle(httpRequest);
		User user = Database.findUserById("hello").orElseThrow(NullPointerException::new);

		//then
		assertThat(user).isNotNull();
		assertThat(user.getPassword()).isEqualTo("sdd");
		assertThat(user.getName()).isEqualTo("swk");
		assertThat(user.getEmail()).isEqualTo("test@naver.com");
	}
}
