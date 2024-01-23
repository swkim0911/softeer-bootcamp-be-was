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
	@DisplayName("회원가입 요청시 사용자 정보 db 저장 성공 테스트")
	void 회원가입시_db_저장_O() {
		//given
		String body = "userId=hello&password=sdd&name=swk&email=test@naver.com";

		HttpRequest httpRequest = new HttpRequestBuilder()
			.method("POST")
			.uri("/user/create")
			.body(body)
			.build();

		RequestHandler requestHandler = new UserRequestHandler();

		//when
		HttpResponse httpResponse = requestHandler.handle(httpRequest);
		User user = Database.findUserById("hello").orElseThrow(NullPointerException::new);

		//then
		assertThat(user).isNotNull();
		assertThat(user.getPassword()).isEqualTo("sdd");
		assertThat(user.getName()).isEqualTo("swk");
		assertThat(user.getEmail()).isEqualTo("test@naver.com");
	}

	@Test
	@DisplayName("회원가입이 완료된 후 로그인 성공 테스트")
	void 회원가입_O_로그인_O() {

	}

	@Test
	@DisplayName("회원가입이 완료된 후 잘못된 로그인 정보 입력으로 로그인 실패 테스트")
	void 회원가입_O_로그인_X() {

	}

	@Test
	@DisplayName("회원가입을 하지 않고 로그인을 시도하여 로그인 실패 테스트")
	void 회원가입_X_로그인_X() {

	}
}
