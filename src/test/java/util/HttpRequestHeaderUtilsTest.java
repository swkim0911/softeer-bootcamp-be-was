package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.*;

public class HttpRequestHeaderUtilsTest {

    private InputStream in;
    @BeforeEach
    void init() {
        String url = "GET /index.html HTTP/1.1" + "\r\n" +
                "Host: localhost:8080" + "\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8" + "\r\n" +
                "Accept-Language: ko-KR,ko;q=0.9" + "\r\n" +
                "Cookie: Idea-14db9320=4d059d9c-532a-48d2-8ebe-3a46dcd4402f"+"\r\n"+
                "\r\n";
        in = new ByteArrayInputStream(url.getBytes());
    }

    @Test
    @DisplayName("요청 메서드 테스트")
    void 요청_메서드_테스트() throws IOException {
        //given
        HttpRequestHeaderUtils httpRequestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(in);

        //when
        String requestMethod = httpRequestHeaderUtils.getRequestMethod();

        //then
        assertThat(requestMethod).isEqualTo("GET");
    }

    @Test
    @DisplayName("요청 경로 테스트")
    void 요청_경로_테스트() throws IOException {
        //given
        HttpRequestHeaderUtils httpRequestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(in);

        //when
        String requestPath = httpRequestHeaderUtils.getRequestPath();

        //then
        assertThat(requestPath).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("프로토콜 버전 테스트")
    void 프로토콜_버전_테스트() throws IOException {
        //given
        HttpRequestHeaderUtils httpRequestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(in);

        //when
        String requestVersion = httpRequestHeaderUtils.getRequestVersion();

        //then
        assertThat(requestVersion).isEqualTo("HTTP/1.1");
    }

    @Test
    @DisplayName("Accept 필드 테스트")
    void Accept_필드_테스트() throws IOException {
        //given
        HttpRequestHeaderUtils httpRequestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(in);

        //when
        String accept = httpRequestHeaderUtils.getAccept();

        //then
        assertThat(accept).isEqualTo("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    }

    @Test
    @DisplayName("Accept_Language 필드 테스트")
    void Accept_Language_필드_테스트() throws IOException {
        //given
        HttpRequestHeaderUtils httpRequestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(in);

        //when
        String acceptLanguage = httpRequestHeaderUtils.getAcceptLanguage();

        //then
        assertThat(acceptLanguage).isEqualTo("ko-KR,ko;q=0.9");
    }

    @Test
    @DisplayName("Cookie 필드 테스트")
    void Cookie_필드_테스트() throws IOException {
        //given
        HttpRequestHeaderUtils httpRequestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(in);

        //when
        String cookie = httpRequestHeaderUtils.getCookie();

        //then
        assertThat(cookie).isEqualTo("Idea-14db9320=4d059d9c-532a-48d2-8ebe-3a46dcd4402f");
    }


}
