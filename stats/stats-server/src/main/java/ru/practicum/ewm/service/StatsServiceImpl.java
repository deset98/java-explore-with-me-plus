package ru.practicum.ewm.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.RequestHitDto;
import ru.practicum.ewm.RequestStatDto;
import ru.practicum.ewm.StatDto;
import ru.practicum.ewm.entity.RequestHit;
import ru.practicum.ewm.repository.RequestHitRepository;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final RequestHitRepository requestHitRepository;
    private final AAAA mapper;

    @Override
    public ResponseEntity<Void> createEndpointHit(RequestHitDto requestHitDto) {
        RequestHit hitEntity = mapper.toEntity(requestHitDto);
        RequestHit savedHit = requestHitRepository.save(hitEntity);
        return ResponseEntity.created(URI.create("/hit/" + savedHit.getId())).build();
    }

    @Override
    public List<StatDto> getViewStats(String start, String end, String app, List<String> uris, Boolean unique) {
        RequestStatDto requestStatDto = new RequestStatDto(LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), uris, unique);
        return getViewStats(requestStatDto, app);
    }

    private List<StatDto> getViewStats(RequestStatDto dto, String app) {
        if (!dto.isUnique()) {
            throw new ValidationException("End date must be after start date");
        }
        if (dto.isUnique()) {
            return requestHitRepository.findUniqueStats(dto.getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), dto.getEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), app, dto.getUris());
        } else {
            return requestHitRepository.findUniqueStats(dto.getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), dto.getEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), app, dto.getUris());
        }
    }
}
