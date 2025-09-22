package ru.practicum.ewm.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestValidDto {
    @NotNull
    List<Long> ids;
    @NotNull
    @Positive
    Integer from;
    @NotNull
    @Positive
    Integer size;
}
