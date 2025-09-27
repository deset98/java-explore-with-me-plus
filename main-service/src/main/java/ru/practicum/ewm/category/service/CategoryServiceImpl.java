package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryRequestDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    // Admin API:
    @Override
    @Transactional
    public CategoryDto add(CategoryRequestDto newDto) {
        log.debug("Метод add(); categoryRequestDto: {}", newDto);

        this.validateCategoryNameExists(newDto.getName());

        Category category = categoryMapper.toEntity(newDto);
        category.setName(newDto.getName());
        category = categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public CategoryDto update(Long categoryId, CategoryRequestDto updDto) {
        log.debug("Метод update(); categoryId: {}, dto: {}", categoryId, updDto);

        this.validateCategoryNameExists(updDto.getName(), categoryId);

        Category category = this.findCategoryById(categoryId);
        category.setName(updDto.getName());
        category = categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public void delete(Long categoryId) {
        log.debug("Метод delete(); categoryId: {}", categoryId);

        this.validateCategoryExists(categoryId);

        if (eventRepository.existsByCategoryId(categoryId)) {
            throw new ConflictException("Category с id={} используется", categoryId);
        }

        categoryRepository.deleteById(categoryId);
    }

    // Public API:
    @Override
    public CategoryDto getById(Long categoryId) {
        log.debug("Метод getById(); categoryId: {}", categoryId);

        Category category = this.findCategoryById(categoryId);

        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getAll(int from, int size) {
        log.debug("Метод getAll(); from: {}, size: {}", from, size);

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        List<Category> categories = categoryRepository.findAll(pageable).getContent();

        return categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
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
                .orElseThrow(() -> new NotFoundException("Category id={} не найдена", categoryId));
    }
}