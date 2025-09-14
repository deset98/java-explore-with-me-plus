package ru.practicum.ewm.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.RequestHitDto;
import ru.practicum.ewm.RequestStatDto;
import ru.practicum.ewm.StatDto;
import ru.practicum.ewm.entity.EndpointHit;
import ru.practicum.ewm.mapper.HitMapper;
import ru.practicum.ewm.repository.RequestHitRepository;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final RequestHitRepository requestHitRepository;
    private final HitMapper mapper;

    @Override
    public ResponseEntity<Void> createEndpointHit(RequestHitDto requestHitDto) {
        EndpointHit hitEntity = mapper.toEntity(requestHitDto);
        EndpointHit savedHit = requestHitRepository.save(hitEntity);
        log.debug("Сохранен хит  {}", savedHit);
        return ResponseEntity.created(URI.create("/hit/" + savedHit.getId())).build();
    }

    @Override
    public List<StatDto> getViewStats(LocalDateTime start, LocalDateTime end, String app, List<String> uris, Boolean unique) {
        RequestStatDto requestStatDto = new RequestStatDto(start, end, uris, unique);
        log.debug("Сервис выполняет getViewStats");
        return getViewStats(requestStatDto, app);
    }

    private List<StatDto> getViewStats(RequestStatDto dto, String app) {
        if (!dto.isValidPeriod()) {
            throw new ValidationException("End date must be after start date");
        }
        if (dto.isUnique()) {
            log.debug("Вызван метод репозитория findUniqueStats()");
            return requestHitRepository.findUniqueStats(dto.getStart().toString(), dto.getEnd().toString(), app, dto.getUris());
        }
        log.debug("Вызван метод репозитория findNotUniqueStats()");
        return requestHitRepository.findNotUniqueStats(dto.getStart().toString(), dto.getEnd().toString(), app, dto.getUris());
    }
}
