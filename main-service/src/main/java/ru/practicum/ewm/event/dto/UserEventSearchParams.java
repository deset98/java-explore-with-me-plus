package ru.practicum.ewm.event.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserEventSearchParams {

    private String text;

    private List<@Positive Long> categories;

    private Boolean paid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeEnd;

    private Boolean onlyAvailable = false;

    private Sort sort = Sort.EVENT_DATE;

    @PositiveOrZero
    private Integer from = 0;

    @Positive
    private Integer size = 10;

    public enum Sort {
        EVENT_DATE,
        VIEWS
    }
}