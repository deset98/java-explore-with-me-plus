package ru.practicum.ewm.client.stats;

import ru.practicum.ewm.RequestStatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsClient {

    List<RequestStatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
