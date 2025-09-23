package ru.practicum.ewm.event.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.event.dto.LocationDto;
import ru.practicum.ewm.event.model.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    Location toEntity(final LocationDto locationDto);

    LocationDto toDto(final Location location);
}
