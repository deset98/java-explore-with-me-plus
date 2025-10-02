package ru.practicum.ewm.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDto {

    @Size(max = 50)
    @NotBlank(message = "Имя категории не может быть пустым")
    private String name;
}