package ru.practicum.ewm.client.hit;

import org.springframework.http.ResponseEntity;
import ru.practicum.ewm.RequestHitDto;

public interface HitClient {

    ResponseEntity<RequestHitDto> createHit(RequestHitDto requestHitDto);
}
