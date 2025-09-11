package ru.practicum.ewm.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.RequestHitDto;
import ru.practicum.ewm.client.hit.HitClient;

@RestController
@RequestMapping("/hit")
public class HitController {

    private final HitClient hitClient;

    public HitController(HitClient hitClient) {
        this.hitClient = hitClient;
    }

    @PostMapping
    public ResponseEntity<RequestHitDto> createHit(@Valid @RequestBody RequestHitDto requestHitDto) {
        return hitClient.createHit(requestHitDto);
    }
}
