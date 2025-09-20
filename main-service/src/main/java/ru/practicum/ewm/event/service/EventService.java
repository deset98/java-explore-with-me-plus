package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.NewEventDto;

public interface EventService {
    EventFullDto create(Long userId, NewEventDto newEventDto);
}
