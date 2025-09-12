package ru.practicum.ewm.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.ewm.RequestHitDto;
import ru.practicum.ewm.StatDto;

import java.util.List;

public interface StatsService {
    ResponseEntity<Void> createEndpointHit(RequestHitDto requestHitDto);

    List<StatDto> getViewStats(String start, String end, String app, List<String> uris, Boolean unique);
}
