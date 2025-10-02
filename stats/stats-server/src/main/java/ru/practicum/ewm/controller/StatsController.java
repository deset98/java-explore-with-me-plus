package ru.practicum.ewm.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.ReqStatsParams;
import ru.practicum.ewm.StatsDto;
import ru.practicum.ewm.service.StatsService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    public ResponseEntity<List<StatsDto>> getStats(@ModelAttribute @Valid ReqStatsParams params) {
        log.info("Метод getStats(); params={}", params);

        List<StatsDto> result = statsService.getStats(params);
        return ResponseEntity.ok(result);
    }
}