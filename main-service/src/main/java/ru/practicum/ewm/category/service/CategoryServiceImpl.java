package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryRequestDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto add(CategoryRequestDto dto) {
        log.debug("Метод add(); categoryRequestDto: {}", dto);

        this.validateCategoryNameExists(dto.getName());

        Category category = categoryMapper.toEntity(dto);
        category.setName(dto.getName());
        category = categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto getById(Long categoryId) {
        log.debug("Метод getById(); categoryId: {}", categoryId);

        Category category = this.findCategoryById(categoryId);

        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getAll(int from, int size) {
        log.debug("Метод getAll(); from: {}, size: {}", from, size);

        List<Category> categories = categoryRepository.findCategoriesByOffsetAndLimit(from, size);

        return categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long categoryId) {
        log.debug("Метод delete(); categoryId: {}", categoryId);

        this.validateCategoryExists(categoryId);

        try {
            categoryRepository.deleteById(categoryId);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Нельзя удалить Category id={}, с ней связаны Event", categoryId);
        }
    }

    @Override
    public CategoryDto update(Long categoryId, CategoryRequestDto dto) {
        log.debug("Метод update(); categoryId: {}, dto: {}", categoryId, dto);

        this.validateCategoryNameExists(dto.getName(), categoryId);

        Category category = this.findCategoryById(categoryId);
        category.setName(dto.getName());
        category = categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }


    private void validateCategoryNameExists(String name) {
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new ConflictException("Category name={} уже существует", name);
        }
    }

    private void validateCategoryNameExists(String name, Long categoryId) {
        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(name, categoryId)) {
            throw new ConflictException("Category name={} уже существует", name, categoryId);
        }
    }

    private void validateCategoryExists(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Category id={} не найдена", categoryId);
        }
    }

    private Category findCategoryById(Long categoryId) {
       return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Категория с id=" + categoryId + " не найдена"));
    }
}