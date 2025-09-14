package ru.practicum.ewm;

import ru.practicum.ewm.entity.RequestHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HitMapper {
    public RequestHit toEntity(RequestHitDto requestHitDto) {

        RequestHit requestHit = new RequestHit();
        requestHit.setApp(requestHitDto.getApp());
        requestHit.setUri(requestHitDto.getUri());
        requestHit.setIp(requestHitDto.getIp());
        requestHit.setTimestamp(convertLocalDateTimeToString(requestHitDto.getTimestamp())); // Преобразование LocalDateTime в String

        return requestHit;
    }

    private String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}