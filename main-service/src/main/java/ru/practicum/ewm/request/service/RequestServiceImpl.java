package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestMapper;
import ru.practicum.ewm.request.model.ParticipationRequestDto;
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
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EventRepository eventRepository;
    private final RequestMapper requestMapper;


    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        log.debug("Метод createRequest(); userId={}, eventId={}", userId, eventId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Ивент не найден"));

        if (event.getInitiator().equals(user)) {
            throw new ConflictException("Нельзя участвовать в собственном событии");
        }

        List<Request> requests = requestRepository.findAllByEventId(eventId);
        Optional<Event> initiatorEvent = eventRepository.findByIdAndInitiator_Id(userId, eventId);

        boolean requestExists = requests.stream()
                .anyMatch(r -> r.getRequester().getId().equals(userId));

        if  (requestExists) {
            throw new ConflictException("Запрос уже создан ранее");
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
            Request request = requestMapper.toEntity(user, event, Status.CONFIRMED);
            request = requestRepository.save(request);
            return requestMapper.toResponseEntity(request);
        }
        Request result = requestRepository.save(requestMapper.toEntity(user, event, Status.PENDING));
        return requestMapper.toResponseEntity(result);
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        List<Request> result = requestRepository.findAllByRequester(user);
        return result.stream().map(requestMapper::toResponseEntity).collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос не найден"));
        request.setStatus(Status.CANCELED);

        if (!request.getRequester().getId().equals(userId)) {
            throw new ConflictException("Пользователь не является автором этого запроса");
        }

        Request result = requestRepository.save(request);
        return requestMapper.toResponseEntity(result);
    }
}
