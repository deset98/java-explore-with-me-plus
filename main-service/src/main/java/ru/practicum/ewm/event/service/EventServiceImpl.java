package ru.practicum.ewm.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(final EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}