package ru.practicum.ewm.service;

import ru.practicum.ewm.NewHitDto;
import ru.practicum.ewm.RequestStatsParams;
import ru.practicum.ewm.ResponseHitDto;

import java.util.List;

public interface StatsService {

    ResponseHitDto hit(NewHitDto hitDto);

    List<ResponseHitDto> getStats(RequestStatsParams statsDto);
}