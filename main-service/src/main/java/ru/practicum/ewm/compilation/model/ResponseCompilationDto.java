package ru.practicum.ewm.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.model.Event;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCompilationDto {
    private Long id;
    private List<Event> events;
    private Boolean pinned;
    private String title;
}
