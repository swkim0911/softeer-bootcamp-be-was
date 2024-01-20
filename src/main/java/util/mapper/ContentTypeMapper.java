package util.mapper;

import java.util.HashMap;
import java.util.Map;

public class ContentTypeMapper {
    static public Map<String, String> contentTypeMap = new HashMap<>();
    static {
        contentTypeMap.put("html", "text/html");
        contentTypeMap.put("js", "application/javascript");
        contentTypeMap.put("css", "text/css");
        contentTypeMap.put("woff", "font/woff");
        contentTypeMap.put("ttf", "font/ttf");
    }
}
