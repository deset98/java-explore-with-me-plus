package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConditionNotMetException;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

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
    @Override
    public EventFullDto create(Long userId, final NewEventDto newEventDto) {
        log.debug("В EventServiceImpl вызван метод для СОЗДАНИЯ event");

        this.startDateIsValid(newEventDto.getEventDate());
        User user = this.findUser(userId);
        Category category = this.findCategory(newEventDto.getCategory());

        Event event = eventMapper.toEntity(newEventDto);
        event.setLocation(newEventDto.getLocation());
        event.setInitiator(user);
        event.setCategory(category);
        event = eventRepository.save(event);

        log.debug("Создан event={}", event);

        return eventMapper.toFullDto(event);
    }

    @Override
    public List<EventShortDto> findAll(Long userId, int from, int size) {
        log.debug("В EventServiceImpl вызван метод для ПОЛУЧЕНИЯ event user id={}", userId);

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size, Sort.by("eventDate").descending());
        Page<Event> events = eventRepository.findAllByInitiator_Id(userId, pageable);
        return events.map(eventMapper::toShortDto).getContent();
    }

    @Override
    public EventFullDto findByIdAndInitiator_Id(Long userId, Long eventId) {
        log.debug("В EventServiceImpl вызван метод для ПОЛУЧЕНИЯ event id={} от user id={}", eventId, userId);

        Event event = eventRepository.findByIdAndInitiator_Id(userId, eventId)
                .orElseThrow(() -> new NotFoundException("Event id={} у user id={} не найдено", eventId, userId));
        return eventMapper.toFullDto(event);
    }

    @Transactional
    @Override
    public EventFullDto userUpdate(Long userId, Long eventId, UpdEventUserRequest updDto) {
        log.debug("Сервис EventServiceImpl; метод userUpdate(); userId={}, eventId: {}, dto={}",
                userId, eventId, updDto);

        this.checkEventDateForUpdate(updDto);

        Event event = eventRepository.findByIdAndInitiator_Id(eventId, userId)
                .orElseThrow(() -> new NotFoundException("Event id={} не найдено; User id={} ", eventId, userId));

        log.debug("Найден Event в репозитории; event={}", event);

        if (!(event.getState().equals(State.CANCELED) || event.getState().equals(State.PENDING))) {
            throw new ForbiddenException("Event id={} нельзя обновить пока оно опубликовано", event.getId());
        }
        if (updDto.getCategory() != null) {
            event.setCategory(this.findCategory(updDto.getCategory()));
        }

        if (updDto.getStateAction() != null) {
            switch (updDto.getStateAction()) {
                case SEND_TO_REVIEW -> event.setState(State.PENDING);
                case CANCEL_REVIEW -> event.setState(State.CANCELED);
            }
        }

        eventMapper.updateFromDto(updDto, event);
        event = eventRepository.save(event);

        log.debug("Метод userUpdate(); Event обновлен в репозитории event={}", event);

        return eventMapper.toFullDto(event);
    }

    @Transactional
    @Override
    public EventFullDto adminUpdate(Long eventId, UpdEventAdminRequest updDto) {
        log.debug("Сервис EventServiceImpl; метод adminUpdateEvent(); eventId: {}, dto={}", eventId, updDto);

        Event event = this.findEvent(eventId);
        eventMapper.updateFromDto(updDto, event);

        this.checkEventDateForPublish(updDto.getEventDate());

        if (updDto.getStateAction() != null) {
            switch (updDto.getStateAction()) {
                case PUBLISH_EVENT -> {
                    if (event.getState().equals(State.PENDING)) {
                        event.setState(State.PUBLISHED);
                        event.setPublishedOn(Instant.now());
                    } else {
                        throw new ConditionNotMetException("Для публикации Event статус должен быть PENDING");
                    }

                    log.debug("Для Event назначен статус={}, время публикации publishedOn={}",
                            event.getState(), event.getPublishedOn());
                }
                case REJECT_EVENT -> {
                    if (event.getState().equals(State.PENDING)) {
                        event.setState(State.CANCELED);
                    } else if (event.getState().equals(State.PUBLISHED)) {
                        throw new ConditionNotMetException("Опубликованные Event не могут быть отклонены");
                    }

                    log.debug("Для Event назначен статус={}", event.getState());
                }
            }
        }

        event = eventRepository.save(event);

        log.debug("метод adminUpdate(); Event обновлен в репозитории event={}", event);

        return eventMapper.toFullDto(event);
    }

    private User findUser(Long userId) {
        log.debug("Поиск User id={} в репозитории", userId);

        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User id={} не найден", userId));
    }

    private Category findCategory(Long categoryId) {
        log.debug("Поиск Category id={} в репозитории", categoryId);

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Объект Category id={} не найден", categoryId));
    }

    private Event findEvent(Long eventId) {
        log.debug("Поиск Event id={} в репозитории", eventId);

        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Объект Event id={} не найден", eventId));
    }

    private void startDateIsValid(LocalDateTime eventDate) {
        log.debug("Проверка даты при СОЗДАНИИ");

        if (eventDate != null && eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ForbiddenException("Дата Event при СОЗДАНИИ должна быть в будущем, мин. через 2 часа");
        }
    }

    private void checkEventDateForUpdate(UpdEventUserRequest updDto) {
        log.debug("Проверка даты Event при ОБНОВЛЕНИИ");

        if (updDto.getEventDate() != null) {
            this.startDateIsValid(updDto.getEventDate());
        }
    }

    private void checkEventDateForPublish(LocalDateTime eventDate) {
        log.debug("Проверка даты Event при ПУБЛИКАЦИИ");

        if (eventDate.isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ForbiddenException("Дата Event при ПУБЛИКАЦИИ должна быть в будущем, мин. через 1 час");
        }
    }
}