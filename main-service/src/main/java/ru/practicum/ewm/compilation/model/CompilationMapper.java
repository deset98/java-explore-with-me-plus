package ru.practicum.ewm.compilation.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompilationMapper {

    private final EventMapper eventMapper;

    public Compilation toEntity(NewCompilationDto dto, List<Event> events) {
        return Compilation.builder()
                .events(events)
                .pinned(dto.getPinned())
                .title(dto.getTitle())
                .build();
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(compilation.getEvents().stream().map(eventMapper::toShortDto).toList())
                .build();
    }

    public void updateFields(Compilation target, Compilation source) {
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
