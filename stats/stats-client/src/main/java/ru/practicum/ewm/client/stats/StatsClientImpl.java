package ru.practicum.ewm.client.stats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.client.BaseClient;
import ru.practicum.ewm.exception.InvalidStatsDates;
import ru.practicum.ewm.RequestStatDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class StatsClientImpl extends BaseClient implements StatsClient {

    private static final Logger log = LoggerFactory.getLogger(StatsClientImpl.class);

    public StatsClientImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public List<RequestStatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (end.isBefore(start)) {
            throw new InvalidStatsDates("Конечная дата не может быть раньше начальной");
        }

        //запрос к бд за выборку данных для статистики
        //маппинг к RequestStatsDto

        log.info("Успешная выгрузка статистики!");

        return Collections.emptyList();
    }
}
