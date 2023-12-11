package com.example.test.user.service;

import com.example.test.exception.NotFoundException;
import com.example.test.user.UserMapperUtil;
import com.example.test.user.dao.UserRepository;
import com.example.test.user.dto.UserDto;
import com.example.test.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User addUser(UserDto userDto) {

        User user = repository.save(UserMapperUtil.toUser(userDto));

        log.info("Добавлен пользователь с паспортными данными {}", user.getPassport());
        return user;
    }

    @Override
    public User findUserById(String passport) {
        User user = repository.findById(passport)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %s не зарегистрирован "
                        + "в базе данных.", passport)));
        log.info("Запрошен пользователь с id {}. Данные получены", passport);
        return user;
    }
}
