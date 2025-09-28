package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.NewHitDto;
import ru.practicum.ewm.ResponseExtHitDto;
import ru.practicum.ewm.ResponseShortHitDto;
import ru.practicum.ewm.entity.Hit;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface HitMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", expression = "java(toInstant(dto.getTimestamp()))")
    Hit toEntity(NewHitDto dto);

    ResponseExtHitDto toExtResponseDto(Hit hit);

    ResponseShortHitDto toShortResponseDto(Hit hit);


    default Instant toInstant(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toInstant(ZoneOffset.UTC) : null;
    }
}