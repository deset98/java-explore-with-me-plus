package ru.practicum.ewm.user.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.ewm.user.model.UserInputDto;
import ru.practicum.ewm.user.model.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> findAll(List<Long> ids, Integer from, Integer size);

    UserResponseDto findById(Long id);

    UserResponseDto add(UserInputDto userInputDto);

    void delete(Long userId);
}
