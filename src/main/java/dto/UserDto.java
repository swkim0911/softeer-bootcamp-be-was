package dto;

import java.util.HashMap;
import java.util.Map;

public class UserDto {

    private String userId;
    private String password;
    private String name;
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public static UserDto fromQueryString(String queryString) {
        UserDto userDTO = new UserDto();
        Map<String, String> keyValueMap = parseQueryString(queryString);

        // 각 필드에 대입
        userDTO.setUserId(keyValueMap.get("userId"));
        userDTO.setPassword(keyValueMap.get("password"));
        userDTO.setName(keyValueMap.get("name"));
        userDTO.setEmail(keyValueMap.get("email"));

        return userDTO;
    }

    private static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> keyValueMap = new HashMap<>();
        String[] parameters = queryString.split("&");
        for (String parameter : parameters) {
            String[] keyValue = parameter.split("=");
            String key = keyValue[0];
            String value = keyValue[1];
            keyValueMap.put(key, value);
        }
        return keyValueMap;
    }
}
