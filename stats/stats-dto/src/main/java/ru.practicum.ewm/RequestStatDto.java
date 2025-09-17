package ru.practicum.ewm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatDto {
    @NotNull(message = "Дата и время начала диапазона не может быть пустым")
    private LocalDateTime start;

    @NotNull(message = "Дата и время конца диапазона не может быть пустым")
    private LocalDateTime end;

    @NotBlank(message = "Список uri не может быть пустым")
    private List<String> uris;

    private boolean unique;

    public boolean isValidPeriod() {
        return end != null && start != null && end.isAfter(start);
    }
}