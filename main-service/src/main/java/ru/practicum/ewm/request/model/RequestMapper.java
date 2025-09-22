package ru.practicum.ewm.request.model;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class RequestMapper {

    private EventMapper eventMapper;

    public static Request toEntity(User requester, Event event, Status status) {
        return new Request(
                null,
                event,
                requester,
                LocalDateTime.now(),
                status
        );
    }

    public static ResponseRequestDto toResponseEntity(Request request) {
        EventShortDto eventDto = eventMapper.toShortDto(request.getEvent());
        return new ResponseRequestDto(
                request.getId(),
                eventDto,
                request.getRequester(),
                request.getCreated(),
                request.getStatus()
        );
    }
}
