package ru.practicum.ewm.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.RequestHitDto;
import ru.practicum.ewm.entity.EndpointHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class HitMapper {
    public EndpointHit toEntity(RequestHitDto requestHitDto) {

        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp(requestHitDto.getApp());
        endpointHit.setUri(requestHitDto.getUri());
        endpointHit.setIp(requestHitDto.getIp());
        endpointHit.setTimestamp(convertLocalDateTimeToString(requestHitDto.getTimestamp()));

        return endpointHit;
    }

    private String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}