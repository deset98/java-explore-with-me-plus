package ru.practicum.ewm.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.validation.FutureAfterHours;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {

    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;

    @NotNull
    private Long categoryId;

    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;

    @NotNull
    @FutureAfterHours(hours = 2)
    private LocalDateTime eventDate;

    @NotNull
    private LocationDto location;

    @NotNull
    private Boolean paid;

    @NotNull
    private Integer participantLimit;

    @NotNull
    private Boolean requestModeration;

    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
}
