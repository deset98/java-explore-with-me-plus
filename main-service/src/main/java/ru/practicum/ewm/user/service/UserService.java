package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.UserInputDto;
import ru.practicum.ewm.user.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> findAll(List<Long> ids, Integer from, Integer size);

    UserResponseDto add(UserInputDto userInputDto);

    void delete(Long userId);
}
