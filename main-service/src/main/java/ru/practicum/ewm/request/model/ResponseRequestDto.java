package ru.practicum.ewm.request.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRequestDto {
    private Long id;
    private EventShortDto event;
    private User requester;
    private LocalDateTime created;
    private Status status;
}
