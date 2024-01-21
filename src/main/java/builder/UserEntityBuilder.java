package builder;

import model.User;

public class UserEntityBuilder {

    private String userId;
    private String password;
    private String name;
    private String email;

    public UserEntityBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public UserEntityBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserEntityBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserEntityBuilder email(String email) {
        this.email = email;
        return this;
    }

    public User build() {
        return new User(userId, password, name, email);
    }
}
