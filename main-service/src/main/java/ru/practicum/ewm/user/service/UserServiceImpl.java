package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.RequestValidDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.UserInputDto;
import ru.practicum.ewm.user.model.UserResponseDto;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDto> findAll(List<Long> ids, Integer from, Integer size) {
        RequestValidDto requestValidDto = new RequestValidDto(ids, from, size);
        return findAll(requestValidDto);
    }

    @Override
    public UserResponseDto add(UserInputDto userInputDto) {
        User savedUser = userRepository.save(userMapper.toEntity(userInputDto));
        return userMapper.toResponseDto(savedUser);
    }

    @Override
    public ResponseEntity<Void> delete(Long userId) {
        userRepository.delete(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found")));
        return ResponseEntity.noContent().build();
    }

    private List<UserResponseDto> findAll(RequestValidDto dto) {
        return userRepository.findAllByParams(dto.getIds(), pageable).stream()
                .map(user -> userMapper.toResponseDto(user))
                .collect(Collectors.toUnmodifiableList());
    }
}
