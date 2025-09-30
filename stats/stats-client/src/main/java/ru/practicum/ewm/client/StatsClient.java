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

    public ResponseEntity<List<StatsDto>> getStats(LocalDateTime start,
                                                   LocalDateTime end,
                                                   List<String> uris,
                                                   Boolean unique) {
        log.debug("Метод getStats(): start={}, end={}, uris={}, unique={}", start, end, uris, unique);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serverUrl + "/stats")
                .queryParam("start", start)
                .queryParam("end", end);

        if (uris != null && !uris.isEmpty()) {
            for (String uri : uris) {
                builder.queryParam("uris", uri);
            }
        }

        builder.queryParam("unique", unique);

        String url = builder.build().encode().toUriString();

        HttpEntity<Void> requestEntity = new HttpEntity<>(defaultHeaders());

        try {
            ResponseEntity<List<StatsDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<StatsDto>>() {
                    }
            );
            log.info("Получена статистика: {}", response.getBody());
            return response;
        } catch (Exception e) {
            log.error("Ошибка при получении статистики: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }
}