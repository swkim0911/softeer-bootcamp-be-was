package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeaderParser {

    // InputStream 에서 header 파싱
    public static Map<String, String> parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        Map<String, String> requestHeaderMap = new HashMap<>();
        String line = parseRequestLine(br, requestHeaderMap);
        while (!line.isEmpty()) { //마지막 라인 = ""(empty)
            line = parseHeader(br, requestHeaderMap);
        }
        return requestHeaderMap;
    }

    private static String parseHeader(BufferedReader br, Map<String, String> requestHeaderMap) throws IOException {
        String line = br.readLine();
        String[] header = line.split(":",2);
        if (header.length > 1) {
            String field = header[0];
            String value = header[1].stripLeading();
            requestHeaderMap.put(field, value);
        }
        return line;
    }

    // 헤더 라인 파싱
    private static String parseRequestLine(BufferedReader br, Map<String, String> requestHeaderMap) throws IOException {
        String line = br.readLine();
        String[] requestLine = line.split(" ");
        requestHeaderMap.put("Method", requestLine[0]);
        requestHeaderMap.put("RequestUri", requestLine[1]);
        requestHeaderMap.put("Version", requestLine[2]);
        return line;
    }
}
