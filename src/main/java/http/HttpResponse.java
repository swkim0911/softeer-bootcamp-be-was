package http;

import http.status.HttpStatusCode;

import java.util.Map;
import java.util.Optional;

public class HttpResponse {
    private final String version;
    private final HttpStatusCode statusCode;
    private final Map<String, String> headerFields;
    private final Optional<byte[]> body; //todo optional 로 하지 않고 스트링으로 하기. body 없으면 ""(empty) -> 예외처리 부분도 수장(runtime -> 체크 예외)
	//todo http request, response가 잘 만들어지는지 확인하는 테스트


    public HttpResponse(String version, HttpStatusCode statusCode, Map<String, String> headerFields, Optional<byte[]> body) {
        this.version = version;
        this.statusCode = statusCode;
        this.headerFields = headerFields;
        this.body = body;
    }

    public String getVersion() {
        return version;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaderFields() {
        return headerFields;
    }

    public Optional<byte[]> getBody() {
        return body;
    }

    public String getResponseHeader() {
        StringBuilder sb = new StringBuilder();
        String responseLine = version + " " + statusCode + "\r\n";
        sb.append(responseLine);
        for (Map.Entry<String, String> line : headerFields.entrySet()) {
            sb.append(line.getKey()).append(": ").append(line.getValue()).append("\r\n");
        }
        sb.append("\r\n");
        return sb.toString();
    }
}
