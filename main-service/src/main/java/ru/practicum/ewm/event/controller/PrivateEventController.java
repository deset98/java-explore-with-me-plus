package ru.practicum.ewm.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdEventUserRequest;
import ru.practicum.ewm.event.service.EventService;

import java.net.URI;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {

    private final EventService eventService;

    public PrivateEventController(final EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventFullDto> create(@PathVariable("userId") @NotNull @Positive Long userId,
                                               @RequestBody @Valid final NewEventDto newEventDto) {
        log.debug("В PrivateEventController от user = {} поступил запрос на СОЗДАНИЕ event: {}", userId, newEventDto);

        EventFullDto result = eventService.create(userId, newEventDto);
        return ResponseEntity
                .created(URI.create("/events/" + result.getId()))
                .body(result);
        // 400 и 409
    }

    @GetMapping
    public ResponseEntity<List<EventShortDto>> findAll(@PathVariable("userId") @NotNull @Positive Long userId,
                                                       @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                       @RequestParam(defaultValue = "10") @PositiveOrZero int size) {
        log.debug("В PrivateEventController от user = {} поступил запрос на ПОЛУЧЕНИЕ списка events; " +
                "from={}, size={}", userId, from, size);

        List<EventShortDto> result = eventService.findAll(userId, from, size);
        return ResponseEntity.ok(result);
        // 400
        // прикрутить модуль статистики
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> find(@PathVariable("userId") @NotNull @Positive Long userId,
                                             @PathVariable("eventId") @NotNull @Positive Long eventId) {
        log.debug("В PrivateEventController от user = {} поступил запрос на ПОЛУЧЕНИЕ event id={}", userId, eventId);

        EventFullDto result = eventService.findOne(userId, eventId);
        return ResponseEntity.ok(result);
        // 400
        // прикрутить модуль статистики
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> update(@PathVariable("userId") @NotNull @Positive Long userId,
                                               @PathVariable("eventId") @NotNull @Positive Long eventId,
                                               @RequestBody @Valid final UpdEventUserRequest updEventUserRequest) {
        log.debug("В PrivateEventController от user = {} поступил запрос на ОБНОВЛЕНИЕ event id={}", userId, eventId);

        EventFullDto result = eventService.update(userId, eventId, updEventUserRequest);
        return ResponseEntity.ok(result);
        // 400 и 409
    }

//    @GetMapping("/{eventId}/requests")

//    @PatchMapping("/{eventId}/requests")

}