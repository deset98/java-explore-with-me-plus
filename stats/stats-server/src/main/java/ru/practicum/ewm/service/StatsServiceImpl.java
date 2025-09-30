package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.NewHitDto;
import ru.practicum.ewm.ReqStatsParams;
import ru.practicum.ewm.StatsDto;
import ru.practicum.ewm.entity.Hit;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.mapper.HitMapper;
import ru.practicum.ewm.repository.StatsRepository;

import java.util.List;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    private final HitMapper hitMapper;

    @Override
    public StatsDto hit(NewHitDto hitDto) {

        Hit hit = hitMapper.toEntity(hitDto);
        hit = statsRepository.save(hit);

        log.debug("Сохранен хит  {}", hit);

        return hitMapper.toStatsDto(hit);
    }

    @Override
    public List<StatsDto> getStats(ReqStatsParams params) {
        log.debug("Метод getStats(); params={}", params);

        if (!params.getEnd().isAfter(params.getStart())) {
            throw new BadRequestException("Дата конца не может быть раньше начала");
        }

        return params.isUnique()
                ? statsRepository.findStatsWithUniqueIp(params.getStart(), params.getEnd(), params.getUris())
                : statsRepository.getAllStats(params.getStart(), params.getEnd(), params.getUris());
    }
}