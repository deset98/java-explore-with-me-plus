package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.mapper.RequestMapper;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public ParticipationRequestDto create(Long userId, Long eventId) {
        log.debug("Метод createRequest(); userId={}, eventId={}", userId, eventId);

        User user = this.findUserBy(userId);
        Event event = this.findEventBy(eventId);

        if (eventRepository.existsByIdAndInitiatorId(eventId, userId)) {
            throw new ConflictException("Нельзя участвовать в собственном событии");
        }

        if (requestRepository.existsByEventIdAndRequesterId(eventId, userId)) {
            throw new ConflictException("Request уже создан ранее");
        }

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
        }

        long limit = event.getParticipantLimit();
        long confirm = event.getConfirmedRequests();

        if (limit > 0 && confirm >= limit) {
            throw new ConflictException("Достигнут лимит запросов на участие в событии");
        }

        RequestStatus status =
                (!event.getRequestModeration() || limit == 0) ? RequestStatus.CONFIRMED : RequestStatus.PENDING;

        if (status == RequestStatus.CONFIRMED) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }

        Request request = Request.builder()
                .requester(user)
                .event(event)
                .status(status)
                .build();
        request = requestRepository.save(request);

        return requestMapper.toDto(request);
    }

    @Override
    public List<ParticipationRequestDto> getAllBy(Long userId) {
        log.debug("Метод getAllBy(); userId={}", userId);

        List<Request> result = requestRepository.findAllByRequesterId(userId);

        return result.stream()
                .map(requestMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        log.debug("Метод cancel(); userId={}, requestId={}", userId, requestId);

        this.findUserBy(userId);
        Request request = this.findRequestBy(requestId);
        request.setStatus(RequestStatus.CANCELED);

        if (!request.getRequester().getId().equals(userId)) {
            throw new ConflictException("User id={} не является автором этого запроса", userId);
        }
        request = requestRepository.save(request);

        return requestMapper.toDto(request);
    }


    private User findUserBy(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User id={} не найден", userId));
    }

    private Request findRequestBy(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request id={} не найден", requestId));
    }

    private Event findEventBy(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event id={} не найден", eventId));
    }
}