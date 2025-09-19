package ru.practicum.ewm.request.model;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class RequestMapper {

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
        return new ResponseRequestDto(
                request.getId(),
                request.getEvent(),
                request.getRequester(),
                request.getCreated(),
                request.getStatus()
        );
    }
}
