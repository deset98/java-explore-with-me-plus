package ru.practicum.ewm.event.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.mapper.UserMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class})
public interface EventMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "eventDate", expression = "java(toInstantForMap(newEventDto.getEventDate()))")
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "paid", expression = "java(newEventDto.getPaid() != null ? newEventDto.getPaid() : false)")
    @Mapping(target = "participantLimit",
            expression = "java(newEventDto.getParticipantLimit() != null ? newEventDto.getParticipantLimit() : 0)")
    @Mapping(target = "requestModeration",
            expression = "java(newEventDto.getRequestModeration() != null ? newEventDto.getRequestModeration() : true)")

    @Mapping(target = "state", ignore = true)
    @Mapping(target = "views", ignore = true)
    Event toEntity(NewEventDto newEventDto);

    @Mapping(target = "eventDate", expression = "java(toLocalDateTimeForMap(event.getEventDate()))")
    EventShortDto toShortDto(Event event);

    @Mapping(target = "createdOn", expression = "java(toLocalDateTimeForMap(event.getCreatedOn()))")
    @Mapping(target = "eventDate", expression = "java(toLocalDateTimeForMap(event.getEventDate()))")
    @Mapping(target = "publishedOn", expression = "java(toLocalDateTimeForMap(event.getPublishedOn()))")
    EventFullDto toFullDto(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "eventDate",
            expression = "java(toInstantForUpdate(updEventUserRequest.getEventDate(), event.getEventDate()))")
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    void updateFromDto(UpdEventUserRequest updEventUserRequest, @MappingTarget Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "eventDate",
            expression = "java(toInstantForUpdate(updEventAdminRequest.getEventDate(), event.getEventDate()))")
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    void updateFromDto(UpdEventAdminRequest updEventAdminRequest, @MappingTarget Event event);

    default Instant toInstantForMap(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toInstant(ZoneOffset.UTC) : null;
    }

    default LocalDateTime toLocalDateTimeForMap(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }

    default Instant toInstantForUpdate(LocalDateTime newDateTime, Instant currentValue) {
        return newDateTime != null ? newDateTime.toInstant(ZoneOffset.UTC) : currentValue;
    }
}