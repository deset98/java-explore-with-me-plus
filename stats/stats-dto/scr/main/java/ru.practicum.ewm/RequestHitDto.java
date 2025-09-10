package ru.practicum.ewm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestHitDto {
    @NotBlank(message = "Название сервиса не может быть пустым")
    private String app;

    @NotBlank(message = "uri не может быть пустым")
    private String uri;

    @NotBlank(message = "ip пользователя не может быть пустым")
    private String ip;

    @NotNull(message = "Дата и время, когда был совершен запрос к эндпоинту не может быть пустым")
    private LocalDateTime timestamp;
}