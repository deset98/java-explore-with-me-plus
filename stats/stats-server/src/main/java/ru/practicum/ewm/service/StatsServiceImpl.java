package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.entity.RequestHit;
import ru.practicum.ewm.repository.RequestHitRepository;
import ru.practicum.ewm.repository.StatRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final RequestHitRepository requestHitRepository;

    @Override
    public ResponseEntity<Void> createEndpointHit(RequestHitDto requestHitDto) {
        RequestHit savedHit = mapper.toEntity(requestHitDto);
        requestHitRepository.save(savedHit);
        return ResponseEntity.created("/hit").build();
    }

    @Override
    public List<StatDto> getViewStats(String start, String end, String app, List<String> uris, Boolean unique) {

    }
}
