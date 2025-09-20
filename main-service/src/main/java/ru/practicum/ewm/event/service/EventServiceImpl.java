package ru.practicum.ewm.event.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventServiceImpl(final EventRepository eventRepository,
                            final EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Transactional
    public EventFullDto create(Long userId, final NewEventDto newEventDto) {
        log.debug("В EventServiceImpl вызван метод для создания события");

        Event event = eventMapper.

        // 1. Собрать сущность Event
        // 1.1. Подставить Category
        // 1.2. Подставить User
        // 2. Сохранить ее в репозиторий
        // 3. Вернуть FullDto

        return EventFullDto.builder().build();
    }
}