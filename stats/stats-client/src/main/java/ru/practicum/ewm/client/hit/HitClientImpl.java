package ru.practicum.ewm.client.hit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.RequestHitDto;
import ru.practicum.ewm.client.BaseClient;

@Service
public class HitClientImpl extends BaseClient implements HitClient {

    @Value("${explore-with-me-server.url}")
    private String serverUrl;

    public HitClientImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public ResponseEntity<RequestHitDto> createHit(RequestHitDto requestHitDto) {
        return createHit(serverUrl, requestHitDto, RequestHitDto.class);
    }
}
