package ru.practicum.ewm.service;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.practicum.ewm.NewHitDto;
import ru.practicum.ewm.RequestStatsParams;
import ru.practicum.ewm.ResponseExtHitDto;
import ru.practicum.ewm.ResponseShortHitDto;
import ru.practicum.ewm.entity.Hit;
import ru.practicum.ewm.mapper.HitMapper;
import ru.practicum.ewm.repository.StatsRepository;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    private final HitMapper hitMapper;

    @Override
    public ResponseShortHitDto create(NewHitDto hitDto) {

        Hit hit = hitMapper.toEntity(hitDto);
        hit = statsRepository.save(hit);

        log.debug("Сохранен хит  {}", hit);

        return hitMapper.toShortResponseDto(hit);
    }

    @Override
    public List<ResponseExtHitDto> getStats(RequestStatsParams params) {
        log.debug("Метод getStats(); params={}", params);

        if (!params.getEnd().isAfter(params.getStart())) {
            throw new ValidationException("Дата конца не может быть раньше начала");
        }

        List<ResponseExtHitDto> stats = statsRepository.getStats(
                params.getStart().toInstant(ZoneOffset.UTC),
                params.getEnd().toInstant(ZoneOffset.UTC),
                params.getUris(),
                params.isUnique());

        return stats;
    }
}
