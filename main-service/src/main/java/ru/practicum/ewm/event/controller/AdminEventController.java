package ru.practicum.ewm.event.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.service.EventService;

@RestController
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;

    public AdminEventController(final EventService eventService) {
        this.eventService = eventService;
    }

}