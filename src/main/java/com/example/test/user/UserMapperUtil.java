package com.example.test.user;

import com.example.test.user.dto.UserDto;
import com.example.test.user.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapperUtil {

    public User toUser(UserDto userDto) {
        User.UserBuilder userBuilder = User.builder();

        userBuilder.passport(userDto.getPassport());
        userBuilder.name(userDto.getName());
        userBuilder.dateOfBirth(userDto.getDateOfBirth());

        return userBuilder.build();
    }
}
