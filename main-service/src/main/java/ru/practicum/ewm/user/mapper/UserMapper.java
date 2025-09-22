package ru.practicum.ewm.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.dto.UserInputDto;
import ru.practicum.ewm.user.dto.UserResponseDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toResponseDto(User user);

    User toEntity(UserInputDto dto);
}
