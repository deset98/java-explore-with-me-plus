package ru.practicum.ewm.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryParamDto {

    @NotBlank(message = "Имя категории не может быть пустым")
    @Size(max = 50)
    private String name;
}