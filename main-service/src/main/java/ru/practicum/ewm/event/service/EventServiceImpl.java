package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private final EventMapper eventMapper;

    @Transactional
    public EventFullDto create(Long userId, final NewEventDto newEventDto) {
        log.debug("В EventServiceImpl вызван метод для создания события");

        Event event = eventMapper.toEntity(newEventDto);
        this.setCategoryAndInitiator(newEventDto.getCategoryId(), userId, event);
        eventRepository.save(event);
        return eventMapper.toFullDto(event);
    }


    private void setCategoryAndInitiator(Long categoryId, Long userId, Event event) {
        Category category = categoryRepository.findById(newEventDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException(
                        "Объект Category id={} не найден", newEventDto.getCategoryId()
                ));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        "Объект User id={} не найден", userId
                ));

        event.setCategory(category);
        event.setInitiator(user);
    }
}