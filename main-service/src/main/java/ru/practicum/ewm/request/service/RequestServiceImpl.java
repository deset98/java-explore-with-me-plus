package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestMapper;
import ru.practicum.ewm.request.model.ResponseRequestDto;
import ru.practicum.ewm.request.model.Status;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final EventService eventService;
    private final EventMapper eventMapper;


    @Override
    public ResponseRequestDto createRequest(Long userId, Long eventId) {
        User user = userMapper.toEntity(userService.findById(userId));
        EventFullDto event = eventService.findOne(userId, eventId);
        List<Request> requests = requestRepository.findAllByEventId(eventId);

        boolean requestExists = requests.stream()
                .anyMatch(r -> r.getRequester().getId().equals(userId));
        if  (requestExists) {
            throw new ConflictException("Запрос уже создан ранее");
        }

        //нет модели ShortUserDto
        //проверка: инициатор события не может добавить запрос на участие в своём событии(409)

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= requests.size()) {
            throw new ConflictException("Достигнут лимит запросов на участие в событии");
        }
        if (!event.getRequestModeration()) {
            Request result = requestRepository.save(RequestMapper.toEntity(user, eventMapper.toEntity(event), Status.CONFIRMED));
            return RequestMapper.toResponseEntity(result);
        }
        Request result = requestRepository.save(RequestMapper.toEntity(user, eventMapper.toEntity(event), Status.PENDING));
        return RequestMapper.toResponseEntity(result);
    }

    @Override
    public List<ResponseRequestDto> getRequests(Long userId) {
        User user = userMapper.toEntity(userService.findById(userId));
        List<Request> result = requestRepository.findAllByRequester(user);
        return result.stream().map(RequestMapper::toResponseEntity).collect(Collectors.toList());
    }

    @Override
    public ResponseRequestDto cancelRequest(Long userId, Long requestId) {
        User user = userMapper.toEntity(userService.findById(userId));
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос не найден"));
        Request result = requestRepository.save(RequestMapper.toEntity(user, request.getEvent(), Status.REJECTED)); //сохранение записи
        return RequestMapper.toResponseEntity(result);
    }
}
