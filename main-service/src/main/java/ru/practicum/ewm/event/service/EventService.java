package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdEventUserRequest;

import java.util.List;

public interface EventService {
    EventFullDto create(Long userId, NewEventDto newEventDto);

    EventFullDto findByIdAndInitiator_Id(Long userId, Long eventId);

    List<EventShortDto> findAll(Long userId, int from, int size);

    EventFullDto userUpdate(Long userId, Long eventId, UpdEventUserRequest updEventUserRequest);

    EventFullDto adminUpdate(Long eventId, UpdEventAdminRequest updEventAdminRequest);
}