package ru.practicum.ewm.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.client.stats.StatsClientImpl;
import ru.practicum.ewm.RequestStatDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsClientImpl client;

    public StatsController(StatsClientImpl client) {
        this.client = client;
    }

    @GetMapping
    public List<RequestStatDto> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                   @RequestParam(required = false) List<String> uris,
                                   @RequestParam(required = false, defaultValue = "false") Boolean unique) {

        return client.getStats(start, end, uris, unique);
    }

}

