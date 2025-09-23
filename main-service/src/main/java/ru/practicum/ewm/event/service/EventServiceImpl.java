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
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdEventUserRequest;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

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
        Category category = this.findCategory(newEventDto.getCategoryId());

        Event event = eventMapper.toEntity(newEventDto);
        event.setInitiator(user);
        event.setCategory(category);
        event = eventRepository.save(event);
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
    public EventFullDto findOne(Long userId, Long eventId) {
        log.debug("В EventServiceImpl вызван метод для ПОЛУЧЕНИЯ event id={} от user id={}", eventId, userId);

        Event event = eventRepository.findByIdAndInitiator_Id(userId, eventId)
                .orElseThrow(() -> new NotFoundException("Event id={} у user id={} не найдено", eventId, userId));
        return eventMapper.toFullDto(event);
    }

    @Override
    public EventFullDto update(Long userId, Long eventId, UpdEventUserRequest updEventUserRequest) {
        log.debug("В EventServiceImpl вызван метод для ОБНОВЛЕНИЯ event");

        this.checkEventDateForUpdate(updEventUserRequest);

        Event event = eventRepository.findByIdAndInitiator_Id(userId, eventId)
                .orElseThrow(() -> new NotFoundException("Event id={} не найдено; User id={} ", eventId, userId));
        if (!(event.getState().equals(State.CANCELED) || event.getState().equals(State.PENDING))) {
            throw new ForbiddenException("Event id={} нельзя обновить пока оно опубликовано", event.getId());
        }
        if (updEventUserRequest.getCategoryId() != null) {
            event.setCategory(this.findCategory(updEventUserRequest.getCategoryId()));
        }
        // StateAction??

        eventMapper.updateFromDto(updEventUserRequest, event);
        event = eventRepository.save(event);
        return eventMapper.toFullDto(event);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User id={} не найден", userId));
    }

    private Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Объект Category id={} не найден", categoryId));
    }

    private void startDateIsValid(LocalDateTime eventDate) {
        log.debug("Проверка даты при СОЗДАНИИ");

        if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ForbiddenException("Event должно быть в будущем, минимум через 2 часа");
        }
    }

    private void checkEventDateForUpdate(UpdEventUserRequest updEventUserRequest) {
        log.debug("Проверка даты при ОБНОВЛЕНИИ");

        if (updEventUserRequest.getEventDate() != null) {
            this.startDateIsValid(updEventUserRequest.getEventDate());
        }
    }

}