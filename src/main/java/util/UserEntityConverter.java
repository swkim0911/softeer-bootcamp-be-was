package util;

import dto.UserDto;
import model.User;

public class UserEntityConverter {
    public static User toEntity(UserDto userDto) {
        return new User(userDto.getUserId(), userDto.getPassword(), userDto.getName(), userDto.getEmail());
    }

}
