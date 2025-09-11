package ru.practicum.ewm.client;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class BaseClient {
    private final RestTemplate restTemplate;

    public BaseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> createHit(String url, Object body, Class<T> responseType) {
        HttpEntity<Object> request = new HttpEntity<>(body, defaultHeaders());
        try {
            return restTemplate.postForEntity(url, request, responseType);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при POST-запросе к " + url, e);
        }
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
