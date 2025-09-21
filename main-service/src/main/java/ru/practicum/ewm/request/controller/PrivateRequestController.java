package ru.practicum.ewm.request.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.service.RequestService;

@RestController
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {

    private final RequestService requestService;

    public PrivateRequestController(final RequestService requestService) {
        this.requestService = requestService;
    }

}