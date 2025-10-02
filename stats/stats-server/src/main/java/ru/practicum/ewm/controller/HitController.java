package ru.practicum.ewm.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.NewHitDto;
import ru.practicum.ewm.StatsDto;
import ru.practicum.ewm.service.StatsService;
import ru.practicum.ewm.service.StatsServiceImpl;

@Slf4j
@RestController
@RequestMapping("/hit")
public class HitController {

    private final StatsService service;

    public HitController(StatsServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<StatsDto> createHit(@Valid @RequestBody NewHitDto newHitDto) {
        log.info("Метод createHit hit: {}", newHitDto);

        StatsDto result = service.hit(newHitDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}