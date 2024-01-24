package util.mapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContentTypeMapper {
    private static Map<String, String> contentTypeMap = new ConcurrentHashMap<>();

	static {
        contentTypeMap.put("html", "text/html");
        contentTypeMap.put("js", "application/javascript");
        contentTypeMap.put("css", "text/css");
        contentTypeMap.put("woff", "font/woff");
        contentTypeMap.put("ttf", "font/ttf");
        contentTypeMap.put("ico", "image/x-icon");
    }

	public static String getContentType(String fileType) {
		return contentTypeMap.get(fileType);
	}
}
