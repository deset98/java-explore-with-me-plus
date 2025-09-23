package ru.practicum.ewm.event.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdEventUserRequest;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.mapper.UserMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring",
        uses = {LocationMapper.class, CategoryMapper.class, UserMapper.class})
public interface EventMapper {

    Event toEntity(NewEventDto newEventDto);

    Event toEntity(EventFullDto eventFullDto);

    EventShortDto toShortDto(Event event);

    EventFullDto toFullDto(Event event);

    void updateFromDto(UpdEventUserRequest updEventUserRequest, @MappingTarget Event event);

    default Instant map(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toInstant(ZoneOffset.UTC) : null;
    }

    default LocalDateTime map(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }
}