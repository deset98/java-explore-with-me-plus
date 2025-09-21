package ru.practicum.ewm.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.request.repository.RequestRepository;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    public RequestServiceImpl(final RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
}
