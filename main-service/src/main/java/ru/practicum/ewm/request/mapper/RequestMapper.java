package ru.practicum.ewm.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.user.mapper.UserMapper;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, EventMapper.class})
public interface RequestMapper {

    @Mapping(target = "event", expression = "java(request.getEvent().getId())")
    @Mapping(target = "requester", expression = "java(request.getRequester().getId())")
    ParticipationRequestDto toDto(Request request);
}