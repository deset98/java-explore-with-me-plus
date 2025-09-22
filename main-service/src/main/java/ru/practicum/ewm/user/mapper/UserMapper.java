package ru.practicum.ewm.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.UserInputDto;
import ru.practicum.ewm.user.model.UserResponseDto;
import ru.practicum.ewm.user.model.UserShortDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toResponseDto(User user);

    User toEntity(UserInputDto dto);

    User toEntity(UserResponseDto dto);

    UserShortDto toUserShortDto(User user);
}
