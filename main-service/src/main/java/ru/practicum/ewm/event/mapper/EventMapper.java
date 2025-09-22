package ru.practicum.ewm.event.mapper;

import org.mapstruct.*;
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "eventDate", expression = "java(toInstant(newEventDto.getEventDate()))")
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "views", ignore = true)
    Event toEntity(NewEventDto newEventDto);

    @Mapping(target = "eventDate", expression = "java(toLocalDateTime(event.getEventDate()))")
    EventShortDto toShortDto(Event event);

    @Mapping(target = "createdOn", expression = "java(toLocalDateTime(event.getCreatedOn()))")
    @Mapping(target = "eventDate", expression = "java(toLocalDateTime(event.getEventDate()))")
    @Mapping(target = "publishedOn", expression = "java(toLocalDateTime(event.getPublishedOn()))")
    EventFullDto toFullDto(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "eventDate", expression = "java(toInstant(updEventUserRequest.getEventDate()))")
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    void updateFromDto(UpdEventUserRequest updEventUserRequest, @MappingTarget Event event);

    @Mapping(target = "id", ignore = true) // если нужно генерировать новый id
    @Mapping(target = "category", ignore = true) // если категорией будет управлять другой сервис
    @Mapping(target = "initiator", ignore = true) // инициатор тоже отдельной логикой
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "views", ignore = true)
    Event toEntity(EventFullDto eventFullDto);

    default Instant toInstant(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.UTC);
    }

    default LocalDateTime toLocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}