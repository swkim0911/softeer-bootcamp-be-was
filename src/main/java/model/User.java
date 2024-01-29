package model;

import java.util.Map;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public static User create(Map<String, String> queryParameters) {
        return new User(queryParameters.get("userId"), queryParameters.get("password"), queryParameters.get("name"), queryParameters.get("email"));
    }

    private User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
