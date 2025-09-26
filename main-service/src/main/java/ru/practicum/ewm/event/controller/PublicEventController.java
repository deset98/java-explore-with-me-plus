package ru.practicum.ewm.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UserEventSearchParams;
import ru.practicum.ewm.event.service.EventService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
public class PublicEventController {

    private final EventService eventService;

    public PublicEventController(final EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * обращение нужно записать в Stats
     */

    @GetMapping
    public ResponseEntity<List<EventFullDto>> publicSearchMany(@Valid @ModelAttribute UserEventSearchParams params) {
        log.debug("Метод publicSearchMany(); {}", params);

        List<EventFullDto> events = eventService.publicSearchMany(params);
        return ResponseEntity.ok(events);
    }

    /**
     * обращение нужно записать в Stats
     */

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> publicSearchOne(@PathVariable @Positive Long eventId) {
        log.debug("Метод publicSearchOne(); eventId={}", eventId);

        EventFullDto event = eventService.publicSearchOne(eventId);
        return ResponseEntity.ok(event);
    }
}