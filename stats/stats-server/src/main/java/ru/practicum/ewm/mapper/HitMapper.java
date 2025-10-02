package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.NewHitDto;
import ru.practicum.ewm.StatsDto;
import ru.practicum.ewm.entity.Hit;

@Mapper(componentModel = "spring")
public interface HitMapper {


    @Mapping(target = "id", ignore = true)
    Hit toEntity(NewHitDto dto);

    @Mapping(target = "hits", ignore = true)
    StatsDto toStatsDto(Hit hit);
}