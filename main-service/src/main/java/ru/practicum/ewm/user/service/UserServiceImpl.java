package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.dto.RequestValidDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAll(List<Long> ids, Integer from, Integer size) {
        log.debug("Сервис UserServiceImpl; Метод findAll(); ids={}, from={}, size={}", ids, from, size);

        RequestValidDto requestValidDto = new RequestValidDto(ids, from, size);
        return findAll(requestValidDto);
    }

    @Override
    public UserDto findById(Long id) {
        User result = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return userMapper.toResponseDto(result);
    }

    @Override
    public UserDto add(NewUserRequest userInputDto) {
        log.debug("Сервис UserServiceImpl; Метод add(); userInputDto={}", userInputDto);

        String localpart = userInputDto.getEmail().substring(0, userInputDto.getEmail().indexOf('@'));
        if (localpart.length() > 64) {
            throw new BadRequestException("Localpart is too long");
        }
        User savedUser = userRepository.save(userMapper.toEntity(userInputDto));

        log.debug("Сервис UserServiceImpl; Метод add(); savedUser={}", userInputDto);

        return userMapper.toResponseDto(savedUser);
    }

    @Override
    public void delete(Long userId) {
        log.debug("Сервис UserServiceImpl; Метод delete(); userId={}", userId);


        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new NotFoundException("User userId=" + userId + " not found");
        }
    }

    private List<UserDto> findAll(RequestValidDto dto) {
        return userRepository.findAllByParams(dto.getIds(), dto.getFrom(), dto.getSize()).stream()
                .map(user -> userMapper.toResponseDto(user))
                .collect(Collectors.toUnmodifiableList());
    }
}
