package ru.practicum.ewm.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UserEventSearchParams;
import ru.practicum.ewm.event.service.EventService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {

    private final EventService eventService;

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> publicSearchOne(@PathVariable @Positive Long eventId,
                                                        HttpServletRequest request) {
        log.debug("Метод publicSearchOne(); eventId={}", eventId);

        EventFullDto event = eventService.getPublicBy(eventId, request);
        return ResponseEntity.ok(event);
    }

    @GetMapping
    public ResponseEntity<List<EventFullDto>> publicSearchMany(@Valid @ModelAttribute UserEventSearchParams params,
                                                               HttpServletRequest request) {
        log.debug("Метод publicSearchMany(); {}", params);

        List<EventFullDto> events = eventService.getPublicBy(params, request);
        return ResponseEntity.ok(events);
    }
}