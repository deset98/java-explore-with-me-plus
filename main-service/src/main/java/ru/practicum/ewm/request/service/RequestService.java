package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.model.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    ParticipationRequestDto createRequest(Long userId, Long eventId);

    List<ParticipationRequestDto> getRequests(Long userId);

    ParticipationRequestDto cancelRequest(Long  userId, Long requestId);
}
