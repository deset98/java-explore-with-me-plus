package ru.practicum.ewm.request.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RequestMapper {

    public Request toEntity(User requester, Event event, Status status) {
        return new Request(
                null,
                event,
                requester,
                LocalDateTime.now(),
                status
        );
    }

    public ParticipationRequestDto toResponseEntity(Request request) {
        return new ParticipationRequestDto(
                request.getId(),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getCreated(),
                request.getStatus()
        );
    }
}
