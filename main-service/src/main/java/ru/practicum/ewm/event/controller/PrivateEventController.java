package ru.practicum.ewm.event.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.service.EventService;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {

    private final EventService eventService;

    public PrivateEventController(final EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventFullDto> create(@PathVariable("userId") Long userId,
                                               @Valid @RequestBody final NewEventDto newEventDto) {
        log.debug("В PrivateEventController от user = {} поступил запрос на создание события {}", userId, newEventDto);
        EventFullDto result = eventService.create(userId, newEventDto);
        return ResponseEntity
                .created(URI.create("/events/" + result.getId()))
                .body(result);
    }


}