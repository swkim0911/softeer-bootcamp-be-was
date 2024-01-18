package util;

import dto.UserDto;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserEntityConverterTest {

    @Test
    @DisplayName("UserDto 에서 User 엔티티 변환 테스트")
    void UserDto_to_User() {
        //given
        String queryString = "userId=hello&password=kkk&name=lee&email=test@naver.com";
        //when
        UserDto userDto = UserDto.fromQueryString(queryString);
        User user = UserEntityConverter.toEntity(userDto);
        //then
        assertThat(user.getUserId()).isEqualTo("hello");
        assertThat(user.getPassword()).isEqualTo("kkk");
        assertThat(user.getName()).isEqualTo("lee");
        assertThat(user.getEmail()).isEqualTo("test@naver.com");
    }

}