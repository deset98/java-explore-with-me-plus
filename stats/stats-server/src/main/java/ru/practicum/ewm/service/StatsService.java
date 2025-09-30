package ru.practicum.ewm.service;

import ru.practicum.ewm.NewHitDto;
import ru.practicum.ewm.ReqStatsParams;
import ru.practicum.ewm.StatsDto;

import java.util.List;

public interface StatsService {

    StatsDto hit(NewHitDto hitDto);

    List<StatsDto> getStats(ReqStatsParams statsDto);
}