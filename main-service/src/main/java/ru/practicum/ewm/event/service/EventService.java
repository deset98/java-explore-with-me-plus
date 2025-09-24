package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.*;

import java.util.List;

public interface EventService {
    EventFullDto create(Long userId, NewEventDto newEventDto);

    List<EventShortDto> findAll(Long userId, int from, int size);

    EventFullDto findOne(Long userId, Long eventId);

    EventFullDto userUpdate(Long userId, Long eventId, UpdEventUserRequest updEventUserRequest);

    EventFullDto adminUpdate(Long eventId, UpdEventAdminRequest updEventAdminRequest);

}