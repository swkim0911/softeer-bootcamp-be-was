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
                "User-Agent: Mozilla/4.0(compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; InfoPath.1)" + "\r\n" +
                "Cookie: Idea-14db9320=4d059d9c-532a-48d2-8ebe-3a46dcd4402f"+"\r\n"+
                "Accept: */*" + "\r\n" +
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
    @DisplayName("요청 uri 테스트")
    void 요청_URI_테스트() throws IOException {
        //given
        HttpRequestHeaderUtils httpRequestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(in);

        //when
        String requestPath = httpRequestHeaderUtils.getRequestUri();

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
    @DisplayName("Host 필드 테스트")
    void Host_필드_테스트() throws IOException {
        //given
        HttpRequestHeaderUtils httpRequestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(in);

        //when
        String host = httpRequestHeaderUtils.getHost();

        //then
        assertThat(host).isEqualTo("localhost:8080");
    }

    @Test
    @DisplayName("User-Agent 필드 테스트")
    void User_Agent_필드_테스트() throws IOException {
        //given
        HttpRequestHeaderUtils httpRequestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(in);

        //when
        String userAgent = httpRequestHeaderUtils.getRequestUserAgent();

        //then
        assertThat(userAgent).isEqualTo("Mozilla/4.0(compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; InfoPath.1)");
    }

    @Test
    @DisplayName("Accept 필드 테스트")
    void Accept_필드_테스트() throws IOException {
        //given
        HttpRequestHeaderUtils httpRequestHeaderUtils = HttpRequestHeaderUtils.createHeaderUtils(in);

        //when
        String accept = httpRequestHeaderUtils.getAccept();

        //then
        assertThat(accept).isEqualTo("*/*");
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
