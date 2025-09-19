package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestMapper;
import ru.practicum.ewm.request.model.ResponseRequestDto;
import ru.practicum.ewm.request.model.Status;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserService userService;


    @Override
    public ResponseRequestDto createRequest(Long userId, Long eventId) {
        //получаю user
        //получаю event
        //проверка: нельзя добавить повторный запрос(409)
        //проверка: инициатор события не может добавить запрос на участие в своём событии(409)
        //проверка: нельзя участвовать в неопубликованном событии (409)
        //проверка: если у события достигнут лимит запросов на участие - необходимо вернуть ошибку(409)
        //если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного(CONFIRMED)
        Request result = requestRepository.save(RequestMapper.toEntity(new User(), new Event(), Status.CONFIRMED)); //сохранение записи
        return RequestMapper.toResponseEntity(result);
    }

    @Override
    public List<ResponseRequestDto> getRequests(Long userId) {
        //получаю user
        List<Request> result = requestRepository.findAllByRequester(new User());
        return result.stream().map(RequestMapper::toResponseEntity).collect(Collectors.toList());
    }

    @Override
    public ResponseRequestDto cancelRequest(Long userId, Long requestId) {
        //получаю user
        //получаю request
        Request result = requestRepository.save(RequestMapper.toEntity(new User(), new Event(), Status.REJECTED)); //сохранение записи
        return RequestMapper.toResponseEntity(result);
    }

}
