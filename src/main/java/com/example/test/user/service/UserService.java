package com.example.test.user.service;

import com.example.test.user.dto.UserDto;
import com.example.test.user.model.User;

public interface UserService {

    User addUser(UserDto userDto);

    User findUserById(String passport);
}
