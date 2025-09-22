package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryParamDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto addCategory(CategoryParamDto categoryParamDto) {
        if (categoryRepository.existsByName(categoryParamDto.getName())) {
            throw new ConflictException("Категория с именем " + categoryParamDto.getName() + " уже существует");
        }

        Category category = new Category();
        category.setName(categoryParamDto.getName());
        category = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public void deleteCategory(Long catId) {
        if (!categoryRepository.existsById(catId)) {
            throw new NotFoundException("Категория с id=" + catId + " не найдена");
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto updateCategory(Long catId, CategoryParamDto categoryParamDto) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория с id=" + catId + " не найдена"));

        if (categoryRepository.existsByName(categoryParamDto.getName())) {
            throw new ConflictException("Категория с именем " + categoryParamDto.getName() + " уже существует");
        }

        category.setName(categoryParamDto.getName());
        category = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        List<Category> categories = categoryRepository.findCategoriesByOffsetAndLimit(from, size);
        return categories.stream()
                .map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория с id=" + catId + " не найдена"));
        return categoryMapper.toCategoryDto(category);
    }
}
