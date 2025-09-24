package ru.practicum.ewm.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UpdEventAdminRequest;
import ru.practicum.ewm.event.service.EventService;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;

    public AdminEventController(final EventService eventService) {
        this.eventService = eventService;
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> adminUpdateEvent(@PathVariable @Positive Long eventId,
                                                         @RequestBody @Valid UpdEventAdminRequest updDto) {
        log.debug("Контроллер AdminEventController; метод adminUpdateEvent(); eventId: {}, dto={}",
                eventId, updDto);

        EventFullDto eventFullDto = eventService.adminUpdate(eventId, updDto);
        return ResponseEntity.ok(eventFullDto);
    }

}