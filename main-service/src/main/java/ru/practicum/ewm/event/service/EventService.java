package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdEventUserRequest;

import java.util.List;

public interface EventService {

    // Private API:
    EventFullDto create(Long userId, NewEventDto newEventDto);

    EventFullDto getByUser(Long userId, Long eventId);

    List<EventShortDto> getAllByUser(Long userId, int from, int size);

    EventFullDto updateByUser(Long userId, Long eventId, UpdEventUserRequest updEventUserRequest);

    // Admin API:
    EventFullDto updateByAdmin(Long eventId, UpdEventAdminRequest updEventAdminRequest);

    List<EventFullDto> searchForAdmin(AdminEventSearchParams params);

    // Public API:
    List<EventFullDto> getPublicEventsBy(UserEventSearchParams params);

    EventFullDto getPublicById(Long eventId);
}