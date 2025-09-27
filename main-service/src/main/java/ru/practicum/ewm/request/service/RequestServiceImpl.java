package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.mapper.RequestMapper;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.model.Status;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    private final UserMapper userMapper;
    private final RequestMapper requestMapper;

    @Override
    public ParticipationRequestDto create(Long userId, Long eventId) {
        log.debug("Метод createRequest(); userId={}, eventId={}", userId, eventId);

        User user = this.findUserBy(userId);
        Event event = this.findEventBy(eventId);

        if (event.getInitiator().equals(user)) {
            throw new ConflictException("Нельзя участвовать в собственном событии");
        }

        List<Request> requests = requestRepository.findAllByEventId(eventId);
        Optional<Event> initiatorEvent = eventRepository.findByIdAndInitiator_Id(userId, eventId);

        boolean requestExists = requests.stream()
                .anyMatch(r -> r.getRequester().getId().equals(userId));

        if  (requestExists) {
            throw new ConflictException("Request уже создан ранее");
        }
        if (initiatorEvent.isPresent()) {
            if (Objects.equals(initiatorEvent.get().getInitiator().getId(), userMapper.toUserShortDto(user).getId())) {
                throw new ConflictException("Инициатор события не может добавить запрос на участие в своём событии");
            }
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= requests.size()) {
            throw new ConflictException("Достигнут лимит запросов на участие в событии");
        }
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            Request request = Request.builder().requester(user).event(event).status(Status.CONFIRMED).build();
            request = requestRepository.save(request);

            return requestMapper.toDto(request);
        }

        Request request = Request.builder().requester(user).event(event).status(Status.PENDING).build();
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
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        log.debug("Метод cancel(); userId={}, requestId={}", userId, requestId);

        this.findUserBy(userId);
        Request request = this.findRequestBy(requestId);
        request.setStatus(Status.CANCELED);

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