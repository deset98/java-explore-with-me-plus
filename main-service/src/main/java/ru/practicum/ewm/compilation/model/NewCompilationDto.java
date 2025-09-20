package ru.practicum.ewm.compilation.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    @NotEmpty
    private List<Long> events;
    @NotNull
    private Boolean pinned;
    @NotBlank
    private String title;
}
