package ru.practicum.ewm.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompilationDto {

    private List<Long> events;

    private Boolean pinned;

    @Length(max = 50)
    private String title;
}