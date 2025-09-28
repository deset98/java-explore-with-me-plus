package ru.practicum.ewm.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.AdminEventSearchParams;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UpdEventAdminRequest;
import ru.practicum.ewm.event.service.EventService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> adminUpdate(@PathVariable @Positive Long eventId,
                                                    @RequestBody @Valid UpdEventAdminRequest updDto) {
        log.debug("Метод adminUpdateEvent(); eventId: {}, dto={}", eventId, updDto);

        EventFullDto eventFullDto = eventService.updateByAdmin(eventId, updDto);
        return ResponseEntity.ok(eventFullDto);
    }

    @GetMapping
    public ResponseEntity<List<EventFullDto>> adminSearch(@Valid @ModelAttribute AdminEventSearchParams params) {
        log.debug("Метод adminSearchEvents; {}", params);

        List<EventFullDto> events = eventService.searchForAdmin(params);
        return ResponseEntity.ok(events);
    }
}