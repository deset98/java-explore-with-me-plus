package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> createRequest(@PathVariable Long userId,
                                                                 @RequestParam Long eventId) {
        log.debug("Метод createRequest(); userId={}, eventId={}", userId, eventId);

        ParticipationRequestDto result = requestService.create(userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> getRequests(@PathVariable Long userId) {
        log.debug("Метод getRequests(); userId={}", userId);

        List<ParticipationRequestDto> result = requestService.getAllBy(userId);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequest(@PathVariable Long userId,
                                                                 @PathVariable Long requestId) {
        log.debug("Метод cancelRequest(); userId={}, requestId={}", userId, requestId);

        ParticipationRequestDto result = requestService.cancel(userId, requestId);
        return ResponseEntity.ok(result);
    }
}