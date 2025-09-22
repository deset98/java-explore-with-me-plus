package ru.practicum.ewm.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(NewUserRequest dto);
}
