package ru.practicum.ewm.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewm.NewHitDto;
import ru.practicum.ewm.ResponseExtHitDto;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class StatsClient {
    private final RestTemplate restTemplate;
    @Value("${explore-with-me-server.url}")
    private String serverUrl;

    public void hit(NewHitDto newHitDto) {
        String url = serverUrl + "/hit";
        HttpEntity<NewHitDto> request = new HttpEntity<>(newHitDto, defaultHeaders());

        try {
            restTemplate.postForEntity(url, request, NewHitDto.class);
            log.info("Хит успешно создан: {}", newHitDto.getUri());
        } catch (Exception e) {
            log.error("Ошибка при создании хита: {}", e.getMessage());
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<ResponseExtHitDto>> getStats(LocalDateTime start,
                                                            LocalDateTime end,
                                                            List<String> uris,
                                                            Boolean unique) {
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
            ResponseEntity<List<ResponseExtHitDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<ResponseExtHitDto>>() {
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
