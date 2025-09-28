package ru.practicum.ewm.service;

import ru.practicum.ewm.NewHitDto;
import ru.practicum.ewm.RequestStatsParams;
import ru.practicum.ewm.ResponseExtHitDto;
import ru.practicum.ewm.ResponseShortHitDto;

import java.util.List;

public interface StatsService {

    ResponseShortHitDto create(NewHitDto hitDto);

    List<ResponseExtHitDto> getStats(RequestStatsParams statsDto);
}