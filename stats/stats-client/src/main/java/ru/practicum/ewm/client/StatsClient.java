package ru.practicum.ewm.client;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewm.NewHitDto;
import ru.practicum.ewm.ReqStatsParams;
import ru.practicum.ewm.StatsDto;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class StatsClient {

    private final RestTemplate restTemplate;

    private final String serverUrl;

    public StatsClient(RestTemplate template, @Value("${explore-with-me-server.url}") String serverUrl) {
        this.restTemplate = template;
        this.serverUrl = serverUrl;

        log.info("StatsClient инициализирован с сервером URL: {}", serverUrl);
    }

    public void hit(HttpServletRequest eventRequest) {
        log.debug("Метод hit(): {}", eventRequest);

        try {
            NewHitDto hitDto = NewHitDto.builder()
                    .app("evm-main-service")
                    .ip(eventRequest.getRemoteAddr())
                    .uri(eventRequest.getRequestURI())
                    .timestamp(LocalDateTime.now())
                    .build();

            log.debug("Создан hit(): {}", hitDto);

            URI uri = URI.create(serverUrl + "/hit");

            restTemplate.postForObject(uri, hitDto, Void.class);
        } catch (Exception e) {
            log.warn("Ошибка при отправке hit; message={}", e.getMessage());
        }
    }

    public List<StatsDto> getStats(ReqStatsParams params) {
        log.debug("Метод getStats(): start={}, end={}, uris={}, unique={}",
                params.getStart(), params.getEnd(), params.getUris(), params.isUnique());

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serverUrl + "/stats")
                    .queryParam("start", params.getStart())
                    .queryParam("end", params.getEnd());

            if (params.getUris() != null && !params.getUris().isEmpty()) {
                builder.queryParam("uris", String.join(",", params.getUris()));
            }

            builder.queryParam("unique", params.isUnique());

            URI url = builder.build().toUri();

            HttpEntity<Void> requestEntity = new HttpEntity<>(defaultHeaders());

            ResponseEntity<List<StatsDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<StatsDto>>() {
                    }
            );

            log.info("Получена статистика: {}", response.getBody());

            return response.getBody();
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }
}