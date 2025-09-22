package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll(List<Long> ids, Integer from, Integer size);

    UserDto add(NewUserRequest userInputDto);

    void delete(Long userId);
}
