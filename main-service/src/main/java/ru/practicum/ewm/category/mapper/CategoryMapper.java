package ru.practicum.ewm.category.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryRequestDto;
import ru.practicum.ewm.category.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

//    @Mapping(target = "id", ignore = true)
    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto dto);

    Category toEntity(CategoryRequestDto dto);
}