package util.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContentTypeMapper {
    static public Map<String, String> contentTypeMap = new ConcurrentHashMap<>();

	static {
        contentTypeMap.put("html", "text/html");
        contentTypeMap.put("js", "application/javascript");
        contentTypeMap.put("css", "text/css");
        contentTypeMap.put("woff", "font/woff");
        contentTypeMap.put("ttf", "font/ttf");
        contentTypeMap.put("ico", "image/x-icon");
    }
}
