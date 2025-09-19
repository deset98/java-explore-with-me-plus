package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.model.ResponseRequestDto;

import java.util.List;

public interface RequestService {

    ResponseRequestDto createRequest(Long userId, Long eventId);

    List<ResponseRequestDto> getRequests(Long userId);

    ResponseRequestDto cancelRequest(Long  userId, Long requestId);
}
