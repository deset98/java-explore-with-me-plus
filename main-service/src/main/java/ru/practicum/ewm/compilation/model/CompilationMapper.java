package ru.practicum.ewm.compilation.model;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.event.model.Event;

import java.util.List;

@UtilityClass
public class CompilationMapper {

    public static Compilation toEntity(NewCompilationDto dto, List<Event> events) {
        return Compilation.builder()
                .events(events)
                .pinned(dto.getPinned())
                .title(dto.getTitle())
                .build();
    }

    public static ResponseCompilationDto toResponseDto(Compilation compilation) {
        return ResponseCompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(compilation.getEvents())
                .build();
    }

    public static void updateFields(Compilation target, Compilation source) {
        if (source.getEvents() != null) {
            target.setEvents(source.getEvents());
        }
        if (source.getPinned() != null) {
            target.setPinned(source.getPinned());
        }
        if (source.getTitle() != null && !source.getTitle().isBlank()) {
            target.setTitle(source.getTitle());
        }
    }

}
