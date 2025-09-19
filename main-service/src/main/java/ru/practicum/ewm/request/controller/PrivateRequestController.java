package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.model.ResponseRequestDto;
import ru.practicum.ewm.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseRequestDto createRequest(@PathVariable Long userId, @RequestBody Long eventId) {
        return requestService.createRequest(userId, eventId);
    }

    @GetMapping
    public List<ResponseRequestDto> getRequests(@PathVariable Long userId) {
        return requestService.getRequests(userId);
    }

    @PatchMapping("{requestId}/cancel")
    public ResponseRequestDto cancelRequest(@PathVariable Long userId, @PathVariable Long requestId) {

    }
}