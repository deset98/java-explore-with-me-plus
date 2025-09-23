package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryParamDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryParamDto paramCategoryDto);

    void deleteCategory(Long catId);

    CategoryDto updateCategory(Long catId, CategoryParamDto paramCategoryDto);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategory(Long catId);
}
