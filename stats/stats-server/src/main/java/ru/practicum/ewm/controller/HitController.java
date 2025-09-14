package ru.practicum.ewm.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.RequestHitDto;
import ru.practicum.ewm.service.StatsService;
import ru.practicum.ewm.service.StatsServiceImpl;

@RestController
@RequestMapping("/hit")
public class HitController {

    private final StatsService service;

    public HitController(StatsServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createHit(@Valid @RequestBody RequestHitDto requestHitDto) {
        return service.createEndpointHit(requestHitDto);
    }
}
