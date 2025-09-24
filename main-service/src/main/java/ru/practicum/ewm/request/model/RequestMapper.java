package ru.practicum.ewm.request.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RequestMapper {

    private final EventMapper eventMapper;

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
        EventShortDto eventDto = eventMapper.toShortDto(request.getEvent());
        return new ParticipationRequestDto(
                request.getId(),
                eventDto,
                request.getRequester(),
                request.getCreated(),
                request.getStatus()
        );
    }
}
