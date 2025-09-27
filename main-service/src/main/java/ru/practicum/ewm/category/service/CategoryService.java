package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryRequestDto;

import java.util.List;

public interface CategoryService {

    CategoryDto add(CategoryRequestDto categoryRequestDto);

    CategoryDto getById(Long categoryId);

    List<CategoryDto> getAll(int from, int size);

    CategoryDto update(Long catId, CategoryRequestDto categoryRequestDto);

    void delete(Long categoryId);
}